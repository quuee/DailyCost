package cn.x.dailycost.ui.screen.hold


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.x.dailycost.ui.components.AppIcons
import cn.x.dailycost.ui.components.SearchBar
import cn.x.dailycost.ui.components.SortDropdownButton


@Composable
fun HoldScreen() {

    var selected by remember { mutableStateOf("创建时间") }
    val optionsList = listOf("创建时间", "过期时间", "预计退役时间", "库存")
    val categories = listOf("全部分类", "电子产品", "服装", "图书", "家居用品", "美妆")

    LazyColumn(

    ) {
        item {
            AssetCard(selected, optionsList, onOptionSelected = { selected = it })
        }
        stickyHeader {
            SearchBar(categories, onSearch = { selectedCategory, searchText -> })
        }
        repeat(20) {
            item {
                GoodsItem()
            }
        }
    }
}

@Composable
private fun AssetCard(
    selected: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit = {}
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
                    selectedOption = selected,
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
                Text("0.00", style = MaterialTheme.typography.titleLarge)
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
private fun GoodsItem() {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = AppIcons.shouji,
                modifier = Modifier.size(36.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text("真我gt8", style = MaterialTheme.typography.titleMedium)
                Text("¥ 2717  日均：20/天", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("137 天", style = MaterialTheme.typography.titleLarge)
                Text("667天后退役",style = MaterialTheme.typography.bodySmall)
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

    GoodsItem()
}