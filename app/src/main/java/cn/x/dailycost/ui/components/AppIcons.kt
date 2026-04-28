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
    // 彩妆粉
    val caizhuangfen: Painter
        @Composable get() = painterResource(R.drawable.ic_caizhuangfen)

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
    val changdi: Painter
        @Composable get() = painterResource(R.drawable.ic_changdi)

    // ==========家用电器类==========
    // 冰箱
    val bingxiang: Painter
        @Composable get() = painterResource(R.drawable.ic_bingxiang)

    // ==========家具类==========
    // 餐具
    val canju: Painter
        @Composable get() = painterResource(R.drawable.ic_canju)

    // ==========运动类==========

    // ==========母婴类==========

    // ==========其他 统称==========

    // ====系统图标====
    val renminbi: Painter
        @Composable get() = painterResource(R.drawable.ic_renminbi)


    // 2. 定义一个数据类来封装“名字”和“图标”
    data class IconItem(val name: String, val resInt: Int)

    // 3. 创建一个列表，把所有需要遍历的图标加进来
    val allIcons: List<IconItem> = listOf(
        IconItem("手机", R.drawable.ic_shouji),
        IconItem("彩妆粉", R.drawable.ic_caizhuangfen),
        IconItem("半裙", R.drawable.ic_banqun),
        IconItem("板鞋", R.drawable.ic_banxie),
        IconItem("包包", R.drawable.ic_baobao),
        IconItem("餐具", R.drawable.ic_canju),
        IconItem("长笛", R.drawable.ic_changdi),
    )
}