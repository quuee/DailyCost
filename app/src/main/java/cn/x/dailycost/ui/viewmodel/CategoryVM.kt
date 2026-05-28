package cn.x.dailycost.ui.viewmodel

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.viewModelScope
import cn.x.dailycost.base.MviEffect
import cn.x.dailycost.base.MviIntent
import cn.x.dailycost.base.MviState
import cn.x.dailycost.base.MviViewModel
import cn.x.dailycost.data.dao.CategoryDao
import cn.x.dailycost.data.entity.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

data class CategoryState(
    override val isLoading: Boolean = false,
    override val error: String? = null,

    // 查询获得分类集合
    val categories: List<CategoryEntity> = emptyList(),
    // 将分类集合转map
    val categoryMap: Map<Long, CategoryEntity> = emptyMap(),

    ) : MviState

sealed class CategoryIntent : MviIntent {
    data object ErrorDismissed : CategoryIntent()
    data object Load : CategoryIntent()

    data class CreateCategory(val category: CategoryEntity) : CategoryIntent()
    data class DeleteCategory(val category: CategoryEntity) : CategoryIntent()
    data class UpdateCategory(val category: CategoryEntity) : CategoryIntent()

    data class StartDrag(val index: Int) : CategoryIntent()
    data class UpdateDrag(val offset: Offset) : CategoryIntent()
    data class CalculateDeltaY(
        val someoneIndex: Int, // 某项索引
        val someoneTopY: Float,// 某项头部Y坐标
        val someoneBottomY: Float //某项底部Y坐标
    ) : CategoryIntent()

    data object FinishDrag : CategoryIntent()
}

