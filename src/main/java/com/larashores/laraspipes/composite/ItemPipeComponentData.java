package com.larashores.laraspipes.composite;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.datagen.DataProvider;
import com.larashores.laraspipes.itempipe.ItemPipeBlock;
import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.registries.RegistryObject;

public class ItemPipeComponentData extends DataProvider {
    protected final RegistryObject<Block> REGISTERED_BLOCK;

    public ItemPipeComponentData(RegistryObject<Block> block) {
        REGISTERED_BLOCK = block;
    }

    public MultiPartBlockStateBuilder register(BlockStateProvider provider) {
        var path = REGISTERED_BLOCK.getId().getPath();
        var models = provider.models();

        var modelBuilder = models.getBuilder("block/" + path + "_side");
        modelBuilder.parent(models.getExistingFile(new ResourceLocation("cube_all")));
        modelBuilder.texture("particle", Main.MOD_ID + ":block/" + path + "_pipe");
        modelBuilder.texture("pipe", Main.MOD_ID + ":block/" + path + "_pipe");
        modelBuilder.element()
            .from(5, 5, 11)
            .to(11, 11, 16)
            .allFaces((direction, faceBuilder) -> faceBuilder.texture("#pipe"))
            .end();

        var blockStateBuilder = provider.getMultipartBuilder(REGISTERED_BLOCK.get());
        for (var direction : Direction.values()) {
            var rotation = Utils.getRotation(direction);
            blockStateBuilder
                .part()
                .modelFile(modelBuilder)
                .rotationX(rotation.x())
                .rotationY(rotation.y())
                .addModel()
                .condition(ItemPipeBlock.CONNECTED.get(direction), true);
        }
        return blockStateBuilder;
    }
}
