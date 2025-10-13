package com.sparky.commands;

import java.util.List;

/**
 * Приклад команди.
 *
 * @author Андрій Будильников
 */
public class ExampleCommand implements Command {
    @Override
    public String getName() {
        return "example";
    }
    
    @Override
    public String getDescription() {
        return "An example command";
    }
    
    @Override
    public String getUsage() {
        return "/example [message]";
    }
    
    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.isEmpty()) {
            sender.sendMessage("Hello from example command!");
        } else {
            sender.sendMessage("You said: " + String.join(" ", args));
        }
    }
}