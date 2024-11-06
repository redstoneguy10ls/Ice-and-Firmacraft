package com.redstoneguy10ls.ifc.common.misc;

import com.redstoneguy10ls.ifc.IFC;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LCSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, IFC.MOD_ID);

    public static final RegistryObject<SoundEvent> COINPURSE_ADD = create("item.coinpurse.some_add1");

    public static final RegistryObject<SoundEvent> COINPURSE_EMPTY_ADD = create("item.coinpurse.empty_add");

    public static final RegistryObject<SoundEvent> MINT_HIT = create("block.minting_die.hit");



    private static RegistryObject<SoundEvent> create(String name)
    {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(IFCHelpers.identifier(name)));
    }
}
