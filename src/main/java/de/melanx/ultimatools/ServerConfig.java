package de.melanx.ultimatools;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ServerConfig {
    public static final ModConfigSpec SERVER_CONFIG;
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

    static {
        init(SERVER_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static ModConfigSpec.ConfigValue<List<? extends String>> OVERWORLD_ORES;
    public static ModConfigSpec.ConfigValue<List<? extends String>> NETHER_ORES;

    public static ModConfigSpec.ConfigValue<List<? extends String>> ANIMALS;
    public static ModConfigSpec.ConfigValue<List<? extends String>> WATER_ANIMALS;

    public static ModConfigSpec.IntValue BEGINNER;
    public static ModConfigSpec.IntValue BLOOD_MAGICIAN;
    public static ModConfigSpec.IntValue CURSED_KNIGHT;
    public static ModConfigSpec.IntValue FARMER;
    public static ModConfigSpec.IntValue ORE_BETTER;
    public static ModConfigSpec.IntValue SCHOLAR;
    public static ModConfigSpec.IntValue SOOTHSAYER;
    public static ModConfigSpec.IntValue ULTIMA_FIGHTER;
    public static ModConfigSpec.IntValue ULTIMA_GOD;

    public static ModConfigSpec.IntValue KRYPTO_BEGINNER;
    public static ModConfigSpec.IntValue KRYPTO_BLOOD_MAGICIAN;
    public static ModConfigSpec.IntValue KRYPTO_CURSED_KNIGHT;
    public static ModConfigSpec.IntValue KRYPTO_FARMER;
    public static ModConfigSpec.IntValue KRYPTO_SCHOLAR;
    public static ModConfigSpec.IntValue KRYPTO_SOOTHSAYER;

    public static void init(ModConfigSpec.Builder builder) {
        builder.push("ores");
        OVERWORLD_ORES = builder.comment("All the ores for upgrading normal ores, order matters")
                .defineList("overworld", Arrays.asList(
                        "minecraft:iron_ore",
                        "minecraft:redstone_ore",
                        "minecraft:lapis_ore",
                        "minecraft:gold_ore",
                        "minecraft:diamond_ore",
                        "minecraft:emerald_ore"
                ), (obj) -> obj instanceof String);
        NETHER_ORES = builder.comment("All the ores for upgrading nether ores, order matters")
                .defineList("nether", Arrays.asList(
                        "minecraft:nether_quartz_ore",
                        "minecraft:nether_gold_ore"
                ), (obj) -> obj instanceof String);
        builder.pop();

        builder.push("animals");
        ANIMALS = builder.comment("All animals which can spawn randomly")
                .defineList("animals", Arrays.asList(
                        "minecraft:bat",
                        "minecraft:bee",
                        "minecraft:cat",
                        "minecraft:chicken",
                        "minecraft:cow",
                        "minecraft:donkey",
                        "minecraft:fox",
                        "minecraft:horse",
                        "minecraft:llama",
                        "minecraft:mule",
                        "minecraft:mooshroom",
                        "minecraft:ocelot",
                        "minecraft:panda",
                        "minecraft:parrot",
                        "minecraft:pig",
                        "minecraft:polar_bear",
                        "minecraft:rabbit",
                        "minecraft:sheep",
                        "minecraft:turtle",
                        "minecraft:wolf"
                ), (obj) -> obj instanceof String);
        WATER_ANIMALS = builder.comment("All animals which can spawn in water randomly")
                .defineList("water_animals", Arrays.asList(
                        "minecraft:cod",
                        "minecraft:dolphin",
                        "minecraft:pufferfish",
                        "minecraft:salmon",
                        "minecraft:squid",
                        "minecraft:tropical_fish"
                ), (obj) -> obj instanceof String);
        builder.pop();

        builder.comment("Cooldowns for the crystals").push("cooldowns");
        builder.push("normal");
        BEGINNER = builder.defineInRange("beginner", 200, 0, Integer.MAX_VALUE);
        BLOOD_MAGICIAN = builder.defineInRange("blood_magician", 1200, 0, Integer.MAX_VALUE);
        CURSED_KNIGHT = builder.defineInRange("cursed_knight", 1200, 0, Integer.MAX_VALUE);
        FARMER = builder.defineInRange("farmer", 1200, 0, Integer.MAX_VALUE);
        ORE_BETTER = builder.defineInRange("ore_better", 200, 0, Integer.MAX_VALUE);
        SCHOLAR = builder.defineInRange("scholar", 200, 0, Integer.MAX_VALUE);
        SOOTHSAYER = builder.defineInRange("soothsayer", 200, 0, Integer.MAX_VALUE);
        ULTIMA_FIGHTER = builder.defineInRange("ultima_fighter", 200, 0, Integer.MAX_VALUE);
        ULTIMA_GOD = builder.defineInRange("ultima_god", 200, 0, Integer.MAX_VALUE);
        builder.pop();

        builder.push("krypto");
        KRYPTO_BEGINNER = builder.defineInRange("krypto_beginner", 200, 0, Integer.MAX_VALUE);
        KRYPTO_BLOOD_MAGICIAN = builder.defineInRange("krypto_blood_magician", 200, 0, Integer.MAX_VALUE);
        KRYPTO_CURSED_KNIGHT = builder.defineInRange("krypto_cursed_knight", 200, 0, Integer.MAX_VALUE);
        KRYPTO_FARMER = builder.defineInRange("krypto_farmer", 200, 0, Integer.MAX_VALUE);
        KRYPTO_SCHOLAR = builder.defineInRange("krypto_scholar", 200, 0, Integer.MAX_VALUE);
        KRYPTO_SOOTHSAYER = builder.defineInRange("krypto_soothsayer", 200, 0, Integer.MAX_VALUE);
        builder.pop(2);
    }
}
