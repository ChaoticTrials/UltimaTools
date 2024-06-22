package de.melanx.ultimatools.item;

import de.melanx.ultimatools.ServerConfig;
import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.util.ToolEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, SkyblockUltimaTools.MODID);

    public static final DeferredHolder<Item, UltimaTool> beginner = ITEMS.register("beginner", () -> new UltimaTool(ServerConfig.BEGINNER.get(), ToolEffects::placeWater));
    public static final DeferredHolder<Item, UltimaTool> bloodMagician = ITEMS.register("blood_magician", () -> new UltimaTool(ServerConfig.BLOOD_MAGICIAN.get(), ToolEffects::spawnAnimal));
    public static final DeferredHolder<Item, UltimaTool> cursedKnight = ITEMS.register("cursed_knight", () -> new UltimaTool(ServerConfig.CURSED_KNIGHT.get(), ToolEffects::applyMagicDamage));
    public static final DeferredHolder<Item, UltimaTool> farmer = ITEMS.register("farmer", () -> new UltimaTool(ServerConfig.FARMER.get(), ToolEffects::useBonemeal));
    public static final DeferredHolder<Item, UltimaCrafting> forestRunner = ITEMS.register("forest_runner", UltimaCrafting::new);
    public static final DeferredHolder<Item, UltimaCrafting> knight = ITEMS.register("knight", UltimaCrafting::new);
    public static final DeferredHolder<Item, UltimaCrafting> lighter = ITEMS.register("lighter", UltimaCrafting::new);
    public static final DeferredHolder<Item, UltimaTool> oreBetter = ITEMS.register("ore_better", () -> new UltimaTool(ServerConfig.ORE_BETTER.get(), ToolEffects::upgradeOre));
    public static final DeferredHolder<Item, UltimaTool> scholar = ITEMS.register("scholar", () -> new UltimaTool(ServerConfig.SCHOLAR.get(), ToolEffects.changeBlock(BlockTags.DIRT, Blocks.GRASS_BLOCK)));
    public static final DeferredHolder<Item, UltimaTool> soothsayer = ITEMS.register("soothsayer", () -> new UltimaTool(ServerConfig.SOOTHSAYER.get(), ToolEffects::applyPotion));
    public static final DeferredHolder<Item, UltimaTool> ultimaFighter = ITEMS.register("ultima_fighter", () -> new UltimaTool(ServerConfig.ULTIMA_FIGHTER.get(), ToolEffects::generateOre));
    public static final DeferredHolder<Item, UltimaTool> ultimaGod = ITEMS.register("ultima_god", () -> new UltimaTool(ServerConfig.ULTIMA_GOD.get(), ToolEffects::ultimate));

    public static final DeferredHolder<Item, UltimaTool> kryptoBeginner = ITEMS.register("krypto_beginner", () -> new UltimaTool(ServerConfig.KRYPTO_BEGINNER.get(), ToolEffects::removeFluid));
    public static final DeferredHolder<Item, UltimaTool> kryptoBloodMagician = ITEMS.register("krypto_blood_magician", () -> new UltimaTool(ServerConfig.KRYPTO_BLOOD_MAGICIAN.get(), ToolEffects::applyRegeneration));
    public static final DeferredHolder<Item, UltimaTool> kryptoCursedKnight = ITEMS.register("krypto_cursed_knight", () -> new UltimaTool(ServerConfig.KRYPTO_CURSED_KNIGHT.get(), ToolEffects::applyLevitation));
    public static final DeferredHolder<Item, UltimaTool> kryptoFarmer = ITEMS.register("krypto_farmer", () -> new UltimaTool(ToolEffects.changeBlock(Set.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK), Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, 7))));
    public static final DeferredHolder<Item, UltimaTool> kryptoScholar = ITEMS.register("krypto_scholar", () -> new UltimaTool(ToolEffects.changeBlock(Set.of(Blocks.DIRT, Blocks.COARSE_DIRT), Blocks.STONE)));
    public static final DeferredHolder<Item, UltimaTool> kryptoSoothsayer = ITEMS.register("krypto_soothsayer", () -> new UltimaTool(ToolEffects.changeBlock(Set.of(Blocks.STONE, Blocks.COBBLESTONE), Blocks.COAL_ORE)));
    public static final DeferredHolder<Item, UltimaCrafting> kryptoForestRunner = ITEMS.register("krypto_forest_runner", UltimaCrafting::new);
    public static final DeferredHolder<Item, UltimaCrafting> kryptoKnight = ITEMS.register("krypto_knight", UltimaCrafting::new);
    public static final DeferredHolder<Item, UltimaCrafting> kryptoLighter = ITEMS.register("krypto_lighter", UltimaCrafting::new);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
