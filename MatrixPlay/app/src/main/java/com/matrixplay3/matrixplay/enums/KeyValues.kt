package com.matrixplay3.matrixplay.enums

enum class KeyValues (val value : String) {
    K_TYPE("type"),
    K_MESSAGE("message"),
    K_VALUE("value"),

    // Server Calls
    K_SALUTION("salutation"),
    K_CLIENTS_LIST("clientsList"),
    K_COUNTDOWN("countdown"),

    // Client Calls
    K_REGISTER("register"),
    K_NAME("clientName"),
    K_CLIENT_TYPE("clientType"),
    K_POSY("posY"),
    K_CLIENT_DATA("clientData"),
    K_DIRECTION("direction"),
    K_PLAYER_POSITION("playerPosition"),
    K_INITIAL_POSITION("initialPosition"),
    K_PLAYER_1("p1"),
    K_PLAYER_2("p2")
}