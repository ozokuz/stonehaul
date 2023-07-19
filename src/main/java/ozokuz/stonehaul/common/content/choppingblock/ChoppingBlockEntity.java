package ozokuz.stonehaul.common.content.choppingblock;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.lib.BlockInventory;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChoppingBlockEntity extends BlockEntity implements BlockInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

    public ChoppingBlockEntity(BlockEntityType<ChoppingBlockEntity> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        var inv = new SimpleContainer(stack);
        return level.getRecipeManager().getRecipeFor(Content.CHOPPING_BLOCK_RECIPE_TYPE.get(), inv, level).isPresent();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, inventory);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();

        var lst = new ListTag();
        var slot = new CompoundTag();
        slot.putByte("Slot", (byte) 0);
        inventory.get(0).save(slot);
        lst.add(slot);

        tag.put("Items", lst);

        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
