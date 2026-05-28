package cn.x.dailycost.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import kotlinx.coroutines.launch

/**
 * 一个可复用的、支持自定义内容的 ModalBottomSheet 组件
 *
 * @param isVisible 控制底部面板显示与隐藏的状态
 * @param onDismissRequest 当用户点击遮罩层或滑动关闭面板时的回调
 * @param content 底部面板中要显示的自定义内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizableBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    // 创建 BottomSheet 的状态
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // 跳过半展开状态，直接完全展开
    )
    // 添加协程作用域用于动画控制
    val coroutineScope = rememberCoroutineScope()

    // 监听 isVisible 状态变化，并相应地显示或隐藏面板
    LaunchedEffect(isVisible) {
        coroutineScope.launch {
            if (isVisible) {
                sheetState.show()
            } else {
                sheetState.hide()
            }
        }
    }

    // 只有当面板可见时，才将 ModalBottomSheet 添加到组合中
    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                // 用户关闭面板时，先更新状态，再通知外部
                onDismissRequest()
            },
            sheetState = sheetState,
            // 可以在这里自定义拖动手柄，设为 null 则不显示
            dragHandle = null
        ) {
            // 将外部传入的内容放入面板中
            content()
        }
    }
}