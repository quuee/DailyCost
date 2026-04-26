package cn.x.dailycost.route


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.x.dailycost.ui.screen.add.AddScreen
import cn.x.dailycost.ui.screen.main.MainScreen


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
            composable<Routes.Add> {
                AddScreen(navController)
            }
        }
    )
}