package ozokuz.stonehaul.integration.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.content.choppingblock.ChoppingBlockRecipe;
import ozokuz.stonehaul.integration.IntegrationTranslations;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChoppingBlockRecipeCategory implements IRecipeCategory<ChoppingBlockRecipe> {
    public static final RecipeType<ChoppingBlockRecipe> TYPE = new RecipeType<>(Content.CHOPPING_BLOCK_RECIPE_SERIALIZER.getId(), ChoppingBlockRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public ChoppingBlockRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(82, 54);
        this.icon = helper.createDrawableItemStack(new ItemStack(Content.CHOPPING_BLOCK.get()));
    }

    @Override
    public Component getTitle() {
        return IntegrationTranslations.jeiChoppingBlockRecipeTitle;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return Content.CHOPPING_BLOCK_RECIPE_SERIALIZER.getId();
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends ChoppingBlockRecipe> getRecipeClass() {
        return ChoppingBlockRecipe.class;
    }

    @Override
    public RecipeType<ChoppingBlockRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChoppingBlockRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 25).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 31, 1).addItemStack(new ItemStack(Items.IRON_AXE));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 25).addItemStack(recipe.getResultItem());
    }
}
