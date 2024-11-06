package com.redstoneguy10ls.ifc;

import com.mojang.logging.LogUtils;
import com.redstoneguy10ls.ifc.common.blockentities.IFCBlockEntities;
import com.redstoneguy10ls.ifc.common.blocks.IFCBlocks;
import com.redstoneguy10ls.ifc.common.fluids.IFCFluids;
import com.redstoneguy10ls.ifc.common.items.IFCItems;
import com.redstoneguy10ls.ifc.common.recipes.IFCRecipeSerializers;
import com.redstoneguy10ls.ifc.common.recipes.IFCRecipeTypes;
import com.redstoneguy10ls.ifc.config.IFCConfig;
import com.redstoneguy10ls.ifc.common.items.IFCTabs;
import com.redstoneguy10ls.ifc.world.IFCWorldRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IFC.MOD_ID)
public class IFC
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ifc";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public IFC()
    {
        IFCConfig.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        IFCFluids.FLUIDS.register(bus);
        IFCItems.ITEMS.register(bus);
        IFCTabs.CREATIVE_TABS.register(bus);
        IFCBlocks.BLOCKS.register(bus);
        IFCBlockEntities.BLOCK_ENTITIES.register(bus);
        IFCWorldRegistry.FEATURES.register(bus);

        ForgeEventHandler.init();

        if(ModList.get().isLoaded("firmaciv"))
        {
            ForgeEventHandler.init2();
        }
        IFCRecipeTypes.RECIPE_TYPES.register(bus);
        IFCRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
        //LCSounds.SOUNDS.register(bus);
        //LCContainerTypes.CONTAINERS.register(bus);



        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            //ClientEventHandler.init();
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ItemBlockRenderTypes.setRenderLayer(IFCBlocks.ALCHEMY_BLOCK.get(), RenderType.translucent());
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }


}
