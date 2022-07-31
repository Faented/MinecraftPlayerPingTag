package me.faented.pingtag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.entity.EntityType;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("pingtag")
public class PingTag {
    public Minecraft mc = Minecraft.getInstance();

    public PingTag() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderNameplate(RenderNameplateEvent event) {
        if (mc.player != null && mc.world != null && mc.player.isAlive()) {
            if (event.getEntity().getType() == EntityType.PLAYER) {
                String ping = mc.getConnection().getPlayerInfo(event.getEntity().getUniqueID()).getResponseTime() + " ms";

                boolean flag = !event.getEntity().isDiscrete();
                float f = event.getEntity().getHeight() + 0.5F;
                event.getMatrixStack().push();
                event.getMatrixStack().translate(0.0D, (double) f, 0.0D);
                event.getMatrixStack().rotate(this.mc.getRenderManager().getCameraOrientation());
                event.getMatrixStack().scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = event.getMatrixStack().getLast().getMatrix();
                float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
                int j = (int) (f1 * 255.0F) << 24;
                FontRenderer fontrenderer = this.mc.fontRenderer;
                float f2 = (float) (-fontrenderer.getStringWidth(ping) / 2);
                fontrenderer.renderString(ping, f2, -20, 553648127, false, matrix4f, event.getRenderTypeBuffer(), flag, j, event.getPackedLight());
                if (flag) {
                    fontrenderer.renderString(ping, f2, -20, -1, false, matrix4f, event.getRenderTypeBuffer(), false, 0, event.getPackedLight());
                }

                event.getMatrixStack().pop();
            }
        }
    }
}
