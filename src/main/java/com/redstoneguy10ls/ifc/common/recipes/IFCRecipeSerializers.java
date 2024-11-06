package com.redstoneguy10ls.ifc.common.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public class IFCRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MOD_ID);

    public static final RegistryObject<BloodAlchemyRecipe.Serializer> BLOOD_ALCHEMY = register("blood_alchemy", BloodAlchemyRecipe.Serializer::new);


    private static <S extends RecipeSerializer<?>> RegistryObject<S> register(String name, Supplier<S> factory)
    {
        return RECIPE_SERIALIZERS.register(name, factory);
    }
}


