package com.larashores.laraspipes;

import com.larashores.laraspipes.datagen.DataGenerator;
import com.larashores.laraspipes.itemdepositor.ItemDepositorData;
import com.larashores.laraspipes.itemextractor.ItemExtractorData;
import com.larashores.laraspipes.itempipe.ItemPipeData;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main
{
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = "laraspipes";

    private static final IEventBus EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    private static final DataGenerator DATA_GEN = new DataGenerator(
        List.of(
            new ItemDepositorData(),
            new ItemExtractorData(),
            new ItemPipeData()
        )
    );

    public Main()
    {
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        Registration.register(EVENT_BUS);
        EVENT_BUS.addListener(DATA_GEN::generate);
        EVENT_BUS.addListener(Config::load);
    }
}
