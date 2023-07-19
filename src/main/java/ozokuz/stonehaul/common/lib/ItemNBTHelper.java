package ozokuz.stonehaul.common.lib;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;

public class ItemNBTHelper {
    private ItemNBTHelper() {}

    public static boolean exists(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(tag);
    }

    public static ListTag getList(ItemStack stack, String tag, int type) {
        return exists(stack, tag) ? stack.getOrCreateTag().getList(tag, type) : new ListTag();
    }

    public static JsonObject serializeStack(ItemStack stack) {
        var nbt = stack.save(new CompoundTag());

        var count = nbt.getByte("Count");
        if (count != 1) {
            nbt.putByte("count", count);
        }

        nbt.remove("Count");

        renameTag(nbt, "id", "item");
        renameTag(nbt, "tag", "nbt");

        return NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt).getAsJsonObject();
    }

    private static void renameTag(CompoundTag tag, String oldName, String newName) {
        var part = tag.get(oldName);
        if (part != null) {
            tag.remove(oldName);
            tag.put(newName, part);
        }
    }
}
