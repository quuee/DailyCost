package cn.x.dailycost.ui.screen.hold


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.x.dailycost.ui.components.AnimatedDropdownButton
import cn.x.dailycost.ui.components.AppIcons


@Composable
fun HoldScreen() {

    var selected by remember { mutableStateOf("创建时间") }
    val optionsList = listOf("创建时间", "过期时间", "预计退役时间", "库存")

    Column(
        modifier = Modifier
            .fillMaxSize().padding(horizontal = 4.dp)

    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // 第一行： 总资产 排序
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null)
                    }
                    Text("总资产", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.weight(1f))

                    AnimatedDropdownButton(
                        selectedOption = selected,
                        options = optionsList,
                        onOptionSelected = { selected = it },
                    )
                }
                // 第二行
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¥ 0.00", style = MaterialTheme.typography.titleLarge)
                }
                // 第三行

                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("¥ 0.00", style = MaterialTheme.typography.bodyLarge)
                        Text("日均成本", style = MaterialTheme.typography.bodySmall)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("0", style = MaterialTheme.typography.bodyLarge)
                        Text("物品数", style = MaterialTheme.typography.bodySmall)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("0", style = MaterialTheme.typography.bodyLarge)
                        Text("库存数", style = MaterialTheme.typography.bodySmall)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("0", style = MaterialTheme.typography.bodyLarge)
                        Text("使用中", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

        }

        LazyColumn(
        ) {
            repeat(20) {
                item {
                    GoodsItem()
                }
            }
        }
    }
}


@Composable
private fun GoodsItem(){
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = AppIcons.shouji, modifier = Modifier.size(36.dp),contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text("真我gt8", style = MaterialTheme.typography.bodyLarge)
                Text("¥ 2717  日均：20/天", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text("137 天", style = MaterialTheme.typography.bodyLarge)
        }
    }

}