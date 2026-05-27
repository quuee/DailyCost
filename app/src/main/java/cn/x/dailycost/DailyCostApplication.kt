package cn.x.dailycost

import android.app.Application
import cn.x.dailycost.di.databaseModule
import cn.x.dailycost.di.viewModelModule
import cn.x.dailycost.util.SPUtil
import cn.x.dailycost.util.ToastUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DailyCostApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SPUtil.init(this)
        ToastUtil.init(this)

        // 初始化 Koin DI
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DailyCostApplication)
            modules(
//                networkModule,      // Retrofit 配置
                databaseModule,     // Room 配置
//                repositoryModule,   // 仓库层
                viewModelModule     // ViewModel 工厂
            )
        }
    }
}