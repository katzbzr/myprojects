package func;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Method;

public class RightClick {

    private static boolean holdingRightClick = false;

    public static void toggleRightClick() {
        holdingRightClick = !holdingRightClick;
    }

    public static void setRightClick(boolean active) {
        holdingRightClick = active;
    }

    public static boolean isHolding() {
        return holdingRightClick;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();

        if (holdingRightClick && mc.currentScreen == null) {
            try {
                Method rightClick = Minecraft.class.getDeclaredMethod("rightClickMouse");
                rightClick.setAccessible(true);
                rightClick.invoke(mc);
            } catch (Exception e) {
                // fallback for obfuscated name
                try {
                    Method rightClick = Minecraft.class.getDeclaredMethod("func_147121_ag");
                    rightClick.setAccessible(true);
                    rightClick.invoke(mc);
                } catch (Exception ignored) {}
            }
        }
    }
}
