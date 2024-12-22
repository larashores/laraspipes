package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;


/**
 * Registers user defined config methods that can be defined for the mod.
 */
public class Config {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    /** Defines which config variables this mod accepts */
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    /**
     * Event handler for loading user-defined config variables. Note that the ModConfigEvent only fires if at least one
     * config variable is defined.
     *
     * @param event The event to receive.
     */
    static void load(final ModConfigEvent event) {

    }
}
