package com.sparky.commands;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Демонстраційний тест для модуля команд.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
class CommandsDemoTest {
    
    @Test
    void testCommandFunctionality() {
        // Test CommandRegistry
        CommandRegistry commandRegistry = CommandRegistry.getInstance();
        
        // Test ExampleCommand
        ExampleCommand exampleCommand = new ExampleCommand();
        commandRegistry.registerCommand(exampleCommand);
        
        // Verify the command was registered
        Command retrievedCommand = commandRegistry.getCommand("example");
        assertNotNull(retrievedCommand);
        assertEquals("example", retrievedCommand.getName());
        assertEquals("An example command", retrievedCommand.getDescription());
        
        // Test command execution with a mock sender
        TestCommandSender sender = new TestCommandSender();
        commandRegistry.executeCommand(sender, "example");
        assertEquals("Hello from example command!", sender.getLastMessage());
        
        // Test command execution with arguments
        commandRegistry.executeCommand(sender, "example Hello World");
        assertEquals("You said: Hello World", sender.getLastMessage());
        
        System.out.println("SparkyLib commands functionality test passed!");
    }
    
    @Test
    void testAdvancedCommandFeatures() {
        CommandRegistry commandRegistry = CommandRegistry.getInstance();
        
        // Test AdvancedExampleCommand
        AdvancedExampleCommand advancedCommand = new AdvancedExampleCommand();
        commandRegistry.registerCommand(advancedCommand);
        
        // Verify the command was registered
        Command retrievedCommand = commandRegistry.getCommand("advancedexample");
        assertNotNull(retrievedCommand);
        assertEquals("advancedexample", retrievedCommand.getName());
        
        // Verify aliases were registered
        Command aliasCommand = commandRegistry.getCommand("aexample");
        assertEquals(retrievedCommand, aliasCommand);
        
        aliasCommand = commandRegistry.getCommand("advex");
        assertEquals(retrievedCommand, aliasCommand);
        
        // Test command execution with a mock sender that has permissions
        TestCommandSender sender = new TestCommandSender();
        sender.addPermission("sparky.commands.advancedexample");
        sender.addPermission("sparky.commands.basic");
        
        String previousMessage = sender.getLastMessage();
        commandRegistry.executeCommand(sender, "advancedexample test argument");
        assertNotEquals(previousMessage, sender.getLastMessage());
        System.out.println("All messages for valid command: " + sender.getAllMessages());
        assertTrue(sender.getAllMessages().contains("Arguments: test, argument") || 
                   sender.getAllMessages().contains("Advanced command"),
                   "Expected message about arguments, but got: " + sender.getAllMessages());
        
        System.out.println("Advanced command features test passed!");
    }
    
    @Test
    void testPermissionSystem() {
        CommandRegistry commandRegistry = CommandRegistry.getInstance();
        
        // Test AdvancedExampleCommand
        AdvancedExampleCommand advancedCommand = new AdvancedExampleCommand();
        commandRegistry.registerCommand(advancedCommand);
        
        // Test command execution with a mock sender that lacks permissions
        TestCommandSender sender = new TestCommandSender();
        
        String previousMessage = sender.getLastMessage();
        commandRegistry.executeCommand(sender, "advancedexample test");
        assertNotEquals(previousMessage, sender.getLastMessage());
        System.out.println("Message for no permission: " + sender.getLastMessage());
        assertTrue(sender.getLastMessage().contains("permission") || 
                   sender.getLastMessage().contains("Permission") ||
                   sender.getLastMessage().contains("don't have"),
                   "Expected message about permissions, but got: " + sender.getLastMessage());
        
        System.out.println("Permission system test passed!");
    }
    
    @Test
    void testPlayerOnlyCommand() {
        CommandRegistry commandRegistry = CommandRegistry.getInstance();
        
        // Test PlayerOnlyCommand
        PlayerOnlyCommand playerOnlyCommand = new PlayerOnlyCommand();
        commandRegistry.registerCommand(playerOnlyCommand);
        
        // Test command execution with console sender
        TestCommandSender consoleSender = new TestCommandSender("Console");
        String previousMessage = consoleSender.getLastMessage();
        commandRegistry.executeCommand(consoleSender, "playeronly");
        assertNotEquals(previousMessage, consoleSender.getLastMessage());
        System.out.println("Message for console execution: " + consoleSender.getLastMessage());
        assertTrue(consoleSender.getLastMessage().contains("player") ||
                   consoleSender.getLastMessage().contains("Player") ||
                   consoleSender.getLastMessage().contains("only"),
                   "Expected message about player-only command, but got: " + consoleSender.getLastMessage());
        
        // Test command execution with player sender
        TestCommandSender playerSender = new TestCommandSender("Player");
        playerSender.addPermission("sparky.commands.playeronly");
        previousMessage = playerSender.getLastMessage();
        commandRegistry.executeCommand(playerSender, "playeronly");
        assertNotEquals(previousMessage, playerSender.getLastMessage());
        System.out.println("Message for player execution: " + playerSender.getLastMessage());
        assertTrue(playerSender.getLastMessage().contains("Hello") ||
                   playerSender.getLastMessage().contains("player"),
                   "Expected success message, but got: " + playerSender.getLastMessage());
        
        System.out.println("Player-only command test passed!");
    }
    
    /**
     * Тестова реалізація CommandSender.
     */
    static class TestCommandSender implements CommandSender {
        private String name;
        private String lastMessage = "";
        private final List<String> allMessages = new ArrayList<>();
        private final HashSet<String> permissions = new HashSet<>();
        
        public TestCommandSender() {
            this.name = "TestSender";
        }
        
        public TestCommandSender(String name) {
            this.name = name;
        }
        
        @Override
        public void sendMessage(String message) {
            this.lastMessage = message;
            this.allMessages.add(message);
        }
        
        @Override
        public String getName() {
            return name;
        }
        
        @Override
        public boolean hasPermission(String permission) {
            return permissions.contains(permission);
        }
        
        public String getLastMessage() {
            return lastMessage;
        }
        
        public String getAllMessages() {
            return String.join(" | ", allMessages);
        }
        
        public void addPermission(String permission) {
            permissions.add(permission);
        }
    }
}