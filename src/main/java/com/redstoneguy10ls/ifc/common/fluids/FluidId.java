package com.redstoneguy10ls.ifc.common.fluids;


import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.world.level.material.Fluid;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record FluidId(String name, OptionalInt color, Supplier<? extends Fluid> fluid) {

    private static final Map<Enum<?>, FluidId> IDENTITY = new HashMap<>();

    private static final List<FluidId> VALUES = Stream.of(
            Arrays.stream(Blood.values()).map(blood -> fromEnum(blood, blood.getColor(), blood.getId(), IFCFluids.DRAGON_BLOOD.get(blood).source() ) )
   )
   .flatMap(Function.identity())
   .toList();

    public static <R> Map<FluidId, R> mapOf(Function<? super FluidId, ? extends R> map)
    {
        return VALUES.stream().collect(Collectors.toMap(Function.identity(), map));
    }

    public static FluidId asType(Enum<?> identity){return IDENTITY.get(identity);}

    private static FluidId fromEnum(Enum<?> identity, int color, String name, Supplier<? extends Fluid> fluid)
    {
        final FluidId type = new FluidId(name, OptionalInt.of(TFCFluids.ALPHA_MASK | color), fluid);
        IDENTITY.put(identity, type);
        return type;
    }
}
