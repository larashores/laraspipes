package com.larashores.laraspipes.composite;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.registries.RegistryObject;

public class ItemPipeConnectorData extends ItemPipeComponentData {
    public ItemPipeConnectorData(RegistryObject<Block> block) {
        super(block);
    }

    public MultiPartBlockStateBuilder register(BlockStateProvider provider) {
        var path = REGISTERED_BLOCK.getId().getPath();
        var models = provider.models();

        var modelBuilder = models.getBuilder("block/" + path);
        modelBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        modelBuilder.texture("pipe", Main.MOD_ID + ":block/" + path + "_pipe");
        modelBuilder.texture("back", Main.MOD_ID + ":block/" + path + "_back");
        modelBuilder.texture("front", Main.MOD_ID + ":block/" + path + "_front");
        modelBuilder.texture("vertical", Main.MOD_ID + ":block/" + path + "_side_vertical");
        modelBuilder.texture("horizontal", Main.MOD_ID + ":block/" + path + "_side_horizontal");
        modelBuilder.element()
            .from(5, 5, 5)
            .to(11, 11, 11)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#pipe"))
            .end();
        modelBuilder.element()
            .from(1, 0, 11)
            .to(15, 14, 17)
            .allFaces(
                (direction, faceBuilder) -> {
                    if (direction == Direction.SOUTH) {
                        faceBuilder.texture("#front");
                    } else if (direction == Direction.NORTH) {
                        faceBuilder.texture("#back");
                    } else if (direction == Direction.UP) {
                        faceBuilder.uvs(0, 10, 14, 16);
                        faceBuilder.texture("#vertical");
                    } else if (direction == Direction.DOWN) {
                        faceBuilder.uvs(0, 0, 14, 6);
                        faceBuilder.texture("#vertical");
                    } else if (direction == Direction.WEST) {
                        faceBuilder.uvs(10, 0, 16, 14);
                        faceBuilder.texture("#horizontal");
                    } else if (direction == Direction.EAST) {
                        faceBuilder.uvs(0, 0, 6, 14);
                        faceBuilder.texture("#horizontal");
                    }
                }
            )
            .end();

        var blockStateBuilder = super.register(provider);
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
        return blockStateBuilder;
    }
}
