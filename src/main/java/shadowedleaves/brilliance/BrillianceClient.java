package shadowedleaves.brilliance;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.lwjgl.glfw.GLFW;

public class BrillianceClient implements ClientModInitializer {
    public static boolean nightVision = false;
    private static KeyBinding toggleNightVisionKey;

    private static final TextColor BRILLIANCE_COLOR = TextColor.fromRgb(0xFFFF00); // Yellow
    private static final TextColor LIGHT_YELLOW_COLOR = TextColor.fromRgb(0xFFFFE0); // Lighter Yellow
    private static final TextColor GREEN_COLOR = TextColor.fromRgb(0x00FF00); // Green
    private static final TextColor RED_COLOR = TextColor.fromRgb(0xFF0000); // Red

    @Override
    public void onInitializeClient() {
        toggleNightVisionKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.brilliance.toggle_night_vision",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.brilliance"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleNightVisionKey.wasPressed()) {
                toggleNightVision();
            }
        });
    }

    public static void toggleNightVision() {
        nightVision = !nightVision;
        MinecraftClient client = MinecraftClient.getInstance();
        if (nightVision) {
            client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        } else {
            client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        client.inGameHud.setOverlayMessage(createActionBarMessage(), true);
    }

    public static void setNightVision(boolean enable) {
        nightVision = enable;
        MinecraftClient client = MinecraftClient.getInstance();
        if (nightVision) {
            client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        } else {
            client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        client.inGameHud.setOverlayMessage(createActionBarMessage(), true);
    }

    private static Text createActionBarMessage() {
        return Text.literal("Night Vision: ")
                .append(Text.literal(nightVision ? "ON" : "OFF")
                        .styled(style -> style.withColor(nightVision ? GREEN_COLOR : RED_COLOR)));
    }

    public static Text createChatMessage(String state) {
        return Text.literal("[Brilliance] ")
                .styled(style -> style.withColor(BRILLIANCE_COLOR))
                .append(Text.literal("Set night vision to ")
                        .styled(style -> style.withColor(LIGHT_YELLOW_COLOR)))
                .append(Text.literal(state)
                        .styled(style -> style.withColor("on".equalsIgnoreCase(state) ? GREEN_COLOR : RED_COLOR)));
    }

    public static Text createToggleChatMessage() {
        return Text.literal("[Brilliance] ")
                .styled(style -> style.withColor(BRILLIANCE_COLOR))
                .append(Text.literal("Toggled night vision!")
                        .styled(style -> style.withColor(LIGHT_YELLOW_COLOR)));
    }
}