package com.redstoneguy10ls.ifc.world;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockGoldPile;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.util.ShapeBuilder;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.github.alexthe666.iceandfire.world.gen.TypedFeature;
import com.github.alexthe666.iceandfire.world.gen.WorldGenCaveStalactites;
import com.github.alexthe666.iceandfire.world.gen.WorldGenDragonCave;
import com.mojang.serialization.Codec;
import com.redstoneguy10ls.ifc.config.IFCConfig;
import com.redstoneguy10ls.ifc.util.IFCHelpers;
import com.redstoneguy10ls.ifc.util.IFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.chunkdata.RockData;
import net.dries007.tfc.world.settings.RockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class IFCWorldGenDragonCaves extends Feature<NoneFeatureConfiguration> implements TypedFeature
{
    public ResourceLocation DRAGON_CHEST;
    public ResourceLocation DRAGON_MALE_CHEST;
    public WorldGenCaveStalactites CEILING_DECO;
    public BlockState PALETTE_BLOCK1;
    public BlockState PALETTE_BLOCK2;
    public TagKey<Block> dragonTypeOreTag;
    public int dragonType;
    public BlockState TREASURE_PILE;
    private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    public boolean isMale;

    protected IFCWorldGenDragonCaves(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldIn = context.level();
        RandomSource rand = context.random();
        BlockPos position = context.origin();
        if (rand.nextInt(Helpers.getValueOrDefault(IFCConfig.COMMON.generateDragonDenChance)) != 0) {
            return false;
        }
        isMale = rand.nextBoolean();
        ChunkPos chunkPos = worldIn.getChunk(position).getPos();

        final BlockPos pos = context.origin();
        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(context.level(), pos);
        final RockSettings rock = data.getRockData().getRock(pos.getX(), context.chunkGenerator().getMinY() + 1, pos.getZ());


        int j = 40;
        // Update the position so it doesn't go above the ocean floor
        for(int k = 0; k < 20; ++k) {
            for(int l = 0; l < 20; ++l) {
                j = Math.min(j, worldIn.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, position.getX() + k, position.getZ() + l));
            }
        }

        // Offset the position randomly
        j -= 20;
        j -= rand.nextInt(30);

        // If the cave generation point is too low
        if (j < worldIn.getMinBuildHeight() + 20) {
            return false;
        }
        // Center the position at the "middle" of the chunk
        position = new BlockPos((chunkPos.x << 4) + 8, j, (chunkPos.z << 4) + 8);
        int dragonAge = 75 + rand.nextInt(50);
        int radius = (int) (dragonAge * 0.2F) + rand.nextInt(4);
        generateCave(worldIn, radius, 3, position, rand,rock);
        EntityDragonBase dragon = createDragon(worldIn, rand, position, dragonAge);
        worldIn.addFreshEntity(dragon);
        return true;
    }
    public void generateCave(LevelAccessor worldIn, int radius, int amount, BlockPos center, RandomSource rand,RockSettings rock)
    {
        List<SphereInfo> sphereList = new ArrayList<>();
        sphereList.add(new SphereInfo(radius, center.immutable()));
        Stream<BlockPos> sphereBlocks = ShapeBuilder.start().getAllInCutOffSphereMutable(radius, radius / 2, center).toStream(false);
        Stream<BlockPos> hollowBlocks = ShapeBuilder.start().getAllInRandomlyDistributedRangeYCutOffSphereMutable(radius - 2, (int) ((radius - 2) * 0.75), (radius - 2) / 2, rand, center).toStream(false);
        //Get shells
        //Get hollows
        for (int i = 0; i < amount + rand.nextInt(2); i++) {
            Direction direction = HORIZONTALS[rand.nextInt(HORIZONTALS.length - 1)];
            int r = 2 * (int) (radius / 3F) + rand.nextInt(8);
            BlockPos centerOffset = center.relative(direction, radius - 2);
            sphereBlocks = Stream.concat(sphereBlocks, ShapeBuilder.start().getAllInCutOffSphereMutable(r, r, centerOffset).toStream(false));
            hollowBlocks = Stream.concat(hollowBlocks, ShapeBuilder.start().getAllInRandomlyDistributedRangeYCutOffSphereMutable(r - 2, (int) ((r - 2) * 0.75), (r - 2) / 2, rand, centerOffset).toStream(false));
            sphereList.add(new SphereInfo(r, centerOffset));
        }
        Set<BlockPos> shellBlocksSet = sphereBlocks.map(BlockPos::immutable).collect(Collectors.toSet());
        Set<BlockPos> hollowBlocksSet = hollowBlocks.map(BlockPos::immutable).collect(Collectors.toSet());
        shellBlocksSet.removeAll(hollowBlocksSet);

        //setBlocks
        createShell(worldIn, rand, shellBlocksSet,rock);
        //removeBlocks
        hollowOut(worldIn, hollowBlocksSet);
        //decorate
        decorateCave(worldIn, rand, hollowBlocksSet, sphereList, center);
        sphereList.clear();

    }
    public void createShell(LevelAccessor worldIn, RandomSource rand, Set<BlockPos> positions,RockSettings rockData) {
        ITagManager<Block> tagManager = ForgeRegistries.BLOCKS.tags();

        List<Block> richOres = getBlockList(tagManager, IFCTags.Blocks.DRAGON_CAVE_RICH_ORES);
        List<Block> normalOres = getBlockList(tagManager, IFCTags.Blocks.DRAGON_CAVE_NORMAL_ORES);
        List<Block> poorOres = getBlockList(tagManager, IFCTags.Blocks.DRAGON_CAVE_POOR_ORES);
        List<Block> minerals = getBlockList(tagManager, IFCTags.Blocks.DRAGON_CAVE_MINERALS_ORES);
        List<Block> dragonTypeOres = getBlockList(tagManager, dragonTypeOreTag);

        List<Block> customRichOres = new ArrayList<>();
        List<Block> customNormalOres = new ArrayList<>();
        List<Block> customPoorOres = new ArrayList<>();
        List<Block> customMinerals = new ArrayList<>();

        if(Helpers.getValueOrDefault(IFCConfig.COMMON.useRockType)) {
            for (DragonOres ores : DragonOres.values()) {
                if (ores.isGraded()) {
                    customRichOres.add(TFCBlocks.GRADED_ORES.get(IFCHelpers.getRock(rockData, rand)).get(Ore.valueOf(ores.name())).get(Ore.Grade.RICH).get());
                    customNormalOres.add(TFCBlocks.GRADED_ORES.get(IFCHelpers.getRock(rockData, rand)).get(Ore.valueOf(ores.name())).get(Ore.Grade.NORMAL).get());
                    customPoorOres.add(TFCBlocks.GRADED_ORES.get(IFCHelpers.getRock(rockData, rand)).get(Ore.valueOf(ores.name())).get(Ore.Grade.POOR).get());
                } else {
                    if (ores.dragonType() == 0) {
                        customMinerals.add(TFCBlocks.ORES.get(IFCHelpers.getRock(rockData, rand)).get(Ore.valueOf(ores.name())).get());
                    }
                }
            }
        }






        positions.forEach(blockPos -> {
            if (!(worldIn.getBlockState(blockPos).getBlock() instanceof BaseEntityBlock) && worldIn.getBlockState(blockPos).getDestroySpeed(worldIn, blockPos) >= 0) {
                boolean doOres = rand.nextInt(Helpers.getValueOrDefault(IFCConfig.COMMON.oreToStoneRatioForDragonCaves) + 1) == 0;


                if (doOres) {
                    Block toPlace = null;

                    if (!Helpers.getValueOrDefault(IFCConfig.COMMON.useRockType))
                    {
                        if (rand.nextBoolean()) {
                            toPlace = !dragonTypeOres.isEmpty() ? dragonTypeOres.get(rand.nextInt(dragonTypeOres.size())) : null;
                        } else {
                            double chance = rand.nextDouble();

                            if (!richOres.isEmpty() && chance <= 0.25) {
                                toPlace = richOres.get(rand.nextInt(richOres.size()));
                            } else if (!normalOres.isEmpty() && chance <= 0.65) {
                                toPlace = normalOres.get(rand.nextInt(normalOres.size()));
                            } else if (!poorOres.isEmpty() && chance <= 0.85) {
                                toPlace = poorOres.get(rand.nextInt(poorOres.size()));
                            } else if (!minerals.isEmpty()) {
                                toPlace = minerals.get(rand.nextInt(minerals.size()));
                            }
                        }
                    }
                    else
                    {
                        if(rand.nextBoolean())
                        {
                            for(DragonOres ores : DragonOres.values())
                            {
                                if(ores.dragonType() == dragonType)
                                {
                                    toPlace = TFCBlocks.ORES.get(IFCHelpers.getRock(rockData,rand)).get(Ore.valueOf(ores.name())).get();
                                }
                            }
                        }
                        else
                        {

                            double chance = rand.nextDouble();

                            if (!customRichOres.isEmpty() && chance <= 0.25) {
                                toPlace = customRichOres.get(rand.nextInt(customRichOres.size()));
                            } else if (!customNormalOres.isEmpty() && chance <= 0.65) {
                                toPlace = customNormalOres.get(rand.nextInt(customNormalOres.size()));
                            } else if (!customPoorOres.isEmpty() && chance <= 0.85) {
                                toPlace = customPoorOres.get(rand.nextInt(customPoorOres.size()));
                            } else if (!customMinerals.isEmpty()) {
                                toPlace = customMinerals.get(rand.nextInt(customMinerals.size()));
                            }

                        }
                    }

                    if (toPlace != null) {
                        worldIn.setBlock(blockPos, toPlace.defaultBlockState(), Block.UPDATE_CLIENTS);
                    } else {
                        worldIn.setBlock(blockPos, rand.nextBoolean() ? PALETTE_BLOCK1 : PALETTE_BLOCK2, Block.UPDATE_CLIENTS);
                    }
                } else {
                    worldIn.setBlock(blockPos, rand.nextBoolean() ? PALETTE_BLOCK1 : PALETTE_BLOCK2, Block.UPDATE_CLIENTS);
                }
            }
        });
    }

    private List<Block> getBlockList(final ITagManager<Block> tagManager, final TagKey<Block> tagKey) {
        if (tagManager == null) {
            return List.of();
        }

        return tagManager.getTag(tagKey).stream().toList();
    }

    public void hollowOut(LevelAccessor worldIn, Set<BlockPos> positions) {
        positions.forEach(blockPos -> {
            if (!(worldIn.getBlockState(blockPos).getBlock() instanceof BaseEntityBlock)) {
                worldIn.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        });
    }

    public void decorateCave(LevelAccessor worldIn, RandomSource rand, Set<BlockPos> positions, List<SphereInfo> spheres, BlockPos center) {
        for (SphereInfo sphere : spheres) {
            BlockPos pos = sphere.pos;
            int radius = sphere.radius;

            for (int i = 0; i < 15 + rand.nextInt(10); i++) {
                CEILING_DECO.generate(worldIn, rand, pos.above(radius / 2 - 1).offset(rand.nextInt(radius) - radius / 2, 0, rand.nextInt(radius) - radius / 2));
            }
        }

        positions.forEach(blockPos -> {
            if (blockPos.getY() < center.getY()) {
                BlockState stateBelow = worldIn.getBlockState(blockPos.below());

                if ((stateBelow.is(BlockTags.BASE_STONE_OVERWORLD) || stateBelow.is(IafBlockTags.DRAGON_ENVIRONMENT_BLOCKS)) && worldIn.getBlockState(blockPos).isAir()) {
                    setGoldPile(worldIn, blockPos, rand);
                }
            }
        });
    }
    public void setGoldPile(LevelAccessor world, BlockPos pos, RandomSource rand) {
        if (!(world.getBlockState(pos).getBlock() instanceof BaseEntityBlock)) {
            int chance = rand.nextInt(99) + 1;
            if (chance < 60) {
                int goldRand = Math.max(1, IafConfig.dragonDenGoldAmount) * (isMale ? 1 : 2);
                boolean generateGold = rand.nextInt(goldRand) == 0;
                world.setBlock(pos, generateGold ? TREASURE_PILE.setValue(BlockGoldPile.LAYERS, 1 + rand.nextInt(7)) : Blocks.AIR.defaultBlockState(), 3);
            } else if (chance == 61) {
                world.setBlock(pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, HORIZONTALS[rand.nextInt(3)]), Block.UPDATE_CLIENTS);

                if (world.getBlockState(pos).getBlock() instanceof ChestBlock) {
                    BlockEntity blockEntity = world.getBlockEntity(pos);

                    if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                        chestBlockEntity.setLootTable(isMale ? DRAGON_MALE_CHEST : DRAGON_CHEST, rand.nextLong());
                    }
                }
            }
        }
    }

    private EntityDragonBase createDragon(final WorldGenLevel worldGen, final RandomSource random, final BlockPos position, int dragonAge) {
        EntityDragonBase dragon = getDragonType().create(worldGen.getLevel());
        dragon.setGender(isMale);
        dragon.growDragon(dragonAge);
        dragon.setAgingDisabled(true);
        dragon.setHealth(dragon.getMaxHealth());
        dragon.setVariant(random.nextInt(4));
        dragon.absMoveTo(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5, random.nextFloat() * 360, 0);
        dragon.setInSittingPose(true);
        dragon.homePos = new HomePosition(position, worldGen.getLevel());
        dragon.setHunger(50);
        return dragon;
    }



    private static class SphereInfo {
        int radius;
        BlockPos pos;

        private SphereInfo(int radius, BlockPos pos) {
            this.radius = radius;
            this.pos = pos;
        }
    }

    public abstract EntityType<? extends EntityDragonBase> getDragonType();


    @Override
    public IafWorldData.FeatureType getFeatureType() {
        return IafWorldData.FeatureType.UNDERGROUND;
    }

    @Override
    public String getId() {
        return "dragon_cave";
    }
}
