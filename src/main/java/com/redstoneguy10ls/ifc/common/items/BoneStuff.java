package com.redstoneguy10ls.ifc.common.items;

import com.redstoneguy10ls.ifc.common.items.bloodalchemy.AlchemyJavilin;
import com.redstoneguy10ls.ifc.common.items.bloodalchemy.AlchemyKnife;
import com.redstoneguy10ls.ifc.common.items.bloodalchemy.AlchemyMace;
import com.redstoneguy10ls.ifc.common.items.bloodalchemy.AlchemyWeapon;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.items.*;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.*;

import java.util.Locale;
import java.util.function.Function;

//BONE(0xFFD3F7F, MapColor.RAW_IRON,Rarity.UNCOMMON, Metal.Tier.TIER_III,IFCTiers.BONE,false,false,false),
public enum BoneStuff implements StringRepresentable{
    DRAGON_BONE,
    FIRE,
    ICE,
    LIGHTNING
    ;


    private final String serializedName;

    @Override
    public String getSerializedName() {
        return serializedName;
    }
    BoneStuff()
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    public enum bone_Tools
    {
        PICKAXE(false,bone -> new PickaxeItem(IFCTiers.BONE, (int) ToolItem.calculateVanillaAttackDamage(0.75f, IFCTiers.BONE), -2.8F, properties())),
        PICKAXE_HEAD(false),
        PROPICK(false,bone -> new PropickItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.5f, IFCTiers.BONE), -2.8F, properties())),
        PROPICK_HEAD(false),
        AXE(false,bone -> new AxeItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(1.5f, IFCTiers.BONE), -3.1F, properties())),
        AXE_HEAD(false),
        SHOVEL(false,bone -> new ShovelItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.875F, IFCTiers.BONE), -3.0F, properties())),
        SHOVEL_HEAD(false),
        HOE(false,bone -> new TFCHoeItem(IFCTiers.BONE, -1, -2f, properties())),
        HOE_HEAD(false),
        CHISEL(false,bone -> new ChiselItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.27f, IFCTiers.BONE), -1.5F, properties())),
        CHISEL_HEAD(false),
        HAMMER(false,bone -> new HammerItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(1f, IFCTiers.BONE), -3, properties())),
        HAMMER_HEAD(false),
        SAW(false,bone -> new AxeItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.5f, IFCTiers.BONE), -3, properties())),
        SAW_BLADE(false),
        JAVELIN(true,bone -> new AlchemyJavilin(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.7f, IFCTiers.BONE), 1.5f * IFCTiers.BONE.getAttackDamageBonus(), -2.6F, properties(), IFCHelpers.identifier("textures/entity/projectiles/bone_javelin.png"))),
        JAVELIN_HEAD(false),
        SWORD(true,bone -> new AlchemyWeapon(IFCTiers.BONE, (int) ToolItem.calculateVanillaAttackDamage(1f, IFCTiers.BONE), -2.4F, properties())),
        SWORD_BLADE(false),
        MACE(true,bone -> new AlchemyMace(IFCTiers.BONE, (int) ToolItem.calculateVanillaAttackDamage(1.3f, IFCTiers.BONE), -3, properties())),
        MACE_HEAD(false),
        KNIFE(true,bone -> new AlchemyKnife(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.6f, IFCTiers.BONE), -2.0F, TFCTags.Blocks.MINEABLE_WITH_KNIFE, properties())),
        KNIFE_BLADE(false),
        SCYTHE(false,bone -> new ScytheItem(IFCTiers.BONE, ToolItem.calculateVanillaAttackDamage(0.7f, IFCTiers.BONE), -3.2F, TFCTags.Blocks.MINEABLE_WITH_SCYTHE, properties())),
        SCYTHE_BLADE(false);


        private final Function<BoneStuff, Item> itemFactory;

        public static Item.Properties properties()
        {
            return new Item.Properties().rarity(Rarity.UNCOMMON);
        }

        private final boolean blood;

        bone_Tools(boolean blood)
        {
            this(blood,bone -> new Item(properties()));
        }
        bone_Tools(boolean blood, Function<BoneStuff, Item> itemFactory)
        {
            this.blood = blood;
            this.itemFactory = itemFactory;
        }

        public boolean isBlood() {
            return blood;
        }

        public Item create(BoneStuff bone) {
            return itemFactory.apply(bone);
        }
    }
}
