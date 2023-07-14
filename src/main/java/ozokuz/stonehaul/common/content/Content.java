package ozokuz.stonehaul.common.content;

import com.tterrag.registrate.util.entry.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Material;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.client.LargeVesselScreen;
import ozokuz.stonehaul.client.SmallVesselScreen;
import ozokuz.stonehaul.common.content.tools.CrudeAxe;
import ozokuz.stonehaul.common.content.tools.CrudeHoe;
import ozokuz.stonehaul.common.content.tools.CrudeShovel;
import ozokuz.stonehaul.common.content.vessels.large.LargeVesselBlock;
import ozokuz.stonehaul.common.content.vessels.large.LargeVesselBlockEntity;
import ozokuz.stonehaul.common.content.vessels.large.LargeVesselContainer;
import ozokuz.stonehaul.common.content.vessels.large.UnfiredLargeVesselBlock;
import ozokuz.stonehaul.common.content.vessels.small.SmallVesselContainer;
import ozokuz.stonehaul.common.content.vessels.small.SmallVesselItem;

import static ozokuz.stonehaul.Stonehaul.REGISTRATE;

public class Content {
    private Content() {}

    static {
        REGISTRATE.creativeModeTab(() -> StonehaulCreativeModeTab.INSTANCE);
        REGISTRATE.addRawLang("itemGroup." + Stonehaul.MOD_ID, "Stonehaul");
    }

    // Crude Tools
    public static final RegistryEntry<CrudeAxe> CRUDE_AXE = REGISTRATE.item("crude_axe", CrudeAxe::new).lang("Crude Axe").register();
    public static final RegistryEntry<CrudeHoe> CRUDE_HOE = REGISTRATE.item("crude_hoe", CrudeHoe::new).lang("Crude Hoe").register();
    public static final RegistryEntry<CrudeShovel> CRUDE_SHOVEL = REGISTRATE.item("crude_shovel", CrudeShovel::new).lang("Crude Shovel").register();

    // Crafting Resources
    public static final RegistryEntry<Item> PLANT_FIBER = REGISTRATE.item("plant_fiber", Item::new).lang("Plant Fiber").register();
    public static final RegistryEntry<Item> PLANT_STRING = REGISTRATE.item("plant_string", Item::new).lang("Plant String").register();

    // Vessels
    // Unfired Large Vessel
    public static final String UNFIRED_LARGE_VESSEL_ID = "unfired_large_vessel";
    public static final BlockEntry<UnfiredLargeVesselBlock> UNFIRED_LARGE_VESSEL_BLOCK = REGISTRATE.block(UNFIRED_LARGE_VESSEL_ID, Material.CLAY, UnfiredLargeVesselBlock::new)
            .blockstate((blockUnfiredLargeVesselBlockDataGenContext, registrateBlockstateProvider) -> {
                var clay = registrateBlockstateProvider.models().mcLoc("block/clay");
                var vessel = registrateBlockstateProvider.models().modLoc("block/vessel");
                var model = registrateBlockstateProvider.models().singleTexture(UNFIRED_LARGE_VESSEL_ID, vessel, "all", clay);
                registrateBlockstateProvider.simpleBlock(blockUnfiredLargeVesselBlockDataGenContext.get(), model);
            })
            .simpleItem()
            .properties(properties -> properties.strength(1.0F))
            .lang("Unfired Large Vessel").register();
    // Large Vessel
    public static final String LARGE_VESSEL_ID = "large_vessel";
    public static final RegistryEntry<LargeVesselBlock> LARGE_VESSEL_BLOCK = REGISTRATE
            .block(LARGE_VESSEL_ID, Material.DECORATION, LargeVesselBlock::new)
            .blockstate((blockLargeVesselBlockDataGenContext, registrateBlockstateProvider) -> {
                var terracotta = registrateBlockstateProvider.models().mcLoc("block/terracotta");
                var vessel = registrateBlockstateProvider.models().modLoc("block/vessel");
                var model = registrateBlockstateProvider.models().singleTexture(LARGE_VESSEL_ID, vessel, "all", terracotta);
                registrateBlockstateProvider.simpleBlock(blockLargeVesselBlockDataGenContext.get(), model);
            })
            .simpleItem()
            .properties(properties -> properties.strength(1.0F))
            .lang("Large Vessel")
            .register();
    public static final BlockEntityEntry<LargeVesselBlockEntity> LARGE_VESSEL_BLOCK_ENTITY = REGISTRATE.blockEntity(LARGE_VESSEL_ID, LargeVesselBlockEntity::new).register();
    public static final MenuEntry<LargeVesselContainer> LARGE_VESSEL_CONTAINER = REGISTRATE.menu(LARGE_VESSEL_ID, LargeVesselContainer::fromNetwork, () -> LargeVesselScreen::new).register();
    // Unfired Small Vessel
    public static final RegistryEntry<Item> UNFIRED_SMALL_VESSEL_ITEM = REGISTRATE.item("unfired_small_vessel", Item::new).lang("Unfired Small Vessel").register();
    // Small Vessel
    public static final String SMALL_VESSEL_ID = "small_vessel";
    public static final RegistryEntry<SmallVesselItem> SMALL_VESSEL_ITEM = REGISTRATE.item(SMALL_VESSEL_ID, SmallVesselItem::new).lang("Small Vessel").register();
    public static final MenuEntry<SmallVesselContainer> SMALL_VESSEL_CONTAINER = REGISTRATE.menu(SMALL_VESSEL_ID, SmallVesselContainer::fromNetwork, () -> SmallVesselScreen::new).register();

    public static void register() {}
}
