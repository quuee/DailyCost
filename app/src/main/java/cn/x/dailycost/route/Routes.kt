package cn.x.dailycost.route

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes : Parcelable {

    // 主界面 bottomBar
    @Serializable
    @Parcelize
    data object Main : Routes()

    @Serializable
    @Parcelize
    data object Hold : Routes()

    @Serializable
    @Parcelize
    data object My : Routes()

    @Serializable
    @Parcelize
    data object Goods : Routes()
    @Serializable
    @Parcelize
    data object DataSync : Routes()
    @Serializable
    @Parcelize
    data object CategoryManager : Routes()
}