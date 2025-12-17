package com.viquelle.examplemod.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Flashlight {
    public Flashlight(boolean Enabled) {
        enabled = Enabled;
    }

    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void Switch() {
        enabled = !enabled;
    }

    public void forceOn() {
        enabled = true;
    }

    public void forceOff() {
        enabled = false;
    }

    public static final Codec<Flashlight> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("flashlight").forGetter(Flashlight::getFlashlight)
    ).apply(instance, Flashlight::new));

    private boolean getFlashlight() {
        return enabled;
    }

    public static final Flashlight DEFAULT = new Flashlight(false);
}
