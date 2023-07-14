package ozokuz.stonehaul.common.lib;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class ItemNBTHelper {
    private ItemNBTHelper() {}

    public static boolean exists(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(tag);
    }

    public static ListTag getList(ItemStack stack, String tag, int type) {
        return exists(stack, tag) ? stack.getOrCreateTag().getList(tag, type) : new ListTag();
    }
}
