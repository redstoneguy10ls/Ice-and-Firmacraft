package com.redstoneguy10ls.ifc.compat.jei.category;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.ingredients.ItemStackIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.compat.jei.JEIIntegration;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public abstract class BaseRecipeCategory<T> implements IRecipeCategory<T> {

    public static final ResourceLocation ICONS = new ResourceLocation(MOD_ID, "textures/gui/jei/icons.png");

    public static RegistryAccess registryAccess()
    {
        return ClientHelpers.getLevelOrThrow().registryAccess();
    }

    protected final IDrawableStatic slot;
    protected final IDrawableStatic arrow;
    protected final IDrawableAnimated arrowAnimated;

    private final RecipeType<T> type;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public BaseRecipeCategory(RecipeType<T> type, IGuiHelper helper, IDrawable background, ItemStack icon)
    {
        this.type = type;
        this.title = Component.translatable(MOD_ID + ".jei." + type.getUid().getPath());
        this.background = background;
        this.icon = helper.createDrawableIngredient(JEIIntegration.ITEM_STACK, icon);
        this.slot = helper.getSlotDrawable();

        this.arrow = helper.createDrawable(ICONS, 0, 14, 22, 16);
        IDrawableStatic arrowAnimated = helper.createDrawable(ICONS, 22, 14, 22, 16);
        this.arrowAnimated = helper.createAnimatedDrawable(arrowAnimated, 80, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static List<ItemStack> collapse(ItemStackIngredient input)
    {
        return Arrays.stream(input.ingredient().getItems())
                .map(stack -> stack.copyWithCount(input.count()))
                .map(FoodCapability::setStackNonDecaying) // Avoid decaying in JEI views
                .toList();
    }

    public static List<ItemStack> collapse(ItemStackProvider output)
    {
        return List.of(output.getEmptyStack());
    }

    public static List<ItemStack> collapse(List<ItemStack> inputs, ItemStackProvider output)
    {
        if (inputs.isEmpty())
        {
            return List.of(output.getEmptyStack());
        }
        return inputs.stream()
                .map(output::getStack)
                .map(FoodCapability::setStackNonDecaying) // Avoid decaying in JEI views
                .toList();
    }

    public static Ingredient collapse(BlockIngredient ingredient)
    {
        return Ingredient.of(ingredient.all().map(ItemStack::new).filter(item -> !item.isEmpty()));
    }

    @Override
    public RecipeType<T> getRecipeType()
    {
        return type;
    }

    @Override
    public Component getTitle()
    {
        return title;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

}
