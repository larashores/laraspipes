package com.larashores.laraspipes;

import com.larashores.laraspipes.itemdepositor.ItemDepositorBlock;
import com.larashores.laraspipes.itemdepositor.ItemDepositorEntity;
import com.larashores.laraspipes.itemdepositor.ItemDepositorMenu;
import com.larashores.laraspipes.itemdepositor.ItemDepositorScreen;
import com.larashores.laraspipes.itemextractor.ItemExtractorBlock;
import com.larashores.laraspipes.itemextractor.ItemExtractorEntity;
import com.larashores.laraspipes.itempipe.ItemPipeBlock;
import com.larashores.laraspipes.itempipe.ItemPipeEntity;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
        ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID
    );
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(
        ForgeRegistries.MENU_TYPES, Main.MOD_ID
    );

    // Item Pipe
    public static final RegistryObject<Block> PIPE_BLOCK = BLOCKS.register(
        "item_pipe", ItemPipeBlock::new
    );
    public static final RegistryObject<Item> PIPE_ITEM = ITEMS.register(
        "item_pipe", () -> new BlockItem(PIPE_BLOCK.get(), new Item.Properties())
    );
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<ItemPipeEntity>> PIPE_ENTITY = BLOCK_ENTITIES.register(
        "item_pipe",
        () -> BlockEntityType.Builder.of(ItemPipeEntity::new, PIPE_BLOCK.get()).build(null)
    );

    // Item Extractor
    public static final RegistryObject<Block> EXTRACTOR_BLOCK = BLOCKS.register(
        "item_extractor", ItemExtractorBlock::new
    );
    public static final RegistryObject<Item> EXTRACTOR_ITEM = ITEMS.register(
        "item_extractor", () -> new BlockItem(EXTRACTOR_BLOCK.get(), new Item.Properties())
    );
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<ItemExtractorEntity>> EXTRACTOR_ENTITY = BLOCK_ENTITIES.register(
        "item_extractor",
        () -> BlockEntityType.Builder.of(ItemExtractorEntity::new, EXTRACTOR_BLOCK.get()).build(null)
    );

    // Item Depositor
    public static final RegistryObject<Block> DEPOSITOR_BLOCK = BLOCKS.register(
        "item_depositor", ItemDepositorBlock::new
    );
    public static final RegistryObject<Item> DEPOSITOR_ITEM = ITEMS.register(
        "item_depositor", () -> new BlockItem(DEPOSITOR_BLOCK.get(), new Item.Properties())
    );
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<ItemDepositorEntity>> DEPOSITOR_ENTITY = BLOCK_ENTITIES.register(
        "item_depositor",
        () -> BlockEntityType.Builder.of(ItemDepositorEntity::new, DEPOSITOR_BLOCK.get()).build(null)
    );
    public static final RegistryObject<MenuType<ItemDepositorMenu>> DEPOSITOR_MENU = MENU_TYPES.register(
        "item_depositor",
        () -> IForgeMenuType.create(
            (windowId, inv, data) -> new ItemDepositorMenu(windowId, inv.player, data.readBlockPos())
        )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        MENU_TYPES.register(eventBus);
        eventBus.addListener(Registration::registerCreative);
        eventBus.addListener(Registration::registerScreens);
    }


    private static void registerCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(Registration.PIPE_ITEM);
            event.accept(Registration.DEPOSITOR_ITEM);
            event.accept(Registration.EXTRACTOR_ITEM);
        }
    }

    private static void registerScreens(FMLClientSetupEvent event) {
        event.enqueueWork(
            () -> MenuScreens.register(Registration.DEPOSITOR_MENU.get(), ItemDepositorScreen::new)
        );
    }
}
