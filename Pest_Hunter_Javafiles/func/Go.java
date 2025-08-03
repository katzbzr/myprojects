package func;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;

import java.lang.reflect.Field;

public class Go {
    private static Thread flightThread;
    public static void setKeyBindPressed(KeyBinding key, boolean pressed) {
        try {
            Field field;
            try {
                field = KeyBinding.class.getDeclaredField("pressed");
            } catch (NoSuchFieldException e) {
                field = KeyBinding.class.getDeclaredField("field_74513_e"); // fallback for obfuscated field
            }
            field.setAccessible(true);
            field.setBoolean(key, pressed);
        } catch (Exception ignored) {
        }
    }

    public static void W(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        KeyBinding forward = mc.gameSettings.keyBindForward;
        setKeyBindPressed(forward, true);
        KeyBinding sprint = mc.gameSettings.keyBindSprint;
        setKeyBindPressed(sprint, true);
    }

    public static void WStop(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        KeyBinding forward = mc.gameSettings.keyBindForward;
        setKeyBindPressed(forward, false);
        KeyBinding sprint = mc.gameSettings.keyBindSprint;
        setKeyBindPressed(sprint, false);
    }

    public static void Space(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        KeyBinding space = mc.gameSettings.keyBindJump;
        setKeyBindPressed(space, true);
    }

    public static void SpaceStop(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        KeyBinding space = mc.gameSettings.keyBindJump;
        setKeyBindPressed(space, false);
    }

    public static void Shift(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        KeyBinding shift = mc.gameSettings.keyBindSneak;
        setKeyBindPressed(shift, true);
    }

    public static void ShiftStop(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        KeyBinding shift = mc.gameSettings.keyBindSneak;
        setKeyBindPressed(shift, false);
    }

    public static void Fly(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        mc.thePlayer.capabilities.allowFlying = true;
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.sendPlayerAbilities();
    }

    public static void FlyStop(){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        mc.thePlayer.capabilities.allowFlying = false;
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.sendPlayerAbilities();
    }

    public static void movePlayerToY(final double targetY) {
        final Minecraft mc = Minecraft.getMinecraft();

        // Stop any previous flight thread
        if (flightThread != null && flightThread.isAlive()) {
            flightThread.interrupt();
        }

        flightThread = new Thread(new Runnable() {
            public void run() {


                if (mc.thePlayer == null || mc.theWorld == null) return;

                // Enable flying

                while (!Thread.currentThread().isInterrupted() &&
                        Math.abs(mc.thePlayer.posY - targetY) > 0.2) {
                    Fly();
                    if(targetY > mc.thePlayer.posY){
                        Space();
                    }
                    else{
                        Shift();
                    }


                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                // Stop both keys
                SpaceStop();
                ShiftStop();
            }
        });

        flightThread.start();
    }
}
