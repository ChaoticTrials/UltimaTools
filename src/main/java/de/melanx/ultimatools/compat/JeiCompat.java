package de.melanx.ultimatools.compat;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.item.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
@JeiPlugin
public class JeiCompat implements IModPlugin {
    public static final ResourceLocation PLUGIN_UID = new ResourceLocation(SkyblockUltimaTools.MODID, "plugin/main");

    @Override
    public @Nonnull
    ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Nonnull
    private static String getDescKey(ResourceLocation name) {
        return "jei." + name.getNamespace() + "." + name.getPath() + ".desc";
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        for (RegistryObject<Item> registryObject : Registration.ITEMS.getEntries()) {
            Item item = registryObject.get();
            registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, getDescKey(item.getRegistryName()));
        }
    }
}
