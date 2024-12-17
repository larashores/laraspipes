package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.composite.ItemPipeComponentData;
import com.larashores.laraspipes.datagen.LootTableCompositeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Consumer;

public class ItemPipeData extends ItemPipeComponentData {
    public ItemPipeData() {
        super(Registration.PIPE_BLOCK);
    }

    public MultiPartBlockStateBuilder register(BlockStateProvider provider) {
        var path = REGISTERED_BLOCK.getId().getPath();
        var models = provider.models();

        var modelBuilder = models.getBuilder("block/" + path);
        modelBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        modelBuilder.texture("pipe", Main.MOD_ID + ":block/" + path + "_pipe");
        modelBuilder.element()
            .from(5, 5, 5)
            .to(11, 11, 11)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#pipe"))
            .end();

        var blockStateBuilder = super.register(provider);
        blockStateBuilder.part().modelFile(modelBuilder).addModel();
        return blockStateBuilder;
    }

    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(REGISTERED_BLOCK.get(), "Item Pipe");
        }
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, REGISTERED_BLOCK.get(), 4)
            .pattern("iri")
            .define('i', Items.IRON_INGOT)
            .define('r', Items.REDSTONE)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);
    }
}