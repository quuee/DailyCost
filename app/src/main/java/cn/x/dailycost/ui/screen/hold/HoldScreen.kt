package cn.x.dailycost.ui.screen.hold


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.x.dailycost.data.entity.GoodsItemEntity
import cn.x.dailycost.route.Routes
import cn.x.dailycost.ui.components.AppIcons
import cn.x.dailycost.ui.components.SearchBar
import cn.x.dailycost.ui.components.SortDropdownButton
import cn.x.dailycost.ui.screen.main.MainIntent
import cn.x.dailycost.ui.screen.main.MainScreenVM
import cn.x.dailycost.util.getDate
import cn.x.dailycost.util.getDaysDifference
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant



@Composable
fun HoldScreen(
    mainVM: MainScreenVM = koinViewModel(),
    navController: NavController
) {
    val state by mainVM.state.collectAsState()

//    val everyDayCostTotal by remember {
//        derivedStateOf {
//            state.goodsItems.sumOf { goods ->
//                val nowDate = Instant.now().toEpochMilli()
//                val usedDays = getDaysDifference(goods.buyDate,nowDate)
//                // 注意处理 usedDays 为 0 的情况，防止除以 0 报错
//                if (usedDays > 0) goods.price / usedDays else 0.0
//            }
//        }
//    }

    LazyColumn(

    ) {
        item {
            AssetCard(
                selectedField = state.selectSortField,
                options = state.sortFiledList,
                onOptionSelected = {
                    mainVM.processIntent(
                        MainIntent.SearchGoods(
                            name = null,
                            cid = null,
                            sortField = it,
                            sortOrder = null
                        )
                    )
                },
                asset = state.asset,
                totalCount = state.goodsItems.size,
                everyDayCostTotal = state.everyDayCostTotal,
                selectOrderOption = state.selectSortOrder,
                onOrderSelected = {
                    mainVM.processIntent(
                        MainIntent.SearchGoods(
                            name = null,
                            cid = null,
                            sortField = null,
                            sortOrder = it
                        )
                    )
                },
            )
        }
        stickyHeader {
            SearchBar(
                state.categories,
                onSearch = { selectedCategory, searchText ->
                    mainVM.processIntent(
                        MainIntent.SearchGoods(
                            searchText,
                            selectedCategory?.cid,
                            null, null
                        )
                    )
                })
        }
        if (state.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator() // 加载中转圈圈
                }
            }
        } else {
            items(state.goodsItems) { goods ->

                GoodsItem(
                    goods,
                    bgColor = state.categoryMap[goods.cid]?.color ?: Color(0XFFF8F1E4).toArgb(),
                    onToggle = { navController.navigate(Routes.Goods.route + "/${it}") })
            }
        }

    }
}

@Composable
private fun AssetCard(
    selectedField: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit = {},
    selectOrderOption: Int,
    onOrderSelected: (Int) -> Unit,
    asset: Double,
    totalCount: Int,
    everyDayCostTotal: Double
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 第一行： 总资产 排序
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    imageVector = Icons.Default.RemoveRedEye,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = {})
                )
                Spacer(Modifier.width(8.dp))
                Text("总资产", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.weight(1f))

                SortDropdownButton(
                    selectedOption = selectedField,
                    options = options,
                    onOptionSelected = { onOptionSelected(it) },
                    selectOrderOption = selectOrderOption,
                    onOrderSelected = { onOrderSelected(it) }
                )

            }

            // 第er行
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    painter = AppIcons.renminbi,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(asset.toString(), style = MaterialTheme.typography.titleLarge)
            }

            // 第san行
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        "¥ ${"%.2f".format(everyDayCostTotal)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text("日均成本", style = MaterialTheme.typography.bodyMedium)
                }

                Column() {
                    Text(totalCount.toString(), style = MaterialTheme.typography.bodyMedium)
                    Text("物品总数", style = MaterialTheme.typography.bodyMedium)
                }

//                Column() {
//                    Text("0", style = MaterialTheme.typography.bodyMedium)
//                    Text("已退役", style = MaterialTheme.typography.bodyMedium)
//                }

            }

        }

    }
}


@Composable
private fun GoodsItem(
    goodsItem: GoodsItemEntity,
    bgColor: Int,
    onToggle: (Long) -> Unit
) {

    val nowDate = Instant.now().toEpochMilli()
    var usedDays by remember { mutableLongStateOf(0L) }
    var everyDayMoney by remember { mutableStateOf("") }
    var retireDays by remember { mutableLongStateOf(0L) }
    if (goodsItem.recoverHealthMoney > 0 && goodsItem.retireDate > 0) {
        usedDays =
            getDaysDifference(goodsItem.buyDate, goodsItem.retireDate)
        everyDayMoney =
            "%.2f".format((goodsItem.price - goodsItem.recoverHealthMoney) / usedDays)

    } else {
        usedDays = getDaysDifference(goodsItem.buyDate, nowDate)
        everyDayMoney = "%.2f".format(goodsItem.price / usedDays)
        retireDays = getDaysDifference(goodsItem.buyDate, goodsItem.retireDate)
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = { onToggle(goodsItem.gid) })
    ) {
        Row(
            modifier = Modifier
                .background(color = Color(bgColor))
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (goodsItem.realPictureUri.isNullOrBlank()) {
                Image(
                    painter = painterResource(goodsItem.iconInt),
                    modifier = Modifier.size(36.dp),
                    contentDescription = null
                )
            } else {
                AsyncImage(
                    model = goodsItem.realPictureUri,
                    modifier = Modifier.size(36.dp),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(goodsItem.goodsName, style = MaterialTheme.typography.titleLarge)
                if (goodsItem.recoverHealthMoney > 0) {
                    Text(
                        "¥${goodsItem.price} 日均:${everyDayMoney}/天 回血:${goodsItem.recoverHealthMoney} ",
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        "¥${goodsItem.price} 日均：${everyDayMoney}/天  ",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("${usedDays} 天", style = MaterialTheme.typography.titleLarge)
                // 距离退役日期
                if (goodsItem.retireDate > nowDate) {
                    if (retireDays > 0) {
                        Text("${retireDays} 天后退役", style = MaterialTheme.typography.bodySmall)
                    }
                } else {
                    // 已退役
                    if (goodsItem.retireDate > 0L) {
                        val date = getDate(goodsItem.retireDate)
                        Text(
                            "${date.year}-${date.monthValue}-${date.dayOfMonth}退役",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }

            }
        }
    }

}

@Preview
@Composable
fun HPPP() {
//    var selected by remember { mutableStateOf("创建时间") }
//    val optionsList = listOf("创建时间", "过期时间", "预计退役时间", "库存")
//    AssetCard(selected, optionsList, onOptionSelected = {})

//    GoodsItem()
}