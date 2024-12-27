package de.melanx.ultimatools.data;

import de.melanx.ultimatools.SkyblockUltimaTools;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = SkyblockUltimaTools.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        gen.addProvider(true, new ItemModels(gen));
    }
}
