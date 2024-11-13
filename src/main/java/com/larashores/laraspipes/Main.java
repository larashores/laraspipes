package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "laraspipes";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
        ForgeRegistries.BLOCK_ENTITY_TYPES, MODID
    );
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(
        ForgeRegistries.MENU_TYPES, MODID
    );

    // Item Pipe
    public static final RegistryObject<Block> PIPE = BLOCKS.register(
        "item_pipe", ItemPipe::new
    );
    public static final RegistryObject<Item> PIPE_ITEM = ITEMS.register(
        "item_pipe", () -> new BlockItem(PIPE.get(), new Item.Properties())
    );

    // Item Extractor
    public static final RegistryObject<Block> EXTRACTOR = BLOCKS.register(
        "item_extractor", ItemExtractor::new
    );
    public static final RegistryObject<Item> EXTRACTOR_ITEM = ITEMS.register(
        "item_extractor", () -> new BlockItem(EXTRACTOR.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockEntityType<ItemExtractorEntity>> EXTRACTOR_ENTITY = BLOCK_ENTITIES.register(
        "item_extractor",
        () -> BlockEntityType.Builder.of(ItemExtractorEntity::new, EXTRACTOR.get()).build(null)
    );

    // Item Depositor
    public static final RegistryObject<Block> DEPOSITOR = BLOCKS.register(
        "item_depositor", ItemDepositor::new
    );
    public static final RegistryObject<Item> DEPOSITOR_ITEM = ITEMS.register(
        "item_depositor", () -> new BlockItem(DEPOSITOR.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockEntityType<ItemDepositorEntity>> DEPOSITOR_ENTITY = BLOCK_ENTITIES.register(
        "item_depositor",
        () -> BlockEntityType.Builder.of(ItemDepositorEntity::new, DEPOSITOR.get()).build(null)
    );
    public static final RegistryObject<MenuType<ItemDepositorMenu>> DEPOSITOR_MENU = MENU_TYPES.register(
        "item_depositor",
        () -> IForgeMenuType.create(
            (windowId, inv, data) -> new ItemDepositorMenu(windowId, inv.player, data.readBlockPos())
        )
    );


    public Main()
    {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MENU_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(PIPE_ITEM);
            event.accept(EXTRACTOR_ITEM);
            event.accept(DEPOSITOR_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Item Depositor
            event.enqueueWork(() -> {
                MenuScreens.register(DEPOSITOR_MENU.get(), ItemDepositorScreen::new);
            });
        }
    }
}
