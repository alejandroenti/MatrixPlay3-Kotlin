package com.matrixplay3.matrixplay.activites

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrixplay3.matrixplay.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaitActivity : BaseActivity() {

    private lateinit var title : TextView
    private lateinit var titleShadow : TextView
    private lateinit var versus : TextView
    private lateinit var versusShadow : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wait)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wait)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = findViewById<TextView>(R.id.waitTitle)
        titleShadow = findViewById<TextView>(R.id.waitTitleShadow)
        versus = findViewById<TextView>(R.id.waitVersusTitle)
        versusShadow = findViewById<TextView>(R.id.waitVersusShadow)

        startDotAnimation(title)
        startDotAnimation(titleShadow)
        startRespirationAnimation(versus)
        startRespirationAnimation(versusShadow)
    }

    fun startDotAnimation(tv : TextView) {
        val baseText = getString(R.string.wait_title)
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
}