package com.takeatrip.utils

import android.content.Context
import android.content.SharedPreferences
import com.takeatrip.models.meal.MealData
import com.takeatrip.utils.Constants.SHARED_AUTH_TOKEN
import com.takeatrip.utils.Constants.SHARED_ORG_ADDED
import com.takeatrip.utils.Constants.SHARED_PREFERENCE_ID
import com.takeatrip.utils.Constants.SHARED_USER_ID
import java.util.*
import com.google.gson.Gson
import com.takeatrip.utils.Constants.SHARED_HOTEL_PRICE_MAP
import java.util.HashMap

import com.google.gson.reflect.TypeToken
import com.takeatrip.utils.Constants.SHARED_WITHOUT_MATTRESS_PRICE_MAP
import com.takeatrip.utils.Constants.SHARED_WITH_MATTRESS_PRICE_MAP


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

    fun saveHotelPriceMap(context: Context, hotelPriceMap: TreeMap<String, TreeMap<String, MealData>>){
        val gson = Gson()
        val hashMapString = gson.toJson(hotelPriceMap)
        editor = getInstance(context).edit()
        editor.putString(SHARED_HOTEL_PRICE_MAP, hashMapString)
        editor.apply()
    }

    fun getWithMattressPriceMap(context: Context): TreeMap<String, TreeMap<String, MealData>>{
        val type = object : TypeToken<TreeMap<String, TreeMap<String, MealData>>>() {}.type
        return Gson().fromJson(getInstance(context).getString(SHARED_WITH_MATTRESS_PRICE_MAP, "{}"), type)
    }

    fun saveWithMattressPriceMap(context: Context, hotelPriceMap: TreeMap<String, TreeMap<String, MealData>>){
        val gson = Gson()
        val hashMapString = gson.toJson(hotelPriceMap)
        editor = getInstance(context).edit()
        editor.putString(SHARED_WITH_MATTRESS_PRICE_MAP, hashMapString)
        editor.apply()
    }

    fun getWithoutMattressPriceMap(context: Context): TreeMap<String, TreeMap<String, MealData>>{
        val type = object : TypeToken<TreeMap<String, TreeMap<String, MealData>>>() {}.type
        return Gson().fromJson(getInstance(context).getString(SHARED_WITHOUT_MATTRESS_PRICE_MAP, "{}"), type)
    }

    fun saveWithoutMattressPriceMap(context: Context, hotelPriceMap: TreeMap<String, TreeMap<String, MealData>>){
        val gson = Gson()
        val hashMapString = gson.toJson(hotelPriceMap)
        editor = getInstance(context).edit()
        editor.putString(SHARED_WITHOUT_MATTRESS_PRICE_MAP, hashMapString)
        editor.apply()
    }

    fun getHotelPriceMap(context: Context): TreeMap<String, TreeMap<String, MealData>>{
        val type = object : TypeToken<TreeMap<String, TreeMap<String, MealData>>>() {}.type
        return Gson().fromJson(getInstance(context).getString(SHARED_HOTEL_PRICE_MAP, "{}"), type)
    }

    private fun getInstance(context: Context) = INSTANCE?: context.getSharedPreferences(SHARED_PREFERENCE_ID, Context.MODE_PRIVATE).also {
        INSTANCE = it
    }
}