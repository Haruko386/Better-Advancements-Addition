package studio.haruko.betterpet.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import studio.haruko.betterpet.BetterPet;

import java.util.UUID;

public record AnchorSyncPayload(UUID entityUuid, boolean anchored) implements CustomPayload {
    public static final Id<AnchorSyncPayload> ID = CustomPayload.id(BetterPet.MOD_ID + ":anchor_sync");
    public static final PacketCodec<RegistryByteBuf, AnchorSyncPayload> CODEC = PacketCodec.of(
            (payload, buffer) -> {
                buffer.writeUuid(payload.entityUuid);
                buffer.writeBoolean(payload.anchored);
            },
            buffer -> new AnchorSyncPayload(buffer.readUuid(), buffer.readBoolean())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
