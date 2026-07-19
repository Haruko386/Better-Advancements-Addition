package studio.haruko.betterpet.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import studio.haruko.betterpet.PetTypes;
import studio.haruko.betterpet.client.render.AnchorMarkerFeatureRenderer;
import studio.haruko.betterpet.network.AnchorSyncPayload;
import studio.haruko.betterpet.network.SetAnchorPayload;

public final class BetterPetClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(AnchorSyncPayload.ID, (payload, context) ->
                ClientAnchorState.set(payload.entityUuid(), payload.anchored()));

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ClientAnchorState.clear());

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient()
                    || hand != Hand.MAIN_HAND
                    || !(entity instanceof TameableEntity pet)
                    || !PetTypes.isSupported(pet)
                    || !pet.isTamed()
                    || !pet.isOwner(player)
                    || !pet.isSitting()
                    || !isControlDown()
                    || !isShiftDown()) {
                return ActionResult.PASS;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == player && ClientPlayNetworking.canSend(SetAnchorPayload.ID)) {
                ClientPlayNetworking.send(new SetAnchorPayload(entity.getId()));
                player.swingHand(hand);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, renderer, helper, context) -> {
            if (entityType == EntityType.CAT || entityType == EntityType.WOLF || entityType == EntityType.PARROT) {
                registerMarker(renderer, helper);
            }
        });
    }

    private static boolean isControlDown() {
        MinecraftClient client = MinecraftClient.getInstance();
        return InputUtil.isKeyPressed(client.getWindow(), InputUtil.GLFW_KEY_LEFT_CONTROL)
                || InputUtil.isKeyPressed(client.getWindow(), InputUtil.GLFW_KEY_RIGHT_CONTROL);
    }

    private static boolean isShiftDown() {
        MinecraftClient client = MinecraftClient.getInstance();
        return InputUtil.isKeyPressed(client.getWindow(), InputUtil.GLFW_KEY_LEFT_SHIFT)
                || InputUtil.isKeyPressed(client.getWindow(), InputUtil.GLFW_KEY_RIGHT_SHIFT);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerMarker(Object renderer,
                                       LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper helper) {
        FeatureRenderer marker = new AnchorMarkerFeatureRenderer((FeatureRendererContext) renderer);
        helper.register(marker);
    }
}
