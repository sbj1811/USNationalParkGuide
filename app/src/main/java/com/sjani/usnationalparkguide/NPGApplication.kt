package com.sjani.usnationalparkguide

import android.app.Application
import com.google.firebase.FirebaseApp

class NPGApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}


