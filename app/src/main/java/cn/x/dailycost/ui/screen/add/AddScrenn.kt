package cn.x.dailycost.ui.screen.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.format.DateTimeFormatter

@Composable
fun AddScreen(
    navController: NavController
) {
    var goodsName by remember { mutableStateOf("") }
    var remark by remember { mutableStateOf("") }
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
                    items(20) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.5f),// 宽高比
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Text(
                                text = "请选择",
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


    }
}

@Preview
@Composable
fun APPFF() {
//    AddScreen()
}