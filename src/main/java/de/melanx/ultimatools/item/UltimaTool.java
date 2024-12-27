package de.melanx.ultimatools.item;

import de.melanx.ultimatools.lib.Function3;
import de.melanx.ultimatools.lib.Function5;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public class UltimaTool extends Item {

    public final int cooldown;
    private final Function3<Level, Player, InteractionHand, Boolean> applyEffect;
    private final Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock;
    private final BiFunction<LivingEntity, Player, Boolean> hitEntity;

    public UltimaTool(Function3<Level, Player, InteractionHand, Boolean> applyEffect, Item.Properties properties) {
        this(100, applyEffect, properties);
    }

    public UltimaTool(int cooldown, Function3<Level, Player, InteractionHand, Boolean> applyEffect, Item.Properties properties) {
        super(properties.stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = applyEffect;
        this.applyBlock = null;
        this.hitEntity = null;
    }

    public UltimaTool(Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock, Item.Properties properties) {
        this(100, applyBlock, properties);
    }

    public UltimaTool(int cooldown, Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock, Item.Properties properties) {
        super(properties.stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = applyBlock;
        this.hitEntity = null;
    }

    public UltimaTool(Function3<Level, Player, InteractionHand, Boolean> applyEffect, BiFunction<LivingEntity, Player, Boolean> hitEntity, Item.Properties properties) {
        this(100, applyEffect, hitEntity, properties);
    }

    public UltimaTool(int cooldown, Function3<Level, Player, InteractionHand, Boolean> applyEffect, BiFunction<LivingEntity, Player, Boolean> hitEntity, Item.Properties properties) {
        super(properties.stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = applyEffect;
        this.applyBlock = null;
        this.hitEntity = hitEntity;
    }

    public UltimaTool(BiFunction<LivingEntity, Player, Boolean> hitEntity, Item.Properties properties) {
        this(100, hitEntity, properties);
    }

    public UltimaTool(int cooldown, BiFunction<LivingEntity, Player, Boolean> hitEntity, Item.Properties properties) {
        super(properties.stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = null;
        this.hitEntity = hitEntity;
    }

    public UltimaTool(Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock, BiFunction<LivingEntity, Player, Boolean> hitEntity, Item.Properties properties) {
        this(100, applyBlock, hitEntity, properties);
    }

    public UltimaTool(int cooldown, Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock, BiFunction<LivingEntity, Player, Boolean> hitEntity, Item.Properties properties) {
        super(properties.stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = applyBlock;
        this.hitEntity = hitEntity;
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (this.applyEffect == null) {
            return super.use(level, player, hand);
        }

        ItemStack held = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResult.PASS;
        }

        if (player.getCooldowns().isOnCooldown(held)) {
            return InteractionResult.FAIL;
        }

        if (!this.applyEffect.apply(level, player, hand)) {
            return InteractionResult.FAIL;
        }

        if (!player.isCreative()) {
            player.getCooldowns().addCooldown(held, this.cooldown);
        }

        player.setItemInHand(hand, held);
        player.swing(hand, false);
        return InteractionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (this.applyBlock == null || context.getPlayer() == null) {
            return super.useOn(context);
        }

        if (context.getLevel().isClientSide) {
            return InteractionResult.PASS;
        }

        ItemStack held = context.getItemInHand();
        if (context.getPlayer().getCooldowns().isOnCooldown(held)) {
            return InteractionResult.FAIL;
        }

        if (!this.applyBlock.apply(
                context.getLevel(),
                context.getPlayer(),
                context.getHand(),
                context.getClickedPos(),
                context.getClickedFace())) {
            return InteractionResult.FAIL;
        }

        if (!context.getPlayer().isCreative()) {
            context.getPlayer().getCooldowns().addCooldown(held, this.cooldown);
        }

        context.getPlayer().swing(context.getHand(), false);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (this.hitEntity == null || !(attacker instanceof Player player)) {
            return super.hurtEnemy(stack, target, attacker);
        }

        if (attacker.getCommandSenderWorld().isClientSide) {
            return false;
        }

        if (player.getCooldowns().isOnCooldown(stack)) {
            return false;
        }

        if (!this.hitEntity.apply(target, player)) {
            return false;
        }

        if (!player.isCreative()) {
            player.getCooldowns().addCooldown(stack, this.cooldown);
        }

        player.swing(InteractionHand.MAIN_HAND, false);
        return true;
    }
}
