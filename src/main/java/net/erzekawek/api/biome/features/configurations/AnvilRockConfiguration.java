package net.erzekawek.api.biome.features.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AnvilRockConfiguration implements FeatureConfiguration {
    public static final Codec<AnvilRockConfiguration> CODEC = RecordCodecBuilder.create((fields) -> {
        return fields.group(BlockStateProvider.CODEC.fieldOf("material").forGetter(AnvilRockConfiguration::getMaterial
        ), IntProviders.codec(1, 3).fieldOf("radius").forGetter((v) -> {
            return v.radius;
        }), IntProviders.codec(3, 64).fieldOf("height").forGetter((v) -> {
            return v.height;
        }), IntProviders.codec(0, 16).fieldOf("stretch").forGetter((v) -> {
            return v.stretch;
        })).apply(fields, AnvilRockConfiguration::new);
    });

    public final BlockStateProvider material;
    private final IntProvider radius;
    private final IntProvider height;
    private final IntProvider stretch;

    public AnvilRockConfiguration(BlockStateProvider material, IntProvider radius, IntProvider height, IntProvider stretch) {
        this.material = material;
        this.radius = radius;
        this.height = height;
        this.stretch = stretch;
    }

    public BlockStateProvider getMaterial() {return this.material;}
    public IntProvider getRadius() {return this.radius;}
    public IntProvider getHeight() {return this.height;}
    public IntProvider getStretch() {return this.stretch;}
}