package de.melanx.ultimatools.util;

import com.google.common.collect.ImmutableList;
import de.melanx.ultimatools.lib.Function5;
import de.melanx.ultimatools.lib.ListHandlers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import org.apache.commons.compress.utils.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class ToolEffects {

    private static final Random RANDOM = new Random();

    private ToolEffects() {

    }

    public static boolean placeWater(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        BlockPos target = pos.relative(face);
        if (!level.getBlockState(target).canBeReplaced(Fluids.WATER)
                && !level.getBlockState(target).hasProperty(BlockStateProperties.WATERLOGGED))
            return false;
        if (!player.mayUseItemAt(pos, face, player.getItemInHand(hand)))
            return false;

        if (!level.getBlockState(target).hasProperty(BlockStateProperties.WATERLOGGED)) {
            level.destroyBlock(target, true);
            level.setBlockAndUpdate(target, Fluids.WATER.defaultFluidState().createLegacyBlock());
        } else {
            level.setBlockAndUpdate(target, level.getBlockState(target).setValue(BlockStateProperties.WATERLOGGED, true));
        }

        return true;
    }

    public static boolean spawnAnimal(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        BlockPos target = pos.relative(face);
        if (level.getBlockState(target).canOcclude())
            return false;
        if (!player.mayUseItemAt(pos, face, player.getItemInHand(hand)))
            return false;

        EntityType<?> entityType;
        if (level.getBlockState(target).getFluidState().getType() == Fluids.WATER
                || level.getBlockState(target).getFluidState().getType() == Fluids.FLOWING_WATER) {
            entityType = ListHandlers.WATER_ANIMALS.get(level.random.nextInt(ListHandlers.WATER_ANIMALS.size()));
        } else {
            entityType = ListHandlers.ANIMALS.get(level.random.nextInt(ListHandlers.ANIMALS.size()));
        }

        Mob entity = (Mob) entityType.create(level);
        if (entity == null)
            return false;
        entity.moveTo(target.getX() + 0.5, target.getY() + 0.1, target.getZ() + 0.5, player.getYHeadRot() - 180, 0);
        if (level instanceof ServerLevel) {
            entity.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(target), MobSpawnType.TRIGGERED, null, null);
        }
        if (entity instanceof Animal) {
            ((Animal) entity).setAge(-24000);
        }
        level.addFreshEntity(entity);

        return true;
    }

    public static boolean applyMagicDamage(LivingEntity target, Player player) {
        if (target.isAlive()) {
            target.hurt(player.level.damageSources().indirectMagic(player, null), 60);
            return true;
        }
        return false;
    }

    public static boolean useBonemeal(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (!player.mayUseItemAt(pos, face, player.getItemInHand(hand)))
            return false;

        return BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), level, pos, player);
    }

    public static boolean upgradeOre(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (!player.mayUseItemAt(pos, face, player.getItemInHand(hand)))
            return false;

        Block block = level.getBlockState(pos).getBlock();
        List<Block> ORES = ImmutableList.copyOf(ListHandlers.ORES);
        for (int i = 0; i < ORES.size() - 1; i++) {
            if (block == ORES.get(i)) {
                BlockState newState = ORES.get(i + 1).defaultBlockState();
                SoundType sound = newState.getSoundType();
                level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                level.setBlockAndUpdate(pos, newState);
                return true;
            }
        }

        List<Block> ORES_NETHER = ImmutableList.copyOf(ListHandlers.NETHER_ORES);
        for (int i = 0; i < ORES_NETHER.size() - 1; i++) {
            if (block == ORES_NETHER.get(i)) {
                BlockState newState = ORES_NETHER.get(i + 1).defaultBlockState();
                SoundType sound = newState.getSoundType();
                level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                level.setBlockAndUpdate(pos, newState);
                return true;
            }
        }

        return false;
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(Block from, Block to) {
        return changeBlock(Collections.singleton(from), to.defaultBlockState());
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(Block from, BlockState to) {
        return changeBlock(Collections.singleton(from), to);
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(Set<Block> from, Block to) {
        return changeBlock(from, to.defaultBlockState());
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(Set<Block> from, BlockState to) {
        return changeBlock(from::contains, to);
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(TagKey<Block> from, Block to) {
        return changeBlock(from, to.defaultBlockState());
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(TagKey<Block> from, BlockState to) {
        Predicate<Block> predicate = block -> {
            //noinspection deprecation
            for (Holder<Block> holder : BuiltInRegistries.BLOCK.getTagOrEmpty(from)) {
                if (block == holder.value()) {
                    return true;
                }
            }

            return false;
        };

        return changeBlock(predicate, to);
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(Predicate<Block> from, Block to) {
        return changeBlock(from, to.defaultBlockState());
    }

    public static Function5<Level, Player, InteractionHand, BlockPos, Direction, Boolean> changeBlock(Predicate<Block> from, BlockState to) {
        return (Level world, Player player, InteractionHand hand, BlockPos pos, Direction face) -> {
            if (!player.mayUseItemAt(pos, face, player.getItemInHand(hand)))
                return false;

            if (!from.test(world.getBlockState(pos).getBlock()))
                return false;

            SoundType sound = to.getSoundType();
            world.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
            world.setBlockAndUpdate(pos, to);

            return true;
        };
    }

    public static boolean applyPotion(LivingEntity target, Player player) {
        if (target.isAlive()) {
            switch (player.getCommandSenderWorld().random.nextInt(5)) {
                case 0 -> target.hurt(player.level.damageSources().indirectMagic(player, null), 10);
                case 1 -> target.addEffect(new MobEffectInstance(MobEffects.POISON, 600));
                case 2 -> target.addEffect(new MobEffectInstance(MobEffects.WITHER, 600));
                case 3 -> target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600));
                default -> target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 3));
            }
            return true;
        }
        return false;
    }

    public static boolean generateOre(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (!player.mayUseItemAt(pos, face, player.getItemInHand(hand)))
            return false;

        if (level.getBlockState(pos).is(Tags.Blocks.COBBLESTONE)
                || level.getBlockState(pos).is(Tags.Blocks.STONE)) {

            Block block = ToolEffects.getRandomBlock(Tags.Blocks.ORES);
            BlockState state = block.defaultBlockState();
            SoundType sound = state.getSoundType();
            level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
            level.setBlockAndUpdate(pos, state);

            return true;
        }

        return false;
    }

    public static boolean ultimate(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        BlockState block = level.getBlockState(pos);
        if (!block.is(BlockTags.DIRT) && !block.is(Blocks.GRASS_BLOCK) && !(block instanceof BonemealableBlock)) {
            if (player.isShiftKeyDown()) {
                return placeWater(level, player, hand, pos, face);
            }
        } else if (!player.isShiftKeyDown()) {
            if (block.is(BlockTags.DIRT)) {
                BlockState newState = Blocks.GRASS_BLOCK.defaultBlockState();
                SoundType sound = newState.getSoundType();
                level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
                level.setBlockAndUpdate(pos, newState);
                return true;
            } else if (block.is(Tags.Blocks.COBBLESTONE) || block.is(Tags.Blocks.STONE)) {
                return generateOre(level, player, hand, pos, face);
            }
        } else {
            if (block.is(BlockTags.DIRT) || block.is(Blocks.GRASS_BLOCK) || level.getBlockState(pos.relative(face)).getBlock() == Blocks.WATER) {
                return spawnAnimal(level, player, hand, pos, face);
            } else {
                return useBonemeal(level, player, hand, pos, face);
            }
        }
        return false;
    }

    public static boolean applyRegeneration(Level level, Player player, InteractionHand hand) {
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
        return true;
    }

    public static boolean applyLevitation(Level level, Player player, InteractionHand hand) {
        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 9));
        return true;
    }

    public static boolean removeFluid(Level level, Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (player.mayUseItemAt(pos, face, player.getItemInHand(hand))) {
            BlockPos target = pos.relative(face);
            BlockState state = level.getBlockState(target);
            if (state.getBlock() instanceof BucketPickup) {
                ItemStack stack = ((BucketPickup) state.getBlock()).pickupBlock(level, target, state);
                if (!stack.isEmpty() && stack.getItem() != Items.BUCKET) {
                    player.playSound(stack.getItem() == Items.LAVA_BUCKET ? SoundEvents.BUCKET_FILL_LAVA : SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                    for (int x = 0; x < 5; ++x) {
                        level.addParticle(ParticleTypes.POOF, pos.getX() + level.random.nextDouble(), pos.getY() + level.random.nextDouble(), pos.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private static Block getRandomBlock(TagKey<Block> key) {
        List<Block> blocks = Lists.newArrayList();
        //noinspection deprecation
        for (Holder<Block> holder : BuiltInRegistries.BLOCK.getTagOrEmpty(key)) {
            blocks.add(holder.value());
        }

        return blocks.get(RANDOM.nextInt(blocks.size()));
    }
}
