package com.challenge.patagonian

import android.content.Context
import com.challenge.patagonian.di.appComponents
import com.challenge.patagonian.di.networkComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Initializer(private val context: Context) {
        fun initKoin(){
                startKoin {
                        androidLogger()
                        androidContext(context)
                        modules(
                                appComponents +
                                        networkComponent
                        )
                }
        }
}