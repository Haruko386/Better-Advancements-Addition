package studio.haruko.betterpet.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.haruko.betterpet.client.BetterPetRenderState;
import studio.haruko.betterpet.client.ClientAnchorState;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "updateRenderState", at = @At("TAIL"))
    private void betterPet$copyAnchorState(LivingEntity entity, LivingEntityRenderState state,
                                           float tickProgress, CallbackInfo ci) {
        ((BetterPetRenderState) state).betterPet$setAnchored(ClientAnchorState.isAnchored(entity.getUuid()));
    }
}
