package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;


public class Config
{
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // Note that the ModConfigEvent only fires if at least one config variable is defined.
    static void load(final ModConfigEvent event)
    {

    }
}
