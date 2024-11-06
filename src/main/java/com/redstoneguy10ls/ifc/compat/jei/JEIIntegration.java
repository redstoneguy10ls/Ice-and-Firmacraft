package com.redstoneguy10ls.ifc.compat.jei;

import com.redstoneguy10ls.ifc.common.blocks.IFCBlocks;
import com.redstoneguy10ls.ifc.common.recipes.BloodAlchemyRecipe;
import com.redstoneguy10ls.ifc.common.recipes.IFCRecipeTypes;
import com.redstoneguy10ls.ifc.compat.jei.category.BloodAlchemyCategory;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.KnappingType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

@JeiPlugin
public class JEIIntegration implements IModPlugin
{
    public static final IIngredientTypeWithSubtypes<Item, ItemStack> ITEM_STACK = VanillaTypes.ITEM_STACK;

    public static RecipeType<BloodAlchemyRecipe> BLOOD_ALCHEMY = type("blood_alchemy", BloodAlchemyRecipe.class);

    private static <T> RecipeType<T> type(String name, Class<T> tClass)
    {
        return RecipeType.create(MOD_ID, name, tClass);
    }

    private static <C extends Container, T extends Recipe<C>> List<T> recipes(net.minecraft.world.item.crafting.RecipeType<T> type)
    {
        return ClientHelpers.getLevelOrThrow().getRecipeManager().getAllRecipesFor(type);
    }
    private static <C extends Container, T extends Recipe<C>> List<T> recipes(net.minecraft.world.item.crafting.RecipeType<T> type, Predicate<T> filter)
    {
        return recipes(type).stream().filter(filter).collect(Collectors.toList());
    }

    private static void addRecipeCatalyst(IRecipeCatalystRegistration registry, TagKey<Item> tag, RecipeType<?> recipeType) {
        Helpers.getAllTagValues(tag, ForgeRegistries.ITEMS).forEach(item -> registry.addRecipeCatalyst(new ItemStack(item),recipeType));
    }
    @Override
    public ResourceLocation getPluginUid()
    {
        return IFCHelpers.identifier("jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        final IGuiHelper gui = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(
                new BloodAlchemyCategory(BLOOD_ALCHEMY,gui)
        );

    }

    @Override
    public void registerRecipes(IRecipeRegistration registry)
    {
        registry.addRecipes(BLOOD_ALCHEMY, recipes(IFCRecipeTypes.BLOOD_ALCHEMY.get()));


    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(IFCBlocks.ALCHEMY_BLOCK.get()),BLOOD_ALCHEMY);

    }
}
