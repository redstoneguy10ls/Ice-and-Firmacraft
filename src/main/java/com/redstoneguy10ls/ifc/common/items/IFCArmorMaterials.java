package com.redstoneguy10ls.ifc.common.items;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.item.IProtectAgainstDragonItem;
import com.github.alexthe666.iceandfire.item.IafArmorMaterial;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import net.dries007.tfc.util.PhysicalDamageType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;


import java.util.Locale;

public enum IFCArmorMaterials implements ArmorMaterial, PhysicalDamageType.Multiplier, IProtectAgainstDragonItem {
    SCALE(468,540,576,396,5, 7, 9, 5,15,
            2.0f,0.0f,32,16,40,SoundEvents.ARMOR_EQUIP_CHAIN),
    FIRE_STEEL(IFCHelpers.armourDamage(13), IFCHelpers.armourDamage(15), IFCHelpers.armourDamage(16), IFCHelpers.armourDamage(11),
  IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor,
            IafConfig.dragonsteelBaseArmor - 5,30,6.0f,1.5f,73,79,73,SoundEvents.ARMOR_EQUIP_DIAMOND),
    ICE_STEEL(IFCHelpers.armourDamage(13), IFCHelpers.armourDamage(15), IFCHelpers.armourDamage(16), IFCHelpers.armourDamage(11),
            IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor,
            IafConfig.dragonsteelBaseArmor - 5,30,6.0f,1.5f,73,73,79,SoundEvents.ARMOR_EQUIP_DIAMOND),
    LIGHTNING_STEEL(IFCHelpers.armourDamage(13), IFCHelpers.armourDamage(15), IFCHelpers.armourDamage(16), IFCHelpers.armourDamage(11),
            IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor,
            IafConfig.dragonsteelBaseArmor - 5,30,6.0f,1.5f,79,73,73,SoundEvents.ARMOR_EQUIP_DIAMOND),
    MYTHRIL(IFCHelpers.armourDamage(13), IFCHelpers.armourDamage(15), IFCHelpers.armourDamage(16), IFCHelpers.armourDamage(11),
            IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor,
            IafConfig.dragonsteelBaseArmor - 5,30,6.0f,1.5f,75,75,75, SoundEvents.ARMOR_EQUIP_DIAMOND)
    ;
    private final ResourceLocation serializedName;
    private final int feetDamage;
    private final int legDamage;
    private final int chestDamage;
    private final int headDamage;
    private final int feetReduction;
    private final int legReduction;
    private final int chestReduction;
    private final int headReduction;
    private final int enchantability;
    private final float toughness;
    private final float knockbackResistance;
    private final float crushingModifier;
    private final float piercingModifier;
    private final float slashingModifier;
    private final SoundEvent equip;

    IFCArmorMaterials(int feetDamage, int legDamage, int chestDamage, int headDamage,
                      int feetReduction, int legReduction, int chestReduction, int headReduction,
                      int enchantability, float toughness, float knockbackResistance,
                      float crushingModifier, float piercingModifier, float slashingModifier,SoundEvent equip)
    {
        this.serializedName = IFCHelpers.identifier(name().toLowerCase(Locale.ROOT));
        this.feetDamage = feetDamage;
        this.legDamage = legDamage;
        this.chestDamage = chestDamage;
        this.headDamage = headDamage;
        this.feetReduction = feetReduction;
        this.legReduction = legReduction;
        this.chestReduction = chestReduction;
        this.headReduction = headReduction;
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;

        // Since each slot is applied separately, the input values are values for a full set of armor of this type.
        this.crushingModifier = crushingModifier * 0.25f;
        this.piercingModifier = piercingModifier * 0.25f;
        this.slashingModifier = slashingModifier * 0.25f;
        this.equip = equip;
    }

    @Override
    public float crushing()
    {
        return crushingModifier;
    }

    @Override
    public float piercing()
    {
        return piercingModifier;
    }

    @Override
    public float slashing()
    {
        return slashingModifier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type slot)
    {
        return switch (slot)
        {
            case BOOTS -> feetReduction;
            case LEGGINGS -> legReduction;
            case CHESTPLATE -> chestReduction;
            case HELMET -> headReduction;
        };
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type)
    {
        return switch (type)
        {
            case BOOTS -> feetDamage;
            case LEGGINGS -> legDamage;
            case CHESTPLATE -> chestDamage;
            case HELMET -> headDamage;
        };
    }

    @Override
    public int getEnchantmentValue()
    {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return equip;
    }

    public ResourceLocation getID() {
        return serializedName;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }

    @Override
    @Deprecated
    public String getName() {
        return serializedName.toString();
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
