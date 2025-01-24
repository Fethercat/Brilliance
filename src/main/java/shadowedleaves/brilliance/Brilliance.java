package shadowedleaves.brilliance;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Brilliance implements ModInitializer {
	public static final String MOD_ID = "brilliance";
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			BrillianceCommands.register(dispatcher);
			LOGGER.info("[Brilliance] Registered command: \"/nightvision\"!");
			LOGGER.info("[Brilliance] Brilliance has been initialized!");
		});
	}
}