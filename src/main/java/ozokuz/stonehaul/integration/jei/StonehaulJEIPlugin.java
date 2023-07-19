package ozokuz.stonehaul.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.common.content.Content;
import ozokuz.stonehaul.common.content.choppingblock.ChoppingBlockRecipe;
import ozokuz.stonehaul.integration.IntegrationTranslations;

import java.util.List;

@JeiPlugin
public class StonehaulJEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return Stonehaul.res("stonehaul_jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ChoppingBlockRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(Content.PLANT_FIBER.get()), VanillaTypes.ITEM_STACK, IntegrationTranslations.jeiInfoPlantFiber);

        List<ChoppingBlockRecipe> choppingRecipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(Content.CHOPPING_BLOCK_RECIPE_TYPE.get());

        registration.addRecipes(ChoppingBlockRecipeCategory.TYPE, choppingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Content.CHOPPING_BLOCK.get()), ChoppingBlockRecipeCategory.TYPE);
    }
}
