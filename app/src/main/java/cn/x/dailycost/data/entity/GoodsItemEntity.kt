package cn.x.dailycost.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "goods_item")
data class GoodsItemEntity(

    @PrimaryKey(autoGenerate = true)
    val gid: Long,

    // 物品名称
    val goodsName: String,

    // 购买价格
    val price: Double,

    // 分类id
    val cid: Long,

    // 系统图标icon
    val iconInt: Int,

    // 购入日期
    val buyDate: Long,

    // 退役日期(可选)
    val endDate: Long,

    // 备注(可选)
    val remark: String,

    // 真实图片(可选)
    val realPictureUri: String,

    //
    val createDate: Long = System.currentTimeMillis()
)