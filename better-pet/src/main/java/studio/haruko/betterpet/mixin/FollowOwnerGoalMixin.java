package studio.haruko.betterpet.mixin;

import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.haruko.betterpet.AnchorAccess;

@Mixin(FollowOwnerGoal.class)
public abstract class FollowOwnerGoalMixin {
    @Shadow @Final private TameableEntity tameable;

    @Inject(method = {"canStart", "shouldContinue"}, at = @At("HEAD"), cancellable = true)
    private void betterPet$disableFollowing(CallbackInfoReturnable<Boolean> cir) {
        if (((AnchorAccess) tameable).betterPet$getAnchor() != null) {
            cir.setReturnValue(false);
        }
    }
}
