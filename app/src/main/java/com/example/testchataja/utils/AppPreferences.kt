package com.example.testchataja.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class AppPreferences(private val context: Context) {
    private lateinit var pref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    fun setPreferences() {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setNameUser(nameUser1 : String) {
        editor = this.pref.edit()
        editor.putString(ConstString().uName,nameUser1)
        editor.apply()
    }

    fun getNameUser() : String? {
        return pref.getString(ConstString().uName,"")
    }
}