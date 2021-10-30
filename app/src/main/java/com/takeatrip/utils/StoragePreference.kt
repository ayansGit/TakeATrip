package com.takeatrip.utils

import android.content.Context
import android.content.SharedPreferences
import com.takeatrip.utils.Constants.SHARED_AUTH_TOKEN
import com.takeatrip.utils.Constants.SHARED_ORG_ADDED
import com.takeatrip.utils.Constants.SHARED_PREFERENCE_ID
import com.takeatrip.utils.Constants.SHARED_USER_ID

object StoragePreference {

    private var INSTANCE: SharedPreferences?= null
    private lateinit var editor: SharedPreferences.Editor

    fun clearData(context: Context){
        editor = getInstance(context).edit()
        editor.clear()
        editor.apply()
    }

    fun setToken(context: Context, token: String){
        editor = getInstance(context).edit()
        editor.putString(SHARED_AUTH_TOKEN, token)
        editor.apply()
    }

    fun getToken(context: Context): String?{
        return getInstance(context).getString(SHARED_AUTH_TOKEN, "")
    }

    fun setUserId(context: Context, userId: String){
        editor = getInstance(context).edit()
        editor.putString(SHARED_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(context: Context): String?{
        return getInstance(context).getString(SHARED_USER_ID, "")
    }

    fun setIsOrganisationAdded(context: Context, userId: String){
        editor = getInstance(context).edit()
        editor.putString(SHARED_ORG_ADDED, userId)
        editor.apply()
    }

    fun getIsOrganisationAdded(context: Context): String?{
        return getInstance(context).getString(SHARED_ORG_ADDED, "No")
    }

    private fun getInstance(context: Context) = INSTANCE?: context.getSharedPreferences(SHARED_PREFERENCE_ID, Context.MODE_PRIVATE).also {
        INSTANCE = it
    }
}