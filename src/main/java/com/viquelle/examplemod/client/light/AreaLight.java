package com.viquelle.examplemod.client.light;

import foundry.veil.api.client.render.light.data.AreaLightData;
import net.minecraft.world.phys.Vec3;

public class AreaLight extends AbstractLight<AreaLightData>{
    protected Vec3 pos;

    protected AreaLight(Builder builder) {
        super(builder);
        this.pos = builder.pos;
    }

    public static class Builder extends AbstractLight.Builder {
        private Vec3 pos;

        public Builder setPosition(Vec3 pos) {
            this.pos = pos;
            return this;
        }

        public Builder setPosition(double x, double y, double z) {
            this.pos = new Vec3(x,y,z);
            return this;
        }

        @Override
        public AreaLight build() {
            return new AreaLight(this);
        }
    }
//    public IAbstractLight create() {
//        light = new AreaLightData();
//        light.setBrightness(0.7f)
//                .setDistance(32f)
//                .setAngle(0.6f)
//                .setSize(0.1f,0.1f)
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

//    @Override
//    public void update(LocalPlayer player, float partialTick) {
//        ExampleMod.LOGGER.info("[TICK] TRYING TO TICK");
//        if (!active) return;
//        ExampleMod.LOGGER.info("[TICK] ACTIVE PASSED!");
//
//        float pitch = (float) -Math.toRadians(player.getXRot());
//        float yaw = (float) Math.toRadians(player.getYRot());
//        Quaternionf orientation = new Quaternionf().rotateXYZ(pitch,yaw,0.0f);
//        light.getOrientation().set(orientation);
//
//        Vec3 eyePos = player.getEyePosition(partialTick);
//        light.getPosition().set(
//                (float) eyePos.x,
//                (float) eyePos.y,
//                (float) eyePos.z
//        );
//        ExampleMod.LOGGER.info("[TICK] rot: {} || eyePos: {}", orientation,eyePos);
//    }

    @Override
    public void remove() {

    }
}
