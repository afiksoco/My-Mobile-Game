package com.example.my_mobile_game

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.my_mobile_game.logic.GameManager
import com.example.my_mobile_game.utils.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {


    private lateinit var gameManager: GameManager
    private lateinit var main_FAB_leftarrow: ExtendedFloatingActionButton
    private lateinit var main_FAB_rightarrow: ExtendedFloatingActionButton
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var main_IMG_char: Array<AppCompatImageView>
    private lateinit var main_IMG_apple: Array<Array<AppCompatImageView>>
    private lateinit var main_LBL_score: MaterialTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size, main_IMG_apple[0].size, main_IMG_apple.size)
        initViews()
    }


    override fun onResume() {
        super.onResume()
        if (!gameStarted && !gameManager.isGameOver) {
            handler.postDelayed(runnable, Constants.GameLogic.DELAY)
            gameStarted = true
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
        // If you want the game to truly "stop" when paused, you might set:
        gameStarted = false
    }


    val handler: Handler = Handler(Looper.getMainLooper())

    private var gameStarted: Boolean = false

    private var gameOverHandled = false

    val runnable: Runnable = object : Runnable {
        override fun run() {
            //reschedule:
            handler.postDelayed(this, Constants.GameLogic.DELAY)
            //refresh UI:
            if (gameManager.isGameOver) {
                if (!gameOverHandled) {
                    gameOverHandled = true // Ensure activity change happens only once
                    changeActivity(gameManager.score)
                }
            } else {
                gameManager.score += Constants.GameLogic.POINTS_PER_SECOND
                updateScoreUI()
                gameManager.moveLogosDown()
                gameManager.spawnApple()
                updateAppleUI()
                if (gameManager.isCollision())
                    gameManager.handleCollision()
                updateHeartsUI()
            }
        }
    }


    private fun findViews() {
        main_FAB_rightarrow = findViewById(R.id.main_FAB_rightarrow)
        main_FAB_leftarrow = findViewById(R.id.main_FAB_leftarrow)
        main_LBL_score = findViewById(R.id.main_LBL_score)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
        main_IMG_char = arrayOf(
            findViewById(R.id.main_IMG_tomco1),
            findViewById(R.id.main_IMG_tomco2),
            findViewById(R.id.main_IMG_tomco3)
        )
        main_IMG_apple = arrayOf(
            arrayOf(
                findViewById(R.id.main_MAT_00),
                findViewById(R.id.main_MAT_01),
                findViewById(R.id.main_MAT_02)
            ),
            arrayOf(
                findViewById(R.id.main_MAT_10),
                findViewById(R.id.main_MAT_11),
                findViewById(R.id.main_MAT_12)
            ),
            arrayOf(
                findViewById(R.id.main_MAT_20),
                findViewById(R.id.main_MAT_21),
                findViewById(R.id.main_MAT_22)
            ),
            arrayOf(
                findViewById(R.id.main_MAT_30),
                findViewById(R.id.main_MAT_31),
                findViewById(R.id.main_MAT_32)
            ),
            arrayOf(
                findViewById(R.id.main_MAT_40),
                findViewById(R.id.main_MAT_41),
                findViewById(R.id.main_MAT_42)
            ),
            arrayOf(
                findViewById(R.id.main_MAT_50),
                findViewById(R.id.main_MAT_51),
                findViewById(R.id.main_MAT_52)
            ),
            arrayOf(
                findViewById(R.id.main_MAT_60),
                findViewById(R.id.main_MAT_61),
                findViewById(R.id.main_MAT_62)
            ),

            )

    }

    private fun initViews() {

        main_LBL_score.text = "${gameManager.score}"

        main_FAB_leftarrow.setOnClickListener {
            gameManager.moveLeft()
            updateCharUI()
            updateHeartsUI()
        }
        main_FAB_rightarrow.setOnClickListener {
            gameManager.moveRight()
            updateCharUI()
            updateHeartsUI()
        }
    }

    private fun updateCharUI() {
        for (charImageView in main_IMG_char) {
            charImageView.visibility = View.INVISIBLE
        }
        main_IMG_char[gameManager.getCurrentPosition()].visibility = View.VISIBLE
    }

    private fun updateAppleUI() {
        for (i in main_IMG_apple.indices) {
            for (j in main_IMG_apple[i].indices) {
                main_IMG_apple[i][j].visibility =
                    if (gameManager.isAppleVisible(i, j)) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private fun updateHeartsUI() {
        if (gameManager.failureCount != 0) {
            main_IMG_hearts[main_IMG_hearts.size - gameManager.failureCount].visibility =
                View.INVISIBLE
        }
    }

    private fun updateScoreUI() {
        main_LBL_score.text = "${gameManager.score}"
    }


    private fun changeActivity(score: Int) {
        val intent = Intent(this, ScoreActivity::class.java)
        var bundle = Bundle()
        bundle.putInt(Constants.BundleKeys.SCORE_KEY, score)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

}
