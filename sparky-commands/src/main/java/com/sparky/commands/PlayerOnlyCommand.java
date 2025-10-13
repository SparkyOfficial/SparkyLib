package com.sparky.commands;

import java.util.*;

/**
 * Приклад команди, доступної лише для гравців.
 *
 * @author Богдан Кравчук
 */
public class PlayerOnlyCommand extends BaseCommand {
    
    public PlayerOnlyCommand() {
        super("playeronly", "A command that can only be executed by players", "/playeronly", 
              new HashSet<>(), Arrays.asList("ponly"), true, 0, 0); // Player only, no args needed
        
        // Add permission
        addPermission(new Permission("sparky.commands.playeronly", "Allows use of the player only command"));
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) {
        sender.sendMessage("Hello, player! This command can only be executed by players.");
    }
}