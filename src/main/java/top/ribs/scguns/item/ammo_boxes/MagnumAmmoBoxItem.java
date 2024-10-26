package top.ribs.scguns.item.ammo_boxes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import top.ribs.scguns.item.AmmoBoxItem;

public class MagnumAmmoBoxItem extends AmmoBoxItem {
    private static final int MAGNUM_BASE_CAPACITY = 512;  // Define the base capacity for this type

    public MagnumAmmoBoxItem(Properties properties) {
        super(properties);
    }

    @Override
    protected ResourceLocation getAmmoTag() {
        return new ResourceLocation("scguns", "magnum_ammo");
    }

    @Override
    protected int getBaseMaxItemCount() {
        return MAGNUM_BASE_CAPACITY;  // Return the base capacity
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Mth.color(0.4F, 0.4F, 0.7F);
    }
}

