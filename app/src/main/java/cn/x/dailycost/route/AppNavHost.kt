package cn.x.dailycost.route


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.x.dailycost.ui.screen.goods.GoodsScreen
import cn.x.dailycost.ui.screen.main.MainScreen
import cn.x.dailycost.ui.screen.my.CategoryManagerScreen
import cn.x.dailycost.ui.screen.my.DataSyncScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Main,  // 类型安全，编译时检查
        builder = {
            composable<Routes.Main> {
                MainScreen(navController)
            }
            composable<Routes.Goods> {
                GoodsScreen()
            }
            composable<Routes.CategoryManager> {
                CategoryManagerScreen(navController = navController)
            }
            composable<Routes.DataSync> {
                DataSyncScreen()
            }
        }
    )
}