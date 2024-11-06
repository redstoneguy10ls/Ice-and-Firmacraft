package com.redstoneguy10ls.ifc.mixin;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseMixin  {

    @Shadow public abstract int getDragonStage();

    @ModifyArg(method = "breakBlock", at= @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;isBreakable(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;FLcom/github/alexthe666/iceandfire/entity/EntityDragonBase;)Z"),index = 2, remap = false)
    private float hardnessFix(float hardness)
    {
        return getDragonStage() <= 3 ? 5.5F : 9.0F;
    }
}
