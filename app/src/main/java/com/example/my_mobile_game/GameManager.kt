package com.example.my_mobile_game

import android.util.Log
import com.example.my_mobile_game.utils.Constants
import kotlin.contracts.Returns
import kotlin.math.log
import kotlin.random.Random

class GameManager(private val lifeCount: Int = 3, private val cols: Int, private val rows: Int) {

    private var currentCharPosition = Constants.STARTING_POS
    private val appleMatrix: Array<Array<Boolean>> = Array(rows) { Array(cols) { false } }

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
        }
    }

    fun moveRight() {
        if (currentCharPosition < cols - 1) {
            currentCharPosition++
            if (isCollision()) {
                handleCollision()
            }
        }
    }

    fun getCurrentPosition(): Int {
        return currentCharPosition
    }


    // Returns the current visibility state of the apple at a specific position
    fun isAppleVisible(row: Int, col: Int): Boolean {
        return appleMatrix[row][col]
    }

    // Sets the visibility of an apple at a specific position
    fun setAppleVisibility(row: Int, col: Int, isVisible: Boolean) {
        appleMatrix[row][col] = isVisible
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
            appleMatrix[0][j] = false
        }
//        logAppleMatrix()
    }

    fun logAppleMatrix() {
        Log.d("AppleMatrix", "Current Apple Matrix:")
        appleMatrix.forEachIndexed { rowIndex, row ->
            Log.d("AppleMatrix", "Row $rowIndex: ${row.joinToString { if (it) "1" else "0" }}")
        }
    }

    fun spawnApple() {
        val randomCol = Random.nextInt(cols) // Randomly pick a column index
        appleMatrix[0][randomCol] = true // Set the apple as visible in the top row
    }


    fun isCollision(): Boolean {
        return appleMatrix[appleMatrix.size - 1][currentCharPosition]
    }

    fun handleCollision() {
        Log.d("AppleMatrix", "Last row: ${appleMatrix[appleMatrix.size - 1].joinToString()}")
        Log.d("A", "collision!")
        failureCount++

    }


}