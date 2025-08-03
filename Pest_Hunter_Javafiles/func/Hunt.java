package func;

import net.minecraft.client.Minecraft;

import java.util.Objects;

public class Hunt {


    private static Thread huntThread = null;
    private static boolean toggle = true;

    public static void HuntPests() {
        if (huntThread != null && huntThread.isAlive()) return;

        toggle = true;

        huntThread = new Thread(new Runnable() {
            public void run() {
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.theWorld == null || mc.thePlayer == null) return;

                while (toggle && InfoPest.AreTherePests()) {
                    Go.movePlayerToY(80);

                    while (toggle && !InfoPest.ArePestsInRender()) {
                        LookAtPos.lookContinuouslyAt(InfoPest.ClosestPest(), 6, 4);
                        Go.W();

                        if (Pos.PlayerDistance(InfoPest.ClosestPest()) > 25) {
                            RightClick.setRightClick(false);
                            continue;
                        }
                        if(!RightClick.isHolding()) {
                            RightClick.setRightClick(true);
                        }
                        try {
                            Thread.sleep(50); // allow game thread to continue
                        } catch (InterruptedException e) {
                            break;
                        }
                    }

                    LookAtPos.lookContinuouslyAtStop();
                    Go.WStop();
                }
            }
        });

        huntThread.start();
    }

    public static void HuntStop() {
        toggle = false;
        if (huntThread != null) huntThread.interrupt();
    }
}
