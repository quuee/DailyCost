package cn.x.dailycost.data.entity


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import cn.x.dailycost.R


@Entity(tableName = "goods_item")
data class GoodsItemEntity(

    @PrimaryKey(autoGenerate = true)
    val gid: Long,

    // 物品名称
    val goodsName: String,

    // 购买价格
    val price: Double,

    // 分类id, 0 代表全部或无分类
    val cid: Long = 0L,

    // 系统图标icon
    val iconInt: Int,

    // 购入日期
    val buyDate: Long = System.currentTimeMillis(),

    // 卖了回血
    val recoverHealthMoney: Double? = 0.0,

    // 退役日期(可选)
    val retireDate: Long = 0L,

    // 0 未退役 ; 1 已退役
//    val retireState: Int = 0,

    // 备注(可选)
    val remark: String?,

    // 真实图片(可选)
    val realPictureUri: String?,

    // 创建时间
    val createDate: Long = System.currentTimeMillis(),

//    @Ignore
//    val bgColor: Int = Color(0XFFF8F1E4).toArgb(),
)