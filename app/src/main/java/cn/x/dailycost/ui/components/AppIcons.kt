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
    val cpu: Painter
        @Composable get() = painterResource(R.drawable.ic_cpu)

    // ==========美妆类==========
    // 彩妆粉
    val caizhuangfen: Painter
        @Composable get() = painterResource(R.drawable.ic_caizhuangfen)
    // 唇釉
    val chunyou: Painter
        @Composable get() = painterResource(R.drawable.ic_chunyou)

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

    // 长袖
    val changxiu: Painter
        @Composable get() = painterResource(R.drawable.ic_changxiu)

    val chenshan: Painter
        @Composable get() = painterResource(R.drawable.ic_chenshan)

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
    val chuifengji: Painter
        @Composable get() = painterResource(R.drawable.ic_chuifengji)
    val chushiji: Painter
        @Composable get() = painterResource(R.drawable.ic_chushiji)

    // ==========家具类==========
    // 餐具
    val canju: Painter
        @Composable get() = painterResource(R.drawable.ic_canju)
    val chuang: Painter
        @Composable get() = painterResource(R.drawable.ic_chuang)
    val chuju: Painter
        @Composable get() = painterResource(R.drawable.ic_chuju)

    // ==========运动类==========
    // 冲浪板
    val chonglangban: Painter
        @Composable get() = painterResource(R.drawable.ic_chonglangban)

    // ==========母婴类==========

    // ==========工具类==========
    val chanzi: Painter
        @Composable get() = painterResource(R.drawable.ic_chanzi)
    val chapai: Painter
        @Composable get() = painterResource(R.drawable.ic_chapai)
    val chazuo: Painter
        @Composable get() = painterResource(R.drawable.ic_chazuo)
    val cheng: Painter
        @Composable get() = painterResource(R.drawable.ic_cheng)
    val chongdianqi: Painter
        @Composable get() = painterResource(R.drawable.ic_chongdianqi)

    // ==========其他 统称==========
    // 宠物用品
    val chongwuyongpin: Painter
        @Composable get() = painterResource(R.drawable.ic_chongwuyongpin)

    // ====系统图标====
    val renminbi: Painter
        @Composable get() = painterResource(R.drawable.ic_renminbi)
    val icPackage: Painter
        @Composable get() = painterResource(R.drawable.ic_package)


    // 2. 定义一个数据类来封装“名字”和“图标”
    data class IconItem(val name: String, val resInt: Int)

    // 3. 创建一个列表，把所有需要遍历的图标加进来
    val allIcons: List<IconItem> = listOf(
        IconItem("手机", R.drawable.ic_shouji),
        IconItem("CPU", R.drawable.ic_cpu),

        IconItem("彩妆粉", R.drawable.ic_caizhuangfen),
        IconItem("唇釉", R.drawable.ic_chunyou),

        IconItem("半裙", R.drawable.ic_banqun),
        IconItem("板鞋", R.drawable.ic_banxie),
        IconItem("包包", R.drawable.ic_baobao),
        IconItem("长袖", R.drawable.ic_changxiu),
        IconItem("衬衫", R.drawable.ic_chenshan),

        IconItem("餐具", R.drawable.ic_canju),
        IconItem("床", R.drawable.ic_chuang),
        IconItem("厨具", R.drawable.ic_chuju),

        IconItem("长笛", R.drawable.ic_changdi),


        IconItem("铲子", R.drawable.ic_chanzi),
        IconItem("插排", R.drawable.ic_chapai),
        IconItem("插座", R.drawable.ic_chazuo),
        IconItem("称", R.drawable.ic_cheng),
        IconItem("充电器", R.drawable.ic_chongdianqi),


        IconItem("冲浪板", R.drawable.ic_chonglangban),


        IconItem("宠物用品", R.drawable.ic_chongwuyongpin),


        IconItem("冰箱", R.drawable.ic_bingxiang),
        IconItem("吹风机", R.drawable.ic_chuifengji),
        IconItem("除湿机", R.drawable.ic_chushiji),

    )
}