package cn.x.dailycost.ui.screen.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManagerScreen() {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("分类管理") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                },
                actions = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            repeat(10) {
                item {
                    CategoryItem()
                }

            }
        }
    }
}


@Composable
private fun CategoryItem() {

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
                .background(color = Color.Red.copy(alpha = 0.6f), shape = RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("电子数码")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null
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
            contentDescription = null
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
    CategoryManagerScreen()
}