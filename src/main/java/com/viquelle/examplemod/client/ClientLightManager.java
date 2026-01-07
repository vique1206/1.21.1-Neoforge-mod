package com.viquelle.examplemod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.viquelle.examplemod.ExampleMod;
import com.viquelle.examplemod.client.light.AbstractLight;
import com.viquelle.examplemod.client.light.AreaLight;
import com.viquelle.examplemod.client.light.PointLight;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.data.LightData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BambooSaplingBlock;

import java.util.*;

public class ClientLightManager {
    private static final Map<String, AbstractLight<?>> activeLights = new HashMap<>();
    private static final Map<String, Long> lastUsed = new HashMap<>();
    private static final String AMBIENT_SUFFIX = "_ambient_aura";
    private static long lastFrameTime = System.currentTimeMillis();

    public static void initPlayerLight(LocalPlayer player) {
        String ambientKey = player.getUUID() + AMBIENT_SUFFIX;
        PointLight light = new PointLight.Builder()
                .setPlayer(player)
                .setBrightness(0f)
                .setColor(0xFFDDAB)
                .build();

        add(ambientKey, light);
    }

    public static void add(String key, AbstractLight<?> light) {
        activeLights.put(key,light);
        light.register();
    }

    public static void remove(String key) {
        AbstractLight<?> light = activeLights.remove(key);
        if (light != null) {
            light.unregister();
        }
    }

    public static AbstractLight<?> getLight(String key) {
        return activeLights.get(key);
    }

    public static void tick(float pT) {
        long now = System.currentTimeMillis();
        LocalPlayer player = Minecraft.getInstance().player;

        // 1. Handle Ambient Light Logic
        if (player != null) {
            updateAmbientLight(player, pT, now);
        }

        // 2. Cleanup and Tick Logic
        List<String> toRemove = new ArrayList<>();
        for (Map.Entry<String, AbstractLight<?>> entry : activeLights.entrySet()) {
            String key = entry.getKey();
            AbstractLight<?> light = entry.getValue();

            // If brightness is low, mark as unused
            if (light.brightness < 0.01f) {
                lastUsed.putIfAbsent(key, now);
                // remove if unused for 10s AND it is NOT our persistent ambient light
                if (now - lastUsed.get(key) > 10_000 && !key.endsWith(AMBIENT_SUFFIX)) {
                    toRemove.add(key);
                }
            } else {
                lastUsed.put(key, now);
            }

            light.tick(pT);
        }

        for (String key : toRemove) {
            remove(key);
            lastUsed.remove(key);
        }
    }

    private static void updateAmbientLight(LocalPlayer player, float pT, long now) {
        String key = player.getUUID() + AMBIENT_SUFFIX;
        AbstractLight<?> rawLight = activeLights.get(key);
        // Safety check: verify it's the right type and exists
        if (!(rawLight instanceof PointLight ambient)) return;

        // Prevent garbage collection of this light
        lastUsed.put(key, now);
        float deltaTime = (now - lastFrameTime) / 1000f;
        lastFrameTime = now;
        // A. Check Environment Conditions
        BlockPos pos = player.blockPosition();
        int skyLight = player.level().getBrightness(LightLayer.SKY, pos);
        int blockLight = player.level().getBrightness(LightLayer.BLOCK, pos);

        boolean isDark = skyLight < 2 && blockLight == 0;
        // B. Check if Player has other active lights (e.g. Flashlight)
        // We look for any active light belonging to this player that isn't the ambient light itself
        boolean hasActiveSource = activeLights.entrySet().stream()
                .filter(e -> !e.getKey().equals(key)) // Ignore self
                .filter(e -> e.getKey().contains(player.getUUID().toString())) // Check player ownership
                .anyMatch(e -> e.getValue().brightness > 0.05f); // Is it actually on?

        // C. Calculate Target Brightness
        // If it's dark and we have no other light -> ON (0.3f is subtle). Otherwise -> OFF.
        float targetBrightness = (isDark && !hasActiveSource) ? 0.3f : 0.0f;
        float fadeSpeed = 0.2f;
        ExampleMod.LOGGER.info("data: {} || {}", deltaTime, targetBrightness);
        if (ambient.brightness < targetBrightness) {
            ambient.brightness = Math.min(ambient.brightness + deltaTime * fadeSpeed, targetBrightness);
        } else if (ambient.brightness > targetBrightness) {
            ambient.brightness = Math.max(ambient.brightness - deltaTime * fadeSpeed, targetBrightness);
        }

        ambient.syncWithObj(player, pT);
    }

}
