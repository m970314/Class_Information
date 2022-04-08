package com.programmers.kmooc.network

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.programmers.kmooc.models.Lecture
import com.google.gson.Gson

@ProvidedTypeConverter
class Converter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: ArrayList<Lecture>): String? {
        return gson.toJson(value)
    }
    @TypeConverter
    fun jsonToList(value: String): ArrayList<Lecture> {
        return gson.fromJson(value, Array<Lecture>::class.java).toList() as ArrayList<Lecture>
    }
}