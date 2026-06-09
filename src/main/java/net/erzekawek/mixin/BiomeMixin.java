package net.erzekawek.mixin;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.*;

@Mixin(value = Biome.class, priority = 69420)
public abstract class BiomeMixin {
    @Shadow
    @Final
    private BiomeSpecialEffects specialEffects;

    @Shadow protected abstract int getGrassColorFromTexture();

    /**
     * @author
     * ErzeKawek
     * @reason
     * Idk
     */
    @Overwrite
    public int getGrassColor(double posX, double posZ) {
        int i = this.specialEffects.grassColorOverride().orElseGet(this::getGrassColorFromTexture);
        return this.specialEffects.grassColorModifier().modifyColor(posX, posZ, i);
    }
}
