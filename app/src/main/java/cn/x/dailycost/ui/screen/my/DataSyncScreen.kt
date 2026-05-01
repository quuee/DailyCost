package cn.x.dailycost.ui.screen.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataSyncScreen() {
    // 1. 定义当前选中的 Tab 索引状态
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    // 2. 定义 Tab 的标题
    val tabTitles = listOf("发送", "接受")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("数据同步") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            // 3. 顶部 Tab 栏
            SecondaryTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }, // 点击时更新索引
                        text = { Text(text = title) }
                    )
                }
            }

            // 4. 根据索引切换下方的页面内容
            when (selectedTabIndex) {
                0 -> SendContent("这是发送的内容")
                1 -> ReceiveContent("这是接收的内容")
            }
        }
    }
}

@Composable
fun SendContent(text: String) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column() {
                Row() {
                    Text("附近的设备")
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = null)

                }

                Row() {
                    Icon(
                        imageVector = Icons.Filled.Devices,
                        contentDescription = null
                    )

                    Text("xxx的手机")
                }
            }
        }


    }
}

@Composable
fun ReceiveContent(text: String) {
    Text(text = text, modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
fun DPPP(){
    DataSyncScreen()
}