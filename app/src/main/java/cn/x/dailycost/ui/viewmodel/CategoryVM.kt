package cn.x.dailycost.ui.viewmodel

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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

    // 偏移量
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
        viewModelScope.launch {
            categoryDao.getAllCategories().collect { items ->
                // 这种方式,增删改后 这里会实时查询
                Log.d("Debug", "CategoryVM load ")
                setState {
                    copy(
                        categories = items,
                        categoryMap = (items).associateBy { it.cid }
                    )
                }
            }
        }
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

    private fun finishDrag() {

        draggingIndex.value = -1
        draggingOffset.update { Offset.Zero }

        viewModelScope.launch {
            val updatedList = currentState().categories.mapIndexed { index, item ->
                item.copy(sort = index)
            }

            withContext(Dispatchers.IO) {
                categoryDao.updateAll(updatedList)
            }
        }
    }

}