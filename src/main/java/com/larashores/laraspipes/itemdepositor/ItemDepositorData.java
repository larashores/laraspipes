package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.composite.ItemPipeConnectorData;
import com.larashores.laraspipes.datagen.LootTableCompositeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Consumer;


public class ItemDepositorData extends ItemPipeConnectorData {
    public ItemDepositorData() {
        super(Registration.DEPOSITOR_BLOCK);
    }

    public void register(ItemModelProvider provider) {
        provider.withExistingParent(
            Registration.DEPOSITOR_BLOCK.getId().getPath(),
            provider.modLoc("block/item_depositor")
        );
    }

    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(Registration.DEPOSITOR_BLOCK.get(), "Item Depositor");
        }
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Registration.DEPOSITOR_ITEM.get())
            .pattern(" h ")
            .pattern("iri")
            .pattern("i i")
            .define('i', Items.IRON_INGOT)
            .define('h', Items.HOPPER)
            .define('r', Items.REDSTONE_BLOCK)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);
    }

    public void register(LootTableCompositeProvider provider) {
        provider.dropSelf(Registration.DEPOSITOR_BLOCK.get());
    }

    public Iterable<Block> getKnownBlocks() {
        return List.of(Registration.DEPOSITOR_BLOCK.get());
    }
}