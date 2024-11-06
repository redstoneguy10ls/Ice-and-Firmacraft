package com.redstoneguy10ls.ifc.util;

import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;

public interface RegistryScales extends StringRepresentable {

    ArmorMaterial armorTier();

    int getType();

    Rarity getRarity();


    ChatFormatting getChat();
}
