package studio.haruko.betterpet;

import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;

public final class PetTypes {
    private PetTypes() {
    }

    public static boolean isSupported(TameableEntity pet) {
        return pet instanceof CatEntity || pet instanceof WolfEntity || pet instanceof ParrotEntity;
    }
}
