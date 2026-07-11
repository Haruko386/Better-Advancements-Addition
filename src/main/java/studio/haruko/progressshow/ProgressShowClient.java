package studio.haruko.progressshow;

import betteradvancements.common.util.CriteriaDetail;
import betteradvancements.common.util.CriterionGrid;
import net.fabricmc.api.ClientModInitializer;

/**
 * Keeps Better Advancements in its "All" criteria mode so incomplete
 * requirements are named instead of being collapsed into "N remaining".
 */
public final class ProgressShowClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CriterionGrid.detailLevel = CriteriaDetail.ALL;
        CriterionGrid.requiresShift = false;

        System.out.println("[Minecraft Progress Show] Enabled completed and missing criterion details");
    }
}
