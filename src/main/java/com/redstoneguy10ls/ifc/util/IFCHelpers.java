package com.redstoneguy10ls.ifc.util;


import com.github.alexthe666.iceandfire.IafConfig;
import com.redstoneguy10ls.ifc.common.items.BoneStuff;
import com.redstoneguy10ls.ifc.common.items.IFCItems;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.settings.RockSettings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


import java.util.Locale;
import java.util.stream.Stream;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public class IFCHelpers {
    
    public static ResourceLocation identifier(String name) {
        return new ResourceLocation(MOD_ID, name);
    }


    public static int getBloodType(String bloodString)
    {
        return switch (bloodString.toLowerCase(Locale.ROOT))
        {
            case "dragon_fire","fire", "fire_dragon" -> 1;
            case "dragon_ice","ice", "ice_dragon" -> 2;
            case "dragon_lightning","lightning", "lightning_dragon" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + bloodString.toLowerCase(Locale.ROOT));
        };
    }

    public static int armourDamage(int start)
    {
        return (int)(start* 0.02 * (double) IafConfig.dragonsteelBaseDurabilityEquipment);
    }

    public static float DragonBreathTemp(int age)
    {
        return switch (age){
            case 0 -> 0.0F;
            case 1 -> 500.0F;
            case 2 -> 750.0F;
            case 3 -> 1000.0F;
            case 4 -> 1500.0F;
            case 5 -> 2000.0F;
            default -> throw new IllegalStateException("Unexpected value: " + age);
        };
    }

    public static Rock getRock(RockSettings rockData, RandomSource rand)
    {
        BlockState rock = rockData.raw().defaultBlockState();

        for(Rock rocks : Rock.VALUES)
        {
            if(Helpers.isBlock(rock, TFCBlocks.ROCK_BLOCKS.get(rocks).get(Rock.BlockType.RAW).get() ))
            {
                return rocks;
            }
        }
        return Rock.VALUES[rand.nextInt(Rock.values().length)];
    }


/*
    public static void applyAlchemy(Player player, ItemStack stack, int bloodType)
    {
        //don't ask me why it's a tag, the game just hates me
        if(Helpers.isItem(stack, IFCTags.Items.SWORDS))
        {
            CompoundTag tag = stack.getTag();
            switch(bloodType)
            {
                case 1:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.FIRE).get(BoneStuff.bone_Tools.SWORD).get(), 1,tag));
                    break;
                }
                case 2:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.ICE).get(BoneStuff.bone_Tools.SWORD).get(), 1,tag));
                    break;

                }
                case 3:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.LIGHTNING).get(BoneStuff.bone_Tools.SWORD).get(), 1,tag));
                    break;

                }

            }
        }
        if(Helpers.isItem(stack, IFCTags.Items.KNIVES))
        {
            CompoundTag tag = stack.getTag();
            switch(bloodType)
            {
                case 1:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.FIRE).get(BoneStuff.bone_Tools.KNIFE).get(), 1,tag));
                    break;

                }
                case 2:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.ICE).get(BoneStuff.bone_Tools.KNIFE).get(), 1,tag));
                    break;

                }
                case 3:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.LIGHTNING).get(BoneStuff.bone_Tools.KNIFE).get(), 1,tag));
                    break;

                }

            }
        }
        if(Helpers.isItem(stack, IFCTags.Items.JAVELINS))
        {
            CompoundTag tag = stack.getTag();
            switch(bloodType)
            {
                case 1:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.FIRE).get(BoneStuff.bone_Tools.JAVELIN).get(), 1,tag));
                    break;

                }
                case 2:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.ICE).get(BoneStuff.bone_Tools.JAVELIN).get(), 1,tag));
                    break;

                }
                case 3:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.LIGHTNING).get(BoneStuff.bone_Tools.JAVELIN).get(), 1,tag));
                    break;

                }

            }
        }
        if(Helpers.isItem(stack, IFCTags.Items.MACES))
        {
            CompoundTag tag = stack.getTag();
            switch(bloodType)
            {
                case 1:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.FIRE).get(BoneStuff.bone_Tools.MACE).get(), 1,tag));
                    break;

                }
                case 2:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.ICE).get(BoneStuff.bone_Tools.MACE).get(), 1,tag));
                    break;

                }
                case 3:{
                    stack.shrink(1);
                    player.getInventory().add(new ItemStack(IFCItems.BLOOD_TOOLS.get(BoneStuff.LIGHTNING).get(BoneStuff.bone_Tools.MACE).get(), 1,tag));
                    break;

                }

            }
        }
    }

 */

}
