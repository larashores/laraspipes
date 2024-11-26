package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Registration;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import java.util.Set;

public class LootTables extends BlockLootSubProvider {
    public LootTables() {
        super(
            Set.of(
                Registration.DEPOSITOR_ITEM.get(),
                Registration.EXTRACTOR_ITEM.get(),
                Registration.PIPE_ITEM.get()
            ),
            FeatureFlags.REGISTRY.allFlags()
        );
    }

    @Override
    protected void generate() {
        dropSelf(Registration.DEPOSITOR.get());
        dropSelf(Registration.EXTRACTOR.get());
        dropSelf(Registration.PIPE.get());
    }
}
