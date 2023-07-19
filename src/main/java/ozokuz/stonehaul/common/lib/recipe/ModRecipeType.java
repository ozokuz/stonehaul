package ozokuz.stonehaul.common.lib.recipe;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
    @Override
    public String toString() {
        return Registry.RECIPE_TYPE.getKey(this).toString();
    }
}
