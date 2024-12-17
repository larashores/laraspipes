package com.larashores.laraspipes.itemextractor;

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


public class ItemExtractorData extends ItemPipeConnectorData {
    public ItemExtractorData() {
        super(Registration.EXTRACTOR_BLOCK);
    }

    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(REGISTERED_BLOCK.get(), "Item Extractor");
        }
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, REGISTERED_BLOCK.get())
            .pattern("i i")
            .pattern("iri")
            .pattern(" h ")
            .define('i', Items.IRON_INGOT)
            .define('h', Items.HOPPER)
            .define('r', Items.REDSTONE_BLOCK)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);
    }
}