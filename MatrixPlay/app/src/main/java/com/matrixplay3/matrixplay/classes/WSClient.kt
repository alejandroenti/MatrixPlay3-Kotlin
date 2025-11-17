package com.matrixplay3.matrixplay.classes

import android.util.Log
import com.matrixplay3.matrixplay.activites.CountdownActivity
import com.matrixplay3.matrixplay.activites.LoginActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.clients
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.currentRefActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.userName
import com.matrixplay3.matrixplay.activites.WaitActivity
import com.matrixplay3.matrixplay.classes.WSManager.Companion.wsClient
import com.matrixplay3.matrixplay.enums.KeyValues
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONArray
import org.json.JSONObject
import java.net.URI


open class WSClient(serverUri : URI) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WSConnection", "[*] Opened Connection!")

        val msgObject : JSONObject = JSONObject()
        msgObject.put(KeyValues.K_TYPE.value, KeyValues.K_REGISTER.value)
        msgObject.put(KeyValues.K_NAME.value, userName)
        msgObject.put(KeyValues.K_CLIENT_TYPE.value, "Android")
        wsClient.send(msgObject.toString())

        if (currentRefActivity is LoginActivity) {
            (currentRefActivity as LoginActivity).passToWait()
        }

        Log.d("WSConnection", "Message send to Server: " + msgObject.toString())    }

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
        val json : JSONObject = JSONObject(response)
        val type : String = json.getString(KeyValues.K_TYPE.value)

        when (type) {
            KeyValues.K_SALUTION.value -> {
                Log.d("Server Communication", json.getString(KeyValues.K_MESSAGE.value))
            }

            KeyValues.K_CLIENTS_LIST.value -> {
                Log.d("Server Communication", "Receiving new clientList")

                val arr: JSONArray = json.getJSONArray(KeyValues.K_CLIENTS_LIST.value)
                clients.clear()

                for (i in 0..<arr.length()) {
                    val `object` = arr.getJSONObject(i)
                    val name = `object`.getString(KeyValues.K_NAME.value)
                    val clientType = `object`.getString(KeyValues.K_CLIENT_TYPE.value)

                    val cd: ClientData = ClientData(name, clientType)
                    clients.add(cd)
                }

                Log.d("Server COmmunication", clients.toString())

                if (currentRefActivity is WaitActivity) {
                    (currentRefActivity as WaitActivity).fillPlayers()
                }
            }

            KeyValues.K_COUNTDOWN.value -> {
                if (currentRefActivity is WaitActivity) {
                    (currentRefActivity as WaitActivity).passToCountdown()
                }

                val value = json.getString(KeyValues.K_VALUE.value)
                if (value.equals("0")) {
                    Log.d("Server Communication", "Recevied 0 - Passing to Play")
                    (currentRefActivity as CountdownActivity).passToGameView()
                    return
                }
                (currentRefActivity as CountdownActivity).updateNumber(value)
            }
        }

    }
}