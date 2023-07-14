package ozokuz.stonehaul.common.lib;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemBasedInventory extends SimpleContainer {
    private static final String TAG_NAME = "items";
    private final ItemStack stack;

    public ItemBasedInventory(ItemStack stack, int size) {
        super(size);
        this.stack = stack;

        ListTag list = ItemNBTHelper.getList(stack, TAG_NAME, Tag.TAG_COMPOUND);

        for (int i = 0; i < size && i < list.size(); i++) {
            setItem(i, ItemStack.of(list.getCompound(i)));
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();

        ListTag list = new ListTag();

        for (int i = 0; i < getContainerSize(); i++) {
            list.add(getItem(i).save(new CompoundTag()));
        }

        stack.getOrCreateTag().put(TAG_NAME, list);
    }

    @Override
    public boolean stillValid(Player player) {
        return !stack.isEmpty();
    }
}
