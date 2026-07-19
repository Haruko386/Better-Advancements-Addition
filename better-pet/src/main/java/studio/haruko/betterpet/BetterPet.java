package studio.haruko.betterpet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import studio.haruko.betterpet.network.AnchorSyncPayload;
import studio.haruko.betterpet.network.SetAnchorPayload;

public final class BetterPet implements ModInitializer {
    public static final String MOD_ID = "better_pet";
    public static final double INTERACTION_DISTANCE_SQUARED = 36.0;

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(SetAnchorPayload.ID, SetAnchorPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(AnchorSyncPayload.ID, AnchorSyncPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(SetAnchorPayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            Entity entity = player.getEntityWorld().getEntityById(payload.entityId());

            if (!(entity instanceof TameableEntity pet)
                    || !PetTypes.isSupported(pet)
                    || !pet.isTamed()
                    || !pet.isOwner(player)
                    || !pet.isSitting()
                    || squaredDistance(pet, player) > INTERACTION_DISTANCE_SQUARED) {
                return;
            }

            BlockPos anchor = pet.getBlockPos().toImmutable();
            pet.setSitting(false);
            pet.setInSittingPose(false);
            setAnchor(pet, anchor);
            player.sendMessage(Text.translatable("message.better_pet.anchor_enabled", anchor.toShortString()), true);
        });

        EntityTrackingEvents.START_TRACKING.register((entity, player) -> {
            if (entity instanceof TameableEntity pet && PetTypes.isSupported(pet)) {
                sendState(player, pet);
            }
        });
    }

    public static void setAnchor(TameableEntity pet, @Nullable BlockPos anchor) {
        AnchorAccess access = (AnchorAccess) pet;
        BlockPos current = access.betterPet$getAnchor();
        if (anchor == null ? current == null : anchor.equals(current)) {
            return;
        }

        access.betterPet$setAnchor(anchor == null ? null : anchor.toImmutable());
        AnchorSyncPayload payload = new AnchorSyncPayload(pet.getUuid(), anchor != null);
        for (ServerPlayerEntity player : PlayerLookup.tracking(pet)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    private static void sendState(ServerPlayerEntity player, TameableEntity pet) {
        boolean anchored = ((AnchorAccess) pet).betterPet$getAnchor() != null;
        ServerPlayNetworking.send(player, new AnchorSyncPayload(pet.getUuid(), anchored));
    }

    private static double squaredDistance(Entity first, Entity second) {
        double x = first.getX() - second.getX();
        double y = first.getY() - second.getY();
        double z = first.getZ() - second.getZ();
        return x * x + y * y + z * z;
    }
}
