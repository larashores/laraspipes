package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.composite.ItemPipeConnectorData;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Consumer;

/**
 * Datagen for the {@link ItemExtractorBlock}.
 */
public class ItemExtractorData extends ItemPipeConnectorData {
    /**
     * Creates the datagen class.
     */
    public ItemExtractorData() {
        super(Registration.EXTRACTOR_BLOCK);
    }

    /**
     * Registers the language data for the {@link ItemExtractorBlock}.
     *
     * @param provider Provider to register language data with.
     * @param locale Locale to register.
     */
    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(REGISTERED_BLOCK.get(), "Item Extractor");
        }
    }

    /**
     * Registers recipes for the {@link ItemExtractorBlock}.
     *
     * @param consumer Consumer to register recipes in.
     */
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