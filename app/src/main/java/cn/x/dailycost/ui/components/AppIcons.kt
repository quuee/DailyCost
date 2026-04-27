package cn.x.dailycost.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import cn.x.dailycost.R


/**
 * 统一图标管理类
 */
object AppIcons {

    // ==========数码类==========
    // 手机
    val shouji: Painter
        @Composable get() = painterResource(R.drawable.ic_shouji)

    // ==========美妆类==========

    // ==========服装类==========
    // 半裙
    val banqun: Painter
        @Composable get() = painterResource(R.drawable.ic_banqun)

    // 板鞋
    val banxie: Painter
        @Composable get() = painterResource(R.drawable.ic_banxie)
    // 包包
    val baobao: Painter
        @Composable get() = painterResource(R.drawable.ic_baobao)

    // ==========乐器类==========
    // 贝斯
    val beisi: Painter
        @Composable get() = painterResource(R.drawable.ic_beisi)

    // ==========家用电器类==========
    // 冰箱
    val bingxiang: Painter
        @Composable get() = painterResource(R.drawable.ic_bingxiang)

    // ==========家具类==========

    // ==========运动类==========

    // ==========母婴类==========

    // ==========其他 统称==========

    // ====系统图标====
    val renminbi: Painter
        @Composable get() = painterResource(R.drawable.ic_renminbi)


    // 2. 定义一个数据类来封装“名字”和“图标”
    data class IconItem(val name: String, val painter: Painter)

    // 3. 创建一个列表，把所有需要遍历的图标加进来
    val allIcons: List<IconItem>
        @Composable get() = listOf(
            IconItem("手机", shouji),
            IconItem("半裙", banqun),
            IconItem("板鞋", banxie),
            IconItem("包包", baobao)
        )
}