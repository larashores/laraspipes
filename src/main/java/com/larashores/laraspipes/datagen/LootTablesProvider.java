package com.larashores.laraspipes.datagen;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LootTablesProvider extends VanillaBlockLoot implements Supplier<LootTableSubProvider> {
    private final DataGenerationProvider[] providers;

    public LootTablesProvider(DataGenerationProvider[] providers) {
        super();
        this.providers = providers;
    }

    public LootTableSubProvider get() {
        return this;
    }

    @Override
    protected void generate() {
        for (var provider: providers) {
            provider.register(this);
        }
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Arrays.stream(providers)
            .flatMap(provider -> StreamSupport.stream(provider.getKnownBlocks().spliterator(), false))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void dropSelf(Block p_249181_) {
        super.dropSelf(p_249181_);
    }
}
