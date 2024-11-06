package com.redstoneguy10ls.ifc.common.blockentities;

import com.eerussianguy.firmalife.common.blockentities.OvenBottomBlockEntity;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.common.blockentities.TickableBlockEntity;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DragonBellowsBlockEntity extends TFCBlockEntity {

    private static final int LURE_DISTANCE = 50;
    public static void serverTick(Level level, BlockPos pos, BlockState state, DragonBellowsBlockEntity bellows)
    {
        if(bellows.syncTicks > 0)
        {
            bellows.syncTicks--;
        }
        if(bellows.syncTicks == 0)
        {
            bellows.syncTicks = 20;

            final int dragon = lureDragons(level,bellows);

            for(Direction direction : Direction.values())
            {
                HeatCapability.provideHeatTo(level, pos.relative(direction), IFCHelpers.DragonBreathTemp(dragon));
            }

            //HeatCapability.provideHeatTo(level, pos.above(), IFCHelpers.DragonBreathTemp(dragon));


        }
    }


    private int syncTicks;



    public DragonBellowsBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        syncTicks = 20;
    }
    public DragonBellowsBlockEntity(BlockPos pos,BlockState state)
    {
        this(IFCBlockEntities.DRAGON_BELLOWS_ENTITY.get(),pos,state);
    }


    protected static int lureDragons(Level level, DragonBellowsBlockEntity bellows)
    {
        int age = 0;
        Vec3 targetPosition = new Vec3(
                bellows.getBlockPos().getX() + 0.5F,
                bellows.getBlockPos().getY() + 0.5F,
                bellows.getBlockPos().getZ() + 0.5F
        );
        AABB searchArea = new AABB(
                (double) bellows.worldPosition.getX() - LURE_DISTANCE,
                (double) bellows.worldPosition.getY() - LURE_DISTANCE,
                (double) bellows.worldPosition.getZ() - LURE_DISTANCE,
                (double) bellows.worldPosition.getX() + LURE_DISTANCE,
                (double) bellows.worldPosition.getY() + LURE_DISTANCE,
                (double) bellows.worldPosition.getZ() + LURE_DISTANCE
        );
        boolean dragonSelected = false;

        for(EntityDragonBase dragon : level.getEntitiesOfClass(EntityDragonBase.class, searchArea))
        {
            if(!dragonSelected && dragon.dragonType.getIntFromType() == 0 && (dragon.isChained() || dragon.isTame()) && canSeeBellows(dragon, targetPosition,bellows))
            {
                dragon.burningTarget = bellows.worldPosition;
                dragonSelected = true;
                dragon.setBreathingFire(true);
                age = dragon.getDragonStage();
            }else if(dragon.burningTarget == bellows.worldPosition)
            {
                dragon.burningTarget = null;
                dragon.setBreathingFire(false);
                age=0;
            }
        }
        return age;
    }


    private static boolean canSeeBellows(EntityDragonBase dragon, Vec3 target,DragonBellowsBlockEntity bellows){
        if(target != null)
        {
            HitResult rayTrace = bellows.level.clip(new ClipContext(dragon.getHeadPosition(), target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, dragon));
            double distance = dragon.getHeadPosition().distanceTo(rayTrace.getLocation());

            return distance < 10.0F + dragon.getBbWidth();
        }
        return false;
    }

}
