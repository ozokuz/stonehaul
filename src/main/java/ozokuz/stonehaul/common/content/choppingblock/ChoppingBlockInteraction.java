package ozokuz.stonehaul.common.content.choppingblock;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ozokuz.stonehaul.Stonehaul;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Stonehaul.MOD_ID)
public class ChoppingBlockInteraction {
    @SubscribeEvent
    public static void choppingBlockInteract(PlayerInteractEvent.LeftClickBlock event) {
        var player = event.getPlayer();
        var level = player.getLevel();
        var pos = event.getPos();
        var state = level.getBlockState(pos);

        if (state.getBlock() instanceof ChoppingBlock choppingBlock && choppingBlock.interceptClick(level, pos, state, player)) {
            event.setCanceled(true);
        }
    }
}
