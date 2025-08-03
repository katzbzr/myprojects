
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;


@Mod(modid = "pathfindermod", name = "Pathfinder Mod", version = "1.0")
public class main {
    public static final Logger LOGGER = LogManager.getLogger("PathfinderMod");
    private static KeyBinding KeyY;
    private static KeyBinding KeyU;
    private static KeyBinding KeyH;
    //private static KeyBinding KeyJ;

    public int sto;
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new Pathfinder());
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new ChatListener());
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new Movement());
        KeyY = new KeyBinding("Press Y", Keyboard.KEY_Y, "Pathfinder Mod");
        KeyU = new KeyBinding("Press U", Keyboard.KEY_U, "Pathfinder Mod");
        KeyH = new KeyBinding("Press H", Keyboard.KEY_H, "Pathfinder Mod");
        //KeyJ = new KeyBinding("Press J", Keyboard.KEY_J, "Pathfinder Mod");

        ClientRegistry.registerKeyBinding(KeyY);
        ClientRegistry.registerKeyBinding(KeyU);
        ClientRegistry.registerKeyBinding(KeyH);
    }


    private static Node current;
    private static ArrayList<Node> openList;


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        if (KeyY.isPressed()) {
            mc.thePlayer.addChatMessage(new ChatComponentText("Y key was pressed!"));
            mc.thePlayer.addChatMessage(new ChatComponentText("x" + mc.thePlayer.posX));
        }

        if(KeyU.isPressed()){
            mc.thePlayer.addChatMessage(new ChatComponentText("U key was pressed!"));

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    final PathResult result = Pathfinder.PathFinder(new BlockPos(100, 70 , 100), 1000000);
                    if (result == null) return;

                    final Node path = result.node;
                    if (path == null) {
                        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                            public void run() {
                                mc.thePlayer.addChatMessage(new ChatComponentText("path = null"));
                            }
                        });
                        return;
                    }

                    StringBuilder msg = new StringBuilder();
                    int step = (int) path.gCost;
                    Node p = path;
                    while (!p.start && step > -1) {
                        step--;
                        msg.append("TT").append(step).append("TT").append(p.pos).append("\n");
                        p = p.parent;
                    }

                    Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                        public void run() {
                            mc.thePlayer.addChatMessage(new ChatComponentText("steps:" + (int) path.gCost));
                            mc.thePlayer.addChatMessage(new ChatComponentText("how many times search() function run: " + result.stepCount));
                            Pathfinder.ShowPath(path);
                        }
                    });

                    System.out.println("Running in a separate thread.");
                }
            });
            thread.start();


        }


        if(KeyH.isPressed()){
            mc.thePlayer.addChatMessage(new ChatComponentText("U key was pressed!"));

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    final PathResult result = Pathfinder.PathFinder(new BlockPos(100, 70 , 100), 10000);
                    if (result == null) return;

                    final Node path = result.node;
                    if (path == null) {
                        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                            public void run() {
                                mc.thePlayer.addChatMessage(new ChatComponentText("path = null"));
                            }
                        });
                        return;
                    }

                    StringBuilder msg = new StringBuilder();
                    int step = (int) path.gCost;
                    Node p = path;
                    while (!p.start && step > -1) {
                        step--;
                        msg.append("TT").append(step).append("TT").append(p.pos).append("\n");
                        p = p.parent;
                    }

                    Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                        public void run() {
                            mc.thePlayer.addChatMessage(new ChatComponentText("steps:" + (int) path.gCost));
                            mc.thePlayer.addChatMessage(new ChatComponentText("how many times search() function run: " + result.stepCount));
                            Pathfinder.ShowPath(path);
                            Movement.walkPath(path);
                        }
                    });

                    System.out.println("Running in a separate thread.");


                }
            });
            thread.start();


        }
    }

}
