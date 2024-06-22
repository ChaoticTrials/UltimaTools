package de.melanx.ultimatools;

import de.melanx.ultimatools.item.Registration;
import de.melanx.ultimatools.lib.ListHandlers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SkyblockUltimaTools.MODID)
public class SkyblockUltimaTools {

    public SkyblockUltimaTools(IEventBus bus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.STARTUP, ServerConfig.SERVER_CONFIG);
        Registration.init(bus);
        bus.addListener(this::onServerStarted);
        bus.addListener(this::onConfigChange);
        bus.addListener(this::creativeModTab);
    }

    public static final String MODID = "ultimatools";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private void creativeModTab(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(ResourceLocation.fromNamespaceAndPath(MODID, "tab"), CreativeModeTab.builder()
                    .title(Component.literal("Skyblock Ultima Tools"))
                    .icon(() -> new ItemStack(Registration.ultimaGod.get()))
                    .displayItems((params, output) -> {
                        for (DeferredHolder<Item, ?> entry : Registration.ITEMS.getEntries()) {
                            output.accept(entry.get());
                        }
                    })
                    .build());
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
