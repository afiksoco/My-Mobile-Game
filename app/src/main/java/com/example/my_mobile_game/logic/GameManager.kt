package com.example.my_mobile_game.logic

import android.content.Context
import com.example.my_mobile_game.R
import com.example.my_mobile_game.utils.Constants
import com.example.my_mobile_game.utils.SignalManager
import com.example.my_mobile_game.utils.SingleSoundPlayer
import kotlin.random.Random

class GameManager(
    private val context: Context,
    private val lifeCount: Int = 3,
    private val cols: Int,
    private val rows: Int
) {


    private var currentCharPosition = Constants.GameLogic.STARTING_POS
    private val appleMatrix: Array<Array<Int>> = Array(rows) { Array(cols) { 0 } }

    var consecutiveSpawns = 0 // Tracks consecutive apple spawns

    var score: Int = 0

    var failureCount: Int = 0
        private set

    val isGameOver: Boolean
        get() = failureCount == lifeCount

    fun moveLeft() {
        if (currentCharPosition > 0) {
            currentCharPosition--
            if (isCollision()) {
                handleCollision()
            }
            if (isPrize()) {
                score += Constants.GameLogic.POINTS_PER_ANDROID
            }
        }
    }

    fun moveRight() {
        if (currentCharPosition < cols - 1) {
            currentCharPosition++
            if (isCollision()) {
                handleCollision()
            }
            if (isPrize()) {
                score += Constants.GameLogic.POINTS_PER_ANDROID

            }
        }
    }

    fun getCurrentPosition(): Int {
        return currentCharPosition
    }


    // Returns the current visibility state of the apple at a specific position
//    fun isAppleVisible(row: Int, col: Int): Boolean {
//        return appleMatrix[row][col]
//    }
    fun getCellState(row: Int, col: Int): Int {
        return appleMatrix[row][col]
    }

    // Logic to move the logos down by one row
    fun moveLogosDown() {
        // Shift each row down
        for (i in rows - 1 downTo 1) {
            for (j in 0 until cols) {
                appleMatrix[i][j] = appleMatrix[i - 1][j]
            }

        }

//         Clear the first row (set all to invisible)
        for (j in 0 until cols) {
            appleMatrix[0][j] = 0
        }
    }


    fun spawnApple() {
        if (consecutiveSpawns < cols - 1) { // Allow spawning only if less than 2 consecutive spawns
            val randomCol = Random.nextInt(cols) // Randomly pick a column index
            val imageInt = if (Random.nextInt(100) < Constants.GameLogic.ANDROID_SPAWN_CHANCE)
                Constants.Images.ANDROID else Constants.Images.APPLE       // Set the apple as visible in the top row
            appleMatrix[0][randomCol] = imageInt
            consecutiveSpawns++ // Increment the counter for consecutive spawns
        } else {
            // Skip spawning and reset the counter
            consecutiveSpawns = 0
        }
    }


    fun isCollision(): Boolean {
        return appleMatrix[appleMatrix.size - 1][currentCharPosition] == 1
    }

    fun isPrize(): Boolean {
        return appleMatrix[appleMatrix.size - 1][currentCharPosition] == 2
    }

    fun handleCollision() {
        var ssp: SingleSoundPlayer = SingleSoundPlayer(context)
        ssp.playSound(R.raw.boom)
        failureCount++
        SignalManager.getInstance().toast("Tom met an apple user!")
        SignalManager.getInstance().vibrate()

    }


}