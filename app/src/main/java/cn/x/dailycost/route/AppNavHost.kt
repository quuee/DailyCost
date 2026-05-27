package cn.x.dailycost.route


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import cn.x.dailycost.ui.screen.goods.GoodsScreen
import cn.x.dailycost.ui.screen.main.MainScreen
import cn.x.dailycost.ui.screen.my.CategoryManagerScreen
import cn.x.dailycost.ui.screen.my.DataSyncScreen


// 使用 CompositionLocal 避免层层传递
val LocalNavigator = staticCompositionLocalOf<AppNavigator> {
    error("Navigator not provided! Wrap your app with ProvideNavigator.")
}

@Composable
fun AppNavHost() {
    val backStack = rememberNavBackStack(Routes.Main)
    val navigator = remember(backStack) { AppNavigatorImpl(backStack) }

    CompositionLocalProvider(LocalNavigator provides navigator) {
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<Routes.Main> {
                    MainScreen()
                }
                entry<Routes.Goods> { key ->
                    GoodsScreen(
                        gid = key.itemId,
                        onBack = { navigator.popBack() }
                    )
                }
                entry<Routes.CategoryManager> {
                    CategoryManagerScreen()
                }
                entry<Routes.DataSync> {
                    DataSyncScreen()
                }
            }
        )
    }


}