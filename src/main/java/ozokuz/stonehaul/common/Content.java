package ozokuz.stonehaul.common;

import com.tterrag.registrate.util.entry.RegistryEntry;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.common.tools.CrudeAxe;
import ozokuz.stonehaul.common.tools.CrudeHoe;
import ozokuz.stonehaul.common.tools.CrudeShovel;

import static ozokuz.stonehaul.Stonehaul.REGISTRATE;

public class Content {
    private Content() {}

    static {
        REGISTRATE.creativeModeTab(() -> StonehaulCreativeModeTab.INSTANCE);
        REGISTRATE.addRawLang("itemGroup." + Stonehaul.MOD_ID, "Stonehaul");
    }

    public static final RegistryEntry<CrudeAxe> CRUDE_AXE = REGISTRATE.item("crude_axe", CrudeAxe::new).lang("Crude Axe").register();
    public static final RegistryEntry<CrudeHoe> CRUDE_HOE = REGISTRATE.item("crude_hoe", CrudeHoe::new).lang("Crude Hoe").register();
    public static final RegistryEntry<CrudeShovel> CRUDE_SHOVEL = REGISTRATE.item("crude_shovel", CrudeShovel::new).lang("Crude Shovel").register();

    public static void register() {}
}
