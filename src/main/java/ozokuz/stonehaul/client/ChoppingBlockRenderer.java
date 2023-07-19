package ozokuz.stonehaul.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import ozokuz.stonehaul.common.content.choppingblock.ChoppingBlockEntity;

public class ChoppingBlockRenderer implements BlockEntityRenderer<ChoppingBlockEntity> {
    public ChoppingBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ChoppingBlockEntity entity, float delta, PoseStack matrices, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        var stack = entity.getItem(0);

        if (stack.isEmpty()) return;

        matrices.pushPose();

        matrices.translate(0.5, 0.5, 0.5);
        matrices.translate(0, -3 / 16f, 0);
        matrices.scale(2, 2, 2);

        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, packedLight, OverlayTexture.NO_OVERLAY, matrices, bufferSource, 0);

        matrices.popPose();
    }
}
