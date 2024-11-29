package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateCompositeProvider extends BlockStateProvider {
    private final Iterable<DataProvider> providers;

    public BlockStateCompositeProvider(
        Iterable<DataProvider> providers,
        PackOutput output,
        ExistingFileHelper helper
    ) {
        super(output, Main.MOD_ID, helper);
        this.providers = providers;
    }

    @Override
    protected void registerStatesAndModels() {
        for (var provider: providers) {
            provider.register(this);
        }
    }
}