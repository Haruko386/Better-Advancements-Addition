package studio.haruko.betterpet.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class ClientAnchorState {
    private static final Set<UUID> ANCHORED_PETS = new HashSet<>();

    private ClientAnchorState() {
    }

    public static void set(UUID uuid, boolean anchored) {
        if (anchored) {
            ANCHORED_PETS.add(uuid);
        } else {
            ANCHORED_PETS.remove(uuid);
        }
    }

    public static boolean isAnchored(UUID uuid) {
        return ANCHORED_PETS.contains(uuid);
    }

    public static void clear() {
        ANCHORED_PETS.clear();
    }
}
