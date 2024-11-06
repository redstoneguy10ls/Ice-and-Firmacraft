package com.redstoneguy10ls.ifc.common.blocks;

import com.redstoneguy10ls.ifc.common.blockentities.IFCBlockEntities;
import com.redstoneguy10ls.ifc.common.fluids.Blood;
import com.redstoneguy10ls.ifc.common.fluids.IFCFluids;
import com.redstoneguy10ls.ifc.common.recipes.BloodAlchemyRecipe;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import com.redstoneguy10ls.ifc.util.IFCTags;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class BloodAlchemyBlock extends HalfTransparentBlock {

    private static final VoxelShape SHAPE = Shapes.or(
            box(5, 0, 5, 11, 1, 11),
            box(5, 1, 5, 6, 16, 11),
            box(10, 1, 5, 11, 16, 11),
            box(6, 1, 5, 10, 16, 6),
            box(6, 1, 10, 10, 16, 11)
    );

    private static final VoxelShape INTERACTION_SHAPE = Shapes.or(box(5, 0, 5, 11, 16, 11));
    public static final IntegerProperty FILLED = IFCStateProperties.FILLED;
    public BloodAlchemyBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FILLED, 0));
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos)
    {
        return INTERACTION_SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {

        ItemStack held = player.getItemInHand(hand);
        return switch (state.getValue(FILLED))
        {
            case 0 -> attemptFill(player,held,state,level,pos);
            case 1 -> attemptAlchemy(player,held, state,level,pos);
            case 2 -> attemptAlchemy(player,held, state,level,pos);
            case 3 -> attemptAlchemy(player,held, state,level,pos);

            default -> InteractionResult.PASS;
        };
    }

    private InteractionResult attemptAlchemy(Player player,ItemStack held, BlockState state, Level level, BlockPos pos)
    {


        BloodAlchemyRecipe recipe = BloodAlchemyRecipe.getRecipe(level,new ItemStackInventory(held),state.getValue(FILLED));
        if(recipe != null)
        {
                ItemStack result = recipe.getResult().getStack(held);
                CompoundTag tag = held.getTag();
                result.setTag(tag);
                result.setDamageValue(held.getDamageValue());

                held.shrink(1);
                player.getInventory().add(result);
                level.setBlock(pos,state.setValue(FILLED, 0),3);
                level.playSound(player,pos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS,1,2);
                level.playSound(player,pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
        }

        /*
        if(Helpers.isItem(held,IFCTags.Items.ALCHEMY_ABLE))
        {
            IFCHelpers.applyAlchemy(player,held,bloodType);
            level.setBlock(pos,state.setValue(FILLED, 0),3);
            level.playSound(player,pos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS,1,2);
            level.playSound(player,pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        }

         */
        if(held.getCapability(Capabilities.FLUID_ITEM).isPresent())
        {
            final IFluidHandler handler = held.getCapability(Capabilities.FLUID_ITEM).resolve().orElse(null);
            if(handler != null && handler.getFluidInTank(0).getAmount()+100 <= handler.getTankCapacity(0))
            {
                final FluidStack blood = handler.getFluidInTank(0);

                if((Helpers.isFluid(blood.getFluid(), IFCTags.Fluids.FIRE_BLOOD) || blood.isEmpty()) && state.getValue(FILLED) == 1)
                {
                    final FluidStack fire = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.FIRE).getSource(), 100);
                    handler.fill(fire, IFluidHandler.FluidAction.EXECUTE);
                    level.setBlock(pos,state.setValue(FILLED, 0),3);
                    level.playSound(player,pos, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS);
                    return InteractionResult.SUCCESS;
                }
                if((Helpers.isFluid(blood.getFluid(), IFCTags.Fluids.ICE_BLOOD) || blood.isEmpty()) && state.getValue(FILLED) == 2)
                {
                    final FluidStack ice = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.ICE).getSource(), 100);
                    handler.fill(ice, IFluidHandler.FluidAction.EXECUTE);
                    level.setBlock(pos,state.setValue(FILLED, 0),3);
                    level.playSound(player,pos, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS);
                    return InteractionResult.SUCCESS;
                }
                if((Helpers.isFluid(blood.getFluid(), IFCTags.Fluids.LIGHTNING_BLOOD) || blood.isEmpty()) && state.getValue(FILLED) == 3)
                {
                    final FluidStack lightning = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.LIGHTNING).getSource(), 100);
                    handler.fill(lightning, IFluidHandler.FluidAction.EXECUTE);
                    level.setBlock(pos,state.setValue(FILLED, 0),3);
                    level.playSound(player,pos, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS);
                    return InteractionResult.SUCCESS;
                }

            }
        }
        return InteractionResult.PASS;
    }

    private InteractionResult attemptFill(Player player,@NotNull ItemStack held, BlockState state,Level level, BlockPos pos)
    {
        final IFluidHandler handler = held.getCapability(Capabilities.FLUID_ITEM).resolve().orElse(null);
        if(handler != null && handler.getFluidInTank(0).getAmount() >= 100)
        {
            final FluidStack blood = handler.getFluidInTank(0);
            if(Helpers.isFluid(blood.getFluid(), IFCTags.Fluids.FIRE_BLOOD))
            {
                handler.drain(100, IFluidHandler.FluidAction.EXECUTE);
                level.setBlock(pos,state.setValue(FILLED, 1),3);
                level.playSound(player,pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
            if(Helpers.isFluid(blood.getFluid(), IFCTags.Fluids.ICE_BLOOD))
            {
                handler.drain(100, IFluidHandler.FluidAction.EXECUTE);
                level.setBlock(pos,state.setValue(FILLED, 2),3);
                level.playSound(player,pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
            if(Helpers.isFluid(blood.getFluid(), IFCTags.Fluids.LIGHTNING_BLOOD))
            {
                handler.drain(100, IFluidHandler.FluidAction.EXECUTE);
                level.setBlock(pos,state.setValue(FILLED, 3),3);
                level.playSound(player,pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FILLED);
    }

}
