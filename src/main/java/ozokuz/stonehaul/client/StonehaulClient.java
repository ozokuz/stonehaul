package ozokuz.stonehaul.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.common.content.Content;

@Mod.EventBusSubscriber(modid = Stonehaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StonehaulClient {
    @SubscribeEvent
    public static void registerERs(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Content.CHOPPING_BLOCK_ENTITY.get(), ChoppingBlockRenderer::new);
    }
}
