package com.example.my_mobile_game

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.my_mobile_game.databinding.ActivityWelcomeBinding
import com.example.my_mobile_game.utils.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class WelcomeActivity : AppCompatActivity() {


    private lateinit var welcome_BTN_leaderboard: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_startgame: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_tilt: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_controls: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_hard: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_easy: ExtendedFloatingActionButton
    private var selectedMode: String? = null
    private var difficulty: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_welcome)
        findViews()
        initViews()
        requestLocationPermission()
    }

    private fun <T> changeActivity(targetActivity: Class<T>, extras: Map<String, Any?>) {
        val intent = Intent(this, targetActivity)
        val bundle = Bundle()

        // Add extras to the bundle
        extras.forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Long -> bundle.putLong(key, value)
                is Int -> bundle.putInt(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                is Float -> bundle.putFloat(key, value)
                is Double -> bundle.putDouble(key, value)
                else -> throw IllegalArgumentException("Unsupported data type for key: $key")
            }
        }

        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }


    private fun initViews() {
        welcome_BTN_leaderboard.setOnClickListener {
            changeActivity(
                LeaderboardActivity::class.java,
                mapOf()
            )
        }

        welcome_BTN_tilt.setOnClickListener {
            selectedMode = Constants.PlayModes.TILT // Save selected mode
            highlightButton(welcome_BTN_tilt, welcome_BTN_controls) // Highlight selected button
        }
        welcome_BTN_controls.setOnClickListener {
            selectedMode = Constants.PlayModes.CONTROLS // Save selected mode
            highlightButton(welcome_BTN_controls, welcome_BTN_tilt) // Highlight selected button
        }
        welcome_BTN_easy.setOnClickListener {
            difficulty = Constants.Difficulties.EASY_MODE_DELAY
            highlightButton(welcome_BTN_easy, welcome_BTN_hard) // Highlight selected button
        }
        welcome_BTN_hard.setOnClickListener {
            difficulty = Constants.Difficulties.HARD_MODE_DELAY
            highlightButton(welcome_BTN_hard, welcome_BTN_easy) // Highlight selected button
        }
        welcome_BTN_startgame.setOnClickListener {
            if (selectedMode != null && difficulty != null) {
                changeActivity(
                    MainActivity::class.java,
                    mapOf(
                        Constants.BundleKeys.PLAY_MODE_KEY to selectedMode,
                        Constants.BundleKeys.DIFFICULTY_KEY to difficulty
                    )
                )
            } else {
                // Show a message if no mode is selected
                Toast.makeText(this, "Please select difficulty and play mode", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Constants.RequestCodes.LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    private fun findViews() {
        welcome_BTN_controls = findViewById(R.id.welcome_BTN_controls)
        welcome_BTN_tilt = findViewById(R.id.welcome_BTN_tilt)
        welcome_BTN_startgame = findViewById(R.id.welcome_BTN_startgame)
        welcome_BTN_easy = findViewById(R.id.welcome_BTN_easy)
        welcome_BTN_hard = findViewById(R.id.welcome_BTN_hard)
        welcome_BTN_leaderboard = findViewById(R.id.welcome_BTN_leaderboard)
    }

    private fun highlightButton(
        selected: ExtendedFloatingActionButton,
        unselected: ExtendedFloatingActionButton
    ) {
        selected.backgroundTintList = ContextCompat.getColorStateList(this, R.color.gross_green)
        unselected.backgroundTintList = ContextCompat.getColorStateList(this, R.color.sky_blue)
    }
}