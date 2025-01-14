package com.example.my_mobile_game.adapters

import Score
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.my_mobile_game.R
import com.example.my_mobile_game.interfaces.HighScoreItemClickedCallback

class ScoreAdapter(private val scores: List<Score>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var highScoreItemClickedCallback: HighScoreItemClickedCallback? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.nameTextView.text = score.name
        holder.scoreTextView.text = score.score.toString()



        holder.itemView.setOnClickListener {
            highScoreItemClickedCallback?.highScoreItemClicked(score.lan,  score.lon)
        }
    }


    override fun getItemCount(): Int = scores.size

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.item_score_name)
        val scoreTextView: TextView = view.findViewById(R.id.item_score_value)



    }
}
