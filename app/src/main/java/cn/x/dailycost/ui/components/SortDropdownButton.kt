package cn.x.dailycost.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDropdownButton(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit = {},
    selectOrderOption: Int = 1,
    onOrderSelected: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonHeightPx by remember { mutableStateOf(0) }
    var buttonWidthPx by remember { mutableStateOf(0) }

    Box {
        TextButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    // 适当增加宽度，给下拉菜单留出空间
                    buttonWidthPx = (coordinates.size.width * 1.5f).toInt()
                    buttonHeightPx = coordinates.size.height
                }
                // 增加点击时的缩放动画，提升现代感
                .scale(if (expanded) 0.95f else 1f)
                .animateContentSize(),
            // 去除默认背景色和容器间距
            colors = ButtonDefaults.textButtonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = selectedOption,
                    color = MaterialTheme.colorScheme.onSurface, // 使用常规文字颜色
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (expanded) FontWeight.Medium else FontWeight.Normal
                )
                // 添加箭头图标，并根据展开状态旋转
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(if (expanded) 180f else 0f)
                )
            }
        }
        // 下拉选择框 (保持你原有的逻辑)
        if (expanded) {
            DropdownMenuPopup(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { option ->
                    onOptionSelected(option)
                    expanded = false
                },
                selectOrderOption = selectOrderOption,
                onOrderSelected = { onOrderSelected(it) },
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
    onOptionSelected: (String) -> Unit,
    selectOrderOption: Int,
    onOrderSelected: (Int) -> Unit,
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
                            onClick = { onOptionSelected(option) },
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
                        onClick = { onOrderSelected(1) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        trailingIcon = {
                            if (1 == selectOrderOption) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null
                                )
                            }
                        }

                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "升序",
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        onClick = { onOrderSelected(0) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        trailingIcon = {
                            if (0 == selectOrderOption) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null
                                )
                            }
                        }

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SPPP(){
    SortDropdownButton(
        selectedOption = "创建时间",
        options = listOf("创建时间","购入时间", "退役时间", "价格"),
        onOptionSelected = { },
        selectOrderOption = 1,
        onOrderSelected = {  }
    )
}

