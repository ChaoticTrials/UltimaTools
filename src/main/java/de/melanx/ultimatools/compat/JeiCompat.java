package de.melanx.ultimatools.compat;

import de.melanx.ultimatools.SkyblockUltimaTools;
import de.melanx.ultimatools.item.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;

@JeiPlugin
@SuppressWarnings("ConstantConditions")
public class JeiCompat implements IModPlugin {

    public static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath(SkyblockUltimaTools.MODID, "plugin/main");

    @Override
    public @Nonnull
    ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Nonnull
    private static Component getDescKey(ResourceLocation name) {
        return Component.translatable("jei." + name.getNamespace() + "." + name.getPath() + ".desc");
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        for (DeferredHolder<Item, ?> registryObject : Registration.ITEMS.getEntries()) {
            Item item = registryObject.get();
            registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, getDescKey(BuiltInRegistries.ITEM.getKey(item)));
        }
    }
}
