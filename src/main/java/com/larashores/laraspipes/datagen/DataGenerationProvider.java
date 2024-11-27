package com.larashores.laraspipes.datagen;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;

public class DataGenerationProvider {
    public void register(BlockStateProvider provider) { }
    public void register(ItemModelProvider provider) { }
    public void register(LanguageProvider provider, String locale) { }
}
