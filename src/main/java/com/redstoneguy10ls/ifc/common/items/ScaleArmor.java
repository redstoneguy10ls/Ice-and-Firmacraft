package com.redstoneguy10ls.ifc.common.items;

import com.github.alexthe666.iceandfire.client.model.armor.ModelFireDragonScaleArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelIceDragonScaleArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelLightningDragonScaleArmor;
import com.github.alexthe666.iceandfire.item.IProtectAgainstDragonItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ScaleArmor extends ArmorItem implements IProtectAgainstDragonItem {
    public int dragontype;
    public final ChatFormatting chatcolor;
    public String colour;
    public ScaleArmor(ArmorMaterial material, Type type, Properties properties,int dragontype,String colour,ChatFormatting chatcolor) {
        super(material,type,properties);
        this.dragontype = dragontype;
        this.colour = colour;
        this.chatcolor = chatcolor;
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity LivingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default)
            {
                boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
                if(itemStack.getItem() instanceof ScaleArmor)
                {
                    return switch (dragontype)
                    {
                        case 1 -> new ModelFireDragonScaleArmor(inner);
                        case 2 -> new ModelIceDragonScaleArmor(inner);
                        case 3 -> new ModelLightningDragonScaleArmor(inner);
                        default -> throw new IllegalStateException("Unexpected value: " + dragontype);
                    };
                }
                return _default;
            }
        });
    }
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "iceandfire:textures/models/armor/armor_" + colour + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("dragon." + colour.toLowerCase()).withStyle(chatcolor));
        tooltip.add(Component.translatable("item.dragonscales_armor.desc").withStyle(ChatFormatting.GRAY));
    }

}
