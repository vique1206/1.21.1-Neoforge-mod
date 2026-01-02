package com.viquelle.examplemod.client.light;

import foundry.veil.api.client.render.light.data.LightData;
import foundry.veil.api.client.render.light.renderer.LightRenderHandle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLight<T extends LightData> implements IAbstractLight{
    protected int color;
    protected float brightness;
    protected boolean active = false;
    protected boolean isDirty = false;

    protected AbstractLight(Builder builder) {
        this.isDirty = true;
        this.active = true;
        this.color = builder.color;
        this.brightness = builder.brightness;
    }

    protected static abstract class Builder {
        private int color = 0xFFFFFF;
        private float brightness = 1.0f;
        private Player player;
        private LightData d;
        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setBrightness(float brightness) {
            this.brightness = brightness;
            return this;
        }

        public Builder setPlayer(Player player) {
            this.player = player;
            return this;
        }

        @ApiStatus.OverrideOnly
        public AbstractLight<?> build() {return null;}
    }
//    protected Map<String, List<CurveSegment>> curves = new HashMap<>();
//    protected int currentCurve = -1;
//    protected void applyCurve() {
//    }
//    protected static class CurveSegment {
//        protected int currentTick = 0;
//        protected int duration;
//        protected LightCurve curve = LightCurve.LINEAR;
//
//        protected void applyCurve() {
//
//        }
//    }
//    protected float lerp(float a, float b, float t) {
//        return a + (b - a) * t;
//    }

//    protected float applyCurve(float x, LightCurve c) {
//        return switch (c) {
//            case LINEAR -> x;
//            case EASE_IN -> x * x;
//            case EASE_OUT -> 1 - (1 - x) * (1 - x);
//            case EASE_IN_OUT -> x < 0.5f
//                    ? 2 * x * x
//                    : 1 - (float) Math.pow(-2 * x + 2, 2) / 2;
//            case FLICKER ->
//                    x + ((float) Math.random() - 0.5f) * 0.1f;
//        };
//    }

}