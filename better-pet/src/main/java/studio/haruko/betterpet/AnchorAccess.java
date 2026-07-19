package studio.haruko.betterpet;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

/** Internal state added to vanilla tameable entities. */
public interface AnchorAccess {
    @Nullable BlockPos betterPet$getAnchor();

    void betterPet$setAnchor(@Nullable BlockPos anchor);
}
