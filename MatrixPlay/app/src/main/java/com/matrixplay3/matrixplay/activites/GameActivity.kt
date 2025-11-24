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
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.ball
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.ballRadius
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.clients
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.currentRefActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.p1Pos
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.p2Pos
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.pSize
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
    private var playerPadProgressInter : Float = playerPadProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentRefActivity = this

        player1Pad = findViewById<SeekBar>(R.id.gamePlayer1ControlPad)
        player2Pad = findViewById<SeekBar>(R.id.gamePlayer2ControlPad)
        canvas = findViewById<CustomCanvas>(R.id.gameCanvas)

        disablePad()

        setPlayer1Pos(p1Pos)
        setPlayer2Pos(p2Pos)
        setPadsSize(pSize)
        setBallSize(ballRadius)

        playerPad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                playerPadProgressInter = progress.toFloat() / SEEKBAR_MULTIPLIER
                var amount = playerPadProgressInter - playerPadProgress
                playerPadProgress = playerPadProgressInter

                var msg : JSONObject = JSONObject()
                msg.put(KeyValues.K_TYPE.value, KeyValues.K_MOVEMENT.value)
                msg.put(KeyValues.K_NAME.value, userName)
                msg.put(KeyValues.K_MESSAGE.value, (amount * 10).toString())

                wsClient.send(msg.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Necessary
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Necessary
            }
        })
    }

    fun setPlayer1Pos(position : String) {
        runOnUiThread {
            var coords = position.split(" ")
            var x = coords[0].toFloat()
            var y = coords[1].toFloat()

            canvas.updatePlayer1PadPosition(x, y)

            if (player2Pad.equals(playerPad)) {
                player1Pad.progress = SEEKBAR_MULTIPLIER - (y * SEEKBAR_MULTIPLIER).toInt()
            }
        }
    }

    fun setPlayer2Pos(position : String) {
        runOnUiThread {
            var coords = position.split(" ")
            var x = coords[0].toFloat()
            var y = coords[1].toFloat()

            canvas.updatePlayer2PadPosition(x, y)

            if (player1Pad.equals(playerPad)) {
                player2Pad.progress = SEEKBAR_MULTIPLIER - (y * SEEKBAR_MULTIPLIER).toInt()
            }
        }
    }

    fun setPlayer1Pos(y : Float) {
        runOnUiThread {
            canvas.updatePlayer1PadPosition(y)

            if (player2Pad.equals(playerPad)) {
                player1Pad.progress = SEEKBAR_MULTIPLIER - (y * SEEKBAR_MULTIPLIER).toInt()
            }
        }
    }
    fun setPlayer2Pos(y : Float) {
        runOnUiThread {
            canvas.updatePlayer2PadPosition(y)

            if (player1Pad.equals(playerPad)) {
                player2Pad.progress = SEEKBAR_MULTIPLIER - (y * SEEKBAR_MULTIPLIER).toInt()
            }
        }
    }

    fun setBallPos(position : String) {
        runOnUiThread {
            var coords = position.split(" ")
            var x = coords[0].toFloat()
            var y = coords[1].toFloat()

            canvas.updateBallPosition(x, y)
        }
    }

    fun updateScore(playerName : String) {
        runOnUiThread {
            if (playerName.equals(clients.get(0).name)) {
                canvas.updateScore(0)
            } else {
                canvas.updateScore(1)
            }
        }
    }

    private fun setPadsSize(values : String) {
        runOnUiThread {
            var coords = values.split(" ")
            var x = coords[0].toFloat()
            var y = coords[1].toFloat()

            canvas.setPadDimensions(x, y)
        }
    }

    private fun setBallSize(r : Float) {
        canvas.setBallRadius(r)
    }

    private fun disablePad() {
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
        }
    }
}