package com.example.mobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.adapter.GameAdapter
import com.example.mobileapp.model.Game
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter
    private val gameList: MutableList<Game> = mutableListOf()

    // Launchers for adding and editing games
    private lateinit var addGameLauncher: ActivityResultLauncher<Intent>
    private lateinit var editGameLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.gameRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = GameAdapter(gameList) { position ->
            editGame(position)
        }
        recyclerView.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            addGameLauncher.launch(intent)
        }

        initializeLaunchers()
    }

    private fun initializeLaunchers() {
        //Adding a new game
        addGameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val title = result.data!!.getStringExtra("title") ?: return@registerForActivityResult
                val year = result.data!!.getIntExtra("year", 0)
                val console = result.data!!.getStringExtra("console") ?: return@registerForActivityResult

                val newGame = Game(title, year, console)
                addGame(newGame)
            }
        }

        // Editing a existing game
        editGameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val title = result.data!!.getStringExtra("title") ?: return@registerForActivityResult
                val year = result.data!!.getIntExtra("year", 0)
                val console = result.data!!.getStringExtra("console") ?: return@registerForActivityResult
                val position = result.data!!.getIntExtra(EXTRA_POSITION, -1)

                if (position != -1) {
                    updateGame(position, title, year, console)
                }
            }
        }
    }

    private fun editGame(position: Int) {
        val game = gameList[position]
        val intent = Intent(this, AddGameActivity::class.java).apply {
            putExtra("title", game.title)
            putExtra("year", game.year)
            putExtra("console", game.console)
            putExtra(EXTRA_POSITION, position)
        }
        editGameLauncher.launch(intent)
    }

    private fun addGame(game: Game) {
        gameList.add(game)
        adapter.notifyItemInserted(gameList.size - 1)
        Toast.makeText(this, "Game added", Toast.LENGTH_SHORT).show()
    }

    private fun updateGame(position: Int, title: String, year: Int, console: String) {
        val game = gameList[position]
        game.title = title
        game.year = year
        game.console = console
        adapter.notifyItemChanged(position)
        Toast.makeText(this, "Game updated", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val EXTRA_POSITION = "position"
    }
}
