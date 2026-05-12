package cn.x.dailycost.route



sealed class Routes(val route: String)  {

    // 主界面 bottomBar
    data object Main : Routes("main")


    data object Goods : Routes("goods")


    data object DataSync : Routes("dataSync")

    data object CategoryManager : Routes("categoryManager")

    data object Demo : Routes("demo")
}