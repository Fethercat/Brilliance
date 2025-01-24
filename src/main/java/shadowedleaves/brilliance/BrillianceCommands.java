package shadowedleaves.brilliance;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BrillianceCommands {
    private static final SuggestionProvider<ServerCommandSource> SUGGEST_STATES = (context, builder) -> {
        return net.minecraft.command.CommandSource.suggestMatching(new String[]{"on", "off"}, builder);
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("nightvision")
                .executes(context -> {
                    BrillianceClient.toggleNightVision();
                    context.getSource().sendFeedback(() -> BrillianceClient.createToggleChatMessage(), false);
                    return 1;
                })
                .then(CommandManager.argument("state", StringArgumentType.string())
                        .suggests(SUGGEST_STATES)
                        .executes(context -> {
                            String state = StringArgumentType.getString(context, "state");
                            if ("on".equalsIgnoreCase(state)) {
                                BrillianceClient.setNightVision(true);
                                context.getSource().sendFeedback(() -> BrillianceClient.createChatMessage("on"), false);
                            } else if ("off".equalsIgnoreCase(state)) {
                                BrillianceClient.setNightVision(false);
                                context.getSource().sendFeedback(() -> BrillianceClient.createChatMessage("off"), false);
                            } else {
                                context.getSource().sendFeedback(() -> Text.literal("[Brilliance] Unknown state: " + state), false);
                            }
                            return 1;
                        })
                )
        );
    }
}