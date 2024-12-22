package com.larashores.laraspipes.datagen;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;


/**
 * Provider that allows defining loot tables in multiple {@link DataProvider}s instead of a single monolithic
 * provider. Each {@link DataProvider} should implement the {@link DataProvider#register(LootTableCompositeProvider)}
 * method to register its loot tables in the composite provider.
 */
public class LootTableCompositeProvider extends VanillaBlockLoot implements Supplier<LootTableSubProvider> {
    private final Iterable<DataProvider> providers;

    /**
     * Creates the composite provider.
     *
     * @param providers Component providers to register with the composite provider.
     */
    public LootTableCompositeProvider(Iterable<DataProvider> providers) {
        super();
        this.providers = providers;
    }

    /**
     * This provider is both a LootTableSubProvider and a supplier of the same type, so this method returns the provider
     * itself so it can act as provider of itself.
     *
     * @return The provider.
     */
    public LootTableSubProvider get() {
        return this;
    }

    /**
     * Generates the loot tables defined by the component providers.
     */
    @Override
    protected void generate() {
        for (var provider: providers) {
            provider.register(this);
        }
    }

    /**
     * Returns all blocks defined by the component providers which there is a loot table defined for.
     *
     * @return Iterable of all blocks.
     */
    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return StreamSupport.stream(providers.spliterator(), false)
            .flatMap(provider -> StreamSupport.stream(provider.getKnownBlocks().spliterator(), false))
            ::iterator;
    }

    /**
     * Registers a loot table for a block such that when destroyed it drops itself. Redefines this protected method from
     * the superclass as public so that {@link DataProvider}s can call it.
     *
     * @param block The block that should drop itself.
     */
    @Override
    public void dropSelf(Block block) {
        super.dropSelf(block);
    }
}
