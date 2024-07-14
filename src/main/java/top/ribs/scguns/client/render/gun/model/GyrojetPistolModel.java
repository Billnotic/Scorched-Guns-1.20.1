package top.ribs.scguns.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.ribs.scguns.client.SpecialModels;
import top.ribs.scguns.client.render.gun.IOverrideModel;
import top.ribs.scguns.client.util.RenderUtil;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.init.ModItems;
import top.ribs.scguns.item.attachment.IAttachment;

/**
 * Since we want to have an animation for the charging handle, we will be overriding the standard model rendering.
 * This also allows us to replace the model for the different stocks.
 */
public class GyrojetPistolModel implements IOverrideModel {

    @SuppressWarnings("resource")
    @Override
    public void render(float partialTicks, ItemDisplayContext transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {

        // Renders the static parts of the model.
        RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_MAIN.getModel(), stack, matrixStack, buffer, light, overlay);

        if (Gun.hasAttachmentEquipped(stack, IAttachment.Type.STOCK)) {
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.WOODEN_STOCK.get())
                RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_STOCK_WOODEN.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.LIGHT_STOCK.get())
                RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_STOCK_LIGHT.getModel(), stack, matrixStack, buffer, light, overlay);
            else if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.WEIGHTED_STOCK.get())
                RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_STOCK_HEAVY.getModel(), stack, matrixStack, buffer, light, overlay);
        }
        if ((Gun.hasAttachmentEquipped(stack, IAttachment.Type.MAGAZINE)))
        {
            if (Gun.getAttachment(IAttachment.Type.MAGAZINE, stack).getItem() == ModItems.EXTENDED_MAG.get())
                RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_EXT_MAG.getModel(), stack, matrixStack, buffer, light, overlay);
            if (Gun.getAttachment(IAttachment.Type.MAGAZINE, stack).getItem() == ModItems.PLUS_P_MAG.get())
                RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_EXT_MAG.getModel(), stack, matrixStack, buffer, light, overlay);
            if (Gun.getAttachment(IAttachment.Type.MAGAZINE, stack).getItem() == ModItems.SPEED_MAG.get())
                RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_SPEED_MAG.getModel(), stack, matrixStack, buffer, light, overlay);
        }
        else
            RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_STAN_MAG.getModel(), stack, matrixStack, buffer, light, overlay);

        if (entity.equals(Minecraft.getInstance().player)) {
            // Render the left flame
            renderFlame(matrixStack, buffer, stack, light, overlay, true);

            // Render the right flame
            renderFlame(matrixStack, buffer, stack, light, overlay, false);
        }
    }

    private void renderFlame(PoseStack matrixStack, MultiBufferSource buffer, ItemStack stack, int light, int overlay, boolean isLeft) {
        // Always push.
        matrixStack.pushPose();
        // Don't touch this, it's better to use the display options in Blockbench.
        matrixStack.translate(0, -5.8 * 0.0625, 0);
        // Gets the cooldown tracker for the item. Items like swords and enderpearls also have this.
        ItemCooldowns tracker = Minecraft.getInstance().player.getCooldowns();
        float cooldown = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
        cooldown = (float) ease(cooldown);

        // Apply scaling based on cooldown to hide the flames initially
        float scale = cooldown > 0 ? 1.0f : 0.0f;
        matrixStack.scale(scale, scale, scale);

        // We are moving whatever part is moving.
        // X, Y, Z, use Z for moving back and forth.
        // The higher the number, the shorter the distance.
        matrixStack.translate(cooldown / -26, 0, 0);
        matrixStack.translate(0, 5.8 * 0.0625, 0);

        if (isLeft) {
            RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_FLAME_LEFT.getModel(), stack, matrixStack, buffer, light, overlay);
        } else {
            matrixStack.translate(-cooldown / -13, 0, 0); // mirror transform for right flame
            RenderUtil.renderModel(SpecialModels.GYROJET_PISTOL_FLAME_RIGHT.getModel(), stack, matrixStack, buffer, light, overlay);
        }

        // Always pop
        matrixStack.popPose();
    }

    private double ease(double x) {
        return 1 - Math.pow(1 - (2 * x), 4);
    }
}
