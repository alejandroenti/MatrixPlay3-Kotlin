package com.matrixplay3.matrixplay.classes

import com.matrixplay3.matrixplay.enums.KeyValues
import org.json.JSONObject


class ClientData {
    var name: String
    var clientType: String
    var posY: Double


    constructor(name: String, clientType: String) {
        this.name = name
        this.clientType = clientType
        this.posY = 0.0
    }

    constructor(name: String, clientType: String, posY: Double) {
        this.name = name
        this.clientType = clientType
        this.posY = posY
    }

    override fun toString(): String {
        return this.toJSON().toString()
    }

    // Converteix l'objecte a JSON
    fun toJSON(): JSONObject {
        val obj = JSONObject()
        obj.put(KeyValues.K_NAME.value, name)
        obj.put(KeyValues.K_CLIENT_TYPE.value, clientType)
        obj.put(KeyValues.K_POSY.value, posY)
        return obj
    }

    companion object {
        // Crea un ClientData a partir de JSON
        fun fromJSON(obj: JSONObject): ClientData {
            val name = obj.optString(KeyValues.K_NAME.value, null)
            val clType = obj.optString(KeyValues.K_CLIENT_TYPE.value, null)

            val cd = ClientData(name, clType)
            cd.posY = obj.optDouble(KeyValues.K_POSY.value, 0.0)
            return cd
        }
    }
}