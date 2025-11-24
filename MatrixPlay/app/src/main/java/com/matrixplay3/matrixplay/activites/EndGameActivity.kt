package com.matrixplay3.matrixplay.activites

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrixplay3.matrixplay.R
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.scoreP1
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.scoreP2
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.winner

class EndGameActivity : BaseActivity() {

    private lateinit var result : TextView
    private lateinit var resultShadow : TextView
    private lateinit var logo : ImageView
    private lateinit var btnPlay : Button
    private lateinit var btnExit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_endgame)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.endgame)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        result = findViewById<TextView>(R.id.endgameResult)
        resultShadow = findViewById<TextView>(R.id.endgameResultShadow)
        logo = findViewById<ImageView>(R.id.endgameLogo)
        btnPlay = findViewById<Button>(R.id.endgameBtnPlay)
        btnExit = findViewById<Button>(R.id.endgameBtnExit)


        result.text = winner + " wins!\n " + scoreP1 + " - " + scoreP2
        resultShadow.text = winner + " wins!\n " + scoreP1 + " - " + scoreP2

        changeResult()

        val bitmap = BitmapFactory.decodeStream( assets.open("logo.png") )
        logo.setImageBitmap( bitmap )

        btnPlay.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnExit.setOnClickListener {
            this.finishAffinity()
        }
    }

    private fun changeResult() {

    }
}