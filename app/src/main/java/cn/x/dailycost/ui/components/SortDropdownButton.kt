package cn.x.dailycost.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDropdownButton(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonHeightPx by remember { mutableStateOf(0) }
    var buttonWidthPx by remember { mutableStateOf(0) }

    Box(
    ) {
        // 按钮
        TextButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    buttonWidthPx = (coordinates.size.width * 1.65f).toInt()
                    buttonHeightPx = coordinates.size.height
                },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = selectedOption,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // 下拉选择框
        if (expanded) {
            DropdownMenuPopup(
                options = options,
                selectedOption = selectedOption,
                onOptionClick = { option ->
                    onOptionSelected(option)
                    expanded = false
                },
                onDismiss = { expanded = false },
                buttonWidthPx = buttonWidthPx,
                buttonHeightPx = buttonHeightPx
            )
        }
    }
}

@Composable
fun DropdownMenuPopup(
    options: List<String>,
    selectedOption: String,
//    orderOption: List<String>,
//    selectOrderOption: String,
    onOptionClick: (String) -> Unit,
    onDismiss: () -> Unit,
    buttonWidthPx: Int,
    buttonHeightPx: Int
) {
    val density = LocalDensity.current

    // 计算偏移量 - 修正版
    val offsetX = 0 // 左对齐
    val offsetY = with(density) {
        // 将 Dp 转换为像素值
        (buttonHeightPx + 4.dp.roundToPx())
    }

    Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset(offsetX, offsetY),
        onDismissRequest = onDismiss,
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        // 带动画的下拉框
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(150)) +
                    slideInVertically(
                        initialOffsetY = { -it / 3 },
                        animationSpec = tween(200, easing = FastOutSlowInEasing)
                    ) +
                    scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(200, easing = FastOutSlowInEasing)
                    ),
            exit = fadeOut(animationSpec = tween(120)) +
                    slideOutVertically(
                        targetOffsetY = { -it / 3 },
                        animationSpec = tween(150)
                    ) +
                    scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(150)
                    )
        ) {
            Card(
                modifier = Modifier
                    .width(with(density) { buttonWidthPx.toDp() })
                    .shadow(4.dp, shape = RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text("排序方式", style = MaterialTheme.typography.labelSmall)
                    options.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (option == selectedOption)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = { onOptionClick(option) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (option == selectedOption)
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                    else
                                        androidx.compose.ui.graphics.Color.Transparent
                                ),
                            trailingIcon = {
                                if (option == selectedOption) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null
                                    )
                                }
                            }
                        )

                        if (index != options.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        }
                    }
                    Text("排序顺序", style = MaterialTheme.typography.labelSmall)
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "降序",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "升序",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }
            }
        }
    }
}

