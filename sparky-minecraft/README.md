# Sparky Minecraft Library

A comprehensive Minecraft library built in Java with advanced features for game development.

## Features

### Audio System
- 3D spatial audio with realistic sound positioning
- Multiple audio sources and listeners
- Sound effects and ambient sounds
- Volume control and audio management

### Redstone System
- Complete redstone circuit simulation
- Component-based architecture with levers, wires, lamps, pistons
- Signal propagation and power management
- Complex circuit support

### Rendering System
- Advanced 3D rendering engine
- Camera system with perspective projection
- Lighting system with point and directional lights
- Texture management and shader support
- Particle effects and weather rendering

### Particle System
- Physics-based particle simulation
- Explosion, rain, and fire effects
- Collision detection and response
- Customizable particle behaviors

### Weather System
- Dynamic weather simulation
- Rain, thunderstorms, snow, and fog
- Wind effects and temperature simulation
- Smooth weather transitions

## Installation

```xml
<dependency>
    <groupId>com.sparky</groupId>
    <artifactId>sparky-minecraft</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Usage

### Audio System
```java
AudioManager audioManager = new AudioManager();
audioManager.loadSound("ambient", "ambient/cave_ambience.wav");
audioManager.playSound("ambient");
```

### Redstone System
```java
RedstoneSystem redstoneSystem = new RedstoneSystem();
Vector3D leverPos = new Vector3D(0, 64, 0);
RedstoneComponent lever = new RedstoneComponent(
    RedstoneComponentType.LEVER, leverPos);
redstoneSystem.addComponent(lever);
```

### Rendering System
```java
RenderEngine renderEngine = new RenderEngine();
renderEngine.initialize();
Camera camera = renderEngine.getCamera();
```

## Testing

Run tests with Maven:
```bash
mvn test
```

## Demo

Run the complete demo:
```bash
mvn compile exec:java
```

## License

This project is licensed under the MIT License.