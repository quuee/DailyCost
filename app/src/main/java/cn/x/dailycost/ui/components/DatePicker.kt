package cn.x.dailycost.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@Composable
private fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    initialIndex: Int = 0,
    itemHeight: Dp = 40.dp,
    visibleItemsCount: Int = 5,
    textStyle: TextStyle = LocalTextStyle.current,
    selectedTextColor: Color = LocalContentColor.current,
    unselectedTextColor: Color = LocalContentColor.current.copy(alpha = 0.6f),
    showDividers: Boolean = true,
    dividerColor: Color = Color.LightGray,
    onValueChange: (Int) -> Unit
) {
    val middleItemIndex = visibleItemsCount / 2
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

    // 监听滚动，计算当前选中的索引
    LaunchedEffect(lazyListState.isScrollInProgress) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { index ->
                onValueChange(index)
            }
    }

    Box(
        modifier = modifier
            .height(itemHeight * visibleItemsCount)
            .drawWithContent {
                drawContent()
                if (showDividers) {
                    val dividerHeight = 1.dp.toPx()
                    drawRect(
                        color = dividerColor,
                        size = androidx.compose.ui.geometry.Size(size.width, dividerHeight),
                        topLeft = androidx.compose.ui.geometry.Offset(
                            0f,
                            itemHeight.toPx() * middleItemIndex
                        )
                    )
                    drawRect(
                        color = dividerColor,
                        size = androidx.compose.ui.geometry.Size(size.width, dividerHeight),
                        topLeft = androidx.compose.ui.geometry.Offset(
                            0f,
                            itemHeight.toPx() * (middleItemIndex + 1)
                        )
                    )
                }
                // 添加上下渐变遮罩，实现滚轮立体感
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = itemHeight.toPx() * middleItemIndex
                    ),
                    blendMode = BlendMode.DstIn
                )
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black, Color.Transparent),
                        startY = itemHeight.toPx() * (middleItemIndex + 1),
                        endY = itemHeight.toPx() * visibleItemsCount
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = itemHeight * middleItemIndex),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(items.size) { index ->
                val isSelected = index == lazyListState.firstVisibleItemIndex
                Text(
                    text = items[index],
                    style = textStyle,
                    color = if (isSelected) selectedTextColor else unselectedTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .height(itemHeight)
                        .padding(vertical = 4.dp)
                        .wrapContentWidth()
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    minYear: Int = 1900,
    maxDate: LocalDate = LocalDate.now(),
    onDateChanged: (LocalDate) -> Unit,
    itemHeight: Dp = 40.dp,
    visibleItemsCount: Int = 5
) {
    // 确保初始日期在合法范围内
    var selectedDate by remember { mutableStateOf(initialDate.coerceAtMost(maxDate)) }

    // 动态生成年份列表
    val years = (minYear..maxDate.year).map { it.toString() }

    // 动态生成月份列表 (如果选中的是最大年份，则月份不能超过最大日期的月份)
    val months = if (selectedDate.year == maxDate.year) {
        (1..maxDate.monthValue).map { it.toString().padStart(2, '0') }
    } else {
        (1..12).map { it.toString().padStart(2, '0') }
    }

    // 动态生成日期列表 (核心联动逻辑：根据当前年月获取当月实际天数)
    val daysInMonth = selectedDate.lengthOfMonth()
    val maxDay =
        if (selectedDate.year == maxDate.year && selectedDate.monthValue == maxDate.monthValue) {
            maxDate.dayOfMonth
        } else {
            daysInMonth
        }
    val days = (1..maxDay).map { it.toString().padStart(2, '0') }

    // 统一更新日期的方法
    fun updateDate(newDate: LocalDate) {
        val constrainedDate = newDate.coerceAtMost(maxDate)
        if (constrainedDate != selectedDate) {
            selectedDate = constrainedDate
            onDateChanged(constrainedDate)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 年份滚轮
        WheelPicker(
            modifier = Modifier.weight(1f),
            items = years,
            initialIndex = (selectedDate.year - minYear).coerceIn(0, years.size - 1),
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            onValueChange = { index ->
                val newYear = years[index].toInt()
                // 年份改变时，尝试保留原有的月日，如果非法（如2月29日变平年）会自动修正
                updateDate(selectedDate.withYear(newYear))
            }
        )
        Text("年", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        // 月份滚轮
        WheelPicker(
            modifier = Modifier.weight(1f),
            items = months,
            initialIndex = (selectedDate.monthValue - 1).coerceIn(0, months.size - 1),
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            onValueChange = { index ->
                val newMonth = months[index].toInt()
                // 月份改变时，核心逻辑：withMonth 会自动处理天数溢出（如1月31日切到2月会变成2月28/29日）
                updateDate(selectedDate.withMonth(newMonth))
            }
        )
        Text("月", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        // 日期滚轮
        WheelPicker(
            modifier = Modifier.weight(1f),
            items = days,
            initialIndex = (selectedDate.dayOfMonth - 1).coerceIn(0, days.size - 1),
            itemHeight = itemHeight,
            visibleItemsCount = visibleItemsCount,
            onValueChange = { index ->
                val newDay = days[index].toInt()
                updateDate(selectedDate.withDayOfMonth(newDay))
            }
        )
        Text("日", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DPPP() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "当前选中日期: ${selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        DatePicker(
            initialDate = selectedDate,
            minYear = 2000,
            maxDate = LocalDate.now(), // 限制最大日期为今天
            onDateChanged = { date ->
                selectedDate = date
            }
        )
    }
}