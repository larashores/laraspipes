package com.larashores.laraspipes.datagen;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Consumer;

public class DataProvider {
    public void register(BlockStateProvider provider) { }
    public void register(ItemModelProvider provider) { }
    public void register(LanguageProvider provider, String locale) { }
    public void register(Consumer<FinishedRecipe> consumer) { }
    public void register(LootTableCompositeProvider provider) { }
    public Iterable<Block> getKnownBlocks() { return List.of(); }
}