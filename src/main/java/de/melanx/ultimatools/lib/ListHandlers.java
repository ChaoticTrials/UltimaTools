package de.melanx.ultimatools.lib;

import de.melanx.ultimatools.ServerConfig;
import de.melanx.ultimatools.SkyblockUltimaTools;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class ListHandlers {

    public static List<Block> ORES = new ArrayList<>();
    public static List<Block> NETHER_ORES = new ArrayList<>();
    public static List<EntityType<?>> ANIMALS = new ArrayList<>();
    public static List<EntityType<?>> WATER_ANIMALS = new ArrayList<>();

    public static void fillOres() {
        ORES.clear();
        ServerConfig.OVERWORLD_ORES.get().forEach(string -> {
            Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(string));
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
            Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(string));
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
            EntityType<?> entity = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryCreate(string));
            if (entity != null) {
                if (entity.getClassification() == EntityClassification.CREATURE || entity.getClassification() == EntityClassification.AMBIENT) {
                    if (entity.getRegistryName().toString().equals(string)) {
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
            EntityType<?> entity = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryCreate(string));
            if (entity != null) {
                if (entity.getClassification() == EntityClassification.WATER_CREATURE || entity.getClassification() == EntityClassification.WATER_AMBIENT) {
                    if (entity.getRegistryName().toString().equals(string)) {
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
