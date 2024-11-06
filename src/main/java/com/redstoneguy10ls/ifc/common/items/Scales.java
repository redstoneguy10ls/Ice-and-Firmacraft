package com.redstoneguy10ls.ifc.common.items;

import com.redstoneguy10ls.ifc.util.RegistryScales;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

public class Scales {

    public enum Default implements StringRepresentable, RegistryScales
    {
        RED(1, ChatFormatting.DARK_RED),
        GREEN(1, ChatFormatting.DARK_GREEN),
        BRONZE(1, ChatFormatting.GOLD),
        GRAY(1, ChatFormatting.GRAY),
        BLUE(2, ChatFormatting.AQUA),
        WHITE(2, ChatFormatting.WHITE),
        SAPPHIRE(2, ChatFormatting.BLUE),
        SILVER(2, ChatFormatting.DARK_GRAY),
        ELECTRIC(3, ChatFormatting.DARK_BLUE),
        AMYTHEST(3, ChatFormatting.LIGHT_PURPLE),
        COPPER(3, ChatFormatting.GOLD),
        BLACK(3, ChatFormatting.DARK_GRAY);


        private final int type;
        private final ChatFormatting chat;
        Default(int type, ChatFormatting col)
        {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.armorTier = IFCArmorMaterials.SCALE;
            this.rarity = Rarity.UNCOMMON;
            this.type =type;
            this.chat = col;
        }

        private final String serializedName;
        private final Rarity rarity;


        private final ArmorMaterial armorTier;

        @Override
        public ArmorMaterial armorTier() {
            return Objects.requireNonNull(armorTier, "Tried to get non-existent armor tier from " + name());
        }

        @Override
        public int getType() {
            return type;
        }

        public ChatFormatting getChat() {
            return chat;
        }

        @Override
        public Rarity getRarity() {
            return rarity;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
    public enum armours
    {
        UNFINISHED_HELMET(),
        HELMET(scale->new ScaleArmor(scale.armorTier(),ArmorItem.Type.HELMET, properties(scale), scale.getType(),scale.getSerializedName(),scale.getChat())),
        UNFINISHED_CHESTPLATE(),
        CHESTPLATE(scale->new ScaleArmor(scale.armorTier(),ArmorItem.Type.CHESTPLATE, properties(scale), scale.getType(),scale.getSerializedName(),scale.getChat())),
        UNFINISHED_GREAVES(),
        GREAVES(scale->new ScaleArmor(scale.armorTier(),ArmorItem.Type.LEGGINGS, properties(scale), scale.getType(),scale.getSerializedName(),scale.getChat())),
        UNFINISHED_BOOTS(),
        BOOTS(scale->new ScaleArmor(scale.armorTier(),ArmorItem.Type.BOOTS, properties(scale), scale.getType(),scale.getSerializedName(),scale.getChat()))
        ;

        private final Function<RegistryScales, Item> itemFactory;

        armours()
        {
            this(scale -> new Item(properties(scale)));
        }
        armours(Function<RegistryScales, Item> itemFactory)
        {
            this.itemFactory = itemFactory;
        }
        public static Item.Properties properties(RegistryScales scales)
        {
            return new Item.Properties().rarity(scales.getRarity());
        }

        public Item create(RegistryScales scales)
        {
            return itemFactory.apply(scales);
        }
    }
}
