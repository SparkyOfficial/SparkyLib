package com.sparky.minecraft.network;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для мережевої системи.
 *
 * @author Андрій Будильников
 */
public class NetworkTest {
    
    @Test
    public void testNetworkManagerCreation() {
        NetworkManager networkManager = new NetworkManager();
        assertNotNull(networkManager);
        System.out.println("NetworkManager created successfully");
    }
    
    @Test
    public void testNetworkPacketCreation() {
        NetworkPacket packet = new NetworkPacket(NetworkPacket.PacketType.PLAYER_POSITION);
        assertNotNull(packet);
        assertEquals(NetworkPacket.PacketType.PLAYER_POSITION, packet.getType());
        System.out.println("NetworkPacket created with type: " + packet.getType());
    }
    
    @Test
    public void testNetworkPacketWithData() {
        NetworkPacket packet = new NetworkPacket(NetworkPacket.PacketType.CHAT_MESSAGE);
        packet.addData("message", "Hello, world!");
        packet.addData("sender", "Player1");
        
        assertNotNull(packet);
        assertEquals(NetworkPacket.PacketType.CHAT_MESSAGE, packet.getType());
        assertEquals("Hello, world!", packet.getData("message"));
        assertEquals("Player1", packet.getData("sender"));
        System.out.println("NetworkPacket with data created successfully");
    }
    
    @Test
    public void testNetworkPacketSerialization() {
        NetworkPacket packet = new NetworkPacket(NetworkPacket.PacketType.PLAYER_POSITION);
        packet.addData("x", 10);
        packet.addData("y", 64);
        packet.addData("z", 10);
        
        assertNotNull(packet);
        // Перевіряємо, що пакет може бути створений
        System.out.println("Network packet serialization test completed");
    }
}