package com.sparky.minecraft.network;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * З'єднання з клієнтом для мережевої гри.
 *
 * @author Андрій Будильников
 */
public class ClientConnection {
    private static int nextId = 1;
    private int id;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean isConnected;
    
    public ClientConnection(Socket socket) throws IOException {
        this.id = nextId++;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.isConnected = true;
    }
    
    // запускає прослуховування повідомлень
    public void startListening() {
        Thread listenThread = new Thread(() -> {
            try {
                while (isConnected && !socket.isClosed()) {
                    // отримуємо пакет від клієнта
                    NetworkPacket packet = (NetworkPacket) inputStream.readObject();
                    
                    // обробляємо пакет
                    handleIncomingPacket(packet);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error listening to client " + id + ": " + e.getMessage());
                disconnect();
            }
        });
        listenThread.start();
    }
    
    // обробляє вхідний пакет
    private void handleIncomingPacket(NetworkPacket packet) {
        // тут була б логіка обробки пакету
        System.out.println("Received packet from client " + id + ": " + packet.getType());
        
        // для прикладу просто відправляємо пакет назад
        sendPacket(packet);
    }
    
    // відправляє пакет клієнту
    public void sendPacket(NetworkPacket packet) {
        try {
            outputStream.writeObject(packet);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending packet to client " + id + ": " + e.getMessage());
            disconnect();
        }
    }
    
    // відключає клієнта
    public void disconnect() {
        isConnected = false;
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client " + id + " connection: " + e.getMessage());
        }
        
        System.out.println("Client " + id + " disconnected");
    }
    
    // отримує ID клієнта
    public int getId() {
        return id;
    }
    
    // перевіряє, чи підключений
    public boolean isConnected() {
        return isConnected && !socket.isClosed();
    }
}