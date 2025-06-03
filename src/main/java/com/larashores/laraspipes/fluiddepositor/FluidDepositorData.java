package com.larashores.laraspipes.fluiddepositor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.composite.PipeConnectorData;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Consumer;


/**
 * Datagen for the {@link FluidDepositorBlock} class.
 */
public class FluidDepositorData extends PipeConnectorData {
    /**
     * Creates the datagen class.
     */
    public FluidDepositorData() {
        super(Registration.FLUID_DEPOSITOR_BLOCK);
    }

    /**
     * Registers the language data for the {@link FluidDepositorBlock}.
     *
     * @param provider Provider to register language data with.
     * @param locale Locale to register.
     */
    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(REGISTERED_BLOCK.get(), "Fluid Depositor");
        }
    }

    /**
     * Registers recipes for the {@link FluidDepositorBlock}.
     *
     * @param consumer Consumer to register recipes in.
     */
    public void register(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, REGISTERED_BLOCK.get())
            .pattern(" h ")
            .pattern("iri")
            .pattern("i i")
            .define('i', Items.IRON_INGOT)
            .define('h', Items.HOPPER)
            .define('r', Items.REDSTONE_BLOCK)
            .unlockedBy("has_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
            .save(consumer);
    }
}