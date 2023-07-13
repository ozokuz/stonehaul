package ozokuz.stonehaul.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonehaul.Stonehaul;
import ozokuz.stonehaul.common.Content;
import ozokuz.stonehaul.integration.IntegrationTranslations;

@JeiPlugin
public class StonehaulJEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return Stonehaul.res("stonehaul_jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(Content.PLANT_FIBER.get()), VanillaTypes.ITEM_STACK, IntegrationTranslations.jeiInfoPlantFiber);

    }
}
