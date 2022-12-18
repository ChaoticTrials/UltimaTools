package de.melanx.ultimatools.data;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.item.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen.getPackOutput(), SkyblockUltimaTools.MODID, helper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : Registration.ITEMS.getEntries())
            this.generateItem(item.get());
    }

    private void generateItem(Item item) {
        @SuppressWarnings("ConstantConditions")
        String path = ForgeRegistries.ITEMS.getKey(item).getPath();
        this.getBuilder(path).parent(this.getExistingFile(this.mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Skyblock Ultima Tools Item Models";
    }
}
