package ozokuz.stonehaul.common.content.choppingblock;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonehaul.common.content.Content;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChoppingBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 8, 16);

    public ChoppingBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChoppingBlockEntity(Content.CHOPPING_BLOCK_ENTITY.get(), pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.SUCCESS;

        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ChoppingBlockEntity choppingBlockBlockEntity) {
            if (choppingBlockBlockEntity.isEmpty()) {
                return tryPutItem(state, level, pos, player, choppingBlockBlockEntity);
            }

            return removeItem(state, level, pos, player, choppingBlockBlockEntity);
        }

        return InteractionResult.SUCCESS;
    }

    private InteractionResult tryPutItem(BlockState state, Level level, BlockPos pos, Player player, ChoppingBlockEntity blockEntity) {
        var mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainHandStack.isEmpty() && offHandStack.isEmpty()) return InteractionResult.SUCCESS;

        ItemStack stack;
        InteractionHand hand;

        if (!mainHandStack.isEmpty() && blockEntity.canPlaceItem(0, mainHandStack)) {
            stack = mainHandStack;
            hand = InteractionHand.MAIN_HAND;
        } else if (!offHandStack.isEmpty() && blockEntity.canPlaceItem(0, offHandStack)) {
            stack = offHandStack;
            hand = InteractionHand.OFF_HAND;
        } else {
            return InteractionResult.SUCCESS;
        }

        var split = stack.split(1);

        if (stack.isEmpty()) {
            player.setItemInHand(hand, ItemStack.EMPTY);
        } else {
            player.getInventory().setChanged();
        }

        blockEntity.setItem(0, split);
        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult removeItem(BlockState state, Level level, BlockPos pos, Player player, ChoppingBlockEntity blockEntity) {
        var choppable = blockEntity.getItem(0);

        blockEntity.setItem(0, ItemStack.EMPTY);
        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

        Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), choppable);

        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() == newState.getBlock()) return;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ChoppingBlockEntity choppingBlockBlockEntity) {
            Containers.dropContents(level, pos, choppingBlockBlockEntity);

            level.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, level, pos, newState, moved);
    }

    public boolean interceptClick(Level level, BlockPos pos, BlockState state, Player player) {
        var blockEntity = (ChoppingBlockEntity) level.getBlockEntity(pos);
        if (blockEntity == null || blockEntity.isEmpty()) return false;
        if (level.isClientSide()) return false;

        var axe = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!axe.is(Content.WOODCUTTERS_TAG)) return false;

        var recipe = level.getRecipeManager().getRecipeFor(Content.CHOPPING_BLOCK_RECIPE_TYPE.get(), blockEntity, level);
        if (recipe.isEmpty()) return false;

        var result = recipe.get().assemble(blockEntity);

        blockEntity.setItem(0, ItemStack.EMPTY);
        blockEntity.setChanged();
        Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), result);
        player.causeFoodExhaustion(0.0025f);
        axe.hurtAndBreak(1, player, stack -> stack.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

        return true;
    }
}
