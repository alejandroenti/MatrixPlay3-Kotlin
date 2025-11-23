package com.matrixplay3.matrixplay.classes

import android.util.Log
import com.matrixplay3.matrixplay.activites.CountdownActivity
import com.matrixplay3.matrixplay.activites.GameActivity
import com.matrixplay3.matrixplay.activites.LoginActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.ball
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.ballRadius
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.clients
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.currentRefActivity
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.p1Pos
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.p2Pos
import com.matrixplay3.matrixplay.activites.LoginActivity.Companion.pSize
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

                Log.d("Server Communication", clients.toString())

                if (currentRefActivity is WaitActivity) {
                    (currentRefActivity as WaitActivity).fillPlayers()
                }
            }

            KeyValues.K_COUNTDOWN.value -> {
                if (currentRefActivity is WaitActivity) {
                    (currentRefActivity as WaitActivity).passToCountdown()
                }

                val value = json.getString(KeyValues.K_VALUE.value)
                (currentRefActivity as CountdownActivity).updateNumber(value)
            }

            KeyValues.K_PLAYER_POSITION.value -> {
                var playerName = json.getString(KeyValues.K_PLAYER_NAME.value)
                var position = json.getString(KeyValues.K_POSITION.value)

                //Log.d("WS Position Changed", json.toString())

                if (playerName.equals(clients.get(0).name)) {
                    val posY = position.split(" ")[1].toFloat()
                    (currentRefActivity as GameActivity).setPlayer1Pos(posY)
                }
                else if (playerName.equals(clients.get(1).name)) {
                    val posY = position.split(" ")[1].toFloat()
                    (currentRefActivity as GameActivity).setPlayer2Pos(posY)
                }
            }

            KeyValues.K_INITIAL_POSITION.value -> {
                p1Pos = json.getString(KeyValues.K_PLAYER_1.value)
                p2Pos = json.getString(KeyValues.K_PLAYER_2.value)
                pSize = json.getString(KeyValues.K_PLAYERS_SIZE.value)
                ball = json.getString(KeyValues.K_BALL.value)
                ballRadius = json.getDouble(KeyValues.K_BALL_RADIUS.value).toFloat()

                Log.d("Server Communication", "Recevied 0 - Passing to Play")
                (currentRefActivity as CountdownActivity).passToGameView()
            }

            KeyValues.K_BALL_POSITION.value -> {
                var position = json.getString(KeyValues.K_POSITION.value)
                (currentRefActivity as GameActivity).setBallPos(position)
            }

            KeyValues.K_GOAL_SCORED.value -> {
                var playerName = json.getString(KeyValues.K_PLAYER_NAME.value)
                (currentRefActivity as GameActivity).updateScore(playerName)
            }
        }

    }
}