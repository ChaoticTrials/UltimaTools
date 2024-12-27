package de.melanx.ultimatools.data;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.item.Registration;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;

public class ItemModels extends ModelProvider {

    public ItemModels(DataGenerator gen) {
        super(gen.getPackOutput(), SkyblockUltimaTools.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (DeferredHolder<Item, ?> item : Registration.ITEMS.getEntries()) {
            itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Skyblock Ultima Tools Item Models";
    }
}
