package com.matrixplay3.matrixplay.activites

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.matrixplay3.matrixplay.R
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.currentRefActivity

class CountdownActivity : BaseActivity() {

    private lateinit var number : TextView
    private lateinit var numberShadow : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_countdown)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.countdown)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentRefActivity = this

        number = findViewById<TextView>(R.id.countdownNumber)
        numberShadow = findViewById<TextView>(R.id.countdownNumberShadow)
    }

    public fun updateNumber(num : String) {
        runOnUiThread {
            number.text = num
            numberShadow.text = num

            startMinMaxAnimation(number)
            startMinMaxAnimation(numberShadow)
        }
    }

    private fun startMinMaxAnimation(tv : TextView) {
        val scaleX = ObjectAnimator.ofFloat(tv, "scaleX", 0.5f, 1.5f)
        val scaleY = ObjectAnimator.ofFloat(tv, "scaleY", 0.5f, 1.5f)

        scaleX.duration = 500
        scaleY.duration = 500

        scaleX.start()
        scaleY.start()
    }
}