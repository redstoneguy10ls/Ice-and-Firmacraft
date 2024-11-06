package com.redstoneguy10ls.ifc.common.blocks;

import com.redstoneguy10ls.ifc.common.blockentities.DragonBellowsBlockEntity;
import com.redstoneguy10ls.ifc.common.blockentities.IFCBlockEntities;
import com.redstoneguy10ls.ifc.common.fluids.Blood;
import com.redstoneguy10ls.ifc.common.fluids.FluidId;
import com.redstoneguy10ls.ifc.common.fluids.IFCFluids;
import com.redstoneguy10ls.ifc.common.fluids.Metals;
import com.redstoneguy10ls.ifc.common.items.IFCItems;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.FluidCauldronBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.MossGrowingBlock;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.fluids.SimpleFluid;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.mixin.accessor.BlockBehaviourAccessor;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public class IFCBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);


    public static final RegistryObject<Block> DRAOGN_BELLOWS = register("dragon_bellows", () -> new DragonBellowsBlock(ExtendedProperties.of(MapColor.METAL)
            .strength(3)
            .sound(SoundType.METAL)
            .blockEntity(IFCBlockEntities.DRAGON_BELLOWS_ENTITY)
            .serverTicks(DragonBellowsBlockEntity::serverTick)));

    public static final Map<Metals, RegistryObject<LiquidBlock>> METALS_FLUIDS = Helpers.mapOfKeys(Metals.class, metals->
            registerNoItem("fluid/metal/"+metals.getId(), () -> new LiquidBlock(IFCFluids.METALS.get(metals).source(),
                    BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable())));

    public static final Map<Blood, RegistryObject<LiquidBlock>> BLOOD_FLUID = Helpers.mapOfKeys(Blood.class, fluid ->
            registerNoItem("fluid/" + fluid.getId(), () -> new
                    LiquidBlock(IFCFluids.DRAGON_BLOOD.get(fluid).source(), BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()))
    );

    public static final Map<FluidId, RegistryObject<DragonCauldron>> CAULDRONS = FluidId.mapOf(fluid ->
            registerNoItem("cauldron/"+fluid.name(), () -> new DragonCauldron(BlockBehaviour.Properties.copy(Blocks.CAULDRON))));

    public static final RegistryObject<Block> ALCHEMY_BLOCK = register("alchemy_trough",
            () -> new BloodAlchemyBlock(BlockBehaviour.Properties.of().strength(3).forceSolidOn().noOcclusion()));

    public static final Map<Rock, RegistryObject<Block>> STRONG_BRICKS =
            Helpers.mapOfKeys(Rock.class, rock -> register(("rock/bricks/"+ rock.name()), () -> Rock.BlockType.BRICKS.create(rock)));

/*
    public static final RegistryObject<Block> MINT = register("mint",
            () -> new mintBlock(ExtendedProperties.of()
                    .mapColor(MapColor.STONE)
                    .strength(2.0F, 2.0F)
                    .sound(SoundType.BASALT)
                    .noOcclusion()
                    .blockEntity(LCBlockEntities.MINT)
                    .serverTicks(mintBlockEntity::serverTick)));

 */


    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier,
              @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(IFCBlocks.BLOCKS, IFCItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
