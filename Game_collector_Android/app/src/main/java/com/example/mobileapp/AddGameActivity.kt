package com.example.mobileapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddGameActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val yearEditText = findViewById<EditText>(R.id.editTextYear)
        val consoleEditText = findViewById<EditText>(R.id.editTextConsole)
        val addButton = findViewById<Button>(R.id.buttonAddGame)

        // Check if we're editing an existing game
        val title = intent.getStringExtra("title")
        val year = intent.getIntExtra("year", 0)
        val console = intent.getStringExtra("console")
        position = intent.getIntExtra("position", -1)

        if (title != null && year != 0 && console != null) {
            titleEditText.setText(title)
            yearEditText.setText(year.toString())
            consoleEditText.setText(console)
            addButton.text = "Update Game"
        }

        addButton.setOnClickListener {
            val newTitle = titleEditText.text.toString()
            val newYear = yearEditText.text.toString().toIntOrNull() ?: 0
            val newConsole = consoleEditText.text.toString()

            if (newTitle.isEmpty() || newYear == 0 || newConsole.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra("title", newTitle)
                putExtra("year", newYear)
                putExtra("console", newConsole)
                putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
