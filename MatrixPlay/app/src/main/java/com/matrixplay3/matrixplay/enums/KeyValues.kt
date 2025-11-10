package com.matrixplay3.matrixplay.enums

enum class KeyValues (val value : String) {
    K_TYPE("type"),
    K_MESSAGE("message"),

    // Server Calls
    K_SALUTION("salutation"),
    K_CLIENTS_LIST("clientsList"),
    K_COUNTDOWN("countdown"),

    // Client Calls
    K_REGISTER("register"),
    K_NAME("clientName"),
    K_CLIENT_TYPE("clientType"),
    K_POSY("posY")
}