package cn.x.dailycost.di

import androidx.room.Room
import cn.x.dailycost.data.DailyCostData
import cn.x.dailycost.data.dao.CategoryDao
import cn.x.dailycost.data.dao.GoodsItemDao
import cn.x.dailycost.ui.screen.main.MainScreenVM
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

// 数据库模块
val databaseModule: Module = module {

    // Room 数据库实例
    single {
        Room.databaseBuilder(
            androidApplication(),
            DailyCostData::class.java,
            "daily_cost_database"
        ).fallbackToDestructiveMigration(false)
            .build()
    }

    // DAO
    single { get<DailyCostData>().categoryDao() }
    single { get<DailyCostData>().goodItemsDao() }
}

// ViewModel 模块 (使用 Koin Compose 4.x 语法)
val viewModelModule: Module = module {
    viewModel<MainScreenVM> {
        MainScreenVM(
            goodsItemDao = get<GoodsItemDao>(),
            categoryDao = get<CategoryDao>()
        )
    }


}