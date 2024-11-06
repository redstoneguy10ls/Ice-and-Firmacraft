package com.redstoneguy10ls.ifc.common.blocks;

import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.CauldronInteractions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collection;
import java.util.Collections;

public class DragonCauldron extends AbstractCauldronBlock {

    public DragonCauldron(Properties properties)
    {
        super(properties, Collections.emptyMap());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        final ItemStack stack = player.getItemInHand(hand);
        if (FluidHelpers.transferBetweenBlockHandlerAndItem(stack, CauldronInteractions.createFluidHandler(level, pos), level, pos, new FluidHelpers.AfterTransferWithPlayer(player, hand)))
        {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isFull(BlockState state)
    {
        return true;
    }
}
