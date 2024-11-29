package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelCompositeProvider extends ItemModelProvider {
    private final Iterable<DataProvider> providers;

    public ItemModelCompositeProvider(
        Iterable<DataProvider> providers,
        PackOutput output,
        ExistingFileHelper existingFileHelper
    ) {
        super(output, Main.MOD_ID, existingFileHelper);
        this.providers = providers;
    }

    @Override
    protected void registerModels() {
        for (var provider: providers) {
            provider.register(this);
        }
    }
}
