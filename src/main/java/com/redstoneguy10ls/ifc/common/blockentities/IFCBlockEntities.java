package com.redstoneguy10ls.ifc.common.blockentities;

import com.redstoneguy10ls.ifc.common.blocks.IFCBlocks;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public class IFCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final RegistryObject<BlockEntityType<DragonBellowsBlockEntity>> DRAGON_BELLOWS_ENTITY = register
            ("d", DragonBellowsBlockEntity::new, IFCBlocks.DRAOGN_BELLOWS);

//    public static final RegistryObject<BlockEntityType<mintBlockEntity>> MINT =
//            register("mint", mintBlockEntity::new, IFCBlocks.MINT);


    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }

}
