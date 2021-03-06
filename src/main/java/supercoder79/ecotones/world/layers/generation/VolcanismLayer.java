package supercoder79.ecotones.world.layers.generation;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import supercoder79.ecotones.util.noise.OpenSimplexNoise;
import supercoder79.ecotones.world.biome.special.BlessedSpringsBiome;
import supercoder79.ecotones.world.biome.special.HotSpringsBiome;
import supercoder79.ecotones.world.biome.special.SuperVolcanicBiome;
import supercoder79.ecotones.world.biome.special.VolcanicBiome;
import supercoder79.ecotones.world.layers.seed.SeedLayer;

public enum VolcanismLayer implements IdentitySamplingLayer, SeedLayer {
    INSTANCE;

    private OpenSimplexNoise volcanismNoise;

    @Override
    public int sample(LayerRandomnessSource context, int value) {
        return 0;
    }

    @Override
    public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
        int sample = parent.sample(x, z);

        if (context.nextInt(6) == 0) {
            double volcanism = volcanismNoise.sample(x / 5f, z / 5f) * 1.25;
            if (volcanism < -0.8) {
                return context.nextInt(10) == 0 ? Registry.BIOME.getRawId(SuperVolcanicBiome.INSTANCE) : Registry.BIOME.getRawId(VolcanicBiome.INSTANCE);
            }
            if (volcanism > 0.8) {
                return context.nextInt(10) == 0 ? Registry.BIOME.getRawId(BlessedSpringsBiome.INSTANCE) : Registry.BIOME.getRawId(HotSpringsBiome.INSTANCE);
            }
        }
        return sample;
    }


    @Override
    public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent, long seed) {
        volcanismNoise = new OpenSimplexNoise(seed);
        return this.create(context, parent);
    }
}
