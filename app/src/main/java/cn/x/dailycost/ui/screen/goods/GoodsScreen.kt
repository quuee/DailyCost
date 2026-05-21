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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import androidx.navigation.NavController
import cn.x.dailycost.data.entity.CategoryEntity
import cn.x.dailycost.data.entity.GoodsItemEntity
import cn.x.dailycost.ui.components.AppIcons
import cn.x.dailycost.ui.components.CameraCaptureComponent
import cn.x.dailycost.ui.components.CustomizableBottomSheet
import cn.x.dailycost.ui.components.DecimalInputField
import cn.x.dailycost.ui.screen.main.MainEffect
import cn.x.dailycost.ui.screen.main.MainIntent
import cn.x.dailycost.ui.screen.main.MainScreenVM
import cn.x.dailycost.ui.screen.my.ReceiveContent
import cn.x.dailycost.ui.screen.my.SendContent
import cn.x.dailycost.ui.theme.GradientStart
import cn.x.dailycost.util.formatTimestamp
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodsScreen(
    mainVM: MainScreenVM = koinViewModel(),
    navController: NavController,
    gid: Long?,
) {

    val state by mainVM.state.collectAsState()
    val goods = state.goodsFormData

    val context = LocalContext.current

    //分类
    var showCategoryBottomSheet by remember { mutableStateOf(false) }
    var showCategoryIconsBottomSheet by remember { mutableStateOf(false) }

    // 购入日期
    var showBuyDateBottomSheet by remember { mutableStateOf(false) }
    var selectedBuyDate by remember { mutableStateOf(LocalDate.now()) }
    // 退役日期
    var showRetireDateBottomSheet by remember { mutableStateOf(false) }
    var selectedRetireDate by remember { mutableStateOf(LocalDate.now()) }


    // 相机card是否展开
    var cameraExpanded by remember { mutableStateOf(true) }

    // 表单属性
    var price by remember { mutableDoubleStateOf(0.0) }
    var recoverHealthMoney by remember { mutableDoubleStateOf(0.0) }


    // 初始加载
    LaunchedEffect(gid) {
        if (gid != null && gid > 0L) {
            // 获取物品数据
            mainVM.processIntent(MainIntent.GetGoodsById(gid))
        }
    }

    LaunchedEffect(Unit) {
        mainVM.effect.collect { effect ->
            when (effect) {
                is MainEffect.ShowMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is MainEffect.NavigateToHome -> {
                    navController.popBackStack()
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
                    enabled = gid != null && gid > 0L,
                    onClick = {
                         mainVM.processIntent(MainIntent.DeleteGoods(goods))
                    }) {
                    Text("Delete")
                }
                TextButton(
//                    enabled = goods.goodsName.isNotEmpty() && goods.price > 0 && goods.buyDate > 0,
                    onClick = {
                        if (state.goodsFormData.gid > 0L) {
                            // 编辑
                            mainVM.processIntent(
                                MainIntent.UpdateGoods(
                                    GoodsItemEntity(
                                        gid = goods.gid,
                                        goodsName = goods.goodsName,
                                        price = goods.price,
                                        cid = goods.cid,
                                        iconInt = goods.iconInt,
                                        buyDate = goods.buyDate,
                                        retireDate = goods.retireDate,
                                        remark = goods.remark,
                                        realPictureUri = goods.realPictureUri,
                                        recoverHealthMoney = goods.recoverHealthMoney,
                                    )
                                )
                            )
                        } else {
                            // 新增
                            mainVM.processIntent(
                                MainIntent.CreateGoods(
                                    GoodsItemEntity(
                                        gid = 0L,
                                        goodsName = goods.goodsName,
                                        price = goods.price,
                                        cid = goods.cid,
                                        iconInt = goods.iconInt,
                                        buyDate = goods.buyDate,
                                        retireDate = goods.retireDate,
                                        remark = goods.remark,
                                        realPictureUri = goods.realPictureUri,
                                        recoverHealthMoney = goods.recoverHealthMoney,
                                    )
                                )
                            )
                        }
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
                    value = goods.goodsName,
                    onValueChange = { newText ->
                        mainVM.processIntent(
                            MainIntent.ChangeGoodsAttr(
                                goodsName = newText,
                            )
                        )
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
                    isError = goods.goodsName.isBlank(),
//                    supportingText = { if (goods.goodsName.isBlank()) Text("不能为空") }
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
                            if (goods.cid > 0L) {
                                val result = state.categories.find { it.cid == goods.cid }
                                Text(result?.name ?: "全部分类", modifier = Modifier.padding(8.dp))
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
                            key(goods.iconInt) {
                                Icon(
                                    painter = painterResource(goods.iconInt),
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .size(36.dp),
                                    contentDescription = null,
                                    tint = null, // 影响默认颜色
                                )
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
                            DecimalInputField(
                                value = if (goods.price > 0.0) goods.price else price,
                                onValueChange = {
                                    price = it
                                    mainVM.processIntent(
                                        MainIntent.ChangeGoodsAttr(
                                            price = price,
                                        )
                                    )
                                },
                                error = goods.price <= 0
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showBuyDateBottomSheet = true })
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
                                text = formatTimestamp(goods.buyDate),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { showRetireDateBottomSheet = true })
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
                                text = if (goods.retireDate > 0L) formatTimestamp(goods.retireDate) else "",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    if (goods.gid > 0L) {
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
                                    text = "回血",
                                    modifier = Modifier.padding(8.dp)
                                )
                                HorizontalDivider()
                                DecimalInputField(
                                    value = if (goods.recoverHealthMoney > 0.0) goods.recoverHealthMoney else recoverHealthMoney,
                                    onValueChange = {
                                        recoverHealthMoney = it
                                        mainVM.processIntent(
                                            MainIntent.ChangeGoodsAttr(
                                                recoverHealthMoney = recoverHealthMoney,
                                            )
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
            }

            // 备注
            item {
                TextField(
                    value = goods.remark ?: "",              // 绑定的文本值
                    onValueChange = {
                        mainVM.processIntent(
                            MainIntent.ChangeGoodsAttr(
                                remark = it
                            )
                        )
                    }, // 更新文本的回调
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
                                    existingPhotoUri = if (goods.realPictureUri.isNullOrBlank()) null else goods.realPictureUri.toUri(),
                                    onPhotoCaptured = { uri ->
                                        // 这里可以拿到拍好的照片 Uri，进行上传服务器等后续业务处理
                                        // content://cn.x.dailycost.debug.fileprovider/camera_cache/IMG_20260502_181434.jpg
                                        Log.d("DEBUG PHOTO", "onPhotoCaptured: $uri")
                                        mainVM.processIntent(MainIntent.ChangeGoodsAttr(photoUri = uri.toString()))
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
//                    selectedCategory = it
                    mainVM.processIntent(
                        MainIntent.ChangeGoodsAttr(
                            cid = it.cid,
                        )
                    )
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
                iconInt = goods.iconInt,
                onSelectIcon = {
                    mainVM.processIntent(
                        MainIntent.ChangeGoodsAttr(
                            iconInt = it,
                        )
                    )
                },
                onClose = {
                    showCategoryIconsBottomSheet = false
                }
            )
        }

        // 购入日期选择
        CustomizableBottomSheet(
            isVisible = showBuyDateBottomSheet,
            onDismissRequest = { showBuyDateBottomSheet = false } // 关闭弹窗
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "当前选中日期: ${selectedBuyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                cn.x.dailycost.ui.components.DatePicker(
                    initialDate = selectedBuyDate,
                    minYear = 2000,
                    maxDate = LocalDate.now(), // 限制最大日期为今天
                    onDateChanged = { date ->
                        selectedBuyDate = date
                        mainVM.processIntent(
                            MainIntent.ChangeGoodsAttr(
                                buyDateMillis = selectedBuyDate.atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()
                                    .toEpochMilli()
                            )
                        )
                    }
                )
                TextButton(onClick = { showBuyDateBottomSheet = false }) {
                    Text("确定")
                }
            }
        }
        // 退役日期选择
        CustomizableBottomSheet(
            isVisible = showRetireDateBottomSheet,
            onDismissRequest = { showRetireDateBottomSheet = false } // 关闭弹窗
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "当前选中日期: ${selectedRetireDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                cn.x.dailycost.ui.components.DatePicker(
                    initialDate = selectedRetireDate,
//                    minYear = LocalDate.now().year,
                    minYear = 2000,
                    maxDate = LocalDate.of(2099, 12, 31),
                    onDateChanged = { date ->
                        selectedRetireDate = date
                        mainVM.processIntent(
                            MainIntent.ChangeGoodsAttr(
                                retireDateMillis = selectedRetireDate.atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()
                                    .toEpochMilli()
                            )
                        )
                    }
                )
                TextButton(onClick = { showRetireDateBottomSheet = false }) {
                    Text("确定")
                }
            }
        }

        state.error?.let { errorMsg ->
            BasicAlertDialog(
                { mainVM.processIntent(MainIntent.ErrorDismissed) },
                Modifier,
                DialogProperties(),
                { Text(errorMsg) })
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
//                        .background(color = Color(category.color))
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            selectedCategory = category
                            onSelect(selectedCategory)
                        },
                    color = Color(category.color)
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
    iconInt: Int?,
    onSelectIcon: (Int) -> Unit,
    onClose: () -> Unit,
) {
    // 1. 定义当前选中的 Tab 索引状态
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    // 2. 定义 Tab 的标题
    val tabTitles = listOf(
        "全部分类",
        "电子数码",
        "护肤美妆",
        "衣裤鞋帽首饰",
        "家用电器",
        "户外运动",
        "日常工具",
        "乐器",
        "家具",
        "其他",
    )
    val tabScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height((LocalConfiguration.current.screenHeightDp / 4 * 3).dp)
            .padding(4.dp)
    ) {
        // 3. 顶部 Tab 栏
        SecondaryScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            scrollState = tabScrollState,
            indicator = {},
            divider = {},
            minTabWidth = 4.dp  // 最小 Tab 宽度
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) GradientStart else Color(
                                0xFF6B7280
                            )
                        )
                    },
                    selectedContentColor = GradientStart,
                    unselectedContentColor = Color(0xFF6B7280),
                )
            }
        }
        HorizontalDivider()
        // 4. 根据索引切换下方的页面内容
        when (selectedTabIndex) {
            0 -> IconContent(iconInt, onSelectIcon, AppIcons.allIcons)
            1 -> IconContent(iconInt, onSelectIcon, AppIcons.digitalIcons)
            2 -> IconContent(iconInt, onSelectIcon, AppIcons.beautyProductsIcons)
            3 -> IconContent(iconInt, onSelectIcon, AppIcons.clothesPantsShoesHatsIcons)
            4 -> IconContent(iconInt, onSelectIcon, AppIcons.homeAppliances)
            5 -> IconContent(iconInt, onSelectIcon, AppIcons.outdoorSports)
            6 -> IconContent(iconInt, onSelectIcon, AppIcons.toolIcons)
            7 -> IconContent(iconInt, onSelectIcon, AppIcons.instrumentIcons)
            8 -> IconContent(iconInt, onSelectIcon, AppIcons.furnitureIcons)
            9 -> IconContent(iconInt, onSelectIcon, AppIcons.otherIcons)
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

@Composable
private fun IconContent(
    iconInt: Int?,
    onSelectIcon: (Int) -> Unit,
    categoryIcons: List<AppIcons.IconItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(categoryIcons) { iconItem ->
            Column(
                modifier = Modifier
                    // 添加内边距，让边框和图标之间有间隙
                    .padding(4.dp)
                    // 根据 isSelected 状态动态设置边框
                    .border(
                        width = if (iconInt == iconItem.resInt) 1.dp else 0.dp,
                        color = if (iconInt == iconItem.resInt) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(8.dp) // 可选：添加圆角
                    )
                    // 添加点击事件
                    .clickable {
                        onSelectIcon(iconItem.resInt)
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
}

@Preview
@Composable
fun APPFF() {
//    GoodsScreen()
//    CategoryIconsSheet(onSelect = {}, onClose = {})
}