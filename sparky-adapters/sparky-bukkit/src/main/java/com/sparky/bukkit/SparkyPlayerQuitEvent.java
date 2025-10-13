package com.sparky.bukkit;

/**
 * Подія виходу гравця в SparkyLib.
 *
 * @author Андрій Будильников
 */
public class SparkyPlayerQuitEvent {
    private final String playerId;
    private final String playerName;
    
    public SparkyPlayerQuitEvent(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }
    
    public String getPlayerId() {
        return playerId;
    }
    
    public String getPlayerName() {
        return playerName;
    }
}