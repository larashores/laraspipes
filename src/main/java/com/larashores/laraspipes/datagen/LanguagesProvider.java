package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import com.larashores.laraspipes.Registration;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguagesProvider extends LanguageProvider {

    public LanguagesProvider(PackOutput output, String locale) {
        super(output, Main.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(Registration.DEPOSITOR.get(), "Item Depositor");
        add(Registration.EXTRACTOR.get(), "Item Extractor");
        add(Registration.PIPE.get(), "Item Pipe");
    }
}
