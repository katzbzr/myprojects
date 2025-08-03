package func;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Pos
{
    public final double x;
    public final double y;
    public final double z;

    public Pos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static double PlayerDistance(Pos pos) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        if (player == null || mc.theWorld == null || pos == null) return -1;

        double dx = player.posX - pos.x;
        double dy = player.posY - pos.y;
        double dz = player.posZ - pos.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }



}
