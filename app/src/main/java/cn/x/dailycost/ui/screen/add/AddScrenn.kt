package cn.x.dailycost.ui.screen.add

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.x.dailycost.ui.components.AppIcons
import cn.x.dailycost.ui.components.CustomizableBottomSheet

@Composable
fun AddScreen(
    navController: NavController
) {
    var goodsName by remember { mutableStateOf("") }
    var remark by remember { mutableStateOf("") }

    var showCategoryBottomSheet by remember { mutableStateOf(false) }
    val categories = listOf(
        "全部分类",
        "电子产品",
        "服装",
        "图书",
        "家居用品",
        "美妆",
        "虚拟物品",
        "运动户外",
        "药品保健",
        "零食饮料"
    )

    var showCategoryIconsBottomSheet by remember { mutableStateOf(false) }

    var showDateDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        yearRange = 1970..2050, // 可设置年份范围
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Picker
    )

    // 换scaffold ？
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            // 底部固定按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {}) {
                    Text("Delete")
                }
                TextButton(onClick = {}) {
                    Text("Save")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // 输入名称
            item {
                TextField(
                    value = goodsName,
                    onValueChange = { newText ->
                        goodsName = newText
                    },
                    label = { Text("物品名称") },
                    placeholder = { Text("物品名称") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 选择分类 图标 拍照 购入价格 购入日期 退役日期  过保日期
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = (LocalWindowInfo.current.containerSize.height).dp),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showCategoryBottomSheet = true })
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "选择分类",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showCategoryIconsBottomSheet = true })
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "选择图标",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "购买价格",
                                modifier = Modifier.padding(8.dp)
                            )
                            TextField(
                                modifier = Modifier.padding(4.dp),
                                value = "",
                                onValueChange = {}
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showDateDialog = true })
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "购买日期",
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "2025-12-2",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showDateDialog = true })
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "退役日期",
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "2026-12-2",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            // 备注
            item {
                TextField(
                    value = remark,              // 绑定的文本值
                    onValueChange = { remark = it }, // 更新文本的回调
                    modifier = Modifier
                        .fillMaxWidth()             // 撑满宽度
                        .padding(16.dp),            // 添加一些外边距
                    // 行数限制 - 核心配置
                    minLines = 4,                   // 至少显示4行
                    maxLines = 6,                   // 最多展开到6行后开始滚动
                    // 样式定制
                    label = { Text("详细描述") },    // 输入框标签
                    placeholder = { Text("请输入您的详细描述...") }, // 占位符提示
                )
            }
        }

        // 选分类
        CustomizableBottomSheet(
            isVisible = showCategoryBottomSheet,
            onDismissRequest = { showCategoryBottomSheet = false } // 关闭弹窗
        ) {
            CategorySheet(
                categories = categories,
                onSelect = {},
                onClose = {
                    showCategoryBottomSheet = false
                }
            )
        }
        // 选图标
        CustomizableBottomSheet(
            isVisible = showCategoryIconsBottomSheet,
            onDismissRequest = { showCategoryIconsBottomSheet = false } // 关闭弹窗
        ) {
            CategoryIconsSheet(
                onSelect = {},
                onClose = {
                    showCategoryIconsBottomSheet = false
                }
            )
        }

        // 日期选择
        if (showDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showDateDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        // datePickerState.selectedDateMillis 获取选择的时间戳
                        showDateDialog = false
                    }) {
                        Text("确定")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDateDialog = false }) {
                        Text("取消")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
private fun CategorySheet(
    categories: List<String>,
    onSelect: (String) -> Unit,
    onClose: () -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("全部分类") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            "选择分类",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        HorizontalDivider()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth(),
//                .heightIn(max = (LocalWindowInfo.current.containerSize.height).dp),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            selectedCategory = category

                        },
                    color = if (category == selectedCategory)
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        color = if (category == selectedCategory)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 自定义按钮
        Button(
            onClick = {
                // 执行某些操作...
                // 然后关闭弹窗
                onClose()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("确认操作")
        }
    }
}

@Composable
private fun CategoryIconsSheet(
    onSelect: (String) -> Unit,
    onClose: () -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            "选择图标",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        HorizontalDivider()
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth(),
//                .heightIn(max = (LocalWindowInfo.current.containerSize.height).dp),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(AppIcons.allIcons) { iconItem ->

                Column(
                    modifier = Modifier
                        // 添加内边距，让边框和图标之间有间隙
                        .padding(4.dp)
                        // 根据 isSelected 状态动态设置边框
                        .border(
                            width = if (isSelected) 1.dp else 0.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = RoundedCornerShape(8.dp) // 可选：添加圆角
                        )
                        // 添加点击事件
                        .clickable { isSelected = !isSelected },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(iconItem.resInt),
                        modifier = Modifier
                            .size(36.dp),
                        contentDescription = null,
                        tint = null, // 影响默认颜色
                    )
                    Text(iconItem.name, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun APPFF() {
    AddScreen(rememberNavController())
//    CategoryIconsSheet(onSelect = {}, onClose = {})
}