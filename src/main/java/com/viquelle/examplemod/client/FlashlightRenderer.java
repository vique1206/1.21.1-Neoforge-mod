package com.viquelle.examplemod.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.data.AreaLightData;
import foundry.veil.impl.client.render.light.AreaLightRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.gui.PlayerListComponent;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.List;

public class FlashlightRenderer {
    private final Minecraft mc = Minecraft.getInstance();
    private static List<AreaLightData> lights;

    public static void render(){
        for (AreaLightData light : lights) {
            //VeilRenderSystem.renderer().getLightRenderer().addLight();
        }
        VeilRenderSystem.renderer().getLightRenderer().free();
    }
}
