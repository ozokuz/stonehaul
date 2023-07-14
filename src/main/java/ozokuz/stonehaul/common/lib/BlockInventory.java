package ozokuz.stonehaul.common.lib;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public interface BlockInventory extends Container {
    NonNullList<ItemStack> getItems();

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        return getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    default ItemStack getItem(int slotIndex) {
        return getItems().get(slotIndex);
    }

    @Override
    default ItemStack removeItem(int slotIndex, int count) {
        var res = ContainerHelper.removeItem(getItems(), slotIndex, count);
        if (!res.isEmpty()) {
            setChanged();
        }
        return res;
    }

    @Override
    default ItemStack removeItemNoUpdate(int slotIndex) {
        return ContainerHelper.takeItem(getItems(), slotIndex);
    }

    @Override
    default void setItem(int slotIndex, @NotNull ItemStack stack) {
        getItems().set(slotIndex, stack);
        if (stack.getCount() > stack.getMaxStackSize()) {
            stack.setCount(stack.getMaxStackSize());
        }
    }

    @Override
    default void clearContent() {
        getItems().clear();
    }

    @Override
    default boolean stillValid(@NotNull Player player) {
        return true;
    }
}
