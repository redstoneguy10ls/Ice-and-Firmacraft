package com.redstoneguy10ls.ifc.common.items.bloodalchemy;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.redstoneguy10ls.ifc.common.items.BoneStuff;
import com.redstoneguy10ls.ifc.common.items.IFCItems;
import com.redstoneguy10ls.ifc.util.IFCTags;
import net.dries007.tfc.util.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemyWeapon extends SwordItem {

    private int attackDamage;
    public AlchemyWeapon(Tier tier, int attackDamage, float attackSpeed, Properties properties)
    {
        super(tier, attackDamage, attackSpeed, properties);
        this.attackDamage = attackDamage;
    }

//TODO centralize this
    //could probably make it a capability idk
    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if(Helpers.isItem(this, IFCTags.Items.FIRE_WEAPONS) && IafConfig.dragonWeaponFireAbility)
        {
            if (target instanceof EntityIceDragon) {
                target.hurt(attacker.level().damageSources().inFire(), attackDamage+8);
            }
            target.setSecondsOnFire(5);
            target.knockback(1F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }
        if(Helpers.isItem(this, IFCTags.Items.ICE_WEAPONS) && IafConfig.dragonWeaponIceAbility)
        {
            if (target instanceof EntityFireDragon) {
                target.hurt(attacker.level().damageSources().drown(), attackDamage+8);
            }

            EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 200));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
            target.knockback(1F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }
        if(Helpers.isItem(this, IFCTags.Items.LIGHTNING_WEAPONS) && IafConfig.dragonWeaponLightningAbility)
        {
            boolean flag = true;
            if (attacker instanceof Player) {
                if (attacker.attackAnim > 0.2) {
                    flag = false;
                }
            }
            if (!attacker.level().isClientSide && flag) {
                LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(target.level());
                lightningboltentity.getTags().add(ServerEvents.BOLT_DONT_DESTROY_LOOT);
                lightningboltentity.getTags().add(attacker.getStringUUID());
                lightningboltentity.moveTo(target.position());
                if (!target.level().isClientSide) {
                    target.level().addFreshEntity(lightningboltentity);
                }
            }
            if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
                target.hurt(attacker.level().damageSources().lightningBolt(), attackDamage+4);
            }
            target.knockback(1F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (Helpers.isItem(this, IFCTags.Items.FIRE_WEAPONS)) {
            tooltip.add(Component.translatable("dragon_sword_fire.hurt1").withStyle(ChatFormatting.GREEN));
            if (IafConfig.dragonWeaponFireAbility)
                tooltip.add(Component.translatable("dragon_sword_fire.hurt2").withStyle(ChatFormatting.DARK_RED));
        }
        if (Helpers.isItem(this, IFCTags.Items.ICE_WEAPONS)) {
            tooltip.add(Component.translatable("dragon_sword_ice.hurt1").withStyle(ChatFormatting.GREEN));
            if (IafConfig.dragonWeaponIceAbility)
                tooltip.add(Component.translatable("dragon_sword_ice.hurt2").withStyle(ChatFormatting.AQUA));
        }
        if (Helpers.isItem(this, IFCTags.Items.LIGHTNING_WEAPONS)) {
            tooltip.add(Component.translatable("dragon_sword_lightning.hurt1").withStyle(ChatFormatting.GREEN));
            if (IafConfig.dragonWeaponLightningAbility)
                tooltip.add(Component.translatable("dragon_sword_lightning.hurt2").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {

        for(BoneStuff.bone_Tools items : BoneStuff.bone_Tools.values())
        {
            if(items.isBlood())
            {
                if(stack.is(IFCItems.BLOOD_TOOLS.get(BoneStuff.DRAGON_BONE).get(items).get()))
                {
                    return false;
                }
            }

        }
        return true;
    }

}
