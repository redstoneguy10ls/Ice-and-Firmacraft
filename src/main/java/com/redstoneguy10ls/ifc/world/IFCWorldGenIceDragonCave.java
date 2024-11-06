package com.redstoneguy10ls.ifc.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.gen.WorldGenCaveStalactites;
import com.mojang.serialization.Codec;
import com.redstoneguy10ls.ifc.util.IFCTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class IFCWorldGenIceDragonCave extends IFCWorldGenDragonCaves
{
    public static ResourceLocation ICE_DRAGON_CHEST = new ResourceLocation(IceAndFire.MODID, "chest/ice_dragon_female_cave");
    public static ResourceLocation ICE_DRAGON_CHEST_MALE = new ResourceLocation(IceAndFire.MODID, "chest/ice_dragon_male_cave");
    public IFCWorldGenIceDragonCave(final Codec<NoneFeatureConfiguration> configuration)
    {
        super(configuration);
        DRAGON_CHEST = ICE_DRAGON_CHEST;
        DRAGON_MALE_CHEST = ICE_DRAGON_CHEST_MALE;
        CEILING_DECO = new WorldGenCaveStalactites(IafBlockRegistry.FROZEN_STONE.get(), 3);
        PALETTE_BLOCK1 = IafBlockRegistry.FROZEN_STONE.get().defaultBlockState();
        PALETTE_BLOCK2 = IafBlockRegistry.FROZEN_COBBLESTONE.get().defaultBlockState();
        TREASURE_PILE = IafBlockRegistry.SILVER_PILE.get().defaultBlockState();
        dragonTypeOreTag = IFCTags.Blocks.ICE_DRAGON_CAVE_ORES;
        dragonType = 2;
    }
    @Override
    public EntityType<? extends EntityDragonBase> getDragonType() {
        return IafEntityRegistry.ICE_DRAGON.get();
    }
}
