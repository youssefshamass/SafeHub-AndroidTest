package com.youssefshamass.safehub

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.youssefshamass.core.di.coreModule
import com.youssefshamass.data.di.dataModule
import com.youssefshamass.domain.di.domainModule
import com.youssefshamass.safehub.di.appModule
import com.youssefshamass.safehub.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        initDi()
        initLogger()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@Application)
            modules(coreModule, dataModule, domainModule, appModule, viewModelModule)
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            val oldHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler { t, e ->
                Timber.e(e)
                oldHandler.uncaughtException(t, e)
            }
        }
    }
}
