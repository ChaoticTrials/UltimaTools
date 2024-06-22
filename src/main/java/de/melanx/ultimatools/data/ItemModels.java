package de.melanx.ultimatools.data;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.item.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen.getPackOutput(), SkyblockUltimaTools.MODID, helper);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ?> item : Registration.ITEMS.getEntries())
            this.generateItem(item.get());
    }

    private void generateItem(Item item) {
        @SuppressWarnings("ConstantConditions")
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        this.getBuilder(path).parent(this.getExistingFile(this.mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Skyblock Ultima Tools Item Models";
    }
}
