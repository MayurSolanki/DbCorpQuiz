package com.dbcorp.quiz

import android.app.Application
import com.google.firebase.FirebaseApp


class DbCorpApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}