package cn.x.dailycost.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import cn.x.dailycost.data.entity.CategoryEntity

@Composable
fun SearchBar(
    categories: List<CategoryEntity>,
    onSearch: (CategoryEntity?, String) -> Unit
) {
    val density = LocalDensity.current
    val rowHeight = 36.dp // 搜索栏高度
    val px = remember { with(density) { rowHeight.toPx() } } // dp 转 像素

    var selectedCategory: CategoryEntity by remember {
        mutableStateOf(
            CategoryEntity(
                cid = 0L,
                name = "全部分类",
                color = Color.Transparent.toArgb(),
                sort = 1
            )
        )
    }

    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // 搜索框焦点状态（可选：用于UI美化）
    var isFocused by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(rowHeight)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 1.dp,
                    color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧：分类下拉选项
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .clickable { expanded = true }
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = selectedCategory.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "下拉选择分类",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 分割线
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            // 中间：搜索输入框
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchText.isEmpty()) {
                            Text(
                                text = "请输入搜索关键词...",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                        innerTextField()
                    }
                },
                singleLine = true
            )

            // 右侧：搜索按钮
            IconButton(
                onClick = {
                    onSearch(selectedCategory, searchText)
                }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "搜索",
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (expanded) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    0,
                    px.toInt()
                ),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = (LocalWindowInfo.current.containerSize.height).dp),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedCategory = category
                                        expanded = false
                                    },
                                color = if (category == selectedCategory)
                                    MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = category.name,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    color = if (category == selectedCategory)
                                        MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun SPP() {
//    val categories = listOf("全部分类", "电子产品", "服装", "图书", "家居用品", "美妆")
//    SearchBar(categories, onSearch = { selectedCategory, searchText -> })
}