package ozokuz.stonehaul.common.content.vessels.small;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonehaul.common.content.vessels.VesselItemFilter;
import ozokuz.stonehaul.common.lib.SlotLocked;

import static ozokuz.stonehaul.common.lib.ContainerMenuHelper.SLOT_WIDTH;

public class SmallVesselContainer extends AbstractContainerMenu {
    private final ItemStack vessel;
    public final Container inventory;

    protected SmallVesselContainer(@Nullable MenuType<SmallVesselContainer> menuType, int containerId, Inventory playerInventory, ItemStack vessel) {
        super(menuType, containerId);

        this.vessel = vessel;

        if (playerInventory.player.level.isClientSide()) {
            inventory = new SimpleContainer(SmallVesselItem.SIZE);
        } else {
            inventory = SmallVesselItem.getInventory(vessel);
        }

        // Vessel Slots
        final int columns = 3;
        final int rows = SmallVesselItem.SIZE / columns;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int slot = col + row * columns;

                var x = 62 + col * SLOT_WIDTH;
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
            if (playerInventory.getItem(i) == vessel) {
                addSlot(new SlotLocked(playerInventory, i, x, y));
            } else {
                addSlot(new Slot(playerInventory, i, x, y));
            }
        }
    }

    public static SmallVesselContainer fromNetwork(MenuType<SmallVesselContainer> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        InteractionHand hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        return new SmallVesselContainer(menuType, containerId, playerInventory, playerInventory.player.getItemInHand(hand));
    }

    @Override
    public boolean stillValid(Player player) {
        var main = player.getMainHandItem();
        var off = player.getOffhandItem();

        return !main.isEmpty() && main == vessel || !off.isEmpty() && off == vessel;
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
            if (!VesselItemFilter.isValid(stack)) {
                return ItemStack.EMPTY;
            }

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
