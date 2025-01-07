package com.example.my_mobile_game

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.my_mobile_game.utils.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcome_BTN_startgame: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_tilt: ExtendedFloatingActionButton
    private lateinit var welcome_BTN_controls: ExtendedFloatingActionButton
    private var selectedMode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_welcome)
        findViews()

        initViews()
    }

    private fun changeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString(
            Constants.BundleKeys.PLAY_MODE_KEY,
            selectedMode
        ) // Add selected mode to bundle
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        welcome_BTN_tilt.setOnClickListener {
            selectedMode = Constants.PlayModes.TILT // Save selected mode
            highlightButton(welcome_BTN_tilt, welcome_BTN_controls) // Highlight selected button
        }
        welcome_BTN_controls.setOnClickListener {
            selectedMode = Constants.PlayModes.CONTROLS // Save selected mode
            highlightButton(welcome_BTN_controls, welcome_BTN_tilt) // Highlight selected button
        }
        welcome_BTN_startgame.setOnClickListener {
            if (selectedMode != null) {
                changeActivity()
            } else {
                // Show a message if no mode is selected
                Toast.makeText(this, "Please select a play mode.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun findViews() {
        welcome_BTN_controls = findViewById(R.id.welcome_BTN_controls)
        welcome_BTN_tilt = findViewById(R.id.welcome_BTN_tilt)
        welcome_BTN_startgame = findViewById(R.id.welcome_BTN_startgame)
    }

    private fun highlightButton(
        selected: ExtendedFloatingActionButton,
        unselected: ExtendedFloatingActionButton
    ) {
        selected.backgroundTintList = ContextCompat.getColorStateList(this, R.color.gross_green)
        unselected.backgroundTintList = ContextCompat.getColorStateList(this, R.color.sky_blue)
    }
}