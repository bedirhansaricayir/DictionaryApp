package com.dictionary.android

import android.app.Application
import com.dictionary.android.feature_dictionary.presentation.wotd.NotificationWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DictionaryApp: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationWorker.start(applicationContext)
    }
}