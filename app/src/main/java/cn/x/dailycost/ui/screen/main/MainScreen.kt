package cn.x.dailycost.ui.screen.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.x.dailycost.route.Routes
import cn.x.dailycost.ui.screen.hold.HoldScreen
import cn.x.dailycost.ui.screen.my.MyScreen
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MainScreen(
    navController: NavController,
) {

    // 用于记录选中的底部导航项
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                selectedItem = selectedItem,
                onItemSelected = { newIndex ->
                    selectedItem = newIndex
                }
            )
        },
    ) { innerPadding ->
        // 页面主要内容
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            ) {
            when (selectedItem) {
                0 -> HoldScreen(navController = navController)
                1 -> MyScreen(navController)
            }
        }
    }
}


@Composable
private fun BottomBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController
) {

    // 按钮的半径（建议根据实际 UI 调整）
    val bottomBarHeight = 48.dp
    val fabRadius = 32.dp
    val density = LocalDensity.current
    val fabRadiusPx = with(density) { fabRadius.toPx() }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 1. 绘制带有凹槽的底部导航栏背景
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomBarHeight) // 底部栏高度，留出空间给凸起按钮
                .clip(
                    BottomNavWithCutoutShape(
                        cutoutRadius = fabRadiusPx,
                        notchCornerRadius = 16f
                    )
                ),
        ) {
            // 这里的颜色就是你的底部栏背景色
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
        }

        // 2. 底部导航栏的按钮区域 (使用 Row 布局)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomBarHeight),
            // 给左右留出内边距，避免按钮被挤到边缘，同时给中央留出空白区域
//                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧按钮1
            BottomNavButton(icon = Icons.Default.Home, isSelected = selectedItem == 0) {
                onItemSelected(0)
            }

            // 中央位置留空（占位符）
            Spacer(modifier = Modifier.width(fabRadius))

            // 右侧按钮2
            BottomNavButton(icon = Icons.Default.Person, isSelected = selectedItem == 1) {
                onItemSelected(1)
            }
        }

        // 3. 添加突出的悬浮按钮 (叠加在凹槽上方)
        FloatingActionButton(
            onClick = { navController.navigate(Routes.Goods.route.plus("/0")) },
            modifier = Modifier
                .size(bottomBarHeight)
                .align(Alignment.BottomCenter) // 居中于父布局底部
                .offset(y = (-20).dp), // 向上偏移，使其突出于导航栏之上
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "添加")
        }
    }
}

/**
 * 底部导航栏的自定义形状，在顶部中央创建一个半圆形凹槽
 * @param cutoutRadius 凹槽的半径（通常与浮动按钮的半径相关）
 * @param notchCornerRadius 倒角
 */
class BottomNavWithCutoutShape(
    private val cutoutRadius: Float,
    private val notchCornerRadius: Float = 8f, // 倒角：凹槽边缘的圆角半径
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // 从左上角开始
            moveTo(0f, 0f)

            val centerX = size.width / 2

            // --- 计算左侧圆角路径 ---
            // 左侧圆角的圆心位置
            val leftNotchCornerCenterX = centerX - cutoutRadius

            // 绘制到左侧圆角开始前的位置
            lineTo(leftNotchCornerCenterX, 0f)

            // 绘制左上角的1/4圆弧 (90度)
            // 这个圆弧从12点钟方向顺时针绘制到3点钟方向
            arcTo(
                rect = Rect(
                    left = leftNotchCornerCenterX - notchCornerRadius,
                    top = 0f,
                    right = leftNotchCornerCenterX + notchCornerRadius,
                    bottom = notchCornerRadius
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // --- 绘制中间的半圆形凹槽 ---
            // 凹槽半圆的圆心在 (centerX, 0)，半径为 cutoutRadius
            arcTo(
                rect = Rect(
                    left = centerX - cutoutRadius + notchCornerRadius,
                    top = -cutoutRadius,
                    right = centerX + cutoutRadius - notchCornerRadius,
                    bottom = cutoutRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f, // 逆时针扫过180度
                forceMoveTo = false
            )

            // --- 计算右侧圆角路径 ---
            // 右侧圆角的圆心位置
            val rightNotchCornerCenterX = centerX + cutoutRadius

            // 绘制右上角的1/4圆弧 (90度)
            // 这个圆弧从9点钟方向顺时针绘制到12点钟方向
            arcTo(
                rect = Rect(
                    left = rightNotchCornerCenterX - notchCornerRadius,
                    top = 0f,
                    right = rightNotchCornerCenterX + notchCornerRadius,
                    bottom = notchCornerRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // --- 完成路径 ---
            // 绘制到右上角
            lineTo(size.width, 0f)

            // 绘制右侧边
            lineTo(size.width, size.height)

            // 绘制底部边
            lineTo(0f, size.height)

            // 闭合路径
            close()
        }
        return Outline.Generic(path)
    }
}


// 底部导航按钮组件
@Composable
private fun BottomNavButton(icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.6f
            )
        )
    }
}

@Preview
@Composable
fun MPPP() {

    MainScreen(rememberNavController())
}