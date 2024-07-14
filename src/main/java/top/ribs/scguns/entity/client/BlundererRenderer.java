package top.ribs.scguns.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import top.ribs.scguns.Reference;
import top.ribs.scguns.entity.monster.BlundererEntity;

public class BlundererRenderer extends MobRenderer<BlundererEntity, BlundererModel<BlundererEntity>> {
    public BlundererRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BlundererModel<>(pContext.bakeLayer(ModModelLayers.BLUNDERER_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(BlundererEntity pEntity) {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/blunderer.png");
    }

    @Override
    public void render(BlundererEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}






