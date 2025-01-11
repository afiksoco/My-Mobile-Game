package com.example.my_mobile_game.utils

import Score
import Scorelist
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        Constants.SPKeys.SCORES_KEY,
        Context.MODE_PRIVATE
    )
    private val gson = Gson()


    companion object {
        @Volatile
        private var instance: SharedPreferencesManager? = null

        fun init(context: Context): SharedPreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferencesManager(context).also { instance = it }
            }
        }

        fun getInstance(): SharedPreferencesManager {
            return instance ?: throw IllegalStateException(
                "SharedPreferencesManager must be initialized by calling init(context) before use."
            )
        }
    }


    fun addScore(score: Score) {
        val scores = getScores(Constants.SPKeys.SCORES_KEY).toMutableList()
        scores.add(score)
        scores.sortByDescending { it.score }
        saveScores(scores.take(10))
    }

    fun saveScores(scoreList: List<Score>) {
        val json = gson.toJson(scoreList)
        sharedPreferences.edit()
            .putString(Constants.SPKeys.SCORES_KEY, json)
            .apply()
    }

    fun getScores(key: String): List<Score> {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            val type = object : TypeToken<List<Score>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }


}