package com.example.my_mobile_game.utils

class Constants {
    object GameLogic {

        const val ANDROID_SPAWN_CHANCE: Int = 20
        const val COLS = 5
        const val ROWS = 7
        const val STARTING_POS: Int = COLS / 2
        const val POINTS_PER_ANDROID: Int = 5
    }

    object Images {
        const val APPLE = 1
        const val ANDROID = 2
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

    object Difficulties {
        const val EASY_MODE_DELAY: Long = 900L
        const val HARD_MODE_DELAY: Long = 500L
    }

    object SPKeys {
        const val SCORES_KEY = "SCORES_KEY"
    }

    object RequestCodes {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}