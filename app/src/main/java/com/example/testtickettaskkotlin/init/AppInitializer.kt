package com.example.testtickettaskkotlin.init

import android.content.Context
import androidx.startup.Initializer
import com.example.testtickettaskkotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppInitializer : Initializer<Boolean> {
    override fun create(context: Context): Boolean {

        startKoin {
            androidContext(context)
            modules(appModule)
        }

        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}