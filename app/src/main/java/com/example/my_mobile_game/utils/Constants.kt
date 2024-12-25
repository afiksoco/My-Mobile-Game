package com.example.my_mobile_game.utils

import com.google.android.material.color.utilities.Score

class Constants {
    object GameLogic {
        const val COLS = 5
        const val ROWS = 7
        const val DELAY: Long = 700L
        const val STARTING_POS: Int = COLS / 2
        const val POINTS_PER_SECOND: Int = 1
    }

    object BundleKeys {
        const val SCORE_KEY = "SCORE_KEY"
    }
}