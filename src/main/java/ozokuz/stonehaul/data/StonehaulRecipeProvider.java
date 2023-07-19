package ozokuz.stonehaul.data;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.common.content.Content;

import java.util.function.Consumer;

public class StonehaulRecipeProvider extends RecipeProvider {
    private static final TagKey<Item>[] logs = new TagKey[] { ItemTags.OAK_LOGS, ItemTags.SPRUCE_LOGS, ItemTags.BIRCH_LOGS, ItemTags.JUNGLE_LOGS, ItemTags.ACACIA_LOGS, ItemTags.DARK_OAK_LOGS, ItemTags.WARPED_STEMS, ItemTags.CRIMSON_STEMS };
    private static final Item[] planks = new Item[] {Items.OAK_PLANKS, Items.SPRUCE_PLANKS, Items.BIRCH_PLANKS, Items.JUNGLE_PLANKS, Items.ACACIA_PLANKS, Items.DARK_OAK_PLANKS, Items.WARPED_PLANKS, Items.CRIMSON_PLANKS };
    public StonehaulRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Chopping Block
        save(ToolUsageRecipeBuilder.shapeless(Content.CHOPPING_BLOCK.get()).requires(ItemTags.LOGS).requires(Content.WOODCUTTERS_TAG), trigger(ItemTags.LOGS), consumer);
        // Logs to Planks
        logsToPlanks(consumer);
    }

    private void logsToPlanks(Consumer<FinishedRecipe> consumer) {
        for (int i = 0; i < logs.length; i++) {
            final TagKey<Item> log = logs[i];
            final Item plank = planks[i];

            ChoppingBlockRecipeBuilder.chopping(new ItemStack(plank, 2), Ingredient.of(log)).save(consumer, Stonehaul.res(ForgeRegistries.ITEMS.getKey(plank).getPath() + "_from_chopping"));
        }
    }

    private CriterionTriggerInstance trigger(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate(tag, null, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
    }

    private void save(RecipeBuilder builder, CriterionTriggerInstance trigger, Consumer<FinishedRecipe> consumer) {
        builder.unlockedBy("has_item", trigger).save(consumer);
    }
}
