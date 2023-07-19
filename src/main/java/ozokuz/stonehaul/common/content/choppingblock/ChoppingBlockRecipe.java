package ozokuz.stonehaul.common.content.choppingblock;

import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.lib.recipe.BaseRecipeSerializer;

import javax.annotation.ParametersAreNonnullByDefault;

import static ozokuz.stonehaul.Stonehaul.REGISTRATE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChoppingBlockRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack result;
    private final Ingredient ingredient;

    public ChoppingBlockRecipe(ResourceLocation id, ItemStack result, Ingredient ingredient) {
        this.id = id;
        this.result = result;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container) {
        return getResultItem().copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        var list = NonNullList.withSize(1, Ingredient.EMPTY);
        list.set(0, ingredient);
        return list;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(REGISTRATE.get(Content.CHOPPING_BLOCK_ID, ForgeRegistries.ITEMS.getRegistryKey()).get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Content.CHOPPING_BLOCK_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Content.CHOPPING_BLOCK_RECIPE_TYPE.get();
    }

    public static class Serializer extends BaseRecipeSerializer<ChoppingBlockRecipe> {
        @Override
        public ChoppingBlockRecipe fromJson(ResourceLocation id, JsonObject json) {
            var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            var ingredient = Ingredient.fromJson(json.get("ingredient"));
            return new ChoppingBlockRecipe(id, result, ingredient);
        }

        @Override
        public ChoppingBlockRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            var ingredient = Ingredient.fromNetwork(buf);
            var result = buf.readItem();
            return new ChoppingBlockRecipe(id, result, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ChoppingBlockRecipe recipe) {
            recipe.getIngredients().get(0).toNetwork(buf);
            buf.writeItem(recipe.getResultItem());
        }
    }
}
