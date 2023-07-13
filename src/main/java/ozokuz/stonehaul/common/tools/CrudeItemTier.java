package ozokuz.stonehaul.common.tools;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class CrudeItemTier implements Tier {
    public static final Tier TIER = new CrudeItemTier();
    private CrudeItemTier() {}

    @Override
    public int getUses() {
        return 64;
    }

    @Override
    public float getSpeed() {
        return 1.5F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 0.5F;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(Items.FLINT);
    }
}
