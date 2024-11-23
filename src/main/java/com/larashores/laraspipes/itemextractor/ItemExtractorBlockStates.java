package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

public class ItemExtractorBlockStates extends BlockStateProvider {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemExtractorBlockStates(PackOutput output, ExistingFileHelper helper) {
        super(output, Main.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        var block = Registration.EXTRACTOR.get();
        var path = Registration.EXTRACTOR.getId().getPath();
        var modelBuilder = models().getBuilder("block/" + path);
        modelBuilder.parent(models().getExistingFile(mcLoc("cube_all")));
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

        var blockStateBuilder = getMultipartBuilder(block);
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