package com.larashores.laraspipes.itemextractor;

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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Consumer;


public class ItemExtractorData extends DataProvider {
    public void register(BlockStateProvider provider) {
        var path = Registration.EXTRACTOR_BLOCK.getId().getPath();
        var models = provider.models();
        var frontModeBuilder = models.getBuilder("block/" + path + "_front");
        frontModeBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        frontModeBuilder.texture("back", Main.MOD_ID + ":block/" + path + "_back");
        frontModeBuilder.texture("front", Main.MOD_ID + ":block/" + path + "_front");
        frontModeBuilder.texture("pipe", Main.MOD_ID + ":block/" + path + "_pipe");
        frontModeBuilder.texture("particle", Main.MOD_ID + ":block/" + path + "_pipe");
        frontModeBuilder.texture("vertical", Main.MOD_ID + ":block/" + path + "_side_vertical");
        frontModeBuilder.texture("horizontal", Main.MOD_ID + ":block/" + path + "_side_horizontal");
        frontModeBuilder.element()
            .from(5, 5, 5)
            .to(11, 11, 11)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#pipe"))
            .end();
        frontModeBuilder.element()
            .from(1, 0, 11)
            .to(15, 14, 17)
            .allFaces(
                (direction, faceBuilder) -> {
                    if (direction == Direction.SOUTH) {
                        faceBuilder.texture("#front");
                    } else if (direction == Direction.NORTH) {
                        faceBuilder.texture("#back");
                    } else if (direction == Direction.UP) {
                        faceBuilder.uvs(0, 0, 14, 6);
                        faceBuilder.texture("#vertical");
                    } else if (direction == Direction.DOWN) {
                        faceBuilder.uvs(0, 10, 14, 16);
                        faceBuilder.texture("#vertical");
                    } else if (direction == Direction.WEST) {
                        faceBuilder.uvs(0, 0, 6, 14);
                        faceBuilder.texture("#horizontal");
                    } else if (direction == Direction.EAST) {
                        faceBuilder.uvs(10, 0, 16, 14);
                        faceBuilder.texture("#horizontal");
                    }
                }
            )
            .end();

        var sideBuilder = models.getBuilder("block/" + path + "_side");
        sideBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        sideBuilder.texture("pipe", Main.MOD_ID + ":block/" + path + "_pipe");
        frontModeBuilder.texture("particle", Main.MOD_ID + ":block/" + path + "_pipe");
        sideBuilder.element()
            .from(5, 5, 11)
            .to(11, 11, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#pipe"))
            .end();

        var blockStateBuilder = provider.getMultipartBuilder(Registration.EXTRACTOR_BLOCK.get());
        for (var direction : Direction.values()) {
            var rotation = Utils.getRotation(direction);
            blockStateBuilder
                .part()
                .modelFile(frontModeBuilder)
                .rotationX(rotation.x())
                .rotationY(rotation.y())
                .addModel()
                .condition(BlockStateProperties.FACING, direction);

            blockStateBuilder
                .part()
                .modelFile(sideBuilder)
                .rotationX(rotation.x())
                .rotationY(rotation.y())
                .addModel()
                .condition(ItemExtractorBlock.CONNECTED.get(direction), true);
        }
    }

    public void register(ItemModelProvider provider) {
        provider.withExistingParent(
            Registration.EXTRACTOR_BLOCK.getId().getPath(),
            provider.modLoc("block/item_extractor_front")
        );
    }

    public void register(LanguageProvider provider, String locale) {
        if (locale.equals("en_us")) {
            provider.add(Registration.EXTRACTOR_BLOCK.get(), "Item Extractor");
        }
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Registration.EXTRACTOR_ITEM.get())
            .pattern("i i")
            .pattern("iri")
            .pattern(" h ")
            .define('i', Items.IRON_INGOT)
            .define('h', Items.HOPPER)
            .define('r', Items.REDSTONE_BLOCK)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);
    }

    public void register(LootTableCompositeProvider provider) {
        provider.dropSelf(Registration.EXTRACTOR_BLOCK.get());
    }

    public Iterable<Block> getKnownBlocks() {
        return List.of(Registration.EXTRACTOR_BLOCK.get());
    }
}