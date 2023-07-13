package ozokuz.stonehaul.common;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.Stonehaul;

public class StonehaulCreativeModeTab extends CreativeModeTab {
    public static final StonehaulCreativeModeTab INSTANCE = new StonehaulCreativeModeTab();

    public static void init() {}

    private StonehaulCreativeModeTab() {
        super(Stonehaul.MOD_ID);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(Items.FLINT);
    }
}
