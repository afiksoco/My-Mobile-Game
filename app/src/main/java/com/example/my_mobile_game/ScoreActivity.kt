package com.example.my_mobile_game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.my_mobile_game.utils.Constants
import com.google.android.material.textview.MaterialTextView

class ScoreActivity : AppCompatActivity() {

    private lateinit var score_LBL_score: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        findViews()
        initViews()
    }

    private fun findViews() {
        score_LBL_score = findViewById(R.id.score_LBL_score)
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras

        val score = bundle?.getInt(Constants.BundleKeys.SCORE_KEY, 0)

        score_LBL_score.text = buildString {
            append("Game Over")
            append("\n")
            append(score)
        }
    }
}