package de.melanx.ultimatools.compat;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.item.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

@JeiPlugin
@SuppressWarnings("ConstantConditions")
public class JeiCompat implements IModPlugin {

    public static final ResourceLocation PLUGIN_UID = new ResourceLocation(SkyblockUltimaTools.MODID, "plugin/main");

    @Override
    public @Nonnull
    ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Nonnull
    private static Component getDescKey(ResourceLocation name) {
        return new TranslatableComponent("jei." + name.getNamespace() + "." + name.getPath() + ".desc");
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        for (RegistryObject<Item> registryObject : Registration.ITEMS.getEntries()) {
            Item item = registryObject.get();
            registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, getDescKey(item.getRegistryName()));
        }
    }
}
