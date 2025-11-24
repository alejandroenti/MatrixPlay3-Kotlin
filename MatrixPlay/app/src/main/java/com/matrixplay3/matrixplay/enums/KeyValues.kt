package com.matrixplay3.matrixplay.enums

enum class KeyValues (val value : String) {
    K_TYPE("type"),
    K_MESSAGE("message"),
    K_VALUE("value"),

    // Server Calls
    K_SALUTION("salutation"),
    K_CLIENTS_LIST("clientsList"),
    K_COUNTDOWN("countdown"),
    K_PLAYER_NAME("playerName"),
    K_PLAYER_POSITION("playerPosition"),
    K_INITIAL_POSITION("initialPosition"),
    K_PLAYER_1("p1"),
    K_PLAYER_2("p2"),
    K_PLAYERS_SIZE("playersSize"),
    K_BALL("ball"),
    K_BALL_RADIUS("ballRadius"),
    K_BALL_POSITION("ballPosition"),
    K_GOAL_SCORED("goalScored"),
    K_GAME_OVER("gameOver"),
    K_WINNER("winner"),
    K_LOSER("loser"),
    K_SCORE_P1("scoreP1"),
    K_SCORE_P2("scoreP2"),
    K_REJECT("rejectPlayer"),

    // Client Calls
    K_REGISTER("register"),
    K_NAME("clientName"),
    K_CLIENT_TYPE("clientType"),
    K_POSY("posY"),
    K_POSITION("position"),
    K_MOVEMENT("movement_android")
}