package com.redstoneguy10ls.ifc.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.dries007.tfc.common.capabilities.SimpleFluidHandler;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.CauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.module.FindException;
/*
public final class DragonCauldronInteraction {
    private static final BiMap<Block, Fluid> CAULDRONS = HashBiMap.create();


    public static void registerDragonCauldronInteractions()
    {

    }


    public static IFluidHandler createFluidHandler(Level level, BlockPos pos)
    {
        return new CauldronBlockHandler(level, pos);
    }

    public static InteractionResult interactWithJug(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack)
    {
        if (FluidHelpers.transferBetweenBlockHandlerAndItem(stack, createFluidHandler(level, pos), level, pos, new FluidHelpers.AfterTransferWithPlayer(player, hand)))
        {
            player.awardStat(Stats.USE_CAULDRON);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    record CauldronBlockHandler(Level level, BlockPos pos) implements SimpleFluidHandler
    {
        @NotNull
        @Override
        public FluidStack getFluidInTank(int tank)
        {

            final Fluid fluid = CAULDRONS.get(level.getBlockState(pos).getBlock());
            final int level = getFluidInTank(tank).getAmount();
            return fluid != null ? new FluidStack(fluid, level) : FluidStack.EMPTY;
        }
        @Override
        public int getTankCapacity(int tank)
        {
            return FluidHelpers.BUCKET_VOLUME;
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack)
        {
            return CAULDRONS.inverse().containsKey(stack.getFluid());
        }

        @Override
        public int fill(FluidStack stack, FluidAction action) {

            if(getFluidInTank(0).getAmount() < FluidHelpers.BUCKET_VOLUME && isFluidValid(0, stack) && stack.getAmount() >= 333)
            {
                final Block block = CAULDRONS.inverse().get(stack.getFluid());
                if(!action.simulate())
                {
                    BlockState state = block.defaultBlockState();

                }
            }
        }
    }
}

 */
