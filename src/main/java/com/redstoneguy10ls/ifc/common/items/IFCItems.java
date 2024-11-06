package com.redstoneguy10ls.ifc.common.items;

import com.eerussianguy.firmalife.common.blocks.FLFluids;
import com.eerussianguy.firmalife.common.util.ExtraFluid;
import com.eerussianguy.firmalife.common.util.FLMetal;
import com.redstoneguy10ls.ifc.common.fluids.Blood;
import com.redstoneguy10ls.ifc.common.fluids.IFCFluids;
import com.redstoneguy10ls.ifc.common.fluids.Metals;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static com.redstoneguy10ls.ifc.IFC.MOD_ID;

public class IFCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);


    public static final Map<BoneStuff, Map<BoneStuff.bone_Tools, RegistryObject<Item>>> BLOOD_TOOLS = Helpers.mapOfKeys(BoneStuff.class, bone ->
            Helpers.mapOfKeys(BoneStuff.bone_Tools.class, BoneStuff.bone_Tools::isBlood, tool->
                    register("bone/"+tool.name()+ "/" + bone.name(),() -> tool.create(bone) )));

    public static final Map<BoneStuff.bone_Tools, RegistryObject<Item>> BONE_TOOLS = Helpers.mapOfKeys(BoneStuff.bone_Tools.class,
            tool-> !tool.isBlood(), tool->
                    register("bone/"+tool.name()+ "/" + BoneStuff.DRAGON_BONE.name(),() -> tool.create(BoneStuff.DRAGON_BONE) ));

    public static final Map<IFCMetals.Default, Map<IFCMetals.ItemType, RegistryObject<Item>>> MYTHICITEMS = Helpers.mapOfKeys(IFCMetals.Default.class, mats ->
            Helpers.mapOfKeys(IFCMetals.ItemType.class, type -> type.has(mats), type->
                    register("material/" + type.name()+ "/" + mats.name(), () -> type.create(mats))));

    public static final Map<Scales.Default, Map<Scales.armours, RegistryObject<Item>>> SCALE_ARMOURS =
            Helpers.mapOfKeys(Scales.Default.class, scale ->
                    Helpers.mapOfKeys(Scales.armours.class, armours -> register(
                            "scale/"+armours.name()+"/"+scale.name(), () -> armours.create(scale)
                    )));


    public static final Map<Metals, RegistryObject<BucketItem>> METAL_FLUID_BUCKETS = Helpers.mapOfKeys(Metals.class, metal ->
            register("bucket/metal/" + metal.name(),
                    () -> new BucketItem(IFCFluids.METALS.get(metal).source(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );

    public static final Map<Blood, RegistryObject<BucketItem>> BLOOD_BUCKET = Helpers.mapOfKeys(Blood.class, fluid ->
            register("bucket/" + fluid.getId(), () -> new BucketItem(IFCFluids.DRAGON_BLOOD.get(fluid).source(),
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );

    /*
    public static final Map<TopDies, Map<Metal.Default, RegistryObject<Item>>> TOP_DIE =
            Helpers.mapOfKeys(TopDies.class, stamps ->
                    Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasTools, metals -> register("top_die/"+stamps.name()+"/"+metals.name(),
                             () -> new stampItem(metals.toolTier(), new Item.Properties().rarity(metals.getRarity()))))
            );

     */

    //public static final RegistryObject<Item> COIN_PURSE = register("coin_purse", () -> new coinPurseItem(new Item.Properties().stacksTo(1)));



    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
