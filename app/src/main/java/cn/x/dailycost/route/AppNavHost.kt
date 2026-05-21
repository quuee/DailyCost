package cn.x.dailycost.route


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import cn.x.dailycost.data.entity.GoodsItemEntity
import cn.x.dailycost.ui.screen.DemoScreen
import cn.x.dailycost.ui.screen.goods.GoodsScreen
import cn.x.dailycost.ui.screen.main.MainScreen
import cn.x.dailycost.ui.screen.my.CategoryManagerScreen
import cn.x.dailycost.ui.screen.my.DataSyncScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Main.route,
    ) {

        composable(route = Routes.Main.route) {
            MainScreen(navController)
        }
        composable(
            route = Routes.Goods.route.plus("/{gid}"),
            arguments = listOf(
                navArgument("gid") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            val gid = backStackEntry.arguments?.getLong("gid")
            GoodsScreen(navController = navController, gid = gid)
        }
        composable(
            route = Routes.CategoryManager.route
        ) {
            CategoryManagerScreen(navController = navController)
        }
        composable(
            route = Routes.DataSync.route
        ) {
            DataSyncScreen(navController = navController)
        }

        composable(
            route = Routes.Demo.route
        ) {
            DemoScreen()
        }

    }
}