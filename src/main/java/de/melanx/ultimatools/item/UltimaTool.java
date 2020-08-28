package de.melanx.ultimatools.item;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.lib.Function3;
import de.melanx.ultimatools.lib.Function5;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public class UltimaTool extends Item {

    public final int cooldown;
    private final Function3<World, PlayerEntity, Hand, Boolean> applyEffect;
    private final Function5<World, PlayerEntity, Hand, BlockPos, Direction, Boolean> applyBlock;
    private final BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity;

    public UltimaTool(Function3<World, PlayerEntity, Hand, Boolean> applyEffect) {
        this(100, applyEffect);
    }

    public UltimaTool(int cooldown, Function3<World, PlayerEntity, Hand, Boolean> applyEffect) {
        super(new Item.Properties().maxStackSize(1).maxDamage(cooldown).group(SkyblockUltimaTools.TAB));
        this.cooldown = cooldown;
        this.applyEffect = applyEffect;
        this.applyBlock = null;
        this.hitEntity = null;
    }

    public UltimaTool(Function5<World, PlayerEntity, Hand, BlockPos, Direction, Boolean> applyBlock) {
        this(100, applyBlock);
    }

    public UltimaTool(int cooldown, Function5<World, PlayerEntity, Hand, BlockPos, Direction, Boolean> applyBlock) {
        super(new Item.Properties().maxStackSize(1).maxDamage(cooldown).group(SkyblockUltimaTools.TAB));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = applyBlock;
        this.hitEntity = null;
    }

    public UltimaTool(Function3<World, PlayerEntity, Hand, Boolean> applyEffect, BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity) {
        this(100, applyEffect, hitEntity);
    }

    public UltimaTool(int cooldown, Function3<World, PlayerEntity, Hand, Boolean> applyEffect, BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity) {
        super(new Item.Properties().maxStackSize(1).maxDamage(cooldown).group(SkyblockUltimaTools.TAB));
        this.cooldown = cooldown;
        this.applyEffect = applyEffect;
        this.applyBlock = null;
        this.hitEntity = hitEntity;
    }

    public UltimaTool(BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity) {
        this(100, hitEntity);
    }

    public UltimaTool(int cooldown, BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity) {
        super(new Item.Properties().maxStackSize(1).maxDamage(cooldown).group(SkyblockUltimaTools.TAB));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = null;
        this.hitEntity = hitEntity;
    }

    public UltimaTool(Function5<World, PlayerEntity, Hand, BlockPos, Direction, Boolean> applyBlock, BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity) {
        this(100, applyBlock, hitEntity);
    }

    public UltimaTool(int cooldown, Function5<World, PlayerEntity, Hand, BlockPos, Direction, Boolean> applyBlock, BiFunction<LivingEntity, PlayerEntity, Boolean> hitEntity) {
        super(new Item.Properties().maxStackSize(1).maxDamage(cooldown).group(SkyblockUltimaTools.TAB));
        this.cooldown = cooldown;
        this.applyEffect = null;
        this.applyBlock = applyBlock;
        this.hitEntity = hitEntity;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int slot, boolean selected) {
        if (stack.getDamage() > 0) {
            stack.setDamage(stack.getDamage() - 1);
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (this.applyEffect != null) {
            ItemStack held = player.getHeldItem(hand);
            if (!world.isRemote) {
                if (held.getDamage() <= 0) {
                    if (this.applyEffect.apply(world, player, hand)) {
                        if (!player.isCreative())
                            held.setDamage(this.cooldown - 1);
                        player.setHeldItem(hand, held);
                        player.swing(hand, false);
                        return ActionResult.resultSuccess(held);
                    } else {
                        return ActionResult.resultFail(held);
                    }
                } else {
                    return ActionResult.resultFail(held);
                }
            } else {
                return ActionResult.resultPass(held);
            }
        } else {
            return super.onItemRightClick(world, player, hand);
        }
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        if (this.applyBlock != null && context.getPlayer() != null) {
            if (!context.getWorld().isRemote) {
                ItemStack stack = context.getItem();
                if (stack.getDamage() <= 0) {
                    if (this.applyBlock.apply(context.getWorld(), context.getPlayer(), context.getHand(), context.getPos(), context.getFace())) {
                        if (!context.getPlayer().isCreative())
                            stack.setDamage(this.cooldown - 1);
                        context.getPlayer().swing(context.getHand(), false);
                        return ActionResultType.SUCCESS;
                    } else {
                        return ActionResultType.FAIL;
                    }
                } else {
                    return ActionResultType.FAIL;
                }
            } else {
                return ActionResultType.PASS;
            }
        } else {
            return super.onItemUse(context);
        }
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (this.hitEntity != null && attacker instanceof PlayerEntity) {
            if (!attacker.getEntityWorld().isRemote) {
                PlayerEntity player = (PlayerEntity) attacker;
                if (stack.getDamage() <= 0) {
                    if (this.hitEntity.apply(target, player)) {
                        if (!player.isCreative())
                            stack.setDamage(this.cooldown - 1);
                        player.swing(Hand.MAIN_HAND, false);
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
            return super.hitEntity(stack, target, attacker);
        }
    }
}