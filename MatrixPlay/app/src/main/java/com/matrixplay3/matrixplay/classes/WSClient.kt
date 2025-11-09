package com.matrixplay3.matrixplay.classes

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class WSClient(serverUri : URI) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WSConnection", "[*] Opened Connection!")
    }

    override fun onMessage(message: String?) {
        wsMessage(message!!)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("WSConnection", "[*] Closed Connection!")
    }

    override fun onError(ex: Exception?) {
        Log.d("WSConnection", "[*] An error ocurred!" + ex.toString())
    }

    private fun wsMessage(response: String) {
    }
}