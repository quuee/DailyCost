package cn.x.dailycost.ui.screen.hold


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HoldScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("共 N 件物品")

                TextButton(onClick = {}) {
                    Text("筛选")
                }
                TextButton(onClick = {}) {
                    Text("排序")
                }
            }
        }

        LazyColumn(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            repeat(20) {
                item {
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(imageVector = Icons.Filled.PhoneIphone, contentDescription = "")

                        Column() {
                            Text("真我gt8")
                            Text("2717  20/天")
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text("137 天")
                    }
                }
            }
        }
    }
}