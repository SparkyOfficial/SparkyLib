# SparkyLib Minecraft Adapter

## Overview
The SparkyLib Minecraft Adapter is a specialized library that extends the core SparkyLib framework to provide seamless integration with Minecraft (Bukkit/Spigot) servers. It leverages the Entity-Component-System (ECS) architecture to create a powerful and flexible framework for Minecraft plugin development.

## Features

### ECS Components for Minecraft
- **BlockComponent** - Represents Minecraft blocks with position, type, and world information
- **PlayerComponent** - Manages player state including health, food level, experience, and online status
- **ItemComponent** - Handles Minecraft items with type, amount, durability, display name, and lore

### ECS Systems for Minecraft
- **BlockSystem** - Manages block manipulation and world interactions
- **PlayerSystem** - Handles player state updates and management
- **ItemSystem** - Manages item interactions and inventory operations

### Integration Features
- Seamless integration with Bukkit/Spigot API
- Event-driven architecture using SparkyLib's EventBus
- Scheduler integration for timed operations
- Particle effects system
- Command handling with permissions
- Asset management for resources

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.sparky</groupId>
    <artifactId>sparky-minecraft</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Usage Example

```java
// Initialize the Minecraft adapter
MinecraftAdapter minecraftAdapter = new MinecraftAdapter(yourPlugin);

// Create a player entity
Entity playerEntity = minecraftAdapter.getEntityManager().createEntity();
PlayerComponent playerComponent = new PlayerComponent(
    "Steve", 
    "player-uuid", 
    20, 20, 15, 10, 0.5f, true
);
playerEntity.addComponent(playerComponent);

// Create a block entity
Entity blockEntity = minecraftAdapter.getEntityManager().createEntity();
BlockComponent blockComponent = new BlockComponent(
    "diamond_ore", 
    50, 64, 50, 
    "world"
);
blockEntity.addComponent(blockComponent);

// The systems will automatically process these entities
minecraftAdapter.update();
```

## Architecture

The Minecraft adapter follows the same ECS pattern as the core SparkyLib:

1. **Entities** - Game objects that hold components
2. **Components** - Data containers that describe aspects of an entity
3. **Systems** - Logic processors that operate on entities with specific components

This architecture allows for:
- High performance through data-oriented design
- Easy extensibility by adding new components and systems
- Clear separation of concerns
- Flexible entity composition

## Requirements
- Java 17 or higher
- Bukkit/Spigot 1.19.4 or compatible
- SparkyLib core modules

## License
This project is licensed under the MIT License - see the LICENSE file for details.