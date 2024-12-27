package de.melanx.ultimatools.item;

import de.melanx.ultimatools.ServerConfig;
import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.util.ToolEffects;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class Registration {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SkyblockUltimaTools.MODID);

    public static final DeferredHolder<Item, UltimaTool> beginner = ITEMS.registerItem("beginner", properties -> new UltimaTool(ServerConfig.BEGINNER.get(), ToolEffects::placeWater, properties));
    public static final DeferredHolder<Item, UltimaTool> bloodMagician = ITEMS.registerItem("blood_magician", properties -> new UltimaTool(ServerConfig.BLOOD_MAGICIAN.get(), ToolEffects::spawnAnimal, properties));
    public static final DeferredHolder<Item, UltimaTool> cursedKnight = ITEMS.registerItem("cursed_knight", properties -> new UltimaTool(ServerConfig.CURSED_KNIGHT.get(), ToolEffects::applyMagicDamage, properties));
    public static final DeferredHolder<Item, UltimaTool> farmer = ITEMS.registerItem("farmer", properties -> new UltimaTool(ServerConfig.FARMER.get(), ToolEffects::useBonemeal, properties));
    public static final DeferredHolder<Item, Item> forestRunner = ITEMS.registerSimpleItem("forest_runner", new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, Item> knight = ITEMS.registerSimpleItem("knight", new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, Item> lighter = ITEMS.registerSimpleItem("lighter", new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, UltimaTool> oreBetter = ITEMS.registerItem("ore_better", properties -> new UltimaTool(ServerConfig.ORE_BETTER.get(), ToolEffects::upgradeOre, properties));
    public static final DeferredHolder<Item, UltimaTool> scholar = ITEMS.registerItem("scholar", properties -> new UltimaTool(ServerConfig.SCHOLAR.get(), ToolEffects.changeBlock(BlockTags.DIRT, Blocks.GRASS_BLOCK), properties));
    public static final DeferredHolder<Item, UltimaTool> soothsayer = ITEMS.registerItem("soothsayer", properties -> new UltimaTool(ServerConfig.SOOTHSAYER.get(), ToolEffects::applyPotion, properties));
    public static final DeferredHolder<Item, UltimaTool> ultimaFighter = ITEMS.registerItem("ultima_fighter", properties -> new UltimaTool(ServerConfig.ULTIMA_FIGHTER.get(), ToolEffects::generateOre, properties));
    public static final DeferredHolder<Item, UltimaTool> ultimaGod = ITEMS.registerItem("ultima_god", properties -> new UltimaTool(ServerConfig.ULTIMA_GOD.get(), ToolEffects::ultimate, properties));

    public static final DeferredHolder<Item, UltimaTool> kryptoBeginner = ITEMS.registerItem("krypto_beginner", properties -> new UltimaTool(ServerConfig.KRYPTO_BEGINNER.get(), ToolEffects::removeFluid, properties));
    public static final DeferredHolder<Item, UltimaTool> kryptoBloodMagician = ITEMS.registerItem("krypto_blood_magician", properties -> new UltimaTool(ServerConfig.KRYPTO_BLOOD_MAGICIAN.get(), ToolEffects::applyRegeneration, properties));
    public static final DeferredHolder<Item, UltimaTool> kryptoCursedKnight = ITEMS.registerItem("krypto_cursed_knight", properties -> new UltimaTool(ServerConfig.KRYPTO_CURSED_KNIGHT.get(), ToolEffects::applyLevitation, properties));
    public static final DeferredHolder<Item, UltimaTool> kryptoFarmer = ITEMS.registerItem("krypto_farmer", properties -> new UltimaTool(ToolEffects.changeBlock(Set.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK), Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, 7)), properties));
    public static final DeferredHolder<Item, UltimaTool> kryptoScholar = ITEMS.registerItem("krypto_scholar", properties -> new UltimaTool(ToolEffects.changeBlock(Set.of(Blocks.DIRT, Blocks.COARSE_DIRT), Blocks.STONE), properties));
    public static final DeferredHolder<Item, UltimaTool> kryptoSoothsayer = ITEMS.registerItem("krypto_soothsayer", properties -> new UltimaTool(ToolEffects.changeBlock(Set.of(Blocks.STONE, Blocks.COBBLESTONE), Blocks.COAL_ORE), properties));
    public static final DeferredHolder<Item, Item> kryptoForestRunner = ITEMS.registerSimpleItem("krypto_forest_runner", new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, Item> kryptoKnight = ITEMS.registerSimpleItem("krypto_knight", new Item.Properties().stacksTo(1));
    public static final DeferredHolder<Item, Item> kryptoLighter = ITEMS.registerSimpleItem("krypto_lighter", new Item.Properties().stacksTo(1));

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
