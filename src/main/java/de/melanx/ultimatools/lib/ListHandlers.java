package de.melanx.ultimatools.lib;

import de.melanx.ultimatools.ServerConfig;
import de.melanx.ultimatools.SkyblockUltimaTools;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class ListHandlers {

    public static final List<Block> ORES = new ArrayList<>();
    public static final List<Block> NETHER_ORES = new ArrayList<>();
    public static final List<EntityType<?>> ANIMALS = new ArrayList<>();
    public static final List<EntityType<?>> WATER_ANIMALS = new ArrayList<>();

    public static void fillOres() {
        ORES.clear();
        ServerConfig.OVERWORLD_ORES.get().forEach(string -> {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(string));
            if (block != Blocks.AIR) {
                ORES.add(block);
            } else {
                SkyblockUltimaTools.LOGGER.error("Block '{}' not found", string);
            }
        });
    }

    public static void fillNetherOres() {
        NETHER_ORES.clear();
        ServerConfig.NETHER_ORES.get().forEach(string -> {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(string));
            if (block != Blocks.AIR) {
                NETHER_ORES.add(block);
            } else {
                SkyblockUltimaTools.LOGGER.error("Block '{}' not found", string);
            }
        });
    }

    public static void fillAnimals() {
        ANIMALS.clear();
        ServerConfig.ANIMALS.get().forEach(string -> {
            EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(string));
            if (entity != null) {
                if (entity.getCategory() == MobCategory.CREATURE || entity.getCategory() == MobCategory.AMBIENT) {
                    if (BuiltInRegistries.ENTITY_TYPE.getKey(entity).toString().equals(string)) {
                        ANIMALS.add(entity);
                    } else {
                        SkyblockUltimaTools.LOGGER.error("Entity '{}' not found", string);
                    }
                } else {
                    SkyblockUltimaTools.LOGGER.error("'{}' is no animal", string);
                }
            }
        });
    }

    public static void fillWaterAnimals() {
        WATER_ANIMALS.clear();
        ServerConfig.WATER_ANIMALS.get().forEach(string -> {
            EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(string));
            if (entity != null) {
                if (entity.getCategory() == MobCategory.WATER_CREATURE || entity.getCategory() == MobCategory.WATER_AMBIENT) {
                    if (BuiltInRegistries.ENTITY_TYPE.getKey(entity).toString().equals(string)) {
                        WATER_ANIMALS.add(entity);
                    } else {
                        SkyblockUltimaTools.LOGGER.error("Entity '{}' not found", string);
                    }
                } else {
                    SkyblockUltimaTools.LOGGER.error("'{}' is no water animal", string);
                }
            }
        });
    }

    public static void reloadLists() {
        fillOres();
        fillNetherOres();
        fillAnimals();
        fillWaterAnimals();
    }
}
