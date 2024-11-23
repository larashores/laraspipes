package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.itempipe.ItemPipe;
import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;


public class ItemDepositorModels {
    public static void register(
        BlockModelProvider models,
        MultiPartBlockStateBuilder blockStateBuilder
    ) {
        var path = Registration.DEPOSITOR.getId().getPath();
        var frontModeBuilder = models.getBuilder("block/" + path + "_front");
        frontModeBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        frontModeBuilder.texture("all", Main.MOD_ID + ":block/" + path);
        frontModeBuilder.element()
            .from(5, 5, 5)
            .to(11, 11, 11)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#all"))
            .end();
        frontModeBuilder.element()
            .from(1, 0, 11)
            .to(15, 14, 17)
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
                .condition(ItemDepositorBlock.CONNECTED.get(direction), true);
        }
    }
}