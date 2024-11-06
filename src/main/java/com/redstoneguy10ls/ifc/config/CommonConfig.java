package com.redstoneguy10ls.ifc.config;

import com.mojang.logging.LogUtils;
import net.dries007.tfc.config.ConfigBuilder;
import net.minecraftforge.common.ForgeConfigSpec;
import org.slf4j.Logger;

public class CommonConfig {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ForgeConfigSpec.BooleanValue generateDragonSkeletons;
    public ForgeConfigSpec.IntValue generateDragonSkeletonChance;
    public ForgeConfigSpec.IntValue generateDragonDenChance;
    public ForgeConfigSpec.IntValue generateDragonRoostChance;
    public ForgeConfigSpec.IntValue dragonDenGoldAmount;
    public ForgeConfigSpec.IntValue oreToStoneRatioForDragonCaves;
    public ForgeConfigSpec.BooleanValue useRockType;


    CommonConfig(ConfigBuilder builder)
    {
        generateDragonSkeletons = builder.comment("Whether to generate dragon skeletons or not")
                .define("Generate Dragon Skeletons",false);

        generateDragonSkeletonChance = builder.comment("1 out of this number chance per chunk for generation")
                .define("Generate Dragon Skeleton Chance",300,1,10000);

        generateDragonDenChance = builder.comment("1 out of this number chance per chunk for generation")
                .define("Generate Dragon Cave Chance",260,1,10000);

        generateDragonRoostChance = builder.comment("1 out of this number chance per chunk for generation")
                .define("Generate Dragon Roost Chance",480,1,10000);

        dragonDenGoldAmount = builder.comment("1 out of this number chance per block that gold will generate in dragon lairs.")
                .define("Dragon Den Gold Amount",4,1,10000);

        oreToStoneRatioForDragonCaves = builder.comment("Ratio of Stone(this number) to Ores in Dragon Caves.")
                .define("Dragon Cave Ore Ratio",45,1,10000);

        useRockType = builder.comment("Whether to make the ores that generate in dragon caves be based on the rocktype in the area. THIS WILL IGNORE THE TAG")
                .define("Better Dragon Cave Ore Generation",false);


    }

}
