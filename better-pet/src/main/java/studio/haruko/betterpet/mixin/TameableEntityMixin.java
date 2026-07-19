package studio.haruko.betterpet.mixin;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.haruko.betterpet.AnchorAccess;

@Mixin(TameableEntity.class)
public abstract class TameableEntityMixin implements AnchorAccess {
    @Unique
    private static final String BETTER_PET_ANCHOR_KEY = "BetterPetAnchor";

    @Unique
    private @Nullable BlockPos betterPet$anchor;

    @Override
    public @Nullable BlockPos betterPet$getAnchor() {
        return betterPet$anchor;
    }

    @Override
    public void betterPet$setAnchor(@Nullable BlockPos anchor) {
        betterPet$anchor = anchor;
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void betterPet$writeAnchor(WriteView view, CallbackInfo ci) {
        if (betterPet$anchor != null) {
            view.putLong(BETTER_PET_ANCHOR_KEY, betterPet$anchor.asLong());
        }
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void betterPet$readAnchor(ReadView view, CallbackInfo ci) {
        betterPet$anchor = view.getOptionalLong(BETTER_PET_ANCHOR_KEY)
                .map(BlockPos::fromLong)
                .orElse(null);
    }

    @Inject(method = "shouldTryTeleportToOwner", at = @At("HEAD"), cancellable = true)
    private void betterPet$disableOwnerTeleport(CallbackInfoReturnable<Boolean> cir) {
        if (betterPet$anchor != null) {
            cir.setReturnValue(false);
        }
    }
}
