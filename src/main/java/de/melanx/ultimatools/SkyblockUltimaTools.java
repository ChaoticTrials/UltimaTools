package de.melanx.ultimatools;

import de.melanx.ultimatools.item.Registration;
import de.melanx.ultimatools.lib.LibMisc;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod(LibMisc.MODID)
public class SkyblockUltimaTools {

    public SkyblockUltimaTools() {
        Registration.init();
    }

    public static final ItemGroup TAB = new ItemGroup(LibMisc.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.ultimaGod.get());
        }
    };
}
