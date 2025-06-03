package com.larashores.laraspipes.composite;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.datagen.DataProvider;
import com.larashores.laraspipes.datagen.LootTableCompositeProvider;
import com.larashores.laraspipes.network.PipeNetworkBlock;
import com.larashores.laraspipes.itempipe.ItemPipeBlock;
import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Objects;


/**
 * Implements shared datagen methods used by {@link PipeNetworkBlock}.
 */
public class PipeComponentData extends DataProvider {
    /** Block that datagen is registered with. */
    protected final RegistryObject<Block> REGISTERED_BLOCK;

    /**
     * Creates the datagen class.
     *
     * @param block The block to create datagen for.
     */
    public PipeComponentData(RegistryObject<Block> block) {
        REGISTERED_BLOCK = block;
    }

    /**
     * Registers the "connection" block states shared by {@link PipeNetworkBlock}s.
     *
     * @param provider Provider to register block states with.
     *
     * @return Model builder that additional models can be registered with.
     */
    public MultiPartBlockStateBuilder register(BlockStateProvider provider) {
        var path = Objects.requireNonNull(REGISTERED_BLOCK.getId()).getPath();
        var models = provider.models();

        var modelBuilder = models.getBuilder("block/" + path + "_side");
        modelBuilder.parent(models.getExistingFile(ResourceLocation.fromNamespaceAndPath("minecraft", "block/cube_all")));
        modelBuilder.texture("particle", Main.MOD_ID + ":block/" + path + "_pipe");
        modelBuilder.texture("pipe", Main.MOD_ID + ":block/" + path + "_pipe");
        modelBuilder.element()
            .from(5, 5, 11)
            .to(11, 11, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#pipe"))
            .end();

        var blockStateBuilder = provider.getMultipartBuilder(REGISTERED_BLOCK.get());
        for (var direction : Direction.values()) {
            var rotation = Utils.getRotation(direction);
            blockStateBuilder
                .part()
                .modelFile(modelBuilder)
                .rotationX(rotation.x())
                .rotationY(rotation.y())
                .addModel()
                .condition(ItemPipeBlock.CONNECTED.get(direction), true);
        }
        return blockStateBuilder;
    }

    /**
     * Registers the item model for the block.
     *
     * @param provider Provider to register item models with.
     */
    public void register(ItemModelProvider provider) {
        var path = Objects.requireNonNull(REGISTERED_BLOCK.getId()).getPath();
        provider.withExistingParent(path, provider.modLoc("block/" + path));
    }

    /**
     * Registers a loot table such that the registered block drops itself.
     *
     * @param provider Provider to register loot tables with.
     */
    public void register(LootTableCompositeProvider provider) {
        provider.dropSelf(REGISTERED_BLOCK.get());
    }

    /**
     * Declares that a loot tables should be defined for the registered block.
     *
     * @return List with only the registered block in it.
     */
    public Iterable<Block> getKnownBlocks() {
        return List.of(REGISTERED_BLOCK.get());
    }
}
