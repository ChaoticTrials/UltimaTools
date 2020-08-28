package de.melanx.ultimatools.item;

import com.google.common.collect.ImmutableSet;
import de.melanx.ultimatools.lib.LibMisc;
import de.melanx.ultimatools.util.ToolEffects;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LibMisc.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LibMisc.MODID);

    public static final RegistryObject<Item> beginner = ITEMS.register("beginner", () -> new UltimaTool(ToolEffects::placeWater));
    public static final RegistryObject<Item> bloodMagician = ITEMS.register("blood_magician", () -> new UltimaTool(600, ToolEffects::spawnAnimal));
    public static final RegistryObject<Item> cursedKnight = ITEMS.register("cursed_knight", () -> new UltimaTool(600, ToolEffects::applyMagicDamage));
    public static final RegistryObject<Item> farmer = ITEMS.register("farmer", () -> new UltimaTool(600, ToolEffects::useBonemeal));
    public static final RegistryObject<Item> forestRunner = ITEMS.register("forest_runner", UltimaCrafting::new);
    public static final RegistryObject<Item> knight = ITEMS.register("knight", UltimaCrafting::new);
    public static final RegistryObject<Item> lighter = ITEMS.register("lighter", UltimaCrafting::new);
    public static final RegistryObject<Item> oreBetter = ITEMS.register("ore_better", () -> new UltimaTool(ToolEffects::upgradeOre));
    public static final RegistryObject<Item> scholar = ITEMS.register("scholar", () -> new UltimaTool(ToolEffects.changeBlock(ImmutableSet.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.FARMLAND), Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Item> soothsayer = ITEMS.register("soothsayer", () -> new UltimaTool(ToolEffects::applyPotion));
    public static final RegistryObject<Item> ultimaFighter = ITEMS.register("ultima_fighter", () -> new UltimaTool(ToolEffects::generateOre));
    public static final RegistryObject<Item> ultimaGod = ITEMS.register("ultima_god", () -> new UltimaTool(ToolEffects::ultimate));

    public static final RegistryObject<Item> kryptoBeginner = ITEMS.register("krypto_beginner", () -> new UltimaTool(ToolEffects::removeFluid));
    public static final RegistryObject<Item> kryptoBloodMagician = ITEMS.register("krypto_blood_magician", () -> new UltimaTool(ToolEffects::applyRegeneration));
    public static final RegistryObject<Item> kryptoCursedKnight = ITEMS.register("krypto_cursed_knight", () -> new UltimaTool(ToolEffects::applyLevitation));
    public static final RegistryObject<Item> kryptoFarmer = ITEMS.register("krypto_farmer", () -> new UltimaTool(ToolEffects.changeBlock(ImmutableSet.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK), Blocks.FARMLAND.getDefaultState().with(BlockStateProperties.MOISTURE_0_7, 7))));
    public static final RegistryObject<Item> kryptoScholar = ITEMS.register("krypto_scholar", () -> new UltimaTool(ToolEffects.changeBlock(ImmutableSet.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK), Blocks.STONE)));
    public static final RegistryObject<Item> kryptoSoothsayer = ITEMS.register("krypto_soothsayer", () -> new UltimaTool(ToolEffects.changeBlock(ImmutableSet.of(Blocks.STONE, Blocks.COBBLESTONE), Blocks.COAL_ORE)));
    public static final RegistryObject<Item> kryptoForestRunner = ITEMS.register("krypto_forest_runner", UltimaCrafting::new);
    public static final RegistryObject<Item> kryptoKnight = ITEMS.register("krypto_knight", UltimaCrafting::new);
    public static final RegistryObject<Item> kryptoLighter = ITEMS.register("krypto_lighter", UltimaCrafting::new);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        BLOCKS.register(bus);
    }
}
