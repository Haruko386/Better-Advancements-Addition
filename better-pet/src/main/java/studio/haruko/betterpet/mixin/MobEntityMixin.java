package studio.haruko.betterpet.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.haruko.betterpet.AnchorAccess;
import studio.haruko.betterpet.BetterPet;
import studio.haruko.betterpet.PetTypes;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    private static final double BETTER_PET_LIMIT = 30.0;
    private static final double BETTER_PET_HARD_LIMIT = 29.75;
    private static final double BETTER_PET_RETURN_DISTANCE = 26.0;

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void betterPet$keepInsideRoamingArea(CallbackInfo ci) {
        MobEntity mob = (MobEntity) (Object) this;
        if (!(mob.getEntityWorld() instanceof ServerWorld)
                || !(mob instanceof TameableEntity pet)
                || !PetTypes.isSupported(pet)) {
            return;
        }

        AnchorAccess access = (AnchorAccess) pet;
        BlockPos anchor = access.betterPet$getAnchor();
        if (anchor == null) {
            return;
        }

        if (pet.isSitting()) {
            BetterPet.setAnchor(pet, null);
            return;
        }

        double centerX = anchor.getX() + 0.5;
        double centerZ = anchor.getZ() + 0.5;
        double deltaX = mob.getX() - centerX;
        double deltaZ = mob.getZ() - centerZ;
        double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;

        if (distanceSquared > BETTER_PET_LIMIT * BETTER_PET_LIMIT) {
            double distance = Math.sqrt(distanceSquared);
            double scale = BETTER_PET_HARD_LIMIT / distance;
            mob.setPosition(centerX + deltaX * scale, mob.getY(), centerZ + deltaZ * scale);

            Vec3d velocity = mob.getVelocity();
            double normalX = deltaX / distance;
            double normalZ = deltaZ / distance;
            double outwardSpeed = velocity.x * normalX + velocity.z * normalZ;
            if (outwardSpeed > 0.0) {
                mob.setVelocity(
                        velocity.x - outwardSpeed * normalX,
                        velocity.y,
                        velocity.z - outwardSpeed * normalZ
                );
            }
        } else if (distanceSquared > BETTER_PET_RETURN_DISTANCE * BETTER_PET_RETURN_DISTANCE
                && mob.getNavigation().isIdle()) {
            mob.getNavigation().startMovingTo(centerX, anchor.getY(), centerZ, 1.0);
        }
    }
}
