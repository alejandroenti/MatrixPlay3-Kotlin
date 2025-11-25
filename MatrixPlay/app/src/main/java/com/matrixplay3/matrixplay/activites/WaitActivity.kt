package com.matrixplay3.matrixplay.activites

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrixplay3.matrixplay.R
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.clients
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.currentRefActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.rejected
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.userName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class WaitActivity : BaseActivity() {

    private lateinit var title : TextView
    private lateinit var titleShadow : TextView
    private lateinit var versus : TextView
    private lateinit var versusShadow : TextView
    private lateinit var player1Image : ImageView
    private lateinit var player2Image : ImageView
    private lateinit var player1Name : TextView
    private lateinit var player2Name : TextView
    private lateinit var players : ArrayList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wait)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wait)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (rejected) {
            passToLogin()
        }

        currentRefActivity = this

        title = findViewById<TextView>(R.id.waitTitle)
        titleShadow = findViewById<TextView>(R.id.waitTitleShadow)
        versus = findViewById<TextView>(R.id.waitVersusTitle)
        versusShadow = findViewById<TextView>(R.id.waitVersusShadow)
        player1Image = findViewById<ImageView>(R.id.waitImagePlayer1)
        player2Image = findViewById<ImageView>(R.id.waitImagePlayer2)
        player1Name = findViewById<TextView>(R.id.waitNamePlayer1)
        player2Name = findViewById<TextView>(R.id.waitNamePlayer2)

        player2Name.text = "..."

        players = arrayListOf(player1Name, player2Name)

        startDotAnimation(title, getString(R.string.wait_title))
        startDotAnimation(titleShadow, getString(R.string.wait_title))
        startRespirationAnimation(versus)
        startRespirationAnimation(versusShadow)

        fillPlayers()
    }

    fun fillPlayers() {
        runOnUiThread {
            var p : Int = 0
            for (client in clients) {
                players.get(p).text = client.name
                p += 1

                if (p >= 2) {
                    break
                }
            }
        }
    }

    fun startDotAnimation(tv : TextView, baseText : String) {
        val maxDots = 3
        val delayMillis : Long = 750

        CoroutineScope(Dispatchers.Main).launch {
            var dotCount = 0
            while (true) {
                val dots = ".".repeat(dotCount)
                tv.text = baseText + dots
                dotCount++
                if (dotCount > maxDots) dotCount = 0 // reinicia los puntos
                delay(delayMillis)
            }
        }
    }

    fun startRespirationAnimation(tv : TextView) {
        val scaleX = ObjectAnimator.ofFloat(tv, "scaleX", 1f, 1.2f)
        val scaleY = ObjectAnimator.ofFloat(tv, "scaleY", 1f, 1.2f)

        scaleX.duration = 1000
        scaleY.duration = 1000
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatCount = ValueAnimator.INFINITE
        scaleX.repeatMode = ValueAnimator.REVERSE
        scaleY.repeatMode = ValueAnimator.REVERSE

        scaleX.start()
        scaleY.start()
    }

    fun passToCountdown() {
        runOnUiThread {
            val intent = Intent(this, CountdownActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun passToLogin() {
        runOnUiThread {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}