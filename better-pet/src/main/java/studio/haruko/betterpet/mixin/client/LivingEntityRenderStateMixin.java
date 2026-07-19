package studio.haruko.betterpet.mixin.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import studio.haruko.betterpet.client.BetterPetRenderState;

@Mixin(LivingEntityRenderState.class)
public abstract class LivingEntityRenderStateMixin implements BetterPetRenderState {
    @Unique
    private boolean betterPet$anchored;

    @Override
    public boolean betterPet$isAnchored() {
        return betterPet$anchored;
    }

    @Override
    public void betterPet$setAnchored(boolean anchored) {
        betterPet$anchored = anchored;
    }
}
