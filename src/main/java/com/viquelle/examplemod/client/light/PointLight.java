package com.viquelle.examplemod.client.light;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.data.PointLightData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PointLight extends AbstractLight<PointLightData>{
    protected Vec3 pos;
    protected PointLight(Builder builder) {
        super(builder);
        this.pos = builder.pos;
    }

    public static class Builder extends AbstractLight.Builder {
        private Vec3 pos;

        public PointLight.Builder setPosition(Vec3 pos) {
            this.pos = pos;
            return this;
        }

        public PointLight.Builder setPosition(double x, double y, double z) {
            this.pos = new Vec3(x,y,z);
            return this;
        }

        @Override
        public PointLight build() {
            return new PointLight(this);
        }
    }
//    @Override
//    public IAbstractLight create() {
//        light = new PointLightData();
//        light.setBrightness(1.0f)
//                .setRadius(6f)
//                .setColor(0xFFFFFF);
//
//        handle = VeilRenderSystem.renderer()
//                .getLightRenderer()
//                .addLight(light);
//
//        active = true;
//    }

    @Override
    public void tick(float partialTick) {

    }

    @Override
    public void remove() {

    }

}
