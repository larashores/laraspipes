package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguagesProvider extends LanguageProvider {
    private final DataGenerationProvider[] providers;
    private final String locale;

    public LanguagesProvider(
        DataGenerationProvider[] providers,
        PackOutput output,
        String locale
    ) {
        super(output, Main.MOD_ID, locale);
        this.providers = providers;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        for (var provider: providers) {
            provider.register(this, locale);
        }
    }
}
