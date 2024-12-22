package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;


/**
 * Provider that allows defining translations in multiple {@link DataProvider}s instead of a single monolithic
 * provider. Each {@link DataProvider} should implement the {@link DataProvider#register(LanguageProvider, String)}
 * method to register its translations in the composite provider.
 */
public class LanguageCompositeProvider extends LanguageProvider {
    private final Iterable<DataProvider> providers;
    private final String locale;

    /**
     * Creates the composite provider.
     *
     * @param providers The component providers.
     * @param output Output to register translations in.
     * @param locale Locale to register translations in.
     */
    public LanguageCompositeProvider(
        Iterable<DataProvider> providers,
        PackOutput output,
        String locale
    ) {
        super(output, Main.MOD_ID, locale);
        this.providers = providers;
        this.locale = locale;
    }

    /**
     * Registers translations of component providers.
     */
    @Override
    protected void addTranslations() {
        for (var provider: providers) {
            provider.register(this, locale);
        }
    }
}
