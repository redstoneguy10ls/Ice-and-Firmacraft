package com.redstoneguy10ls.ifc.common.items;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.redstoneguy10ls.ifc.IFC;
import net.dries007.tfc.util.SelfTests;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class IFCTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IFC.MOD_ID);

/*
    public static final IFCTabs.CreativeTabHolder EVERYTHING_ELSE =
            register("everything_else", () -> new ItemStack(IFCBlocks.MINT.get()), IFCTabs::fillTab);
*/
    public static final IFCTabs.CreativeTabHolder COINS =
            register("coins", () -> new ItemStack(IafItemRegistry.DRAGONSCALES_RED.get()), IFCTabs::filltab);




    private static void filltab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for(Scales.Default scale : Scales.Default.values())
        {
            for(Scales.armours armours : new Scales.armours[]{
                    Scales.armours.HELMET,
                    Scales.armours.CHESTPLATE,
                    Scales.armours.GREAVES,
                    Scales.armours.BOOTS,
                    Scales.armours.UNFINISHED_HELMET,
                    Scales.armours.UNFINISHED_CHESTPLATE,
                    Scales.armours.UNFINISHED_GREAVES,
                    Scales.armours.UNFINISHED_BOOTS,
            })
            {
                accept(out,IFCItems.SCALE_ARMOURS, scale, armours);
            }
        }

        for(BoneStuff.bone_Tools items : BoneStuff.bone_Tools.values())
        {
            for(BoneStuff bloods : BoneStuff.values())
            {
                accept(out, IFCItems.BLOOD_TOOLS, bloods,items);
            }
            accept(out, IFCItems.BONE_TOOLS,items);
        }

    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            //TerraFirmaCraft.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            SelfTests.reportExternalError();
            return;
        }
        out.accept(reg.get());
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }
    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }


    private static IFCTabs.CreativeTabHolder register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("lithiccoins.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new IFCTabs.CreativeTabHolder(reg, displayItems);
    }

    private static <T> void consumeOurs(IForgeRegistry<T> registry, Consumer<T> consumer)
    {
        for (T value : registry)
        {
            if (Objects.requireNonNull(registry.getKey(value)).getNamespace().equals(IFC.MOD_ID))
            {
                consumer.accept(value);
            }
        }
    }

    public record CreativeTabHolder(RegistryObject<CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {}
}
