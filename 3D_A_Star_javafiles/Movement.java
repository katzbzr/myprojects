import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static net.minecraft.util.MathHelper.normalizeAngle;

public class Movement {

    private static Node path;
    private static boolean arrive;
    private boolean walking;

    private static int[] t = new int[10];


    public static void walkPath(Node p){
        path = p;
        arrive = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;


        for(int i = 0; i < t.length; i++){
            t[i]++;
        }

        if(t[0] > 40){
            mc.thePlayer.addChatMessage(new ChatComponentText("11111"));
            t[0] = 0;
        }

        if(arrive || path == null) return;
        if(t[1] > 40){
            mc.thePlayer.addChatMessage(new ChatComponentText("2222"));
            t[1] = 0;
        }
        Node next = onWhatNode(path, mc);
        if(next == null || next.goal){
            walkforward(false, mc);
            walking = false;
            return;
        }

        if(!walking){
            walkforward(true, mc);
            walking = true;
        }
        lookAtSmooth(next.pos.getX() + 0.5, next.pos.getZ()+ 0.5,20, mc);

        if(t[2] > 40){
            mc.thePlayer.addChatMessage(new ChatComponentText("33333"));
            t[2] = 0;
        }

    }

    private void walkforward(boolean toggle, Minecraft mc) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), toggle);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), toggle);
    }


    private void lookAtSmooth(double targetX, double targetZ, float speed, Minecraft mc) {
        double dx = targetX - mc.thePlayer.posX;
        double dz = targetZ - mc.thePlayer.posZ;

        // Calculate target yaw angle
        float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90F;

        // Normalize angles
        float currentYaw = normalizeAngle(mc.thePlayer.rotationYaw);
        float delta = normalizeAngle(targetYaw - currentYaw);

        // Clamp the rotation speed
        if (Math.abs(delta) > speed) {
            delta = (delta > 0 ? speed : -speed);
        }

        mc.thePlayer.rotationYaw = normalizeAngle(currentYaw + delta);
    }

    private float normalizeAngle(float angle) {
        angle = angle % 360F;
        if (angle < -180F) angle += 360F;
        if (angle > 180F) angle -= 360F;
        return angle;
    }

    private Node onWhatNode(Node path, Minecraft mc){
        Node p = path;
        while(path != null){
            if((int) mc.thePlayer.posX == path.pos.getX() && (int) mc.thePlayer.posZ == path.pos.getZ()){
                return p;
            }
            p = path;
            path = path.parent;
        }
        return null;
    }

    private boolean isFacingTarget(double targetX, double targetZ, float toleranceDeg, Minecraft mc) {
        double dx = targetX - mc.thePlayer.posX;
        double dz = targetZ - mc.thePlayer.posZ;

        // Desired yaw to face target
        float targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90F;

        // Normalize both angles
        float currentYaw = normalizeAngle(mc.thePlayer.rotationYaw);
        targetYaw = normalizeAngle(targetYaw);

        float diff = normalizeAngle(targetYaw - currentYaw);
        return Math.abs(diff) <= toleranceDeg;
    }
}
