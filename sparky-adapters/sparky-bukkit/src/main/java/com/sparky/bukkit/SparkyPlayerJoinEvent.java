package com.sparky.bukkit;

/**
 * Подія приєднання гравця в SparkyLib.
 *
 * @author Андрій Будильников
 */
public class SparkyPlayerJoinEvent {
    private final String playerId;
    private final String playerName;
    
    public SparkyPlayerJoinEvent(String playerId, String playerName) {
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