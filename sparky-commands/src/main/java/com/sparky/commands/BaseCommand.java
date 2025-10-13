package com.sparky.commands;

import java.util.*;

/**
 * Базова реалізація розширеного інтерфейсу команди.
 *
 * @author Богдан Кравчук
 */
public abstract class BaseCommand implements AdvancedCommand {
    private final String name;
    private final String description;
    private final String usage;
    private final Set<Permission> permissions;
    private final List<String> aliases;
    private final boolean playerOnly;
    private final int minArgs;
    private final int maxArgs;
    
    protected BaseCommand(String name, String description, String usage) {
        this(name, description, usage, new HashSet<>(), new ArrayList<>(), false, 0, Integer.MAX_VALUE);
    }
    
    protected BaseCommand(String name, String description, String usage, 
                         Set<Permission> permissions, List<String> aliases, 
                         boolean playerOnly, int minArgs, int maxArgs) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permissions = permissions;
        this.aliases = aliases;
        this.playerOnly = playerOnly;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public String getUsage() {
        return usage;
    }
    
    @Override
    public Set<Permission> getPermissions() {
        return new HashSet<>(permissions);
    }
    
    @Override
    public List<String> getAliases() {
        return new ArrayList<>(aliases);
    }
    
    @Override
    public boolean isPlayerOnly() {
        return playerOnly;
    }
    
    @Override
    public int getMinArgs() {
        return minArgs;
    }
    
    @Override
    public int getMaxArgs() {
        return maxArgs;
    }
    
    /**
     * Додає дозвіл до команди.
     */
    protected void addPermission(Permission permission) {
        this.permissions.add(permission);
    }
    
    /**
     * Додає псевдонім до команди.
     */
    protected void addAlias(String alias) {
        this.aliases.add(alias);
    }
}