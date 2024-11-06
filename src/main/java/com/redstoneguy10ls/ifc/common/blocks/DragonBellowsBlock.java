package com.redstoneguy10ls.ifc.common.blocks;

import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.minecraft.world.level.block.Block;

public class DragonBellowsBlock extends DeviceBlock {

    public DragonBellowsBlock(ExtendedProperties properties)
    {
        super(properties, InventoryRemoveBehavior.NOOP);
    }
}
