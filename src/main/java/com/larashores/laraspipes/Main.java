package com.larashores.laraspipes;

import com.larashores.laraspipes.datagen.DataGenerator;
import com.larashores.laraspipes.itemdepositor.ItemDepositorData;
import com.larashores.laraspipes.itemdepositor.ItemDepositorScreen;
import com.larashores.laraspipes.itemextractor.ItemExtractorData;
import com.larashores.laraspipes.itempipe.ItemPipeData;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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


    public Main()
    {
        var dataGen = new DataGenerator(
            List.of(
                new ItemDepositorData(),
                new ItemExtractorData(),
                new ItemPipeData()
            )
        );

        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(dataGen::generate);
        eventBus.addListener(Config::load);

        Registration.register(eventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        eventBus.addListener(this::registerCreativeModeItems);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void registerCreativeModeItems(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(Registration.PIPE_ITEM);
            event.accept(Registration.DEPOSITOR_ITEM);
            event.accept(Registration.EXTRACTOR_ITEM);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Item Depositor
            event.enqueueWork(() ->
                MenuScreens.register(Registration.DEPOSITOR_MENU.get(), ItemDepositorScreen::new)
            );
        }
    }
}