sealed class CategoryEffect : MviEffect {
    data class ShowMessage(val message: String) : CategoryEffect()
    data object NavigateToHome : CategoryEffect()
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CategoryVM(
    private val categoryDao: CategoryDao,
) : MviViewModel<CategoryState, CategoryIntent, CategoryEffect>(CategoryState()) {

    // 这些变量与业务数据无关,且更新频繁(60/秒)
    // 拖拽index
    val draggingIndex = MutableStateFlow(-1)

    // 拖拽偏移量
    val draggingOffset = MutableStateFlow(Offset.Zero)

    override suspend fun handleIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.ErrorDismissed -> {
                setState {
                    copy(
                        error = null,
                    )
                }
            }
            is CategoryIntent.Load -> load()
            is CategoryIntent.CreateCategory -> createCategory(intent.category)
            is CategoryIntent.DeleteCategory -> deleteCategory(intent.category)
            is CategoryIntent.UpdateCategory -> updateCategory(intent.category)

            is CategoryIntent.StartDrag -> startDrag(intent.index)
            is CategoryIntent.UpdateDrag -> updateDrag(intent.offset)
            is CategoryIntent.CalculateDeltaY -> calculateDeltaY(
                intent.someoneIndex,
                intent.someoneTopY,
                intent.someoneBottomY
            )

            is CategoryIntent.FinishDrag -> finishDrag()

        }
    }

    init {
        load()
    }

    private fun load() {
        // 手动 collect + setState(命令式)
        viewModelScope.launch {
            categoryDao.getAllCategories()
                .catch { e ->
                    // 必须捕获异常，否则 Flow 静默终止，UI 永远停在 loading
                    setState { copy(isLoading = false, error = e.message) }
                }
                .collect { items ->
                    // 仅在列表引用变化时重建（Room Flow 在数据不变时不会 emit）
                    // Room 的 Flow 本身已保证这一点，但加一层防御更安全
                    val newMap =
                        if (items !== state.value.categories) items.associateBy { it.cid } else state.value.categoryMap
                    setState {
                        copy(
                            categories = items,
                            categoryMap = newMap,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }

        /**
         * 为什么不建议用纯 stateIn（声明式） 替代 BaseViewModel 的 StateFlow？stateIn可以用在一些简单场景
         * | 痛点 | 说明 |
         * | :--- | :--- |
         * | 用户交互难以表达 | 用户的点击、输入是离散事件，不是连续流。要接入 `stateIn` 必须额外创建 `MutableSharedFlow` 来承载 Intent，再与数据源 `combine`，代码比原版复杂 3 倍以上 |
         * | 瞬时状态丢失 | Loading、Error 等瞬时状态在 `combine` 中极难管理。当数据源 emit 新值时，如何保留当前的 Loading 状态？需要极其复杂的 `scan` 或状态机逻辑 |
         * | 性能陷阱 | `stateIn` + `combine` 在任何上游变化时都会触发全量重算。而 `setState` 是精准局部更新，只修改受影响的字段 |
         * | 调试困难 | 纯声明式链路在出问题时需要追踪整条 Flow 链；而 `setState` 可以通过堆栈直接定位到哪个 Intent 触发了哪次更新 |
         */

        /**
         * | 维度 | 手动 collect + setState | stateIn |
         * | :--- | :--- | :--- |
         * | 生命周期管理 | 依赖 viewModelScope，VM 存活即活跃 | `WhileSubscribed` 自动暂停/恢复上游 |
         * | 初始状态 | 需手动设置 | `initialValue` 声明式指定 |
         * | 多源组合 | 需嵌套 collect 或额外变量 | 天然支持 `combine` |
         * | 代码量 | 较多 | 更少、更声明式 |
         */

    }


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

    private fun startDrag(index: Int) {
        Log.d("Debug", "开始位置：${index}")
        draggingIndex.value = index
    }

    // 更新拖动位置
    private fun updateDrag(offset: Offset) {
//        Log.d("CategoryVM updateDrag", "offset.x: ${offset.x} ,offset.y:${offset.y}")
        val current = draggingOffset.value
        draggingOffset.value = Offset(
            x = current.x + offset.x,
            y = current.y + offset.y // 往上减，往下加
        )
    }

    // 计算和交换位置
    private fun calculateDeltaY(
        someoneIndex: Int, // 某项索引
        someoneTopY: Float,// 某项头部Y坐标
        someoneBottomY: Float //某项底部Y坐标
    ) {
//        Log.d("CategoryVM calculateDeltaY", "selected index：${someoneIndex}")
        val threshold = 1.2
        val currentList = currentState().categories.toMutableList()
        // 通过计算，得出要交换的目标位置 ,和此元素的下面元素交换
        if (draggingOffset.value.y > ((someoneBottomY - someoneTopY) / threshold)) {
            if (someoneIndex == currentState().categories.size - 1) {
                return
            }
            Collections.swap(currentList, someoneIndex, someoneIndex + 1)
            setState {
                copy(
                    categories = currentList,
                )
            }
            draggingOffset.update { Offset.Zero }
            draggingIndex.value = someoneIndex + 1
        }

        // 和和此元素的上面元素交换
        if (draggingOffset.value.y < -((someoneBottomY - someoneTopY) / threshold)) {
            if (someoneIndex == 0) {
                return
            }
            Collections.swap(currentList, someoneIndex, someoneIndex - 1)
            setState {
                copy(
                    categories = currentList,
                )
            }
            draggingOffset.update { Offset.Zero }
            draggingIndex.value = someoneIndex - 1
        }
    }

    private suspend fun finishDrag() {
        draggingIndex.value = -1
        draggingOffset.update { Offset.Zero }

        val updatedList = currentState().categories.mapIndexed { index, item ->
            item.copy(sort = index)
        }
        categoryDao.updateAll(updatedList)
//        viewModelScope.launch {
//            val updatedList = currentState().categories.mapIndexed { index, item ->
//                item.copy(sort = index)
//            }
//            withContext(Dispatchers.IO) {
//                categoryDao.updateAll(updatedList)
//            }
//        }
    }

}