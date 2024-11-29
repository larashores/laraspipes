package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Main;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.slf4j.Logger;

public class ItemDepositorScreen extends AbstractContainerScreen<ItemDepositorMenu> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private final ResourceLocation GUI = new ResourceLocation(Main.MOD_ID, "textures/gui/item_depositor.png");

    public ItemDepositorScreen(ItemDepositorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 177;
        this.imageHeight = 220;
        this.inventoryLabelY = 127;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float partialTicks) {
        super.render(graphics, x, y, partialTicks);
        renderTooltip(graphics, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
