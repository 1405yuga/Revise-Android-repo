package com.example.reviseapp1.database

import android.app.Application

class BaseApplication : Application() {

    val database : MyDataBase by lazy {
        MyDataBase.getDatabase(this)
    }
}