package de.melanx.ultimatools.data;

import de.melanx.ultimatools.lib.LibMisc;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = LibMisc.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {
        DataGenerator gen = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();

        if (e.includeClient()) {
            gen.addProvider(new ItemModels(gen, helper));
        }
    }
}
