package de.melanx.ultimatools;

import de.melanx.ultimatools.item.Registration;
import de.melanx.ultimatools.lib.ListHandlers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SkyblockUltimaTools.MODID)
public class SkyblockUltimaTools {

    public SkyblockUltimaTools() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);
        ServerConfig.loadConfig(ServerConfig.SERVER_CONFIG, FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve(MODID + "-server.toml"));
        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerStarted);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigChange);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::creativeModTab);
    }

    public static final String MODID = "ultimatools";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private void creativeModTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MODID, "tab"), builder -> {
            builder.title(Component.literal("Skyblock Ultima Tools"))
                    .icon(() -> new ItemStack(Registration.ultimaGod.get()))
                    .displayItems((params, output) -> {
                        for (RegistryObject<Item> entry : Registration.ITEMS.getEntries()) {
                            output.accept(entry.get());
                        }
                    });
        });
    }

    private void onServerStarted(FMLCommonSetupEvent event) {
        ListHandlers.reloadLists();
    }

    private void onConfigChange(ModConfigEvent event) {
        if (event.getConfig().getModId().equals(MODID)) {
            ListHandlers.reloadLists();
        }
    }
}
