package ozokuz.stonehaul.common.content.vessels.large;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.content.vessels.VesselItemFilter;
import ozokuz.stonehaul.common.lib.BlockInventory;

@MethodsReturnNonnullByDefault
public class LargeVesselBlockEntity extends BlockEntity implements MenuProvider, BlockInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(LargeVesselBlock.SIZE, ItemStack.EMPTY);

    public LargeVesselBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new LargeVesselContainer(Content.LARGE_VESSEL_CONTAINER.get(), containerId, playerInventory, this);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, inventory);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, inventory);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return VesselItemFilter.isValid(stack);
    }
}
