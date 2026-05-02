package cn.x.dailycost.ui.screen.my

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.x.dailycost.data.entity.CategoryEntity
import cn.x.dailycost.ui.components.CustomizableBottomSheet
import cn.x.dailycost.ui.screen.main.MainIntent
import cn.x.dailycost.ui.screen.main.MainScreenVM
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManagerScreen(
    mainVM: MainScreenVM = koinViewModel(),
    navController: NavController
) {
    val state by mainVM.state.collectAsState()

    var showCreateCategoryBottomSheet by remember { mutableStateOf(false) }
    var selectCategory: CategoryEntity? by rememberSaveable { mutableStateOf(null) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("分类管理") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = {
                            navController.popBackStack()
                        })
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier.clickable(onClick = {
                            // 打开创建窗口
                            showCreateCategoryBottomSheet = true
                        })
                    )
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(state.categories) { category ->
                CategoryItem(
                    category,
                    onShowEditSheet = {
                        showCreateCategoryBottomSheet = true
                        selectCategory = it
                    },
                    onDelete = { mainVM.processIntent(MainIntent.DeleteCategory(it)) })
            }
        }

        CustomizableBottomSheet(
            isVisible = showCreateCategoryBottomSheet,
            onDismissRequest = { showCreateCategoryBottomSheet = false } // 关闭弹窗
        ) {
            CreateCategorySheet(
                category = selectCategory,
                onConfirm = {
                    mainVM.processIntent(MainIntent.CreateCategory(it))
                    showCreateCategoryBottomSheet = false
                    selectCategory = null
                }
            )
        }
    }
}

@Composable
private fun CreateCategorySheet(
    category: CategoryEntity?,
    onConfirm: (CategoryEntity) -> Unit,
) {
    var categoryName by remember { mutableStateOf(category?.name ?: "") }

    // 1. 定义数据源和状态变量
    val colors = listOf(
        Color.Red.copy(alpha = 0.6f),
        Color.Blue.copy(alpha = 0.6f),
        Color.Yellow.copy(alpha = 0.6f),
        Color.Green.copy(alpha = 0.6f)
    )
    // 记录当前选中的颜色，初始值可以设为 null 或列表中的第一个
    var selectedColor by remember { mutableStateOf<Color?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            TextField(
                value = categoryName,              // 绑定的文本值
                onValueChange = { categoryName = it }, // 更新文本的回调
                // 行数限制 - 核心配置
                minLines = 1,
                maxLines = 1,
                label = { Text("分类名称") },
//            placeholder = { Text("请输入..") }, // 占位符提示
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,   // 聚焦时边框透明（去掉边框/下划线）
                    unfocusedBorderColor = Color.Transparent, // 未聚焦时边框透明（去掉边框/下划线）
                    disabledBorderColor = Color.Transparent   // 禁用状态边框透明
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                    ),
            )

            HorizontalDivider(Modifier.padding(4.dp))
            Text("分类颜色")

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(colors) { color ->
                    // 判断当前项是否被选中
                    val isSelected = selectedColor == color

                    Box(
                        modifier = Modifier
                            // 2. 选中时稍微放大 (scale 1.2倍)
                            .graphicsLayer {
                                scaleX = if (isSelected) 1.2f else 1f
                                scaleY = if (isSelected) 1.2f else 1f
                            }
                            // 3. 选中时添加边框 (border 需要放在 background 之前)
                            .then(
                                if (isSelected) {
                                    Modifier.border(3.dp, Color.Black, RoundedCornerShape(8.dp))
                                } else {
                                    Modifier
                                }
                            )
                            .size(48.dp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(8.dp)
                            )
                            // 4. 点击事件：更新选中的值
                            .clickable { selectedColor = color }
                    )
                }
            }

            TextButton(
                onClick = {
                    val c = CategoryEntity(
                        cid = category?.cid ?: 0L,
                        name = categoryName,
                        color = selectedColor?.toArgb() ?: Color.Yellow.toArgb(),
                        sort = 1
                    )

                    onConfirm(c)
                    categoryName = ""
                    selectedColor = null
                }, modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("确定")
            }
        }
    }


}

@Composable
private fun CategoryItem(
    category: CategoryEntity,
    onShowEditSheet: (CategoryEntity) -> Unit,
    onDelete: (CategoryEntity) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .background(
                    color = Color(category.color).copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(category.name)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = {
                onShowEditSheet(category)
            })
        )
        // 排序改拖动排序
//        Icon(
//            imageVector = Icons.Filled.ArrowUpward,
//            contentDescription = null
//        )
//        Icon(
//            imageVector = Icons.Filled.ArrowDownward,
//            contentDescription = null
//        )
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = {
                onDelete(category)
            })
        )
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Filled.Dehaze,
            contentDescription = null
        )
    }
}


@Preview
@Composable
fun CPPP() {
//    CategoryManagerScreen()
    CreateCategorySheet(category = null, onConfirm = {})
}