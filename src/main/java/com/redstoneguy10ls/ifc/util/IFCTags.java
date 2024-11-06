package com.redstoneguy10ls.ifc.util;

import com.redstoneguy10ls.ifc.IFC;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class IFCTags {
    public static class Blocks{

        public static final TagKey<Block> DRAGON_CAVE_RICH_ORES = tag("dragon_cave_rich_ores");
        public static final TagKey<Block> DRAGON_CAVE_NORMAL_ORES = tag("dragon_cave_normal_ores");
        public static final TagKey<Block> DRAGON_CAVE_POOR_ORES = tag("dragon_cave_poor_ores");
        public static final TagKey<Block> DRAGON_CAVE_MINERALS_ORES = tag("dragon_cave_mineral_ores");

        public static final TagKey<Block> FIRE_DRAGON_CAVE_ORES = tag("fire_dragon_cave_ores");
        public static final TagKey<Block> ICE_DRAGON_CAVE_ORES = tag("ice_dragon_cave_ores");
        public static final TagKey<Block> LIGHTNING_DRAGON_CAVE_ORES = tag("lightning_dragon_cave_ores");



        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(IFC.MOD_ID, name));
        }

    }

    public static class Items {
        public static final TagKey<Item> FIRE_WEAPONS = tag("fire_weapons");
        public static final TagKey<Item> ICE_WEAPONS = tag("ice_weapons");
        public static final TagKey<Item> LIGHTNING_WEAPONS = tag("lightning_weapons");
        public static final TagKey<Item> ALCHEMY_ABLE = tag("blood_alchemy_ingredients");

        public static final TagKey<Item> SWORDS = tag("swords");
        public static final TagKey<Item> KNIVES = tag("knives");
        public static final TagKey<Item> MACES = tag("maces");
        public static final TagKey<Item> JAVELINS = tag("javelins");






        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(IFC.MOD_ID, name));
        }

    }

    public static class Fluids
    {
        public static final TagKey<Fluid> USABLE_IN_COIN_MOLD  = create("usable_in_coin_mold");
        public static final TagKey<Fluid> FIRE_BLOOD  = create("fire_blood");
        public static final TagKey<Fluid> ICE_BLOOD  = create("ice_blood");
        public static final TagKey<Fluid> LIGHTNING_BLOOD  = create("lightning_blood");


        private static TagKey<Fluid> create(String name){
            return FluidTags.create(new ResourceLocation(IFC.MOD_ID, name));
        }
    }


}
