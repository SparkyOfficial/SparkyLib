package com.sparky.bukkit;

import com.sparky.core.SparkyLogger;
import com.sparky.particles.ParticleEffect;
import com.sparky.particles.ParticleSpec;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

/**
 * Адаптер для відображення частинок Bukkit зі специфікацій SparkyLib.
 *
 * @author Андрій Будильников
 */
public class BukkitParticleAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(BukkitParticleAdapter.class);
    
    /**
     * Відображає ефект частинок у світі Bukkit.
     */
    public void spawnParticleEffect(ParticleEffect effect, World world, double x, double y, double z) {
        Location location = new Location(world, x, y, z);
        
        for (ParticleSpec spec : effect.getSpecs()) {
            try {
                // Convert SparkyLib particle type to Bukkit particle type
                Particle particleType = convertParticleType(spec.getType());
                
                if (particleType != null) {
                    world.spawnParticle(
                        particleType,
                        location,
                        spec.getCount(),
                        0, 0, 0, // offset
                        spec.getSpeed()
                    );
                }
            } catch (Exception e) {
                logger.error("Failed to spawn particle: " + spec.getType(), e);
            }
        }
        
        logger.debug("Spawned particle effect: " + effect.getName());
    }
    
    /**
     * Конвертує тип частинки SparkyLib у тип частинки Bukkit.
     */
    private Particle convertParticleType(String sparkyType) {
        switch (sparkyType.toLowerCase()) {
            case "flame":
                return Particle.FLAME;
            case "smoke":
                return Particle.SMOKE_NORMAL;
            case "heart":
                return Particle.HEART;
            case "note":
                return Particle.NOTE;
            case "portal":
                return Particle.PORTAL;
            default:
                logger.warn("Unknown particle type: " + sparkyType);
                return null;
        }
    }
}