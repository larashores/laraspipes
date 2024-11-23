package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;


public class ItemExtractorModels {
    public static void register(
        BlockModelProvider models,
        MultiPartBlockStateBuilder blockStateBuilder
    ) {
        var path = Registration.EXTRACTOR.getId().getPath();
        var modelBuilder = models.getBuilder("block/" + path);
        modelBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        modelBuilder.texture("all", Main.MOD_ID + ":block/" + path);
        modelBuilder.element()
            .from(5, 5, 0)
            .to(11, 11, 8)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#all"))
            .end();
        modelBuilder.element()
            .from(1, 0, 8)
            .to(15, 14, 17)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#all"))
            .end();

        for (var direction : Direction.values()) {
            var rotation = Utils.getRotation(direction);
            blockStateBuilder
                .part()
                .modelFile(modelBuilder)
                .rotationX(rotation.x())
                .rotationY(rotation.y())
                .addModel()
                .condition(BlockStateProperties.FACING, direction);
        }
    }
}