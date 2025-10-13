package com.sparky.commands;

import com.sparky.core.SparkyLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * Реєстр команд.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class CommandRegistry {
    private static final CommandRegistry INSTANCE = new CommandRegistry();
    
    private final SparkyLogger logger = SparkyLogger.getLogger(CommandRegistry.class);
    private final Map<String, Command> commands = new HashMap<>();
    
    private CommandRegistry() {}
    
    public static CommandRegistry getInstance() {
        return INSTANCE;
    }
    
    /**
     * Реєструє команду.
     */
    public void registerCommand(Command command) {
        String name = command.getName().toLowerCase();
        if (commands.containsKey(name)) {
            logger.warn("Command " + name + " is already registered, overwriting");
        }
        commands.put(name, command);
        logger.info("Registered command: " + name);
        
        // Register aliases if it's an AdvancedCommand
        if (command instanceof AdvancedCommand) {
            AdvancedCommand advancedCommand = (AdvancedCommand) command;
            for (String alias : advancedCommand.getAliases()) {
                String aliasLower = alias.toLowerCase();
                if (commands.containsKey(aliasLower)) {
                    logger.warn("Alias " + aliasLower + " is already registered, overwriting");
                }
                commands.put(aliasLower, command);
                logger.info("Registered alias: " + aliasLower + " for command: " + name);
            }
        }
    }
    
    /**
     * Видаляє команду.
     */
    public void unregisterCommand(String name) {
        Command command = commands.get(name.toLowerCase());
        if (command != null) {
            commands.remove(name.toLowerCase());
            logger.info("Unregistered command: " + name);
            
            // Remove aliases if it's an AdvancedCommand
            if (command instanceof AdvancedCommand) {
                AdvancedCommand advancedCommand = (AdvancedCommand) command;
                for (String alias : advancedCommand.getAliases()) {
                    commands.remove(alias.toLowerCase());
                    logger.info("Unregistered alias: " + alias);
                }
            }
        }
    }
    
    /**
     * Отримує команду за назвою.
     */
    public Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    
    /**
     * Виконує команду.
     */
    public void executeCommand(CommandSender sender, String commandLine) {
        String[] parts = commandLine.trim().split("\\s+");
        if (parts.length == 0) {
            return;
        }
        
        String commandName = parts[0];
        Command command = getCommand(commandName);
        
        if (command == null) {
            sender.sendMessage("Unknown command: " + commandName);
            return;
        }
        
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        
        // Check permissions and other constraints for AdvancedCommand
        if (command instanceof AdvancedCommand) {
            AdvancedCommand advancedCommand = (AdvancedCommand) command;
            
            // Check if command is player-only
            if (advancedCommand.isPlayerOnly() && "Console".equals(sender.getName())) {
                sender.sendMessage("This command can only be executed by players.");
                return;
            }
            
            // Check permissions
            for (Permission permission : advancedCommand.getPermissions()) {
                if (!sender.hasPermission(permission.getPermissionNode())) {
                    sender.sendMessage("You don't have permission to execute this command. Required permission: " + permission.getPermissionNode());
                    return;
                }
            }
            
            // Check argument count
            if (args.length < advancedCommand.getMinArgs()) {
                sender.sendMessage("Too few arguments. Usage: " + advancedCommand.getUsage());
                return;
            }
            
            if (args.length > advancedCommand.getMaxArgs()) {
                sender.sendMessage("Too many arguments. Usage: " + advancedCommand.getUsage());
                return;
            }
        }
        
        try {
            command.execute(sender, java.util.Arrays.asList(args));
        } catch (Exception e) {
            logger.error("Error executing command: " + commandName, e);
            sender.sendMessage("Error executing command: " + e.getMessage());
        }
    }
}