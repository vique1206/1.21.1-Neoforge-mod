package com.viquelle.examplemod.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public abstract class AbstractLightItem extends Item {
    public static final String TAG_ENABLED = "enabled";
    public static final String TAG_FUEL = "fuel";
    public static final String TAG_COOLDOWN = "flashlight_cooldown";

    public AbstractLightItem(Properties properties) {
        super(properties);
    }

    public static boolean isEnabled(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean(TAG_ENABLED);
    }

    public static void toggleTo(ItemStack stack, boolean value) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var tag = data.copyTag(); tag.putBoolean(TAG_ENABLED,value);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void toggle(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var tag = data.copyTag(); tag.putBoolean(TAG_ENABLED, !tag.getBoolean(TAG_ENABLED));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static void setFuel(ItemStack stack, int value) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var tag = data.copyTag(); tag.putInt(TAG_FUEL, value);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public static int getFuel(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getInt(TAG_FUEL);
    }

    public static void consumeFuel(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        var tag = data.copyTag(); tag.putInt(TAG_FUEL, Math.max(0,getFuel(stack) - 1));
    }

    public static int getCooldown(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag()
                .getInt(TAG_COOLDOWN);
    }

    public static void setCooldown(ItemStack stack, int ticks) {
        CompoundTag data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag();
        data.putInt(TAG_COOLDOWN, ticks);
        stack.set(DataComponents.CUSTOM_DATA,CustomData.of(data));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
//        if (level.isClientSide && !selected && !isEnabled(stack)) return;
//
//        float fuel = getFuel(stack);
//        if (fuel <= 0) {
//            setEnabled(stack,false);
//            return;
//        }
//
//        consumeFuel(stack);
        if (level.isClientSide) {
            int cd = getCooldown(stack);
            if (cd > 0) {
                setCooldown(stack, cd - 1);
            }
        }
    }

}
