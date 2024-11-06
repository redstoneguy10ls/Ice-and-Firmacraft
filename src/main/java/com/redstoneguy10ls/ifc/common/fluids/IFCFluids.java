package com.redstoneguy10ls.ifc.common.fluids;

import com.redstoneguy10ls.ifc.common.blocks.IFCBlocks;
import com.redstoneguy10ls.ifc.common.items.IFCItems;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.fluids.*;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;
import static net.dries007.tfc.common.fluids.TFCFluids.*;


public final class IFCFluids {

    //public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, MOD_ID);

    public static final Map<Metals, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(Metals.class, fluid -> register(
            fluid.getId(),
            properties -> properties
                    .block(IFCBlocks.METALS_FLUIDS.get(fluid))
                    .bucket(IFCItems.METAL_FLUID_BUCKETS.get(fluid))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.ifc.metal." +fluid.getId())
                    .canConvertToSource(false),
                    new FluidTypeClientProperties(fluid.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    public static final Map<Blood, FluidRegistryObject<ForgeFlowingFluid>> DRAGON_BLOOD = Helpers.mapOfKeys(Blood.class, fluid -> register(
            fluid.getId(),
            properties -> properties
                    .block(IFCBlocks.BLOOD_FLUID.get(fluid))
                    .bucket(IFCItems.BLOOD_BUCKET.get(fluid)),
            waterLike()
                    .descriptionId("fluid.ifc." + fluid.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(fluid.getColor(), WATER_STILL, WATER_FLOW, WATER_OVERLAY, UNDERWATER_LOCATION),
            MixingFluid.Source::new,
            MixingFluid.Flowing::new
    ));

    private static FluidType.Properties lavaLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(15)
                .density(3000)
                .viscosity(6000)
                .temperature(1300)
                .canConvertToSource(false)
                .canDrown(false)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(false)
                .canSwim(false)
                .supportsBoating(false);
    }
    private static FluidType.Properties waterLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.WATER)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .canConvertToSource(true)
                .canDrown(true)
                .canExtinguish(true)
                .canHydrate(true)
                .canPushEntity(true)
                .canSwim(true)
                .supportsBoating(true);
    }

    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        // Names `metal/foo` to `metal/flowing_foo`
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder, () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }


}
