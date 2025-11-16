package com.matrixplay3.matrixplay.activites

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrixplay3.matrixplay.R
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.clients
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.userName
import com.matrixplay3.matrixplay.classes.WSManager.Companion.wsClient
import com.matrixplay3.matrixplay.components.CustomCanvas
import com.matrixplay3.matrixplay.enums.KeyValues
import org.json.JSONObject

class GameActivity : BaseActivity() {

    private val SEEKBAR_MULTIPLIER : Int = 100

    private lateinit var player1Pad : SeekBar
    private lateinit var player2Pad : SeekBar

    private lateinit var playerPad : SeekBar
    private lateinit var rivalPad : SeekBar
    private lateinit var canvas : CustomCanvas

    private var playerPadProgress : Float = 0.5f

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

        playerPad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newProgress : Float = progress.toFloat() / SEEKBAR_MULTIPLIER
                seekBar?.progress = (playerPadProgress * SEEKBAR_MULTIPLIER).toInt()
                var msg : JSONObject = JSONObject()
                msg.put(KeyValues.K_TYPE.value, KeyValues.K_CLIENT_DATA.value)
                msg.put(KeyValues.K_POSY.value, newProgress.toString())

                wsClient.send(msg.toString())
                //Log.d("SeekBar", msg.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Necessary
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Necessary
            }
        })
    }

    fun updatePlayerPad(progress : Float) {
        playerPadProgress = progress
        playerPad.progress = (progress * SEEKBAR_MULTIPLIER).toInt()
    }

    fun updateRivalPad(progress : Float) {
        rivalPad.progress = (progress * SEEKBAR_MULTIPLIER).toInt()
    }

    private fun disablePad() {
        playerPad = player1Pad
        rivalPad = player2Pad
        player2Pad.isClickable = false
        player2Pad.isEnabled = false
        player2Pad.isFocusable = false
        /*
        if (clients.get(0).name.equals(userName)) {
            playerPad = player1Pad
             rivalPad = player2Pad
            player2Pad.isClickable = false
            player2Pad.isEnabled = false
            player2Pad.isFocusable = false
        }
        else {
            playerPad = player2Pad
             rivalPad = player1Pad
            player1Pad.isClickable = false
            player1Pad.isEnabled = false
            player1Pad.isFocusable = false
        }*/
    }
}