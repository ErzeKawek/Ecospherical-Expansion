package com.Apothic0n.api.biome.features.trunk_placers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class StraightBranchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<StraightBranchingTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> StraightBranchingTrunkPlacer.trunkPlacerParts(instance).and(
            (IntProviders.codec(-42, 42).fieldOf("max_branch_height")).forGetter(straightBranchingTrunkPlacer -> straightBranchingTrunkPlacer.maxBranchHeight)
    ).apply(instance, StraightBranchingTrunkPlacer::new));

    private final IntProvider maxBranchHeight;
    public StraightBranchingTrunkPlacer(int i, int j, int k, IntProvider maxBranchHeight) {
        super(i, j, k);
        this.maxBranchHeight = maxBranchHeight;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return EcoTrunkPlacerType.STRAIGHT_BRANCHING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(WorldGenLevel level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        BlockPos below = pos.below();
        BlockState currentState = level.getBlockState(below);
        if (currentState.canBeReplaced()) {
            level.setBlock(below, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
        }
        int maxHeight = maxBranchHeight.sample(random);
        maxHeight = freeTreeHeight-maxHeight;
        for (int i = 0; i < freeTreeHeight; ++i) {
            this.placeLog(level, blockSetter, random, pos.above(i), config);
            if (i > 2 && maxHeight > i) {
                int randomNumber = (int) (Math.random() * (30) + 1);
                if (randomNumber < 2) {
                    placeBranch(level, blockSetter, random, pos.above(i).north(), config, Direction.Axis.Z);
                } else if (randomNumber < 3) {
                    placeBranch(level, blockSetter, random, pos.above(i).east(), config, Direction.Axis.X);
                } else if (randomNumber < 4) {
                    placeBranch(level, blockSetter, random, pos.above(i).south(), config, Direction.Axis.Z);
                } else if (randomNumber < 5) {
                    placeBranch(level, blockSetter, random, pos.above(i).west(), config, Direction.Axis.X);
                }
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }

    private void placeBranch(WorldGenLevel level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, Direction.Axis axis) {
        if (level.isStateAtPosition(pos.below(), BlockBehaviour.BlockStateBase::isAir) && level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir)) {
            this.placeLog(level, blockSetter, random, pos, config, blockState -> blockState.trySetValue(RotatedPillarBlock.AXIS, axis));
        }
    }
}
