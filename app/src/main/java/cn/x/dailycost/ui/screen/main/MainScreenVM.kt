package cn.x.dailycost.ui.screen.main


import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import cn.x.dailycost.base.MviEffect
import cn.x.dailycost.base.MviIntent
import cn.x.dailycost.base.MviState
import cn.x.dailycost.base.MviViewModel
import cn.x.dailycost.R
import cn.x.dailycost.data.dao.CategoryDao
import cn.x.dailycost.data.dao.GoodsItemDao
import cn.x.dailycost.data.entity.CategoryEntity
import cn.x.dailycost.data.entity.GoodsItemEntity
import cn.x.dailycost.util.getDaysDifference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.Instant

// State - UI 状态
data class MainState(
    override val isLoading: Boolean = false,
    override val error: String? = null,

    // 总资产
    val asset: Double = 0.0,
    // 每日成本总和
    val everyDayCostTotal: Double = 0.0,
    // 用于编辑或新增页面的物品信息 默认参数
    val goodsFormData: GoodsItemEntity = GoodsItemEntity(
        gid = 0L,
        goodsName = "",
        price = 0.0,
        cid = 0L,
        iconInt = R.drawable.ic_package,
        remark = "",
        realPictureUri = ""
    ),
    // 查询获得物品集合
    val goodsItems: List<GoodsItemEntity> = emptyList(),
    // 查询获得分类集合
    val categories: List<CategoryEntity> = emptyList(),
    val categoryMap: Map<Long, CategoryEntity> = emptyMap(),

    // 这里想展示可供选择的排序字段,选择完成后 有个实际字段用于排序
    val sortFiledList: List<String> = listOf("购入时间", "创建时间", "退役时间", "价格"),
    val selectSortField: String = "购入时间",

    // 0 升序; 1 降序
    val selectSortOrder: Int = 1

) : MviState

// Intent - 用户操作
sealed class MainIntent : MviIntent {
    data object SyncRemote : MainIntent()
    data class ToggleGoods(val gid: Long) : MainIntent()
    data class CreateGoods(val goodsItem: GoodsItemEntity) : MainIntent()
    data class DeleteGoods(val goodsItem: GoodsItemEntity) : MainIntent()
    data class UpdateGoods(val goodsItem: GoodsItemEntity) : MainIntent()
    data class ChangeGoodsAttr(
        val goodsName: String? = null,
        val price: Double? = null,
        val cid: Long? = null,
        val iconInt: Int? = null,
        val buyDateMillis: Long? = null,
        val retireDateMillis: Long? = null,
        val photoUri: String? = null,
        val remark: String? = null,
        val recoverHealthMoney: Double? = null,
    ) :
        MainIntent()

    data class GetGoodsById(val gid: Long) : MainIntent()

    data class SearchGoods(
        val name: String?,
        val cid: Long?,
        val sortField: String?,
        val sortOrder: Int?
    ) : MainIntent()

    data class CreateCategory(val category: CategoryEntity) : MainIntent()
    data class DeleteCategory(val category: CategoryEntity) : MainIntent()
    data class UpdateCategory(val category: CategoryEntity) : MainIntent()

}

