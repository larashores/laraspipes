package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.itemextractor.ItemExtractorModels;
import com.larashores.laraspipes.itempipe.ItemPipeModels;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStatesProvider extends BlockStateProvider {
    public BlockStatesProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Main.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        ItemExtractorModels.register(
            models(),
            getMultipartBuilder(Registration.EXTRACTOR.get())
        );
        ItemPipeModels.register(
            models(),
            getMultipartBuilder(Registration.PIPE.get())
        );
    }
}
