package com.example.my_mobile_game

import Score
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.my_mobile_game.interfaces.TiltCallback
import com.example.my_mobile_game.logic.GameManager
import com.example.my_mobile_game.utils.BackgroundMusicPlayer
import com.example.my_mobile_game.utils.Constants
import com.example.my_mobile_game.utils.SharedPreferencesManager
import com.example.my_mobile_game.utils.TiltDetector
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
    private lateinit var tiltDetector: TiltDetector
    private lateinit var playMode: String
    private var difficulty: Long? = null
    val handler: Handler = Handler(Looper.getMainLooper())
    private var gameStarted: Boolean = false
    private var gameOverHandled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        playMode = intent.extras?.getString(Constants.BundleKeys.PLAY_MODE_KEY)!!
        difficulty = intent.extras?.getLong(Constants.BundleKeys.DIFFICULTY_KEY)!!
        findViews()
        gameManager =
            GameManager(this, main_IMG_hearts.size, main_IMG_apple[0].size, main_IMG_apple.size)
        initTiltDetector()
        initViews()
    }


    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {

                override fun tiltLeft() {
                    gameManager.moveLeft()
                    updateCharUI()
                }

                override fun tiltRight() {
                    gameManager.moveRight()
                    updateCharUI()
                }

                override fun tiltUp() {
                }

                override fun tiltDown() {
                }

            }
        )
    }

    override fun onResume() {
        super.onResume()
        BackgroundMusicPlayer.getInstance().playMusic()
        if (!gameStarted && !gameManager.isGameOver) {
            if (playMode == Constants.PlayModes.TILT)
                tiltDetector.start()
            handler.postDelayed(runnable, difficulty!!)
            gameStarted = true
        }
    }

    override fun onPause() {
        super.onPause()
        BackgroundMusicPlayer.getInstance().stopMusic()
        if (playMode == Constants.PlayModes.TILT)
            tiltDetector.stop()

        handler.removeCallbacks(runnable)
        gameStarted = false
    }


    val runnable: Runnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, difficulty!!)
            handleGameLogic()
        }
    }

    private fun handleGameLogic() {
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
            findViewById(R.id.main_IMG_tomco3),
            findViewById(R.id.main_IMG_tomco4),
            findViewById(R.id.main_IMG_tomco5)
        )
        main_IMG_apple = Array(Constants.GameLogic.ROWS) { row ->
            Array(Constants.GameLogic.COLS) { col ->
                val resId = resources.getIdentifier(
                    "main_MAT_${row}${col}", // Dynamic resource ID
                    "id",
                    packageName
                )
                findViewById<AppCompatImageView>(resId)
            }
        }
    }

    private fun initViews() {

        main_LBL_score.text = "${gameManager.score}"

        if (playMode == Constants.PlayModes.CONTROLS) {
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
        } else {
            tiltDetector.stop()
            main_FAB_leftarrow.visibility = View.GONE
            main_FAB_rightarrow.visibility = View.GONE

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

        savePlayerRecord("Afik", score)
        val intent = Intent(this, LeaderboardActivity::class.java)
        var bundle = Bundle()
        bundle.putInt(Constants.BundleKeys.SCORE_KEY, score)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun savePlayerRecord(playerName: String, score: Int) {
        val sharedPreferencesManager = SharedPreferencesManager.getInstance()
//        val playerLocation = getPlayerLocation()
        sharedPreferencesManager.addScore(Score(playerName, score, 8, 5))
    }

}
