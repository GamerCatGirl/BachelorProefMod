package siheynde.bachelorproefmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import siheynde.bachelorproefmod.BachelorProef;

public class ModScreenHandlers {
    public static final ScreenHandlerType<FunctionScreenHandler> FUNCTION_SCREEN =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(BachelorProef.MOD_ID, "function_screen"),
                    new ExtendedScreenHandlerType<>(FunctionScreenHandler::new));

    public static final ScreenHandlerType<TestScreenHandler> TEST_SCREEN =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(BachelorProef.MOD_ID, "test_screen"),
                    new ExtendedScreenHandlerType<>(TestScreenHandler::new));

    public static void registerScreenHandlers() {
        BachelorProef.LOGGER.info("Registering Screen Handlers for " + BachelorProef.MOD_ID);
    }
}
