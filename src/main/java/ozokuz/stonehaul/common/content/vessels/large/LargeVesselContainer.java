package ozokuz.stonehaul.common.content.vessels.large;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.common.content.vessels.VesselItemFilter;

import static ozokuz.stonehaul.common.content.vessels.large.LargeVesselBlock.SIZE;
import static ozokuz.stonehaul.common.lib.ContainerMenuHelper.SLOT_WIDTH;

public class LargeVesselContainer extends AbstractContainerMenu {
    private final Container inventory;

    public LargeVesselContainer(MenuType<LargeVesselContainer> menuType, int containerId, Inventory playerInventory, Container inventory) {
        super(menuType, containerId);

        checkContainerSize(inventory, SIZE);

        this.inventory = inventory;

        inventory.startOpen(playerInventory.player);

        // Vessel Slots
        final int columns = 5;
        final int rows = SIZE / columns;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int slot = col + row * columns;

                var x = 44 + col * SLOT_WIDTH;
                var y = 17 + row * SLOT_WIDTH;
                addSlot(new Slot(inventory, slot, x, y) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return VesselItemFilter.isValid(stack);
                    }
                });
            }
        }

        // Player Inventory Slots
        var cols = 9;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < cols; col++) {
                var index = col + row * cols + cols;
                var x = 8 + col * SLOT_WIDTH;
                var y = 84 + row * SLOT_WIDTH;
                addSlot(new Slot(playerInventory, index, x, y));
            }
        }

        // Player Hotbar Slots
        for (int i = 0; i < 9; i++) {
            var x = 8 + i * SLOT_WIDTH;
            var y = 142;
            addSlot(new Slot(playerInventory, i, x, y));
        }

    }

    public static LargeVesselContainer fromNetwork(MenuType<LargeVesselContainer> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new LargeVesselContainer(menuType, containerId, playerInventory, new SimpleContainer(SIZE));
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return inventory.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        var slot = getSlot(slotIndex);
        var stack = slot.getItem();
        var copy = stack.copy();

        var isVesselSlot = slotIndex < inventory.getContainerSize();
        if (isVesselSlot) {
            if (!moveItemStackTo(stack, inventory.getContainerSize(), slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(stack, 0, inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);

        return copy;
    }
}
