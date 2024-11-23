package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

public class ItemModelsProvider extends ItemModelProvider {

    public ItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.DEPOSITOR.getId().getPath(), modLoc("block/item_depositor_front"));
        withExistingParent(Registration.EXTRACTOR.getId().getPath(), modLoc("block/item_extractor_front"));
        withExistingParent(Registration.PIPE.getId().getPath(), modLoc("block/item_pipe_center"));
    }
}
