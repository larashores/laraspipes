package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Registration;
import net.minecraft.data.loot.packs.VanillaBlockLoot;

public class LootTables extends VanillaBlockLoot {
    @Override
    protected void generate() {
        super.generate();
        dropSelf(Registration.DEPOSITOR.get());
        dropSelf(Registration.EXTRACTOR.get());
        dropSelf(Registration.PIPE.get());
    }
}
