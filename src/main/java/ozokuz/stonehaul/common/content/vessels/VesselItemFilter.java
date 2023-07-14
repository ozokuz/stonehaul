package ozokuz.stonehaul.common.content.vessels;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HalfTransparentBlock;

public class VesselItemFilter {
    private VesselItemFilter() {}

    public static boolean isValid(ItemStack stack) {
        var isBlock = stack.getItem() instanceof BlockItem;

        if (!isBlock) {
            return stack.isStackable();
        }

        var block = ((BlockItem) stack.getItem()).getBlock();
        var is2d = !Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0).isGui3d();

        if (is2d) return true;

        var isSeeThrough = !block.defaultBlockState().canOcclude();


        if (isSeeThrough) {
            if (block instanceof HalfTransparentBlock) {
                return false;
            }

            return true;
        }

        // TODO figure out a way to filter in and out items

        return false;
    }
}
