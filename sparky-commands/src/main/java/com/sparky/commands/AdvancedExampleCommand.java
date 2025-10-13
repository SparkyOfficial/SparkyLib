package com.sparky.commands;

import java.util.*;

/**
 * Приклад розширеної команди з підтримкою дозволів, псевдонімів та обмежень.
 *
 * @author Богдан Кравчук
 */
public class AdvancedExampleCommand extends BaseCommand {
    
    public AdvancedExampleCommand() {
        super("advancedexample", "An advanced example command", "/advancedexample <message>", 
              new HashSet<>(), Arrays.asList("aexample", "advex"), false, 1, 5);
        
        // Add permissions
        addPermission(new Permission("sparky.commands.advancedexample", "Allows use of the advanced example command"));
        addPermission(new Permission("sparky.commands.basic", "Basic command permission"));
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) {
        sender.sendMessage("Advanced command executed successfully!");
        sender.sendMessage("Arguments: " + String.join(", ", args));
        
        // Show command info
        sender.sendMessage("Command name: " + getName());
        sender.sendMessage("Aliases: " + String.join(", ", getAliases()));
        sender.sendMessage("Min args: " + getMinArgs() + ", Max args: " + getMaxArgs());
    }
}