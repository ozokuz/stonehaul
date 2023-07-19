package ozokuz.stonehaul.data;

import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.lib.ItemNBTHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChoppingBlockRecipeBuilder implements RecipeBuilder {
    private final ItemStack result;
    private final Ingredient ingredient;

    public ChoppingBlockRecipeBuilder(ItemStack result, Ingredient ingredient) {
        this.result = result;
        this.ingredient = ingredient;
    }

    public static ChoppingBlockRecipeBuilder chopping(ItemStack result, Ingredient ingredient) {
        return new ChoppingBlockRecipeBuilder(result, ingredient);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RecipeBuilder group(@Nullable String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new Result(id, this.result, this.ingredient));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final Ingredient ingredient;

        public Result(ResourceLocation id, ItemStack result, Ingredient ingredient) {
            this.id = id;
            this.result = result;
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", ingredient.toJson());
            json.add("result", ItemNBTHelper.serializeStack(result));
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return Content.CHOPPING_BLOCK_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
