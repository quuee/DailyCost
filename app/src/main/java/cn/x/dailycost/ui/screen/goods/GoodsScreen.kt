package cn.x.dailycost.ui.screen.goods


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.x.dailycost.R
import cn.x.dailycost.data.entity.CategoryEntity
import cn.x.dailycost.data.entity.GoodsItemEntity
import cn.x.dailycost.ui.components.AppIcons
import cn.x.dailycost.ui.components.CameraCaptureComponent
import cn.x.dailycost.ui.components.CustomizableBottomSheet
import cn.x.dailycost.ui.screen.main.MainEffect
import cn.x.dailycost.ui.screen.main.MainIntent
import cn.x.dailycost.ui.screen.main.MainScreenVM
import cn.x.dailycost.util.formatTimestamp
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodsScreen(
    mainVM: MainScreenVM = koinViewModel(),
    navController: NavController,
    gid: Long?,
) {

    val state by mainVM.state.collectAsState()

    val context = LocalContext.current

    var showCategoryBottomSheet by remember { mutableStateOf(false) }
    var showCategoryIconsBottomSheet by remember { mutableStateOf(false) }

    var showBuyDateDialog by remember { mutableStateOf(false) }
    var showEndDateDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        yearRange = 1970..2050, // 可设置年份范围
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Picker
    )

    // 相机card是否展开
    var cameraExpanded by remember { mutableStateOf(true) }

    var selectedCategory: CategoryEntity? by remember { mutableStateOf(null) }
    var selectIcon: AppIcons.IconItem? by remember { mutableStateOf(null) }
    var price: Double by remember { mutableStateOf(0.0) }
    // priceText 文本中间值
    var priceText by remember { mutableStateOf("") }
    var goodsName by remember { mutableStateOf("") }
    var remark by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf("") }
    var buyDateMillis by remember { mutableStateOf(0L) }
    var endDateMillis by remember { mutableStateOf(0L) }

    // 初始加载
    LaunchedEffect(Unit) {
        if (gid != null && gid > 0L) {
            // 获取物品数据
        }
    }

    LaunchedEffect(Unit) {
        mainVM.effect.collect { effect ->
            when (effect) {
                is MainEffect.ShowMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("添加物品") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = {
                            navController.popBackStack()
                        })
                    )
                },
                actions = {}
            )
        },
        bottomBar = {
            // 底部固定按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    enabled = gid != null,
                    onClick = {
//                        mainVM.processIntent(MainIntent.DeleteGoods(gid!!))

                    }) {
                    Text("Delete")
                }
                TextButton(onClick = {
                    mainVM.processIntent(
                        MainIntent.CreateGoods(
                            GoodsItemEntity(
                                gid = 0L,
                                goodsName = goodsName,
                                price = price,
                                cid = selectedCategory?.cid ?: 0L,
                                iconInt = selectIcon?.resInt ?: R.drawable.ic_package,
                                buyDate = buyDateMillis,
                                endDate = endDateMillis,
                                remark = remark,
                                realPictureUri = photoUri,
                            )
                        )
                    )
                    navController.popBackStack()
                }) {
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
                OutlinedTextField(
                    value = goodsName,
                    onValueChange = { newText ->
                        goodsName = newText
                    },
                    placeholder = { Text("输入物品名称") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,   // 聚焦时边框透明（去掉边框/下划线）
                        unfocusedBorderColor = Color.Transparent, // 未聚焦时边框透明（去掉边框/下划线）
                        disabledBorderColor = Color.Transparent   // 禁用状态边框透明
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                        ),

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
                            HorizontalDivider()
                            selectedCategory?.let {
                                Text(it.name)
                            }
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
                            HorizontalDivider()
                            selectIcon?.let {
                                Row() {
                                    // 使用 key 包裹 Icon，当 resInt 变化时强制重新创建 Icon 组件
                                    key(it.resInt) {
                                        Icon(
                                            painter = painterResource(it.resInt),
                                            modifier = Modifier
                                                .size(36.dp),
                                            contentDescription = null,
                                            tint = null, // 影响默认颜色
                                        )
                                    }
                                    Text(it.name)
                                }
                            }
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
                            HorizontalDivider()
                            OutlinedTextField(
                                value = priceText,
                                onValueChange = { newText ->
                                    // 2. 核心逻辑：使用正则表达式过滤非法输入
                                    // 允许输入数字、小数点，且保证只有一个小数点，小数点后最多保留2位
                                    val regex = Regex("^\\d*\\.?\\d{0,2}$")
                                    if (newText.isEmpty() || newText.matches(regex)) {
                                        priceText = newText
                                        // 3. 安全地更新你的 price 变量（处理空字符串的情况）
                                        price = newText.toDoubleOrNull() ?: 0.0
                                    }
                                },
//                                placeholder = { Text(price.toString()) },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,   // 聚焦时边框透明（去掉边框/下划线）
                                    unfocusedBorderColor = Color.Transparent, // 未聚焦时边框透明（去掉边框/下划线）
                                    disabledBorderColor = Color.Transparent   // 禁用状态边框透明
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
//                                        shape = RoundedCornerShape(8.dp),
                                    ),
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showBuyDateDialog = true })
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "购买日期",
                                modifier = Modifier.padding(8.dp)
                            )
                            HorizontalDivider()
                            Text(
                                text = formatTimestamp(buyDateMillis),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showEndDateDialog = true })
                                .aspectRatio(2f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "退役日期",
                                modifier = Modifier.padding(8.dp)
                            )
                            HorizontalDivider()
                            Text(
                                text = formatTimestamp(endDateMillis),
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
                    // 行数限制 - 核心配置
                    minLines = 4,                   // 至少显示4行
                    maxLines = 8,                   // 最多展开到6行后开始滚动
                    placeholder = { Text("请输入您的详细描述...") }, // 占位符提示
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,   // 聚焦时边框透明（去掉边框/下划线）
                        unfocusedBorderColor = Color.Transparent, // 未聚焦时边框透明（去掉边框/下划线）
                        disabledBorderColor = Color.Transparent   // 禁用状态边框透明
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                        ),
                )
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        // 3. 核心动画修饰符：当内容尺寸变化时，自动产生平滑的展开/折叠动画
                        .animateContentSize(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // 始终显示的标题部分
                        Row(
                            modifier = Modifier.clickable { cameraExpanded = !cameraExpanded }
                        ) {
                            Text(
                                text = "添加照片(可选)",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (cameraExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (cameraExpanded) "折叠" else "展开",
                            )
                        }
                        HorizontalDivider()

                        // 根据 expanded 状态，条件渲染隐藏的内容
                        if (cameraExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "选择照片",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                CameraCaptureComponent(
                                    modifier = Modifier.fillMaxSize(),
                                    onPhotoCaptured = { uri ->
                                        // 这里可以拿到拍好的照片 Uri，进行上传服务器等后续业务处理
                                        // content://cn.x.dailycost.debug.fileprovider/camera_cache/IMG_20260502_181434.jpg
                                        Log.d("DEBUG PHOTO", "onPhotoCaptured: $uri")
                                        photoUri = uri.path.toString()
                                    }
                                )
                            }

                        }

                    }
                }
            }
        }

        // 选分类
        CustomizableBottomSheet(
            isVisible = showCategoryBottomSheet,
            onDismissRequest = { showCategoryBottomSheet = false } // 关闭弹窗
        ) {
            CategorySheet(
                categories = state.categories,
                onSelect = {
                    selectedCategory = it
                },
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
                selectedIcon = selectIcon,
                onSelectIcon = { selectIcon = it },
                onClose = {
                    showCategoryIconsBottomSheet = false
                }
            )
        }

        // 日期选择
        if (showBuyDateDialog || showEndDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showBuyDateDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        // datePickerState.selectedDateMillis 获取选择的时间戳
                        if (showBuyDateDialog) {
                            buyDateMillis = datePickerState.selectedDateMillis ?: 0L
                            showBuyDateDialog = false
                        }
                        if (showEndDateDialog) {
                            endDateMillis = datePickerState.selectedDateMillis ?: 0L
                            showEndDateDialog = false
                        }

                    }) {
                        Text("确定")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showBuyDateDialog = false
                        showEndDateDialog = false
                    }) {
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
    categories: List<CategoryEntity>,
    onSelect: (CategoryEntity) -> Unit,
    onClose: () -> Unit,
) {
    var selectedCategory: CategoryEntity by remember {
        mutableStateOf(
            CategoryEntity(
                cid = 0L,
                name = "全部分类",
                color = Color.Transparent.toArgb(),
                sort = 1
            )
        )
    }

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
                            onSelect(selectedCategory)
                        },
                    color = if (category == selectedCategory)
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = category.name,
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
            Text("确认")
        }
    }
}

@Composable
private fun CategoryIconsSheet(
    selectedIcon: AppIcons.IconItem?,
    onSelectIcon: (AppIcons.IconItem) -> Unit,
    onClose: () -> Unit,
) {

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
                            width = if (selectedIcon == iconItem) 1.dp else 0.dp,
                            color = if (selectedIcon == iconItem) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = RoundedCornerShape(8.dp) // 可选：添加圆角
                        )
                        // 添加点击事件
                        .clickable {
                            onSelectIcon(iconItem)
                        },
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
        Button(
            onClick = {
                // 执行某些操作...
                // 然后关闭弹窗
                onClose()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("确认")
        }
    }
}

@Preview
@Composable
fun APPFF() {
//    GoodsScreen()
//    CategoryIconsSheet(onSelect = {}, onClose = {})
}