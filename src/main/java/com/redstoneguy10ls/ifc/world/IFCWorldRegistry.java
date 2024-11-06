package com.redstoneguy10ls.ifc.world;




import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public class IFCWorldRegistry
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_CAVE = register("fire_dragon_cave", () -> new IFCWorldGenFireDragonCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_CAVE = register("ice_dragon_cave", () -> new IFCWorldGenIceDragonCave(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_CAVE = register("lightning_dragon_cave",
            () -> new IFCWorldGenLightningDragonCave(NoneFeatureConfiguration.CODEC));


    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(final String name, final Supplier<? extends F> supplier) {
        return FEATURES.register(name, supplier);
    }
}
