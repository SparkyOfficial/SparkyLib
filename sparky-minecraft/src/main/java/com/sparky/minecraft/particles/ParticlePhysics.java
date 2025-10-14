package com.sparky.minecraft.particles;

import com.sparky.minecraft.math.Vector3D;

/**
 * Фізичний рух частинок для Minecraft.
 *
 * @author Андрій Будильников
 */
public class ParticlePhysics {
    private Vector3D position;
    private Vector3D velocity;
    private Vector3D acceleration;
    private double mass;
    private boolean affectedByGravity;
    private double dragCoefficient;
    private double restitution; // Коефіцієнт відновлення (пружності)

    public ParticlePhysics(Vector3D position) {
        this.position = new Vector3D(position);
        this.velocity = new Vector3D(0, 0, 0);
        this.acceleration = new Vector3D(0, 0, 0);
        this.mass = 1.0;
        this.affectedByGravity = true;
        this.dragCoefficient = 0.01;
        this.restitution = 0.8; // 80% енергії зберігається при відскакуванні
    }

    
    public Vector3D getPosition() {
        return new Vector3D(position);
    }

    public void setPosition(Vector3D position) {
        this.position = new Vector3D(position);
    }

    public Vector3D getVelocity() {
        return new Vector3D(velocity);
    }

    public void setVelocity(Vector3D velocity) {
        this.velocity = new Vector3D(velocity);
    }

    public Vector3D getAcceleration() {
        return new Vector3D(acceleration);
    }

    public void setAcceleration(Vector3D acceleration) {
        this.acceleration = new Vector3D(acceleration);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        if (mass <= 0) {
            throw new IllegalArgumentException("Mass must be positive");
        }
        this.mass = mass;
    }

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public void setAffectedByGravity(boolean affectedByGravity) {
        this.affectedByGravity = affectedByGravity;
    }

    public double getDragCoefficient() {
        return dragCoefficient;
    }

    public void setDragCoefficient(double dragCoefficient) {
        this.dragCoefficient = dragCoefficient;
    }

    public double getRestitution() {
        return restitution;
    }

    public void setRestitution(double restitution) {
        this.restitution = Math.max(0, Math.min(1, restitution)); // Обмежуємо між 0 та 1
    }

    /**
     * Застосовує силу до частинки.
     */
    public void applyForce(Vector3D force) {
        // F = ma => a = F/m
        Vector3D forceAcceleration = force.divide(mass);
        acceleration = acceleration.add(forceAcceleration);
    }

    /**
     * Застосовує імпульс до частинки.
     */
    public void applyImpulse(Vector3D impulse) {
        // J = Δp = mΔv => Δv = J/m
        Vector3D deltaVelocity = impulse.divide(mass);
        velocity = velocity.add(deltaVelocity);
    }

    /**
     * Застосовує гравітацію.
     */
    public void applyGravity(double gravity) {
        if (affectedByGravity) {
            applyForce(new Vector3D(0, -gravity * mass, 0));
        }
    }

    /**
     * Застосовує опір середовища.
     */
    public void applyDrag() {
        // F_drag = -dragCoefficient * v
        Vector3D dragForce = velocity.multiply(-dragCoefficient);
        applyForce(dragForce);
    }

    /**
     * Оновлює фізичний стан частинки.
     */
    public void update(double deltaTime) {
        // Застосовуємо гравітацію
        applyGravity(9.81); // Стандартна гравітація Землі
        
        // Застосовуємо опір
        applyDrag();
        
        // Оновлюємо швидкість згідно з прискоренням
        velocity = velocity.add(acceleration.multiply(deltaTime));
        
        // Оновлюємо позицію згідно зі швидкістю
        position = position.add(velocity.multiply(deltaTime));
        
        // Скидаємо прискорення для наступного кадру
        acceleration = new Vector3D(0, 0, 0);
    }

    /**
     * Обробляє зіткнення з площиною.
     */
    public void collideWithPlane(Vector3D planeNormal, double planeDistance) {
        // Обчислюємо відстань від частинки до площини
        double distance = position.dot(planeNormal) - planeDistance;
        
        // Якщо частинка перетинає площину
        if (distance < 0) {
            // Коригуємо позицію, щоб частинка не проникала в площину
            position = position.subtract(planeNormal.multiply(distance));
            
            // Відбиваємо швидкість
            double velocityDotNormal = velocity.dot(planeNormal);
            if (velocityDotNormal < 0) { // Лише якщо частинка рухається до площини
                Vector3D reflection = velocity.subtract(planeNormal.multiply(2 * velocityDotNormal));
                velocity = reflection.multiply(restitution);
            }
        }
    }

    /**
     * Обробляє зіткнення з коробкою.
     */
    public void collideWithBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        // Перевіряємо зіткнення з мінімальними гранями
        if (position.getX() < minX) {
            position = new Vector3D(minX, position.getY(), position.getZ());
            if (velocity.getX() < 0) {
                velocity = new Vector3D(-velocity.getX() * restitution, velocity.getY(), velocity.getZ());
            }
        }
        if (position.getY() < minY) {
            position = new Vector3D(position.getX(), minY, position.getZ());
            if (velocity.getY() < 0) {
                velocity = new Vector3D(velocity.getX(), -velocity.getY() * restitution, velocity.getZ());
            }
        }
        if (position.getZ() < minZ) {
            position = new Vector3D(position.getX(), position.getY(), minZ);
            if (velocity.getZ() < 0) {
                velocity = new Vector3D(velocity.getX(), velocity.getY(), -velocity.getZ() * restitution);
            }
        }
        
        // Перевіряємо зіткнення з максимальними гранями
        if (position.getX() > maxX) {
            position = new Vector3D(maxX, position.getY(), position.getZ());
            if (velocity.getX() > 0) {
                velocity = new Vector3D(-velocity.getX() * restitution, velocity.getY(), velocity.getZ());
            }
        }
        if (position.getY() > maxY) {
            position = new Vector3D(position.getX(), maxY, position.getZ());
            if (velocity.getY() > 0) {
                velocity = new Vector3D(velocity.getX(), -velocity.getY() * restitution, velocity.getZ());
            }
        }
        if (position.getZ() > maxZ) {
            position = new Vector3D(position.getX(), position.getY(), maxZ);
            if (velocity.getZ() > 0) {
                velocity = new Vector3D(velocity.getX(), velocity.getY(), -velocity.getZ() * restitution);
            }
        }
    }

    @Override
    public String toString() {
        return "ParticlePhysics{" +
               "position=" + position +
               ", velocity=" + velocity +
               ", acceleration=" + acceleration +
               ", mass=" + mass +
               '}';
    }
}