// Effect：一次性事件
sealed class MainEffect : MviEffect {
    data class ShowMessage(val message: String) : MainEffect()
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainScreenVM(
    private val goodsItemDao: GoodsItemDao,
    private val categoryDao: CategoryDao,
) : MviViewModel<MainState, MainIntent, MainEffect>(MainState()) {

    // 用于触发搜索的内部 Flow（支持防抖）
    private val searchGoodsTriggerFlow =
        MutableSharedFlow<SearchGoodsParams>(extraBufferCapacity = 1)

    init {
        viewModelScope.launch {
            categoryDao.getAllCategories().collect { items ->
                setState {
                    copy(
                        categories = listOf(
                            CategoryEntity(
                                cid = 0L,
                                name = "全部分类",
                                color = Color(0XFFF8F1E4).toArgb(),
                                sort = 1
                            )
                        ) + items,
                        categoryMap = (listOf(
                            CategoryEntity(
                                cid = 0L,
                                name = "全部分类",
                                color = Color(0XFFF8F1E4).toArgb(),
                                sort = 1
                            )
                        ) + items).associateBy { it.cid }
                    )
                }
            }
        }

        // 监听搜索触发，并自动收集最新的查询结果
        viewModelScope.launch {
            searchGoodsTriggerFlow
                .debounce(300) // 300毫秒防抖，避免频繁查询
                .distinctUntilChanged() // 如果搜索条件没变，不重复查询
                .flatMapLatest { params ->
                    queryGoods(
                        params.name,
                        params.cid,
                        params.sortField,
                        params.sortOrder
                    ).catch { e ->
                        // 捕获数据库查询异常
                        setState { copy(isLoading = false, error = e.message) }
                    }
                }
                .collect { items ->
                    Log.d("MainScreenVM", "${items.size}: ")
                    // 查询成功，更新 State
                    setState {
                        copy(
                            goodsItems = items,
                            asset = items.sumOf { goods ->
                                if (goods.recoverHealthMoney > 0 && goods.retireDate > 0) {
                                    0.0
                                } else {
                                    goods.price
                                }
                            },
                            everyDayCostTotal = items.sumOf { goods ->
                                // 出二手, 已出二手使用成本计算 (购入价格-回血价格)/(购入日期-出售(退役)日期)
                                if (goods.recoverHealthMoney > 0 && goods.retireDate > 0) {
//                                    val usedDays =
//                                        getDaysDifference(goods.buyDate, goods.retireDate)
//                                    (goods.price - goods.recoverHealthMoney) / usedDays
                                    // 不计入当前
                                    0.0
                                } else {
                                    // 持有成本计算 价格/(当前日期-购入日期)
                                    val nowDate = Instant.now().toEpochMilli()
                                    val usedDays = getDaysDifference(goods.buyDate, nowDate)
                                    // 注意处理 usedDays 为 0 的情况，防止除以 0 报错
                                    if (usedDays > 0) goods.price / usedDays else 0.0
                                }

                            },
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }

        viewModelScope.launch {
            load()
        }

    }

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.SyncRemote -> syncRemote()
            is MainIntent.ToggleGoods -> toggleGoods(intent.gid)
            is MainIntent.DeleteGoods -> deleteGoods(intent.goodsItem)
            is MainIntent.CreateGoods -> createGoods(intent.goodsItem)
            is MainIntent.UpdateGoods -> updateGoods(intent.goodsItem)
            is MainIntent.ChangeGoodsAttr -> changeGoodsAttr(
                intent.goodsName,
                intent.price,
                intent.cid,
                intent.iconInt,
                intent.buyDateMillis,
                intent.retireDateMillis,
                intent.photoUri,
                intent.remark,
                intent.recoverHealthMoney,
            )

            is MainIntent.GetGoodsById -> getGoodsById(intent.gid)
            is MainIntent.SearchGoods -> {
                // 触发搜索（进入 Flow 管道）
                setState {
                    copy(
                        isLoading = true,
                    )
                }
                intent.sortField?.let {
                    setState {
                        copy(
                            selectSortField = intent.sortField
                        )
                    }
                }
                intent.sortOrder?.let {
                    setState {
                        copy(
                            selectSortOrder = intent.sortOrder
                        )
                    }
                }
                searchGoodsTriggerFlow.emit(
                    SearchGoodsParams(
                        intent.name,
                        intent.cid,
                        state.value.selectSortField,
                        state.value.selectSortOrder
                    )
                )
            }

            is MainIntent.CreateCategory -> createCategory(intent.category)
            is MainIntent.DeleteCategory -> deleteCategory(intent.category)
            is MainIntent.UpdateCategory -> updateCategory(intent.category)

        }
    }

    // 将 UI 的排序字段映射为数据库实际字段
    private fun mapSortFieldToDb(uiField: String?): String {
        return when (uiField) {
            "创建时间" -> "createDate"
            "购入时间" -> "buyDate"
            "退役时间" -> "retireDate"
            "价格" -> "price"
            else -> "buyDate" // 默认排序字段
        }
    }

    private fun syncRemote() {}
    private fun toggleGoods(gid: Long) {}
    private suspend fun deleteGoods(goodsItem: GoodsItemEntity) {
        goodsItemDao.delete(goodsItem)
        sendEffect(MainEffect.ShowMessage("删除成功"))
    }

    private suspend fun updateGoods(goodsItem: GoodsItemEntity) {
        goodsItemDao.update(goodsItem)
    }

    private fun changeGoodsAttr(
        goodsName: String?,
        price: Double?,
        cid: Long?,
        iconInt: Int?,
        buyDateMillis: Long?,
        retireDateMillis: Long?,
        photoUri: String?,
        remark: String?,
        recoverHealthMoney: Double?,
    ) {
        // todo 能合并嘛
        goodsName?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(goodsName = goodsName)
                )
            }
        }
        price?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(price = price)
                )
            }
        }
        cid?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(cid = cid)
                )
            }
        }
        iconInt?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(iconInt = iconInt)
                )
            }
        }
        buyDateMillis?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(buyDate = buyDateMillis)
                )
            }
        }
        retireDateMillis?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(retireDate = retireDateMillis)
                )
            }
        }
        photoUri?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(realPictureUri = photoUri)
                )
            }
        }
        remark?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(remark = remark)
                )
            }
        }
        recoverHealthMoney?.let {
            setState {
                copy(
                    goodsFormData = goodsFormData.copy(recoverHealthMoney = recoverHealthMoney)
                )
            }
        }
    }

    private suspend fun createGoods(goodsItem: GoodsItemEntity) {
        if (goodsItem.price <= 0) {
//            sendEffect(MainEffect.ShowMessage("购入价格必填"))
            return
        }
        if (goodsItem.buyDate <= 0) {
//            sendEffect(MainEffect.ShowMessage("购入时间必填"))
            return
        }
        if (goodsItem.goodsName.isBlank()) {
//            sendEffect(MainEffect.ShowMessage("名称必填"))
            return
        }
        goodsItemDao.insert(goodsItem)
    }

    private suspend fun load() {
        // 触发搜索（进入 Flow 管道）
        // todo 得保存这几个条件
        // SPUtil
        setState { copy(isLoading = true) }
        searchGoodsTriggerFlow.emit(
            SearchGoodsParams(null, 0L, state.value.selectSortField, state.value.selectSortOrder)
        )
    }

    private suspend fun getGoodsById(gid: Long) {
        val temp = goodsItemDao.getById(gid)
        setState { copy(goodsFormData = temp) }
    }

    private fun queryGoods(
        name: String?,
        cid: Long?,
        sortField: String?,
        sortOrder: Int?
    ): Flow<List<GoodsItemEntity>> {
        val sqlBuilder = StringBuilder("SELECT * FROM goods_item WHERE 1=1")
        val args = mutableListOf<Any>()

        // 物品名称模糊查询
        if (!name.isNullOrBlank()) {
            sqlBuilder.append(" AND goodsName LIKE ?")
            args.add("%$name%")
        }

        // 分类 ID 精确查询
        if (cid != null && cid > 0L) {
            sqlBuilder.append(" AND cid = ?")
            args.add(cid)
        }

        // 动态排序（映射 UI 字段到数据库字段）
        val dbSortField = mapSortFieldToDb(sortField)
        val safeSortOrder = if (sortOrder == 0) "ASC" else "DESC"

        // 白名单二次校验，绝对保证 SQL 安全
        val validDbFields = listOf("buyDate", "createDate", "retireDate", "price")
        val finalSortField = if (dbSortField in validDbFields) dbSortField else "buyDate"

        sqlBuilder.append(" ORDER BY $finalSortField $safeSortOrder")

        val sql = sqlBuilder.toString()
        val query = SimpleSQLiteQuery(sql, args.toTypedArray())
        return goodsItemDao.query(query)
    }

    // 内部数据类，用于传递搜索参数
    private data class SearchGoodsParams(
        val name: String?,
        val cid: Long?,
        val sortField: String?,
        val sortOrder: Int?
    )


    private suspend fun createCategory(category: CategoryEntity) {
        // todo 空校验
        categoryDao.insert(category)
    }

    private suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.update(category)
    }

    private suspend fun deleteCategory(category: CategoryEntity) {
        categoryDao.delete(category)
    }
}