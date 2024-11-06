package com.redstoneguy10ls.ifc.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.gen.WorldGenCaveStalactites;
import com.mojang.serialization.Codec;
import com.redstoneguy10ls.ifc.util.IFCTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class IFCWorldGenLightningDragonCave extends IFCWorldGenDragonCaves
{
    public static ResourceLocation LIGHTNING_DRAGON_CHEST = new ResourceLocation(IceAndFire.MODID, "chest/lightning_dragon_female_cave");
    public static ResourceLocation LIGHTNING_DRAGON_CHEST_MALE = new ResourceLocation(IceAndFire.MODID, "chest/lightning_dragon_male_cave");

    public IFCWorldGenLightningDragonCave(final Codec<NoneFeatureConfiguration> configuration)
    {
        super(configuration);
        DRAGON_CHEST = LIGHTNING_DRAGON_CHEST;
        DRAGON_MALE_CHEST = LIGHTNING_DRAGON_CHEST_MALE;
        CEILING_DECO = new WorldGenCaveStalactites(IafBlockRegistry.CRACKLED_STONE.get(), 6);
        PALETTE_BLOCK1 = IafBlockRegistry.CRACKLED_STONE.get().defaultBlockState();
        PALETTE_BLOCK2 = IafBlockRegistry.CRACKLED_COBBLESTONE.get().defaultBlockState();
        TREASURE_PILE = IafBlockRegistry.COPPER_PILE.get().defaultBlockState();
        dragonTypeOreTag = IFCTags.Blocks.LIGHTNING_DRAGON_CAVE_ORES;
        dragonType = 3;
    }
    @Override
    public EntityType<? extends EntityDragonBase> getDragonType() {
        return IafEntityRegistry.LIGHTNING_DRAGON.get();
    }
}
