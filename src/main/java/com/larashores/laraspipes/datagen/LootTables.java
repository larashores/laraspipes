package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Registration;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.List;

public class LootTables extends VanillaBlockLoot {
    @Override
    protected void generate() {
        dropSelf(Registration.DEPOSITOR.get());
        dropSelf(Registration.EXTRACTOR.get());
        dropSelf(Registration.PIPE.get());
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(
            Registration.DEPOSITOR.get(),
            Registration.EXTRACTOR.get(),
            Registration.PIPE.get()
        );
    }
}
