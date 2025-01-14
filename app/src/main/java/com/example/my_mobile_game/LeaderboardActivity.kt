package com.example.my_mobile_game

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.my_mobile_game.fragments.GoogleMapFragment
import com.example.my_mobile_game.fragments.LeaderboardFragment
import com.example.my_mobile_game.interfaces.HighScoreItemClickedCallback


class LeaderboardActivity : AppCompatActivity(), HighScoreItemClickedCallback {

    private lateinit var leaderboard_FRAME_scores: FrameLayout

    private lateinit var leaderboard_FRAME_map: FrameLayout

    private lateinit var mapFragment: GoogleMapFragment

    private lateinit var leaderboardFragment: LeaderboardFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)

        findViews()
        initViews()
    }

    private fun findViews() {

        leaderboard_FRAME_scores = findViewById(R.id.leaderboard_FRAME_scores)

        leaderboard_FRAME_map = findViewById(R.id.leaderboard_FRAME_map)
    }

    private fun initViews() {
        mapFragment = GoogleMapFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.leaderboard_FRAME_map, mapFragment)
            .commit()

        leaderboardFragment = LeaderboardFragment()
        leaderboardFragment.setItemClickListener(this)


        supportFragmentManager
            .beginTransaction()
            .add(R.id.leaderboard_FRAME_scores, leaderboardFragment)
            .commit()

    }

    override fun highScoreItemClicked(lat: Double, lon: Double) {
        mapFragment.zoom(lat,lon)
    }
}