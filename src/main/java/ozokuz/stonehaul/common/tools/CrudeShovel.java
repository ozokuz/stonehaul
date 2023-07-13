package ozokuz.stonehaul.common.tools;

import net.minecraft.world.item.ShovelItem;

public class CrudeShovel extends ShovelItem {
    public CrudeShovel(Properties properties) {
        super(CrudeItemTier.TIER, 1, -3.0F, properties);
    }
}
