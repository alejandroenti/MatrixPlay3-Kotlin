package com.matrixplay3.matrixplay.classes

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.coroutines.resume

class WSManager {
    companion object {
        lateinit var wsClient: WSClient
        public var currentActivityRef: AppCompatActivity? = null

        suspend fun connectWithWSS(uri: String, timeoutMillis: Long = 10_000): Boolean {
            return suspendCancellableCoroutine { cont ->

                // Crear el WebSocket
                val ws = object : WSClient(URI(uri)) {

                    override fun onOpen(handshakedata: ServerHandshake?) {
                        // Assign static variable to this instance of Web Socket connection
                        wsClient = this
                        if (!cont.isCompleted) cont.resume(true)
                    }

                    override fun onMessage(message: String?) {
                        // Needed
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
                ws.connect()

                // Manual Timeout
                val timeoutThread = Thread {
                    Thread.sleep(timeoutMillis)
                    if (!ws.isOpen) {
                        ws.close()
                        if (!cont.isCompleted) cont.resume(false)
                    }
                }.apply { start() }

                // Ending Coroutine
                cont.invokeOnCancellation {
                    ws.close()
                    timeoutThread.interrupt()
                }
            }
        }
    }
}
