package com.matrixplay3.matrixplay.activites

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.matrixplay3.matrixplay.R

class LoginActivity : BaseActivity() {

    private lateinit var username : TextInputEditText
    private lateinit var serverUrl : TextInputEditText
    private lateinit var btnConnect : Button
    private lateinit var logo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        username = findViewById<TextInputEditText>(R.id.loginUsername)
        serverUrl = findViewById<TextInputEditText>(R.id.loginURL)
        btnConnect = findViewById<Button>(R.id.loginBtnConnect)
        logo = findViewById<ImageView>(R.id.loginLogo)

        val bitmap = BitmapFactory.decodeStream( assets.open("logo.png") )
        logo.setImageBitmap( bitmap )

        username.addTextChangedListener {
            enableBtn(areFieldsFilled())
        }

        serverUrl.addTextChangedListener {
            enableBtn(areFieldsFilled())
        }

        btnConnect.setOnClickListener {
            // Connect To Server
        }
    }

    private fun areFieldsFilled() : Boolean {
        return !username.text.isNullOrBlank() && !serverUrl.text.isNullOrBlank()
    }

    private fun enableBtn(status : Boolean) {
        btnConnect.isEnabled = status
    }
}