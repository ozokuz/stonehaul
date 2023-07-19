package ozokuz.stonehaul.common.lib.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonehaul.Stonehaul;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ToolUsageShapelessRecipe extends ShapelessRecipe {
    public ToolUsageShapelessRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(id, group, result, ingredients);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        var list = super.getRemainingItems(container);

        for (int i = 0; i < list.size(); i++) {
            ItemStack item = container.getItem(i);
            if (item.isDamageableItem()) {
                var copy = item.copy();

                var broken = copy.hurt(1, Stonehaul.RANDOM, null);

                list.set(i, broken ? ItemStack.EMPTY : copy);
            }
        }

        return list;
    }

    public static class Serializer extends BaseRecipeSerializer<ToolUsageShapelessRecipe> {
        private static NonNullList<Ingredient> parseShapeless(final JsonObject serializedRecipe) {
            final NonNullList<Ingredient> list = NonNullList.create();

            for (final JsonElement element : GsonHelper.getAsJsonArray(serializedRecipe, "ingredients"))
                list.add(CraftingHelper.getIngredient(element));

            if (list.isEmpty()) throw new JsonParseException("No ingredients for shapeless recipe");

            if (list.size() > 9)
                throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is 9.");

            return list;
        }

        @Override
        public ToolUsageShapelessRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            final String group = GsonHelper.getAsString(serializedRecipe, "group", "");
            final NonNullList<Ingredient> ingredients = parseShapeless(serializedRecipe);
            final ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(serializedRecipe, "result"), true);

            return new ToolUsageShapelessRecipe(recipeId, group, result, ingredients);
        }

        @Nullable
        @Override
        public ToolUsageShapelessRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
            final String group = buf.readUtf();
            final int ingredientCount = buf.readVarInt();
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }

            final ItemStack result = buf.readItem();

            return new ToolUsageShapelessRecipe(recipeId, group, result, ingredients);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ToolUsageShapelessRecipe recipe) {
            buf.writeUtf(recipe.getGroup());
            buf.writeVarInt(recipe.getIngredients().size());

            for (final Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.getResultItem());
        }
    }
}
