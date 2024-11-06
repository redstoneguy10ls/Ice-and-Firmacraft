package com.redstoneguy10ls.ifc.common.items.bloodalchemy;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class AlchemyMace extends AlchemyWeapon{
    public AlchemyMace(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction)
    {
        return super.canPerformAction(stack, toolAction) && toolAction != ToolActions.SWORD_SWEEP;
    }
}
