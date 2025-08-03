package func;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.FMLLog;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LookAtPos {

    private static Thread currentLookThread;

    public static void lookContinuouslyAt(final Pos pos, final float speed, final long delayMs) {
        // Stop previous thread if running
        if (currentLookThread != null && currentLookThread.isAlive()) {
            currentLookThread.interrupt();
        }

        final Minecraft mc = Minecraft.getMinecraft();
        final EntityPlayer player = mc.thePlayer;

        currentLookThread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        final float[] targetRot = getRotationsTo(pos.x, pos.y, pos.z);
                        final float yaw = updateRotation(player.rotationYaw, targetRot[0], speed);
                        final float pitch = updateRotation(player.rotationPitch, targetRot[1], speed);

                        mc.addScheduledTask(new Runnable() {
                            public void run() {
                                player.rotationYaw = yaw;
                                player.rotationPitch = pitch;
                            }
                        });

                        Thread.sleep(delayMs);
                    }
                } catch (InterruptedException e) {
                    // Stop cleanly
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    FMLLog.severe("lookContinuouslyAt error:\n" + sw.toString());
                }
            }
        });

        currentLookThread.start();
    }
    /// LookAtPos.lookContinuouslyAt(100, 70, 100, 4.0f, 50); // 4° per step, every 50ms)
    public static void lookContinuouslyAtStop(){
        if (currentLookThread != null && currentLookThread.isAlive()) {
            currentLookThread.interrupt();
        }
    }

    public static float[] getRotationsTo(double targetX, double targetY, double targetZ) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        double dx = targetX - player.posX;
        double dy = targetY - (player.posY + player.getEyeHeight());
        double dz = targetZ - player.posZ;
        double distXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, distXZ));
        return new float[]{yaw, pitch};
    }

    private static float updateRotation(float current, float target, float maxChange) {
        float delta = MathHelper.wrapAngleTo180_float(target - current);
        if (delta > maxChange) delta = maxChange;
        if (delta < -maxChange) delta = -maxChange;
        return current + delta;
    }

}
