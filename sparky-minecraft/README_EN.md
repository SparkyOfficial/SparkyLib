# Sparky Minecraft Library

A comprehensive library for Minecraft plugin development with advanced features for particles, mathematics, and more.

## Features

### Advanced Particle System

The library provides a powerful particle system with the following capabilities:

1. **ParticleEffect3D** - Advanced 3D particle effects with physics simulation
   - Position, velocity, and acceleration tracking
   - Lifetime management
   - Physics simulation (gravity, drag, forces)
   - Pre-built effects (explosions, rain, fire)

2. **ParticleSystem** - Management system for multiple particle effects
   - Add/remove effects
   - Start/stop management
   - Automatic cleanup of expired effects

### Mathematical Library

Comprehensive mathematical utilities for 3D computations:

1. **Vector3D** - 3D vector mathematics
   - Basic operations (add, subtract, multiply, divide)
   - Advanced operations (dot product, cross product, normalization)
   - Distance calculations
   - Rotation operations (X, Y, Z axes)
   - Interpolation (lerp)

2. **TrigonometryUtils** - Trigonometric utilities
   - Angle normalization
   - Spherical coordinate conversions
   - Interpolation functions

3. **ComplexNumber** - Complex number mathematics
   - Arithmetic operations
   - Power and root calculations
   - Trigonometric functions

4. **Matrix4x4** - 4x4 matrix operations
   - Transformations (translation, rotation, scaling)
   - Projections (perspective, orthographic)
   - Matrix multiplication and inversion

## Usage Examples

### Creating Particle Effects

```java
// Create a simple particle effect
Vector3D position = new Vector3D(0, 64, 0);
ParticleEffect3D effect = new ParticleEffect3D("simple_effect", position);

// Create a moving particle effect
Vector3D velocity = new Vector3D(1, 2, 0);
ParticleEffect3D movingEffect = new ParticleEffect3D("moving_effect", position, velocity, 5.0);

// Create an explosion
ParticleEffect3D explosion = ParticleEffect3D.createExplosion("explosion", position, 20);
```

### Physics Simulation

```java
// Create a particle with physics
ParticleEffect3D physicsEffect = new ParticleEffect3D("physics", position);
physicsEffect.setVelocity(new Vector3D(5, 10, 0));
physicsEffect.applyGravity(9.8);
physicsEffect.applyDrag(0.1);

// Update the effect
physicsEffect.update(0.016); // Update with 16ms delta time
```

### Particle System Management

```java
// Create and manage a particle system
ParticleSystem system = new ParticleSystem();
system.addEffect(effect);
system.addEffect(movingEffect);
system.start();

// Update the system
system.update(0.016);
```

### Mathematical Operations

```java
// Vector operations
Vector3D v1 = new Vector3D(1, 2, 3);
Vector3D v2 = new Vector3D(4, 5, 6);
Vector3D result = v1.add(v2);
double dotProduct = v1.dot(v2);
Vector3D crossProduct = v1.cross(v2);

// Trigonometry
double normalizedAngle = TrigonometryUtils.normalizeAngle(Math.PI * 3);
Vector3D cartesian = TrigonometryUtils.sphericalToCartesian(5, Math.PI/4, Math.PI/3);

// Complex numbers
ComplexNumber c1 = new ComplexNumber(3, 4);
ComplexNumber c2 = new ComplexNumber(1, 2);
ComplexNumber sum = c1.add(c2);

// Matrix operations
Matrix4x4 translation = Matrix4x4.translation(1, 2, 3);
Matrix4x4 rotation = Matrix4x4.rotationY(Math.PI/2);
Matrix4x4 transform = translation.multiply(rotation);
```

## Installation

Add the following dependency to your Maven project:

```xml
<dependency>
    <groupId>com.sparky</groupId>
    <artifactId>sparky-minecraft</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Requirements

- Java 17 or higher
- Minecraft 1.19.4 or higher
- Spigot/Paper API

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

Andriy Budilnikov