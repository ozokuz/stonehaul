package ozokuz.stonehaul.common.content;

import com.tterrag.registrate.util.entry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.client.LargeVesselScreen;
import ozokuz.stonehaul.client.SmallVesselScreen;
import ozokuz.stonehaul.common.content.choppingblock.ChoppingBlock;
import ozokuz.stonehaul.common.content.choppingblock.ChoppingBlockEntity;
import ozokuz.stonehaul.common.content.choppingblock.ChoppingBlockRecipe;
import ozokuz.stonehaul.common.content.tools.CrudeAxe;
import ozokuz.stonehaul.common.content.tools.CrudeHoe;
import ozokuz.stonehaul.common.content.tools.CrudeShovel;
import ozokuz.stonehaul.common.content.vessels.large.LargeVesselBlock;
import ozokuz.stonehaul.common.content.vessels.large.LargeVesselBlockEntity;
import ozokuz.stonehaul.common.content.vessels.large.LargeVesselContainer;
import ozokuz.stonehaul.common.content.vessels.large.UnfiredLargeVesselBlock;
import ozokuz.stonehaul.common.content.vessels.small.SmallVesselContainer;
import ozokuz.stonehaul.common.content.vessels.small.SmallVesselItem;
import ozokuz.stonehaul.common.lib.recipe.ModRecipeType;
import ozokuz.stonehaul.common.lib.recipe.ToolUsageShapelessRecipe;

import static ozokuz.stonehaul.Stonehaul.REGISTRATE;

public class Content {
    private Content() {}

    static {
        REGISTRATE.creativeModeTab(() -> StonehaulCreativeModeTab.INSTANCE);
        REGISTRATE.addRawLang("itemGroup." + Stonehaul.MOD_ID, "Stonehaul");
    }

    public static final TagKey<Item> WOODCUTTERS_TAG = ItemTags.create(Stonehaul.res("woodcutters"));
    public static final TagKey<Item> FORGE_TOOLS_AXES = ItemTags.create(new ResourceLocation("forge", "tools/axes"));
    public static final TagKey<Item> FORGE_TOOLS_HOES = ItemTags.create(new ResourceLocation("forge", "tools/hoes"));
    public static final TagKey<Item> FORGE_TOOLS_SHOVELS = ItemTags.create(new ResourceLocation("forge", "tools/shovels"));

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Stonehaul.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Stonehaul.MOD_ID);

    // Common Recipe Serializers
    public static final RegistryObject<RecipeSerializer<ToolUsageShapelessRecipe>> TOOL_USAGE_SHAPELESS_RECIPE_SERIALIZER = RECIPE_SERIALIZER_DEFERRED_REGISTER.register("tool_usage_shapeless", ToolUsageShapelessRecipe.Serializer::new);

    // Crude Tools
    public static final RegistryEntry<CrudeAxe> CRUDE_AXE = REGISTRATE.item("crude_axe", CrudeAxe::new).lang("Crude Axe").tag(FORGE_TOOLS_AXES).register();
    public static final RegistryEntry<CrudeHoe> CRUDE_HOE = REGISTRATE.item("crude_hoe", CrudeHoe::new).lang("Crude Hoe").tag(FORGE_TOOLS_HOES).register();
    public static final RegistryEntry<CrudeShovel> CRUDE_SHOVEL = REGISTRATE.item("crude_shovel", CrudeShovel::new).lang("Crude Shovel").tag(FORGE_TOOLS_SHOVELS).register();

    // Crafting Resources
    public static final RegistryEntry<Item> PLANT_FIBER = REGISTRATE.item("plant_fiber", Item::new).lang("Plant Fiber").register();
    public static final RegistryEntry<Item> PLANT_STRING = REGISTRATE.item("plant_string", Item::new).lang("Plant String").register();

    // Chopping Block
    public static final String CHOPPING_BLOCK_ID = "chopping_block";
    public static final RegistryEntry<ChoppingBlock> CHOPPING_BLOCK = REGISTRATE.block(CHOPPING_BLOCK_ID, Material.WOOD, ChoppingBlock::new)
            .blockstate((blockChoppingBlockDataGenContext, registrateBlockstateProvider) -> {
                var model = registrateBlockstateProvider.models().getExistingFile(registrateBlockstateProvider.modLoc("block/chopping_block"));
                registrateBlockstateProvider.simpleBlock(blockChoppingBlockDataGenContext.get(), model);
            })
            .simpleItem()
            .properties(properties -> properties.strength(1.0F))
            .lang("Chopping Block").register();
    public static final BlockEntityEntry<ChoppingBlockEntity> CHOPPING_BLOCK_ENTITY = REGISTRATE.blockEntity(CHOPPING_BLOCK_ID, ChoppingBlockEntity::new).validBlock(CHOPPING_BLOCK).register();
    public static final RegistryObject<RecipeType<ChoppingBlockRecipe>> CHOPPING_BLOCK_RECIPE_TYPE = RECIPE_TYPE_DEFERRED_REGISTER.register(CHOPPING_BLOCK_ID, ModRecipeType::new);
    public static final RegistryObject<RecipeSerializer<ChoppingBlockRecipe>> CHOPPING_BLOCK_RECIPE_SERIALIZER = RECIPE_SERIALIZER_DEFERRED_REGISTER.register(CHOPPING_BLOCK_ID, ChoppingBlockRecipe.Serializer::new);

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

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZER_DEFERRED_REGISTER.register(modEventBus);
        RECIPE_TYPE_DEFERRED_REGISTER.register(modEventBus);
    }
}
