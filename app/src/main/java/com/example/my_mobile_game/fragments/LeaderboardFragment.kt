package com.example.my_mobile_game.fragments

import Score
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_mobile_game.R
import com.example.my_mobile_game.adapters.ScoreAdapter
import com.example.my_mobile_game.interfaces.HighScoreItemClickedCallback
import com.example.my_mobile_game.utils.Constants
import com.example.my_mobile_game.utils.SharedPreferencesManager

class LeaderboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreAdapter: ScoreAdapter
    private val scores = mutableListOf<Score>()
    private var highScoreItemClickedCallback: HighScoreItemClickedCallback? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_high_score, container, false)

        recyclerView = view.findViewById(R.id.leaderboard_RV_scores)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        scoreAdapter = ScoreAdapter(scores)
        scoreAdapter.highScoreItemClickedCallback = object : HighScoreItemClickedCallback{
            override fun highScoreItemClicked(lat: Double, lon: Double) {
                highScoreItemClickedCallback?.highScoreItemClicked(lat,lon)
            }

        }
        recyclerView.adapter = scoreAdapter

        // Load and display scores
        loadScores()

        return view
    }

     fun setItemClickListener(listener: HighScoreItemClickedCallback) {
        highScoreItemClickedCallback = listener
    }

    private fun loadScores() {
        val sharedPreferencesManager = SharedPreferencesManager.getInstance()
        val loadedScores = sharedPreferencesManager.getScores(Constants.SPKeys.SCORES_KEY)

        // Clear the existing list and add loaded scores
        scores.clear()
        scores.addAll(loadedScores)

        // Notify adapter about data change
        scoreAdapter.notifyDataSetChanged()
    }
}
