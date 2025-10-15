package com.sparky.minecraft.network;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Менеджер мережі для багатокористувацької гри.
 *
 * @author Андрій Будильников
 */
public class NetworkManager {
    private ServerSocket serverSocket;
    private Map<Integer, ClientConnection> clients;
    private List<NetworkPacket> packetQueue;
    private boolean isServer;
    private String serverAddress;
    private int serverPort;
    
    public NetworkManager() {
        this.clients = new HashMap<>();
        this.packetQueue = new ArrayList<>();
        this.isServer = false;
    }
    
    // запускає сервер
    public void startServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.isServer = true;
        this.serverPort = port;
        
        // запускаємо потік для приймання з'єднань
        Thread acceptThread = new Thread(() -> {
            try {
                while (!serverSocket.isClosed()) {
                    Socket clientSocket = serverSocket.accept();
                    handleNewClient(clientSocket);
                }
            } catch (IOException e) {
                System.err.println("Error accepting client connections: " + e.getMessage());
            }
        });
        acceptThread.start();
        
        System.out.println("Server started on port " + port);
    }
    
    // обробляє нове клієнтське з'єднання
    private void handleNewClient(Socket clientSocket) {
        try {
            ClientConnection client = new ClientConnection(clientSocket);
            clients.put(client.getId(), client);
            
            // запускаємо потік для обробки повідомлень від клієнта
            client.startListening();
            
            System.out.println("New client connected: " + client.getId());
        } catch (IOException e) {
            System.err.println("Error handling new client: " + e.getMessage());
        }
    }
    
    // підключається до серверу
    public void connectToServer(String address, int port) throws IOException {
        this.serverAddress = address;
        this.serverPort = port;
        this.isServer = false;
        
        // тут була б логіка підключення до серверу
        System.out.println("Connected to server at " + address + ":" + port);
    }
    
    // відправляє пакет всім клієнтам
    public void broadcastPacket(NetworkPacket packet) {
        if (!isServer) {
            System.err.println("Only server can broadcast packets");
            return;
        }
        
        for (ClientConnection client : clients.values()) {
            client.sendPacket(packet);
        }
    }
    
    // відправляє пакет конкретному клієнту
    public void sendPacketToClient(int clientId, NetworkPacket packet) {
        ClientConnection client = clients.get(clientId);
        if (client != null) {
            client.sendPacket(packet);
        }
    }
    
    // додає пакет до черги
    public void queuePacket(NetworkPacket packet) {
        packetQueue.add(packet);
    }
    
    // обробляє чергу пакетів
    public void processPacketQueue() {
        while (!packetQueue.isEmpty()) {
            NetworkPacket packet = packetQueue.remove(0);
            handlePacket(packet);
        }
    }
    
    // обробляє отриманий пакет
    private void handlePacket(NetworkPacket packet) {
        switch (packet.getType()) {
            case PLAYER_POSITION:
                handlePlayerPositionPacket(packet);
                break;
            case CHAT_MESSAGE:
                handleChatMessagePacket(packet);
                break;
            case BLOCK_CHANGE:
                handleBlockChangePacket(packet);
                break;
            default:
                System.out.println("Unknown packet type: " + packet.getType());
        }
    }
    
    // обробляє пакет позиції гравця
    private void handlePlayerPositionPacket(NetworkPacket packet) {
        // тут була б логіка оновлення позиції гравця
        System.out.println("Received player position packet");
    }
    
    // обробляє пакет повідомлення в чаті
    private void handleChatMessagePacket(NetworkPacket packet) {
        // тут була б логіка відображення повідомлення в чаті
        System.out.println("Received chat message packet");
    }
    
    // обробляє пакет зміни блоку
    private void handleBlockChangePacket(NetworkPacket packet) {
        // тут була б логіка оновлення блоку в світі
        System.out.println("Received block change packet");
    }
    
    // відключається від серверу
    public void disconnect() {
        if (isServer && serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
        
        // відключаємо всіх клієнтів
        for (ClientConnection client : clients.values()) {
            client.disconnect();
        }
        clients.clear();
        
        System.out.println("Disconnected from network");
    }
    
    // перевіряє, чи є сервером
    public boolean isServer() {
        return isServer;
    }
    
    // отримує кількість підключених клієнтів
    public int getClientCount() {
        return clients.size();
    }
}