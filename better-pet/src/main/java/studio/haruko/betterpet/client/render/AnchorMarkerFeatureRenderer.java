package studio.haruko.betterpet.client.render;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import studio.haruko.betterpet.client.BetterPetRenderState;

public final class AnchorMarkerFeatureRenderer<S extends LivingEntityRenderState, M extends EntityModel<? super S>>
        extends FeatureRenderer<S, M> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/block/yellow_concrete.png");
    private static final ModelPart MARKER = createMarker();

    public AnchorMarkerFeatureRenderer(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state,
                       float limbAngle, float limbDistance) {
        if (!((BetterPetRenderState) state).betterPet$isAnchored() || state.invisible) {
            return;
        }

        ModelPart root = getContextModel().getRootPart();
        if (!root.hasChild("head")) {
            return;
        }

        matrices.push();
        root.getChild("head").applyTransform(matrices);

        if (state.entityType == EntityType.PARROT) {
            matrices.translate(0.0, 0.30, -0.08);
        } else {
            matrices.translate(0.0, 0.33, -0.20);
        }

        queue.submitModelPart(
                MARKER,
                matrices,
                RenderLayers.entityCutoutNoCull(TEXTURE),
                light,
                OverlayTexture.DEFAULT_UV,
                null
        );
        matrices.pop();
    }

    private static ModelPart createMarker() {
        ModelData modelData = new ModelData();
        modelData.getRoot().addChild(
                "marker",
                ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                ModelTransform.NONE
        );
        return TexturedModelData.of(modelData, 16, 16).createModel().getChild("marker");
    }
}
