package net.erzekawek.api.biome.features.decorators;

import net.erzekawek.TerrasphericalExpansion;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.treedecorators.*;

public abstract class EcoTreeDecoratorType {
    public static final TreeDecoratorType<MushroomsDecorator> MUSHROOMS = EcoTreeDecoratorType.register("mushrooms", MushroomsDecorator.CODEC);

    public static final TreeDecoratorType<CaveVineDecorator> CAVE_VINES = EcoTreeDecoratorType.register("cave_vines", CaveVineDecorator.CODEC);
    private static <P extends TreeDecorator> TreeDecoratorType<P> register(String key, Codec<P> codec) {
        return Registry.register(
                BuiltInRegistries.TREE_DECORATOR_TYPE,
                Identifier.fromNamespaceAndPath(TerrasphericalExpansion.MODID, key),
                new TreeDecoratorType<>(codec.fieldOf("value")) // Convert Codec to MapCodec
        );
    }

    public static void init() {

    }
}