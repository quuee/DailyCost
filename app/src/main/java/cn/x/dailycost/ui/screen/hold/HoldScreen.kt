package cn.x.dailycost.ui.screen.hold


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HoldScreen(
    mainVM: MainScreenVM = koinViewModel(),
    navController: NavController
) {
    val state by mainVM.state.collectAsState()

    LazyColumn(

    ) {
        item {
            AssetCard(
                selectedField = state.selectSortField,
                options = state.sortFiledList,
                onOptionSelected = { state.selectSortField = it },
                asset = state.asset
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
                            null,
                            null
                        )
                    )
                })
        }
        items(state.goodsItems) { goods ->
            GoodsItem(goods, onToggle = { navController.navigate(Routes.Goods.route+"/${it}") })
        }
    }
}

@Composable
private fun AssetCard(
    selectedField: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit = {},
    asset: Double
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
                    Text("¥ 0.00", style = MaterialTheme.typography.bodyMedium)
                    Text("日均成本", style = MaterialTheme.typography.bodyMedium)
                }

                Column() {
                    Text("1", style = MaterialTheme.typography.bodyMedium)
                    Text("物品总数", style = MaterialTheme.typography.bodyMedium)
                }

                Column() {
                    Text("0", style = MaterialTheme.typography.bodyMedium)
                    Text("已退役", style = MaterialTheme.typography.bodyMedium)
                }

            }

        }

    }
}


@Composable
private fun GoodsItem(goodsItem: GoodsItemEntity, onToggle: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = { onToggle(goodsItem.gid) })
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(goodsItem.iconInt),
                modifier = Modifier.size(36.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(goodsItem.goodsName, style = MaterialTheme.typography.titleMedium)
                Text("¥ ${goodsItem.price}  日均：20/天", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("137 天", style = MaterialTheme.typography.titleLarge)
                Text("667天后退役", style = MaterialTheme.typography.bodySmall)
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