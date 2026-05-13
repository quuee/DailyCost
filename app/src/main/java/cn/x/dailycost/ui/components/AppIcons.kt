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
        IconItem("耳机", R.drawable.ic_erji),
        IconItem("风扇", R.drawable.ic_fengshan),
        IconItem("镜头", R.drawable.ic_jingtou),
        IconItem("机器人", R.drawable.ic_jiqiren),
        IconItem("录音机", R.drawable.ic_luyinji),
        IconItem("路由器", R.drawable.ic_luyouqi),
        IconItem("麦克风", R.drawable.ic_maikefeng),
        IconItem("NAS", R.drawable.ic_nas),
        IconItem("内存", R.drawable.ic_neicun),
        IconItem("平板", R.drawable.ic_pad),
        IconItem("卡带", R.drawable.ic_pad),
    )

    val beautyProductsIcons: List<IconItem> = listOf(
        IconItem("彩妆粉", R.drawable.ic_caizhuangfen),
        IconItem("唇釉", R.drawable.ic_chunyou),
        IconItem("防晒霜", R.drawable.ic_fangshaishuang),
        IconItem("粉底液", R.drawable.ic_fendiye),
        IconItem("隔离霜", R.drawable.ic_gelishuang),
        IconItem("化妆蛋", R.drawable.ic_huazhuangdan),
        IconItem("化妆包", R.drawable.ic_huazhuangbao),
        IconItem("化妆镜", R.drawable.ic_huazhuangjing),
        IconItem("化妆品", R.drawable.ic_huazhuangpin),
        IconItem("化妆刷", R.drawable.ic_huazhuangshua),
        IconItem("护脸霜", R.drawable.ic_hulianshuang),
        IconItem("假睫毛", R.drawable.ic_jiajiemao),
        IconItem("睫毛膏", R.drawable.ic_jiemaogao),
        IconItem("洁面乳", R.drawable.ic_jiemianru),
        IconItem("精华液", R.drawable.ic_jinghuaye),
        IconItem("卷发棒", R.drawable.ic_juanfabang),
        IconItem("口红", R.drawable.ic_kouhong),
        IconItem("美甲", R.drawable.ic_meijia),
        IconItem("美容", R.drawable.ic_meirong),
        IconItem("美容仪", R.drawable.ic_meirongyi),
        IconItem("美瞳", R.drawable.ic_meitong),
        IconItem("气垫", R.drawable.ic_qidian),
        IconItem("润唇膏", R.drawable.ic_runchungao),
        IconItem("乳液", R.drawable.ic_ruye),
        IconItem("腮红", R.drawable.ic_saihong),
    )

    val clothesPantsShoesHatsIcons: List<IconItem> = listOf(
        IconItem("半裙", R.drawable.ic_banqun),
        IconItem("板鞋", R.drawable.ic_banxie),
        IconItem("包包", R.drawable.ic_baobao),
        IconItem("长袖", R.drawable.ic_changxiu),
        IconItem("衬衫", R.drawable.ic_chenshan),
        IconItem("短裤", R.drawable.ic_duanku),
        IconItem("耳环", R.drawable.ic_erhuan),
        IconItem("风衣", R.drawable.ic_fengyi),
        IconItem("腰带", R.drawable.ic_fuzhuang_yaodai),
        IconItem("高跟鞋", R.drawable.ic_gaogenxie),
        IconItem("公文包", R.drawable.ic_gongwenbao),
        IconItem("汉服", R.drawable.ic_hanfu),
        IconItem("夹克", R.drawable.ic_jiake),
        IconItem("挎包", R.drawable.ic_kuabao),
        IconItem("裤子", R.drawable.ic_kuzi),
        IconItem("连衣裙", R.drawable.ic_lianyiqun),
        IconItem("毛衣", R.drawable.ic_maoyi),
        IconItem("毛衣开衫", R.drawable.ic_maoyikaishan),
        IconItem("帽子", R.drawable.ic_maozi),
        IconItem("墨镜", R.drawable.ic_mojing),
        IconItem("内衣裤", R.drawable.ic_neiyiku),
        IconItem("尿不湿", R.drawable.ic_niaobushi),
        IconItem("皮鞋", R.drawable.ic_pixie),
        IconItem("钱包", R.drawable.ic_qianbao),
        IconItem("旗袍", R.drawable.ic_qipao),
        IconItem("热裤", R.drawable.ic_reku),
    )

    val tablewareIcons: List<IconItem> = listOf(
        IconItem("餐具", R.drawable.ic_canju),
        IconItem("厨具", R.drawable.ic_chuju),
        IconItem("刀具", R.drawable.ic_daoju),
        IconItem("锅具", R.drawable.ic_guoju),
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
        IconItem("二胡", R.drawable.ic_erhu),
        IconItem("古筝", R.drawable.ic_guzheng),
        IconItem("架子鼓", R.drawable.ic_jiazigu),
        IconItem("吉他", R.drawable.ic_jita),
        IconItem("钢琴", R.drawable.ic_piano),
        IconItem("枇杷", R.drawable.ic_pipa),
        IconItem("萨克斯", R.drawable.ic_sakesi), // todo sakesi
    )

    val toolIcons: List<IconItem> = listOf(
        IconItem("铲子", R.drawable.ic_chanzi),
        IconItem("插排", R.drawable.ic_chapai),
        IconItem("插座", R.drawable.ic_chazuo),
        IconItem("称", R.drawable.ic_cheng),
        IconItem("充电器", R.drawable.ic_chongdianqi),
        IconItem("电池", R.drawable.ic_dianchi),
        IconItem("钉耙", R.drawable.ic_dingpa),
        IconItem("工具", R.drawable.ic_gongju),
        IconItem("剪刀", R.drawable.ic_jiandao),
        IconItem("脸盆", R.drawable.ic_lianpeng),
        IconItem("毛巾", R.drawable.ic_maojin),
        IconItem("喷壶", R.drawable.ic_penhu),
        IconItem("清洁", R.drawable.ic_qingjiyongpin),

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
        IconItem("高压锅", R.drawable.ic_gaoyaguo),
        IconItem("烘干机", R.drawable.ic_hongganji),
        IconItem("花洒", R.drawable.ic_huasa),
        IconItem("监控", R.drawable.ic_jiankong),
        IconItem("加湿器", R.drawable.ic_jiashiqi),
        IconItem("净水器", R.drawable.ic_jingshuiqi),
        IconItem("咖啡机", R.drawable.ic_kafeiji),
        IconItem("烤箱", R.drawable.ic_kaoxiang),
        IconItem("空气炸锅", R.drawable.ic_kongqizhaguo),
        IconItem("空调", R.drawable.ic_kongtiao),
        IconItem("马桶", R.drawable.ic_matong),
        IconItem("面包机", R.drawable.ic_mianbaoji),
        IconItem("燃气灶", R.drawable.ic_ranqizao),
        IconItem("热水器", R.drawable.ic_reshuiqi),

    )

    val outdoorSports: List<IconItem> = listOf(
        IconItem("冲浪板", R.drawable.ic_chonglangban),
        IconItem("滑板", R.drawable.ic_huaban),
        IconItem("篮球", R.drawable.ic_lanqiu),
        IconItem("摩托车", R.drawable.ic_motuoche),
        IconItem("排球", R.drawable.ic_paiqiu),
        IconItem("跑步机", R.drawable.ic_paobuji),
        IconItem("皮划艇", R.drawable.ic_pihuating),
        IconItem("平衡车", R.drawable.ic_pinghengche),
        IconItem("乒乓", R.drawable.ic_pipang),
        IconItem("潜水镜", R.drawable.ic_qianshuizhuangbei),
        IconItem("汽车", R.drawable.ic_qiche),
    )

    val otherIcons: List<IconItem> = listOf(
        IconItem("宠物用品", R.drawable.ic_chongwuyongpin),
        IconItem("狗粮", R.drawable.ic_gouliang),
        IconItem("房产", R.drawable.ic_fangchang),
        IconItem("罐头", R.drawable.ic_guantou),
        IconItem("行李箱", R.drawable.ic_hanglixiang),
        IconItem("刮胡刀", R.drawable.ic_guahudao),
        IconItem("会员卡", R.drawable.ic_huiyuanka),
        IconItem("礼物", R.drawable.ic_liwu),
        IconItem("猫粮", R.drawable.ic_maoliang),
        IconItem("母婴", R.drawable.ic_muying),
        IconItem("奶瓶", R.drawable.ic_naiping),
        IconItem("卫生纸", R.drawable.ic_paper),
        IconItem("盆栽", R.drawable.ic_penzai),
        IconItem("PPR软管", R.drawable.ic_pprruanguan),
        IconItem("日用品", R.drawable.ic_riyongpin),
    )

    val allIcons: List<IconItem> =
        digitalIcons + beautyProductsIcons + clothesPantsShoesHatsIcons +
                tablewareIcons + furnitureIcons + instrumentIcons + toolIcons + homeAppliances +
                outdoorSports + otherIcons
}