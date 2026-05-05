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
    val dayinji: Painter
        @Composable get() = painterResource(R.drawable.ic_dayinji)
    val diannao: Painter
        @Composable get() = painterResource(R.drawable.ic_diannao)
    val diannaozhuban: Painter
        @Composable get() = painterResource(R.drawable.ic_diannaozhuban)

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
    val duanku: Painter
        @Composable get() = painterResource(R.drawable.ic_duanku)

    // ==========乐器类==========
    // 贝斯
    val beisi: Painter
        @Composable get() = painterResource(R.drawable.ic_beisi)
    val changdi: Painter
        @Composable get() = painterResource(R.drawable.ic_changdi)
    val datiqin: Painter
        @Composable get() = painterResource(R.drawable.ic_datiqin)
    val dianziqin: Painter
        @Composable get() = painterResource(R.drawable.ic_dianziqin)

    // ==========家用电器类==========
    // 冰箱
    val bingxiang: Painter
        @Composable get() = painterResource(R.drawable.ic_bingxiang)
    val chuifengji: Painter
        @Composable get() = painterResource(R.drawable.ic_chuifengji)
    val chushiji: Painter
        @Composable get() = painterResource(R.drawable.ic_chushiji)
    val diancilu: Painter
        @Composable get() = painterResource(R.drawable.ic_diancilu)
    val diandongche: Painter
        @Composable get() = painterResource(R.drawable.ic_diandongche)
    val dianchi: Painter
        @Composable get() = painterResource(R.drawable.ic_dianchi)
    val dianfengshan: Painter
        @Composable get() = painterResource(R.drawable.ic_dianfengshan)
    val dianshuihu: Painter
        @Composable get() = painterResource(R.drawable.ic_dianshuihu)

    // ==========家具类==========
    // 餐具
    val canju: Painter
        @Composable get() = painterResource(R.drawable.ic_canju)
    val daoju: Painter
        @Composable get() = painterResource(R.drawable.ic_daoju)
    val chuju: Painter
        @Composable get() = painterResource(R.drawable.ic_chuju)

    val chuang: Painter
        @Composable get() = painterResource(R.drawable.ic_chuang)
    val dengju: Painter
        @Composable get() = painterResource(R.drawable.ic_dengju)
    val diannaozhuo: Painter
        @Composable get() = painterResource(R.drawable.ic_diannaozhuo)
    val dianshiji: Painter
        @Composable get() = painterResource(R.drawable.ic_dianshiji)

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
    val dingpa: Painter
        @Composable get() = painterResource(R.drawable.ic_dingpa)


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

    // 3. todo 创建不同分类的列表 再把所有需要遍历的图标加进来

    val digitalIcons: List<IconItem> = listOf(
        IconItem("手机", R.drawable.ic_shouji),
        IconItem("CPU", R.drawable.ic_cpu),
        IconItem("打印机", R.drawable.ic_dayinji),
        IconItem("电脑", R.drawable.ic_diannao),
        IconItem("电脑主板", R.drawable.ic_diannaozhuban),
    )

    val beautyProductsIcons: List<IconItem> = listOf(
        IconItem("彩妆粉", R.drawable.ic_caizhuangfen),
        IconItem("唇釉", R.drawable.ic_chunyou),
    )

    val clothesPantsShoesHatsIcons: List<IconItem> = listOf(
        IconItem("半裙", R.drawable.ic_banqun),
        IconItem("板鞋", R.drawable.ic_banxie),
        IconItem("包包", R.drawable.ic_baobao),
        IconItem("长袖", R.drawable.ic_changxiu),
        IconItem("衬衫", R.drawable.ic_chenshan),
        IconItem("短裤", R.drawable.ic_duanku),
    )

    val tablewareIcons: List<IconItem> = listOf(
        IconItem("餐具", R.drawable.ic_canju),
        IconItem("厨具", R.drawable.ic_chuju),
        IconItem("刀具", R.drawable.ic_daoju),
    )

    val furnitureIcons: List<IconItem> = listOf(
        IconItem("床", R.drawable.ic_chuang),
        IconItem("灯具", R.drawable.ic_dengju),
        IconItem("电脑桌", R.drawable.ic_diannaozhuo),
    )

    val instrumentIcons: List<IconItem> = listOf(
        IconItem("长笛", R.drawable.ic_changdi),
        IconItem("电子琴", R.drawable.ic_dianziqin),
        IconItem("贝斯", R.drawable.ic_beisi),
        IconItem("大提琴", R.drawable.ic_datiqin),
    )

    val toolIcons: List<IconItem> = listOf(
        IconItem("铲子", R.drawable.ic_chanzi),
        IconItem("插排", R.drawable.ic_chapai),
        IconItem("插座", R.drawable.ic_chazuo),
        IconItem("称", R.drawable.ic_cheng),
        IconItem("充电器", R.drawable.ic_chongdianqi),
        IconItem("电池", R.drawable.ic_dianchi),
        IconItem("钉耙", R.drawable.ic_dingpa),

    )

    val homeAppliances: List<IconItem> = listOf(
        IconItem("冰箱", R.drawable.ic_bingxiang),
        IconItem("吹风机", R.drawable.ic_chuifengji),
        IconItem("除湿机", R.drawable.ic_chushiji),
        IconItem("电动车", R.drawable.ic_diandongche),
        IconItem("电磁炉", R.drawable.ic_diancilu),
        IconItem("电饭煲", R.drawable.ic_dianfanbao),
        IconItem("电风扇", R.drawable.ic_dianfengshan),
        IconItem("电视机", R.drawable.ic_dianshiji),
        IconItem("电水壶", R.drawable.ic_dianshuihu),
    )

    val outdoorSports: List<IconItem> = listOf(
        IconItem("冲浪板", R.drawable.ic_chonglangban),
    )

    val otherIcons: List<IconItem> = listOf(
        IconItem("宠物用品", R.drawable.ic_chongwuyongpin),
    )

    val allIcons: List<IconItem> =
        digitalIcons + beautyProductsIcons + clothesPantsShoesHatsIcons +
                tablewareIcons + furnitureIcons + instrumentIcons + toolIcons + homeAppliances +
                outdoorSports + otherIcons
}