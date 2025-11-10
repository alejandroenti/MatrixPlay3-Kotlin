package com.matrixplay3.matrixplay.classes

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.userName
import com.matrixplay3.matrixplay.enums.KeyValues
import kotlinx.coroutines.suspendCancellableCoroutine
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.net.URI
import kotlin.coroutines.resume

class WSManager {
    companion object {
        lateinit var wsClient: WSClient
        public var currentActivityRef: AppCompatActivity? = null

        suspend fun connectWithWSS(uri: String, timeoutMillis: Long = 10_000): Boolean {
            return suspendCancellableCoroutine { cont ->

                // Crear el WebSocket
                wsClient = object : WSClient(URI(uri)) {

                    override fun onMessage(message: String?) {
                        // Needed
                    }

                    override fun onOpen(handshakedata: ServerHandshake?) {


                        if (!cont.isCompleted) cont.resume(true)
                    }

                    override fun onError(ex: Exception?) {
                        if (!cont.isCompleted) cont.resume(false)
                    }

                    override fun onClose(code: Int, reason: String?, remote: Boolean) {
                        if (!isOpen && !cont.isCompleted) {
                            cont.resume(false)
                        }
                    }
                }

                // Start connection with server
                wsClient.connect()

                // Manual Timeout
                val timeoutThread = Thread {
                    Thread.sleep(timeoutMillis)
                    if (!wsClient.isOpen) {
                        wsClient.close()
                        if (!cont.isCompleted) cont.resume(false)
                    }
                }.apply { start() }

                // Ending Coroutine
                cont.invokeOnCancellation {
                    wsClient.close()
                    timeoutThread.interrupt()
                }
            }
        }
    }
}
