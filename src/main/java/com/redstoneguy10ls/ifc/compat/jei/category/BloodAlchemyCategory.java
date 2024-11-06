package com.redstoneguy10ls.ifc.compat.jei.category;

import com.redstoneguy10ls.ifc.common.blocks.IFCBlocks;
import com.redstoneguy10ls.ifc.common.fluids.Blood;
import com.redstoneguy10ls.ifc.common.fluids.IFCFluids;
import com.redstoneguy10ls.ifc.common.recipes.BloodAlchemyRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.dries007.tfc.compat.jei.JEIIntegration;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BloodAlchemyCategory extends BaseRecipeCategory<BloodAlchemyRecipe>
{
    public BloodAlchemyCategory(RecipeType<BloodAlchemyRecipe> type, IGuiHelper helper)
    {
        super(type,helper,helper.createBlankDrawable(118,26), new ItemStack(IFCBlocks.ALCHEMY_BLOCK.get()));

    }
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BloodAlchemyRecipe recipe, IFocusGroup focuses)
    {
        final List<FluidStack> blood = new ArrayList<>(getBlood(recipe.getBloodType()));


        builder.addSlot(RecipeIngredientRole.INPUT, 26, 5)
                .addIngredients(recipe.getIngredient())
                .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 46, 5)
                .addIngredients(JEIIntegration.FLUID_STACK,blood)
                .setFluidRenderer(100, true, 16,16)
                .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 5)
                .addItemStack(recipe.getResultItem(registryAccess()))
                .setBackground(slot, -1, -1);
    }

    public List<FluidStack> getBlood(int bloodType)
    {
        final List<FluidStack> blood = new ArrayList<>();
        switch (bloodType)
        {
            case 1 -> blood.add(new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.FIRE).getSource(), 100));
            case 2 -> blood.add(new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.ICE).getSource(), 100));
            case 3 -> blood.add(new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.LIGHTNING).getSource(), 100));

            default -> throw new IllegalStateException("Unexpected value: " + bloodType);
        }
        return blood;
    }

}
