package com.viquelle.examplemod.item;

import com.viquelle.examplemod.ExampleMod;
import com.viquelle.examplemod.ExampleModClient;
import com.viquelle.examplemod.client.ClientLightManager;
import com.viquelle.examplemod.client.light.AbstractLight;
import com.viquelle.examplemod.client.light.AreaLight;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class FlashlightItem extends AbstractLightItem {

    public FlashlightItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);

        boolean enabled = isEnabled(stack);
        toggle(stack);
        enabled = !enabled;
        if (level.isClientSide) {
            ExampleMod.LOGGER.info("{}",getCooldown(stack));
            if (getCooldown(stack) > 0) {
                return InteractionResultHolder.pass(stack);
            }

            ExampleMod.LOGGER.info("[CLIENT] {}", enabled);

            UUID id = player.getUUID();
            AbstractLight<?> light = ClientLightManager.getLight(id);

            if (enabled) {
                if (light == null) {
                    light = new AreaLight.Builder()
                            .setPlayer(player)
                            .setBrightness(1.0f)
                            .setColor(0xFFFFFF)
                            .build();
                    ClientLightManager.enable(id,light);
                } else {
                    light.brightness = 1.0f;
                }
            } else {
                if (light != null) {
                    light.brightness = 0f;
                }
            }

            setCooldown(stack, 10);
            ExampleMod.LOGGER.info("{}",getCooldown(stack));
        } else {
            boolean nowEnabled = isEnabled(stack);
            ExampleMod.LOGGER.info("[SERVER] {}",nowEnabled);
            player.displayClientMessage(
                    Component.literal(nowEnabled ? "ON" : "OFF"),
                    true
            );
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}