package cn.x.dailycost.ui.viewmodel

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

data class CategoryState(
    override val isLoading: Boolean = false,
    override val error: String? = null,

    // 查询获得分类集合
    val categories: List<CategoryEntity> = emptyList(),
    // 将分类集合转map
    val categoryMap: Map<Long, CategoryEntity> = emptyMap(),

    // 拖动项索引
    val draggingIndex: Int = -1,
    // 偏移量
    val draggingOffset: Offset = Offset.Zero,

    ) : MviState

sealed class CategoryIntent : MviIntent {
    data object ErrorDismissed : CategoryIntent()

    data class CreateCategory(val category: CategoryEntity) : CategoryIntent()
    data class DeleteCategory(val category: CategoryEntity) : CategoryIntent()
    data class UpdateCategory(val category: CategoryEntity) : CategoryIntent()
}

sealed class CategoryEffect : MviEffect {
    data class ShowMessage(val message: String) : CategoryEffect()
    data object NavigateToHome : CategoryEffect()
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CategoryVM(
    private val categoryDao: CategoryDao,
) : MviViewModel<CategoryState, CategoryIntent, CategoryEffect>(CategoryState()) {

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

            else -> {}
        }
    }

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
}