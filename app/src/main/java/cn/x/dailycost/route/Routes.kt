package cn.x.dailycost.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


sealed class Routes {

    // 主界面 bottomBar
    @Serializable
    data object Main : NavKey

    @Serializable
    data class Goods(val itemId: Long) : NavKey

    @Serializable
    data object DataSync : NavKey

    @Serializable
    data object CategoryManager : NavKey

    @Serializable
    data object Demo : NavKey
}