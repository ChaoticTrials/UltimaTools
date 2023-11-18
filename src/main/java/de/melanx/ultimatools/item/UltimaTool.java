package de.melanx.ultimatools.item;

import de.melanx.ultimatools.lib.Function3;
import de.melanx.ultimatools.lib.Function5;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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

    public UltimaTool(Function3<Level, Player, InteractionHand, Boolean> applyEffect) {
        this(100, applyEffect);
    }

    public UltimaTool(int cooldown, Function3<Level, Player, InteractionHand, Boolean> applyEffect) {
        super(new Item.Properties().stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = applyEffect;
        this.applyBlock = null;
        this.hitEntity = null;
    }

    public UltimaTool(Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock) {
        this(100, applyBlock);
    }

    public UltimaTool(int cooldown, Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock) {
        super(new Item.Properties().stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = applyBlock;
        this.hitEntity = null;
    }

    public UltimaTool(Function3<Level, Player, InteractionHand, Boolean> applyEffect, BiFunction<LivingEntity, Player, Boolean> hitEntity) {
        this(100, applyEffect, hitEntity);
    }

    public UltimaTool(int cooldown, Function3<Level, Player, InteractionHand, Boolean> applyEffect, BiFunction<LivingEntity, Player, Boolean> hitEntity) {
        super(new Item.Properties().stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = applyEffect;
        this.applyBlock = null;
        this.hitEntity = hitEntity;
    }

    public UltimaTool(BiFunction<LivingEntity, Player, Boolean> hitEntity) {
        this(100, hitEntity);
    }

    public UltimaTool(int cooldown, BiFunction<LivingEntity, Player, Boolean> hitEntity) {
        super(new Item.Properties().stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = null;
        this.hitEntity = hitEntity;
    }

    public UltimaTool(Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock, BiFunction<LivingEntity, Player, Boolean> hitEntity) {
        this(100, applyBlock, hitEntity);
    }

    public UltimaTool(int cooldown, Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> applyBlock, BiFunction<LivingEntity, Player, Boolean> hitEntity) {
        super(new Item.Properties().stacksTo(1).durability(cooldown));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = applyBlock;
        this.hitEntity = hitEntity;
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return false;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (this.applyEffect != null) {
            ItemStack held = player.getItemInHand(hand);
            if (!level.isClientSide) {
                if (!player.getCooldowns().isOnCooldown(this)) {
                    if (this.applyEffect.apply(level, player, hand)) {
                        if (!player.isCreative()) player.getCooldowns().addCooldown(this, this.cooldown);
                        player.setItemInHand(hand, held);
                        player.swing(hand, false);
                        return InteractionResultHolder.success(held);
                    } else {
                        return InteractionResultHolder.fail(held);
                    }
                } else {
                    return InteractionResultHolder.fail(held);
                }
            } else {
                return InteractionResultHolder.pass(held);
            }
        } else {
            return super.use(level, player, hand);
        }
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        if (this.applyBlock != null && context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {
                if (!context.getPlayer().getCooldowns().isOnCooldown(this)) {
                    if (this.applyBlock.apply(context.getLevel(), context.getPlayer(), context.getHand(), context.getClickedPos(), context.getClickedFace())) {
                        if (!context.getPlayer().isCreative()) context.getPlayer().getCooldowns().addCooldown(this, this.cooldown);
                        context.getPlayer().swing(context.getHand(), false);
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.FAIL;
                    }
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                return InteractionResult.PASS;
            }
        } else {
            return super.useOn(context);
        }
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (this.hitEntity != null && attacker instanceof Player) {
            if (!attacker.getCommandSenderWorld().isClientSide) {
                Player player = (Player) attacker;
                if (!player.getCooldowns().isOnCooldown(this)) {
                    if (this.hitEntity.apply(target, player)) {
                        if (!player.isCreative()) player.getCooldowns().addCooldown(this, this.cooldown);
                        player.swing(InteractionHand.MAIN_HAND, false);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return super.hurtEnemy(stack, target, attacker);
        }
    }
}
