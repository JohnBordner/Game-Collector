package com.example.mobileapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.R
import com.example.mobileapp.model.Game

class GameAdapter(
    private val gameList: List<Game>,
    private val onGameClick: (position: Int) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.title.text = game.title
        holder.year.text = game.year.toString()
        holder.console.text = game.console
        holder.itemView.setOnClickListener {
            onGameClick(position)
        }
    }

    override fun getItemCount(): Int = gameList.size

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.gameTitle)
        val year: TextView = itemView.findViewById(R.id.gameYear)
        val console: TextView = itemView.findViewById(R.id.gameConsole)
    }
}
