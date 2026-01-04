package com.viquelle.examplemod.client;

import com.viquelle.examplemod.client.light.AbstractLight;
import foundry.veil.api.client.render.light.data.LightData;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class ClientLightManager {
    private static Map<UUID, AbstractLight<?>> activeLights = new HashMap<>();

    public static void enable(UUID id, AbstractLight<?> light) {
        if (!activeLights.containsKey(id)) {
            activeLights.put(id, light);
            light.register();
        }
    }
    public static void disable(UUID id) {
        AbstractLight<?> light = activeLights.remove(id);
        if (light != null) {
            light.unregister();
        }
    }

    public static AbstractLight<?> getLight(UUID id) {
        return activeLights.get(id);
    }

    public static void disableAll() {
        for (AbstractLight<?> light : activeLights.values()) {
            light.unregister();
        }
        activeLights.clear();
    }

    public static boolean isEnabled(UUID id) {
        return activeLights.containsKey(id);
    }
    public static void tick(float pT) {
        for (AbstractLight<?> light : activeLights.values()) {
            light.tick(pT);
        }
    }

}
