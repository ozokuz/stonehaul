package ozokuz.stonehaul.data;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.common.Content;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Consumer;

import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.campfireCooking;
import static ozokuz.stonehaul.Stonehaul.res;

public class VanillaRecipeProvider extends RecipeProvider {
    public VanillaRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ingredients(consumer);
        tools(consumer);
    }

    private void ingredients(Consumer<FinishedRecipe> consumer) {
        var fiber = Content.PLANT_FIBER.get();

        save(shapeless(Content.PLANT_STRING.get()).requires(fiber, 3), trigger(fiber), consumer);
        save(campfireCooking(Ingredient.of(fiber), ModItems.STRAW.get(), 0.1f, 200), trigger(fiber), consumer, res("straw_by_drying"));
    }

    private void tools(Consumer<FinishedRecipe> consumer) {
        crudeToolRecipe(Content.CRUDE_AXE.get(), "HB", " R", consumer);
        crudeToolRecipe(Content.CRUDE_HOE.get(), "HH", "BR", consumer);
        crudeToolRecipe(Content.CRUDE_SHOVEL.get(), " H", "RB", consumer);
    }

    private CriterionTriggerInstance trigger(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    private void save(RecipeBuilder builder, CriterionTriggerInstance trigger, Consumer<FinishedRecipe> consumer, ResourceLocation path) {
        builder.unlockedBy("has_item", trigger).save(consumer, path);
    }
    private void save(RecipeBuilder builder, CriterionTriggerInstance trigger, Consumer<FinishedRecipe> consumer) {
        builder.unlockedBy("has_item", trigger).save(consumer);
    }

    private void crudeToolRecipe(DiggerItem tool, String row1, String row2, Consumer<FinishedRecipe> consumer) {
        var fiber = Content.PLANT_FIBER.get();
        var flint = Items.FLINT;
        var string = Content.PLANT_STRING.get();
        var stick = Items.STICK;

        var builder = ShapedRecipeBuilder
                .shaped(tool)
                .pattern(row1)
                .pattern(row2)
                .define('H', flint) // Head
                .define('B', string) // Binding
                .define('R', stick); // Rod

        save(builder, trigger(fiber), consumer);
    }
}
