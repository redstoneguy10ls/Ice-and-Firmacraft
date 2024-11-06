package com.redstoneguy10ls.ifc.common.recipes;

import com.google.gson.JsonObject;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import net.dries007.tfc.common.recipes.RecipeSerializerImpl;
import net.dries007.tfc.common.recipes.SimpleItemRecipe;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;


public class BloodAlchemyRecipe extends SimpleItemRecipe
{
    public static final IndirectHashCollection<Item,BloodAlchemyRecipe> CACHE = IndirectHashCollection.createForRecipe(BloodAlchemyRecipe::getValidItems, IFCRecipeTypes.BLOOD_ALCHEMY);

    @Nullable
    public static BloodAlchemyRecipe getRecipe(Level world, ItemStackInventory wrapper,int bloodType)
    {
        for(BloodAlchemyRecipe recipe : CACHE.getAll(wrapper.getStack().getItem()))
        {
            if(recipe.matches(wrapper, world) && bloodType == recipe.getBloodType())
            {
                return recipe;
            }
        }
        return null;
    }

    private final int bloodType;

    public BloodAlchemyRecipe(ResourceLocation id, Ingredient ingredient, ItemStackProvider result, int bloodType)
    {
        super(id,ingredient,result);
        this.bloodType = bloodType;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {return IFCRecipeSerializers.BLOOD_ALCHEMY.get();}

    public int getBloodType() {return bloodType;}

    @Override
    public RecipeType<?> getType() {return IFCRecipeTypes.BLOOD_ALCHEMY.get();}

    public static class Serializer extends RecipeSerializerImpl<BloodAlchemyRecipe>
    {
        @Override
        public BloodAlchemyRecipe fromJson(ResourceLocation id, JsonObject json)
        {
            final Ingredient ingredient = Ingredient.fromJson(JsonHelpers.get(json, "ingredient"));
            final ItemStackProvider result = ItemStackProvider.fromJson(GsonHelper.getAsJsonObject(json, "result"));
            final int bloodtype = IFCHelpers.getBloodType(JsonHelpers.getAsString(json,"blood_type","fire")) ;
            return new BloodAlchemyRecipe(id,ingredient, result, bloodtype);
        }
        @Override
        public BloodAlchemyRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            final Ingredient ingredient = Ingredient.fromNetwork(buffer);
            final ItemStackProvider result = ItemStackProvider.fromNetwork(buffer);
            final int bloodType = buffer.readVarInt();
            return new BloodAlchemyRecipe(id,ingredient,result,bloodType);
        }
        @Override
        public void toNetwork(FriendlyByteBuf buffer, BloodAlchemyRecipe recipe)
        {
            recipe.getIngredient().toNetwork(buffer);
            recipe.getResult().toNetwork(buffer);
            buffer.writeVarInt(recipe.getBloodType());
        }

    }
}
