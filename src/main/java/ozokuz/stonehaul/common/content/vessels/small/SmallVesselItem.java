package ozokuz.stonehaul.common.content.vessels.small;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.content.vessels.VesselItemFilter;
import ozokuz.stonehaul.common.lib.ItemBasedInventory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SmallVesselItem extends Item {
    public static final int SIZE = 9;

    public SmallVesselItem(Properties properties) {
        super(properties);
    }

    public static Container getInventory(ItemStack stack) {
        return new ItemBasedInventory(stack, SIZE) {
            @Override
            public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
                return VesselItemFilter.isValid(stack);
            }
        };
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var item = player.getItemInHand(hand);

        if (level.isClientSide()) return InteractionResultHolder.success(item);

        NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return item.getHoverName();
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
                return new SmallVesselContainer(Content.SMALL_VESSEL_CONTAINER.get(), containerId, playerInventory, player.getItemInHand(hand));
            }
        }, friendlyByteBuf -> friendlyByteBuf.writeBoolean(hand == InteractionHand.MAIN_HAND));

        return InteractionResultHolder.success(item);
    }
}
