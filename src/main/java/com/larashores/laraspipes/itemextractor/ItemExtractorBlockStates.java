package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
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
        var path = Registration.EXTRACTOR.getId().getPath();
        var builder = models().getBuilder("block/" + path);
        builder.parent(models().getExistingFile(mcLoc("cube_all")));
        builder.texture("all", Main.MOD_ID + ":block/" + path);
    }
}