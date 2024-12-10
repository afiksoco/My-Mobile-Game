package com.example.my_mobile_game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.my_mobile_game.utils.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {


    private lateinit var gameManager: GameManager
    private lateinit var main_FAB_leftarrow: ExtendedFloatingActionButton
    private lateinit var main_FAB_rightarrow: ExtendedFloatingActionButton
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>
    private lateinit var main_IMG_char: Array<AppCompatImageView>
    private lateinit var main_IMG_apple: Array<Array<AppCompatImageView>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size, main_IMG_apple[0].size, main_IMG_apple.size)
        initViews()
        startGame()
    }


    val handler: Handler = Handler(Looper.getMainLooper())
    private var gameStarted: Boolean = false

    val runnable: Runnable = object : Runnable {
        override fun run() {
            //reschedule:
            handler.postDelayed(this, Constants.DELAY)
            //refresh UI:
            gameManager.moveLogosDown()
            gameManager.spawnApple()

            updateAppleUI()
            if (gameManager.isCollision()) {
                gameManager.handleCollision()
            }
        }
    }

    private fun startGame() {
        if (!gameStarted) {
            handler.postDelayed(runnable, Constants.DELAY)
            gameStarted = true;

        }
    }

    private fun findViews() {
        main_FAB_rightarrow = findViewById(R.id.main_FAB_rightarrow)
        main_FAB_leftarrow = findViewById(R.id.main_FAB_leftarrow)
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
//            arrayOf(
//                findViewById(R.id.main_MAT_70),
//                findViewById(R.id.main_MAT_71),
//                findViewById(R.id.main_MAT_72)
//            ),
//            arrayOf(
//                findViewById(R.id.main_MAT_80),
//                findViewById(R.id.main_MAT_81),
//                findViewById(R.id.main_MAT_82)
//            ),
//            arrayOf(
//                findViewById(R.id.main_MAT_90),
//                findViewById(R.id.main_MAT_91),
//                findViewById(R.id.main_MAT_92)
//            ),
//            arrayOf(
//                findViewById(R.id.main_MAT_100),
//                findViewById(R.id.main_MAT_101),
//                findViewById(R.id.main_MAT_102)
//            ),
//            arrayOf(
//                findViewById(R.id.main_MAT_110),
//                findViewById(R.id.main_MAT_111),
//                findViewById(R.id.main_MAT_112)
//            ),
//            arrayOf(
//                findViewById(R.id.main_MAT_120),
//                findViewById(R.id.main_MAT_121),
//                findViewById(R.id.main_MAT_122)
//            ),
//            arrayOf(
//                findViewById(R.id.main_MAT_130),
//                findViewById(R.id.main_MAT_131),
//                findViewById(R.id.main_MAT_132)
        )

    }

    private fun initViews() {
        main_FAB_leftarrow.setOnClickListener {
            gameManager.moveLeft()
            updateCharUI()
        }
        main_FAB_rightarrow.setOnClickListener {
            gameManager.moveRight()
            updateCharUI()
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

}
