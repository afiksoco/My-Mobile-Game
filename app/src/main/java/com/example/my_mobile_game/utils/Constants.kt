package com.example.my_mobile_game.utils

class Constants {
    object GameLogic {
        const val COLS = 5
        const val ROWS = 7
        const val STARTING_POS: Int = COLS / 2
        const val POINTS_PER_SECOND: Int = 1
    }

    object PlayModes {
        const val TILT = "Tilt"
        const val CONTROLS = "Controls"
    }

    object BundleKeys {
        const val SCORE_KEY = "SCORE_KEY"
        const val PLAY_MODE_KEY = "PLAY_MODE_KEY"
        const val DIFFICULTY_KEY = "DIFFICULTY_KEY"
    }

    object Difficulties{
        const val EASY_MODE_DELAY: Long = 900L
        const val HARD_MODE_DELAY: Long = 500L
    }
}