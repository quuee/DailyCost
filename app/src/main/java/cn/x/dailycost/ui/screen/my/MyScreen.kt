package cn.x.dailycost.ui.screen.my

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.x.dailycost.data.entity.GoodsItemEntity
import cn.x.dailycost.route.LocalNavigator
import cn.x.dailycost.route.Routes
import cn.x.dailycost.ui.viewmodel.GoodsEffect
import cn.x.dailycost.ui.viewmodel.GoodsIntent
import cn.x.dailycost.ui.viewmodel.GoodsVM
import cn.x.dailycost.util.ToastUtil
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MyScreen(
    goodsVM: GoodsVM = koinViewModel(),
) {
    val navigator = LocalNavigator.current

    val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    val state = goodsVM.state.collectAsState()

    LaunchedEffect(Unit) {
        goodsVM.effect.collect { effect ->
            when (effect) {
                is GoodsEffect.ShowMessage -> {
                    ToastUtil.show(effect.message)
                }

                is GoodsEffect.NavigateToHome ->{

                }
            }
        }
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // ========== 文件选择器 ==========
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            scope.launch {
//                try {
//                } catch (e: Exception) {
//                }
                // 1. 在UI层读取文件内容
                val jsonString = context.contentResolver
                    .openInputStream(it)
                    ?.bufferedReader()
                    .use { reader -> reader?.readText() }
                jsonString?.let{
                    val goods = json.decodeFromString<List<GoodsItemEntity>>(jsonString)
                    goodsVM.processIntent(GoodsIntent.ImportData(goods))
                }
            }
        }
    }

    val createFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        uri?.let {
            scope.launch {
                val goodsString = json.encodeToString(state.value.goodsItems)
                context.contentResolver.openOutputStream(it)?.use { stream ->
                    stream.write(goodsString.toByteArray())
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {  navigator.navigate(Routes.DataSync) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("数据同步")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {  navigator.navigate(Routes.CategoryManager) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("分类管理")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("通用设置")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                // 简单点数据同步方案
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            createFileLauncher.launch("goods_backup_${System.currentTimeMillis()}.json")
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("导出json数据")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            filePickerLauncher.launch(arrayOf("application/json"))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("导入json数据")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {},
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("更新日志/计划")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {},
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("功能建议")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {},
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("打赏作者")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navigator.navigate(Routes.Demo)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("测试页面")
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
fun MPPP() {
    MyScreen()
}