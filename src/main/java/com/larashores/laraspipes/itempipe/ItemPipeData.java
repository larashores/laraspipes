package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.datagen.DataProvider;
import com.larashores.laraspipes.datagen.LootTableCompositeProvider;
import com.larashores.laraspipes.utils.Utils;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Consumer;

public class ItemPipeData extends DataProvider {
    public void register(BlockStateProvider provider) {
        var path = Registration.PIPE.getId().getPath();
        var models = provider.models();
        var centerBuilder = models.getBuilder("block/" + path + "_center");
        centerBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        centerBuilder.texture("all", Main.MOD_ID + ":block/" + path);
        centerBuilder.element()
            .from(5, 5, 5)
            .to(11, 11, 11)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#all"))
            .end();
        var sideBuilder = models.getBuilder("block/" + path + "_side");
        sideBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        sideBuilder.texture("all", Main.MOD_ID + ":block/" + path);
        sideBuilder.element()
            .from(5, 5, 11)
            .to(11, 11, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#all"))
            .end();

        var blockStateBuilder = provider.getMultipartBuilder(Registration.PIPE.get());
        blockStateBuilder
            .part()
            .modelFile(centerBuilder)
            .addModel();
        for (var direction : Direction.values()) {
            var rotation = Utils.getRotation(direction);
            blockStateBuilder
                .part()
                .modelFile(sideBuilder)
                .rotationX(rotation.x())
                .rotationY(rotation.y())
                .addModel()
                .condition(ItemPipeBlock.CONNECTED.get(direction), true);
        }
    }

    public void register(ItemModelProvider provider) {
        provider.withExistingParent(
            Registration.PIPE.getId().getPath(),
            provider.modLoc("block/item_pipe_center")
        );
    }

    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(Registration.PIPE.get(), "Item Pipe");
        }
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Registration.PIPE_ITEM.get(), 4)
            .pattern("iri")
            .define('i', Items.IRON_INGOT)
            .define('r', Items.REDSTONE)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);
    }

    public void register(LootTableCompositeProvider provider) {
        provider.dropSelf(Registration.PIPE.get());
    }

    public Iterable<Block> getKnownBlocks() {
        return List.of(Registration.PIPE.get());
    }
}