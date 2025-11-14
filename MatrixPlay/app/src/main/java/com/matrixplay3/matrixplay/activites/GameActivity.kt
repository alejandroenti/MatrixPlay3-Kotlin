package com.matrixplay3.matrixplay.activites

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrixplay3.matrixplay.R
import com.matrixplay3.matrixplay.components.CustomCanvas

class GameActivity : BaseActivity() {

    private val SEEKBAR_MULTIPLIER : Int = 100

    private lateinit var player1Pad : SeekBar
    private lateinit var player2Pad : SeekBar
    private lateinit var canvas : CustomCanvas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player1Pad = findViewById<SeekBar>(R.id.gamePlayer1ControlPad)
        player2Pad = findViewById<SeekBar>(R.id.gamePlayer2ControlPad)
        canvas = findViewById<CustomCanvas>(R.id.gameCanvas)

        disablePad()
    }

    private fun disablePad() {
        player2Pad.isClickable = false
        player2Pad.isEnabled = false
        player2Pad.isFocusable = false
    }
}