package com.example.testtickettaskkotlin.di

import com.example.testtickettaskkotlin.mvvm.MainViewModel
import com.example.testtickettaskkotlin.serialization.ticketDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import servermock.IServerHandler
import servermock.ServerHandler

val appModule = module {

    single<IServerHandler> {ServerHandler(androidContext().ticketDataStore)}

    single { MainViewModel(get()) }
}