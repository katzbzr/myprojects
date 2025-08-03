import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class ChatListener {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onChatSend(ClientChatReceivedEvent event) {
        String command = event.message.getUnformattedText().toLowerCase();
        if (command.contains("!")) {
            event.setCanceled(true);


            int index = command.indexOf("!");
            if (index != -1) {
                command = command.substring(index); // cuts from '!' to end
                mc.thePlayer.addChatMessage(new ChatComponentText(command));
            }

            String[] parts = command.split(" ");

            if (parts.length == 4) {
                final int x = Integer.parseInt(parts[1]);
                final int y = Integer.parseInt(parts[2]);
                final int z = Integer.parseInt(parts[3]);


                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        final PathResult result = Pathfinder.PathFinder(new BlockPos(x, y , z), 1000000);
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

                mc.thePlayer.addChatMessage(new ChatComponentText("§aX: " + x + ", Y: " + y + ", Z: " + z));
            } else {
                mc.thePlayer.addChatMessage(new ChatComponentText("§cInvalid format. Use: ! x y z.... parts:" + parts.length));
            }



        }
    }
}