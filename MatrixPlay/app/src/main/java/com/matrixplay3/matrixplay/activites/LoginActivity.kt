package com.matrixplay3.matrixplay.activites

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.matrixplay3.matrixplay.R
import com.matrixplay3.matrixplay.classes.ClientData
import com.matrixplay3.matrixplay.classes.WSClient
import com.matrixplay3.matrixplay.classes.WSManager.Companion.connectWithWSS
import com.matrixplay3.matrixplay.classes.WSManager.Companion.currentActivityRef
import com.matrixplay3.matrixplay.classes.WSManager.Companion.wsClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI

class LoginActivity : BaseActivity() {

    companion object {
        lateinit var userName : String
        var clients : ArrayList<ClientData> = ArrayList<ClientData>();
        var currentRefActivity : BaseActivity? = null

        public fun connectWS(uri : String) {
            var uri : URI = URI(uri)
            wsClient = WSClient(uri)
            wsClient.connect()
        }
    }

    private val USERNAME_MAX_LENGTH : Int = 8
    private val CONNECTION_TIMEOUT : Long = 10_000

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

        currentRefActivity = this

        username = findViewById<TextInputEditText>(R.id.loginUsername)
        serverUrl = findViewById<TextInputEditText>(R.id.loginURL)
        btnConnect = findViewById<Button>(R.id.loginBtnConnect)
        logo = findViewById<ImageView>(R.id.loginLogo)

        val bitmap = BitmapFactory.decodeStream( assets.open("logo.png") )
        logo.setImageBitmap( bitmap )

        btnConnect.setOnClickListener {
            // Check Username and URL are not empty
            if (!areFieldsFilled()) {
                // Alert Dialog
                showAlertDialog(R.string.login_alert_dialog_message_blank)
            }
            else {
                // Check Username length
                if (username.text!!.length <= USERNAME_MAX_LENGTH) {
                    // Connect To Server
                    userName = username.text!!.toString()
                    attemptConnection()
                }
                else {
                    showAlertDialog(R.string.login_alert_dialog_message_username)
                }
            }
        }
    }

    private fun areFieldsFilled() : Boolean {
        return !username.text.isNullOrBlank() && !serverUrl.text.isNullOrBlank()
    }

    private fun showAlertDialog(messageId : Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(getString(messageId))
            .setPositiveButton(R.string.login_alert_dialog_accept) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun attemptConnection() {
        val usernameStr = username.text.toString()
        val serverStr = serverUrl.text.toString()

        if (usernameStr.length > USERNAME_MAX_LENGTH) {
            showAlertDialog(R.string.login_alert_dialog_message_username)
            return
        }

        connectWS(serverStr)
    }

    public fun passToWait() {
        val intent = Intent(this, WaitActivity::class.java)
        startActivity(intent)
        finish()
    }
}