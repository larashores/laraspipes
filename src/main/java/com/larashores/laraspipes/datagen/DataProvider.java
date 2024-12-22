package com.larashores.laraspipes.datagen;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.IGeneratedBlockState;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Consumer;

/**
 * DataProvider class that allows defining all datagen related methods for a block in a single class.
 */
public abstract class DataProvider {
    /**
     * Registers block states for the block.
     *
     * @param provider Provider to register block states with.
     *
     * @return Block state file.
     */
    public abstract IGeneratedBlockState register(BlockStateProvider provider);

    /**
     * Registers item models for the block.
     *
     * @param provider Provider to register item models with.
     */
    public void register(ItemModelProvider provider) { }

    /**
     * Registers translations for the block.
     *
     * @param provider Provider to register translations with.
     * @param locale Locale to register translations in.
     */
    public void register(LanguageProvider provider, String locale) { }

    /**
     * Registers recipes for the block.
     *
     * @param consumer Consumer to register recipes with.
     */
    public void register(Consumer<FinishedRecipe> consumer) { }

    /**
     * Registers loot tables for the block.
     *
     * @param provider Provider to register loot tables with.
     */
    public void register(LootTableCompositeProvider provider) { }

    /**
     * All blocks which a loot table should be defined for.
     *
     * @return All blocks.
     */
    public Iterable<Block> getKnownBlocks() { return List.of(); }
}