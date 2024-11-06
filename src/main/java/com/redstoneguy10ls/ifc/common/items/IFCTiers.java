package com.redstoneguy10ls.ifc.common.items;

import com.github.alexthe666.iceandfire.item.DragonSteelTier;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.ToolTier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.dries007.tfc.common.TFCTiers.*;

public final class IFCTiers {

    public static final Tier BONE = register("bone", List.of(BRONZE, BISMUTH_BRONZE, BLACK_BRONZE, Tiers.IRON), List.of(Tiers.DIAMOND), TFCTags.Blocks.NEEDS_WROUGHT_IRON_TOOL, 4, 2000, 10.0f, 4.5f, 22);
    public static final Tier FIRE_STEEL = register("fire_steel", RED_STEEL, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 6, 8000, 15.0f, 21.0f, 22);
    public static final Tier ICE_STEEL = register("ice_steel", BLUE_STEEL, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 6, 8000, 15.0f, 21.0f, 22);
    public static final Tier LIGHTNING_STEEL = register("lightning_steel", BLUE_STEEL, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 6, 8000, 15.0f, 21.0f, 22);
    public static final Tier MYTHRIL = register("mythril", RED_STEEL, null, TFCTags.Blocks.NEEDS_COLORED_STEEL_TOOL, 6, 8000, 15.0f, 21.0f, 22);



    private static Tier register(String name, Tier before, @Nullable Tier after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        return register(name, List.of(before), after == null ? List.of() : List.of(after), tag, level, uses, speed, damage, enchantmentValue);
    }

    private static Tier register(String name, List<Object> before, List<Object> after, TagKey<Block> tag, int level, int uses, float speed, float damage, int enchantmentValue)
    {
        final Tier tier = new ToolTier(name, level, uses, speed, damage, enchantmentValue, tag, () -> Ingredient.EMPTY);
        if (!Helpers.BOOTSTRAP_ENVIRONMENT) TierSortingRegistry.registerTier(tier, IFCHelpers.identifier(name), before, after);
        return tier;
    }
}
