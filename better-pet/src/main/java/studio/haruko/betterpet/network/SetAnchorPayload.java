package studio.haruko.betterpet.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import studio.haruko.betterpet.BetterPet;

public record SetAnchorPayload(int entityId) implements CustomPayload {
    public static final Id<SetAnchorPayload> ID = CustomPayload.id(BetterPet.MOD_ID + ":set_anchor");
    public static final PacketCodec<RegistryByteBuf, SetAnchorPayload> CODEC = PacketCodec.of(
            (payload, buffer) -> buffer.writeVarInt(payload.entityId),
            buffer -> new SetAnchorPayload(buffer.readVarInt())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
