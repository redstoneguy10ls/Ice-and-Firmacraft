package com.redstoneguy10ls.ifc;

//import com.alekiponi.alekiships.common.entity.vehiclehelper.CleatEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.*;
import com.github.alexthe666.iceandfire.entity.props.ChainData;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.redstoneguy10ls.ifc.common.fluids.Blood;
import com.redstoneguy10ls.ifc.common.fluids.IFCFluids;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.capabilities.food.TFCFoodData;
import net.dries007.tfc.common.capabilities.forge.ForgingBonus;
import net.dries007.tfc.common.entities.EntityHelpers;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.misc.ThrownJavelin;
import net.dries007.tfc.common.items.FluidContainerItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.PhysicalDamageType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.alexthe666.iceandfire.entity.props.CapabilityHandler.ENTITY_DATA;

public class ForgeEventHandler {

    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(ForgeEventHandler::onLivingHurt);
        bus.addListener(ForgeEventHandler::onEntityInteract);


    }
    public static void init2()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        //bus.addListener(ForgeEventHandler::boat);
        //bus.addGenericListener(Entity.class, ForgeEventHandler::attachCapability);

    }
/*
    public static void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof CleatEntity)
            {
                event.addCapability(ENTITY_DATA, new EntityDataProvider());
            }
    }

 */

    public static void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event)
    {
        final Player player = event.getEntity();
        final Entity target = event.getTarget();
        ItemStack stack = player.getItemInHand(event.getHand());

        //can the item hold a fluid
        final IFluidHandler handler = stack.getCapability(Capabilities.FLUID_ITEM).resolve().orElse(null);
        //checks if the item can hold fluids and its not at it's not full
        if(handler != null && handler.getFluidInTank(0).getAmount() < handler.getTankCapacity(0))
        {

            if(target instanceof EntityDragonBase dragon && IafConfig.dragonDropBlood)
            {
                //code from IAF on how much blood to give
                int lastDeathStage = Math.min(dragon.getAgeInDays() / 5, 25);
                if (dragon.getDeathStage() < lastDeathStage / 2)
                {
                    //fire dragons
                    if (dragon instanceof EntityFireDragon) {
                        //create a fluid stack of dragons blood to fill
                        final FluidStack fire = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.FIRE).getSource(), 100);
                        //fill
                        handler.fill(fire, IFluidHandler.FluidAction.EXECUTE);
                    }
                    //ice
                    if (dragon instanceof EntityIceDragon) {
                        //create a fluid stack of dragons blood to fill
                        final FluidStack ice = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.ICE).getSource(), 100);
                        //fill
                        handler.fill(ice, IFluidHandler.FluidAction.EXECUTE);

                    }
                    //lightning
                    if (dragon instanceof EntityLightningDragon) {
                        //create a fluid stack of dragons blood to fill
                        final FluidStack lightning = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.LIGHTNING).getSource(), 100);
                        //fill
                        handler.fill(lightning, IFluidHandler.FluidAction.EXECUTE);
                    }

                    event.getLevel().playSound(null,event.getPos(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS,0.6f,1.0f);
                    dragon.setDeathStage(dragon.getDeathStage() + 1);
                }
            }
        }
    }




    public static void onLivingHurt(LivingHurtEvent event)
    {
        final Entity source = event.getSource().getDirectEntity();
        final LivingEntity target = event.getEntity();
        float amount = event.getAmount();
        if(source instanceof ThrownJavelin javelin)
        {
            //fire javelin
            if(javelin.getTags().contains("fire"))
            {
                if(target instanceof EntityIceDragon)
                {
                    event.setAmount(amount+8);
                }
                target.setSecondsOnFire(5);
                target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
            }
            //ice javelin
            if(javelin.getTags().contains("ice"))
            {
                if(target instanceof EntityFireDragon)
                {
                    event.setAmount(amount+8);
                }
                EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 200));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
                target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
            }
            //lightning javelin
            if(javelin.getTags().contains("lightning"))
            {

                if(true)
                {
                    LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(target.level());
                    lightningboltentity.getTags().add(ServerEvents.BOLT_DONT_DESTROY_LOOT);
                    lightningboltentity.getTags().add(source.getStringUUID());
                    lightningboltentity.moveTo(target.position());
                    if (!target.level().isClientSide) {
                        target.level().addFreshEntity(lightningboltentity);
                    }
                }
                if(target instanceof EntityFireDragon || target instanceof EntityIceDragon)
                {
                    event.setAmount(amount+4);
                }
                target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());

            }
        }
    }

/*
    public static void boat(final PlayerInteractEvent.EntityInteract event)
    {
        //if (!(event.getTarget() instanceof final LivingEntity living)) return;
        //if (!living.isPassenger()) return;
        //if (!event.getEntity().isSecondaryUseActive()) return;
        //System.out.println("test");
        if(event.getTarget() instanceof CleatEntity cleat)
        {
            //System.out.println(EntityDataProvider.getCapability(cleat).isPresent());

            if(Helpers.isItem(event.getEntity().getItemInHand(event.getHand()), IafItemRegistry.CHAIN.get()))
            {
               event.getLevel().explode(cleat,cleat.getX(),cleat.getY(),cleat.getZ(),10, Level.ExplosionInteraction.MOB);

            }

        }
    }

 */
/*
    public static void onLivingHurt(LivingHurtEvent event)
    {
        final Entity attackerEntity = event.getSource().getEntity();

        if(event.getEntity() instanceof Player player && attackerEntity instanceof LivingEntity livingEntity)
        {
            System.out.println(event.getSource().getEntity());
            System.out.println(event.getSource());
        }


    }

 */
}



/*
old dragon blood code

    public static void onEntityInteract(PlayerInteractEvent.@NotNull EntityInteract event)
    {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack stack = player.getItemInHand(event.getHand());

        //can the item hold a fluid
        final IFluidHandler handler = stack.getCapability(Capabilities.FLUID_ITEM).resolve().orElse(null);
        //checks if the item can hold fluids and its not at it's not full
        if(handler != null && handler.getFluidInTank(0).getAmount() < handler.getTankCapacity(0))
        {
            //checks if the target or the thing you're clicking on is a dragon, or a part of a dragon
            //as well as if the iaf config allows you to get blood
            if(target instanceof EntityDragonPart && IafConfig.dragonDropBlood)
            {
                //create a bounding box
                AABB area = new AABB(event.getPos().offset(-5,-5,-5),event.getPos().offset(5,5,5));
                //get every entity in said bounding box excluding the dragon part
                List<Entity> ent = event.getLevel().getEntities(target,area);
                //for each loop of every entity in the list
                for(Entity ents : ent)
                {
                    //god im commenting like its getting graded
                    //checks if the current entity from the list is a dragon base
                    if(ents instanceof EntityDragonBase dragon)
                    {
                        //code from IAF on how much blood to give
                        int lastDeathStage = Math.min(dragon.getAgeInDays() / 5, 25);
                        //fire dragons
                        if (dragon instanceof EntityFireDragon)
                        {
                            //this is to see if it turns to bones
                            if (dragon.getDeathStage() < lastDeathStage / 2)
                            {
                                //create a fluid stack of dragons blood to fill
                                final FluidStack fire = new FluidStack(IFCFluids.DRAGON_BLOOD.get(Blood.FIRE).getSource(), 100);
                                //fill
                                handler.fill(fire, IFluidHandler.FluidAction.EXECUTE);
                            }

                        }
                    }
                }

            }
        }
    }
 */
