package ar.edu.unicen.mymovies.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}