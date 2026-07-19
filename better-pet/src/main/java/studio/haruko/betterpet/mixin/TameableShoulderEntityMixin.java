package studio.haruko.betterpet.mixin;

import net.minecraft.entity.passive.TameableShoulderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.haruko.betterpet.AnchorAccess;

@Mixin(TameableShoulderEntity.class)
public abstract class TameableShoulderEntityMixin {
    @Inject(method = "isReadyToSitOnPlayer", at = @At("HEAD"), cancellable = true)
    private void betterPet$disableShoulderMounting(CallbackInfoReturnable<Boolean> cir) {
        if (((AnchorAccess) this).betterPet$getAnchor() != null) {
            cir.setReturnValue(false);
        }
    }
}
