import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Pathfinder {

    public static final Logger LOGGER = LogManager.getLogger("3D_A_star");
    private static Node start;
    private static Node goal;
    private static Node current;
    private static boolean goalReached;
    private static ArrayList<Node> openList;
    private static ArrayList<BlockPos> checkedList;
    private static final Set<BlockPos> activeParticles = new HashSet<BlockPos>();

    public static PathResult PathFinder(BlockPos g, int maxruns){
        activeParticles.clear();
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) {return null;}
        goal = new Node(g);
        start = new Node(new BlockPos(Math.floor(mc.thePlayer.posX), Math.floor(mc.thePlayer.posY) - 1, Math.floor(mc.thePlayer.posZ)));
        goal.SetAsGoal();
        start.SetAsStart();
        openList = new ArrayList<Node>();
        checkedList = new ArrayList<BlockPos>();
        current = start;
        goalReached = false;

        int maxstep = 0;
        if(maxruns < 0){
            maxruns = Integer.MAX_VALUE;
        }
        mc.thePlayer.addChatMessage(new ChatComponentText("current before loop:" + current.pos));
        StringBuilder str = new StringBuilder();
        while(maxstep < maxruns && !goalReached){
            search();
            str.append("#").append(maxstep).append(":").append(current.pos).append("\n");
            maxstep++;
        }
        LOGGER.error(str.toString());
        mc.thePlayer.addChatMessage(new ChatComponentText("Start point:" + start.pos));
        if(!goalReached){
            mc.thePlayer.addChatMessage(new ChatComponentText("path not found" + current.pos));
            return null;
        }
        mc.thePlayer.addChatMessage(new ChatComponentText("path found!"));
        return new PathResult(current, maxstep);
    }

    private static void GetCost(Node node){
        node.gCost = current.gCost + 1;

        int dx = Math.abs(node.pos.getX() - goal.pos.getX());
        int dy = Math.abs(node.pos.getY() - goal.pos.getY());
        int dz = Math.abs(node.pos.getZ() - goal.pos.getZ());

        node.hCost = dx + dy + dz;

        node.fCost = (node.hCost * 2) + node.gCost;
    }

    private static void search(){
        if(current.pos.getX() == goal.pos.getX() && current.pos.getY() == goal.pos.getY() && current.pos.getZ() == goal.pos.getZ()){
            goalReached = true;
            return;
        }
        if(!goalReached){
            current.SetAsChecked();
            checkedList.add(current.pos);
            openList.remove(current);

            //Y - 1
            //OPEN NODE X + 1
            openNode(new Node(new BlockPos((current.pos.getX() + 1), current.pos.getY() - 1, current.pos.getZ())));
            //OPEN NODE X - 1
            openNode(new Node(new BlockPos((current.pos.getX() - 1), current.pos.getY() - 1, current.pos.getZ())));
            //OPEN NODE Z + 1
            openNode(new Node(new BlockPos((current.pos.getX()), current.pos.getY() - 1, current.pos.getZ() + 1)));
            //OPEN NODE Z - 1
            openNode(new Node(new BlockPos((current.pos.getX()), current.pos.getY() - 1, current.pos.getZ() - 1)));

            //Y + 0
            //OPEN NODE X + 1
            openNode(new Node(new BlockPos((current.pos.getX() + 1), current.pos.getY(), current.pos.getZ())));
            //OPEN NODE X - 1
            openNode(new Node(new BlockPos((current.pos.getX() - 1), current.pos.getY(), current.pos.getZ())));
            //OPEN NODE Z + 1
            openNode(new Node(new BlockPos((current.pos.getX()), current.pos.getY(), current.pos.getZ() + 1)));
            //OPEN NODE Z - 1
            openNode(new Node(new BlockPos((current.pos.getX()), current.pos.getY(), current.pos.getZ() - 1)));

            //Y + 1
            //OPEN NODE X + 1
            openNode(new Node(new BlockPos((current.pos.getX() + 1), current.pos.getY() + 1, current.pos.getZ())));
            //OPEN NODE X - 1
            openNode(new Node(new BlockPos((current.pos.getX() - 1), current.pos.getY() + 1, current.pos.getZ())));
            //OPEN NODE Z + 1
            openNode(new Node(new BlockPos((current.pos.getX()), current.pos.getY() + 1, current.pos.getZ() + 1)));
            //OPEN NODE Z - 1
            openNode(new Node(new BlockPos((current.pos.getX()), current.pos.getY() + 1, current.pos.getZ() - 1)));

            int bestNodeIndex = 0;
            double bestNodefCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {
                Node node = openList.get(i);

                if (node.fCost < bestNodefCost) {
                    bestNodefCost = node.fCost;
                    bestNodeIndex = i;
                } else if (node.fCost == bestNodefCost) {
                    if (node.gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            if (openList.isEmpty()) {
                goalReached = false;
                LOGGER.warn("openList empty — no path found.");
                return;
            }
            current = openList.get(bestNodeIndex);
        }

    }

    private static void openNode(Node node){
        World world = Minecraft.getMinecraft().theWorld;
        if (checkedList.contains(node.pos)) return;
        IBlockState state = world.getBlockState(node.pos);
        Block block = state.getBlock();

        if(!myFullBlock(node.pos, world)) {
            checkedList.add(node.pos);
            return;
        }
        if((!iswalkthrogh(new BlockPos(node.pos.getX(), node.pos.getY() + 1, node.pos.getZ()), world)
                || !iswalkthrogh(new BlockPos(node.pos.getX(), node.pos.getY() + 2, node.pos.getZ()), world)
                || !iswalkthrogh(new BlockPos(node.pos.getX(), current.pos.getY() + 2, node.pos.getZ()), world))
                && !isBottomSlab(new BlockPos(node.pos.getX(), node.pos.getY() + 1, node.pos.getZ()), world)) return ;

        if(isBottomSlab(new BlockPos(current.pos.getX(), current.pos.getY() + 1, current.pos.getZ()), world)
                &&  (!iswalkthrogh(new BlockPos(node.pos.getX(), current.pos.getY() + 3, node.pos.getZ()), world)
                ||  isBottomSlab(new BlockPos(node.pos.getX(), current.pos.getY() + 3, node.pos.getZ()), world))) return;

        if(node.pos.getY() == current.pos.getY() && isBottomSlab(new BlockPos(node.pos.getX(), node.pos.getY() + 1, node.pos.getZ()), world)
                && !iswalkthrogh(new BlockPos(node.pos.getX(), current.pos.getY() + 3, node.pos.getZ()), world)){
            return;
        }
        if(node.pos.getY() == (current.pos.getY() + 1)
                && isBottomSlab(new BlockPos(node.pos.getX(), node.pos.getY() + 1, node.pos.getZ()), world)
                && (!isBottomSlab(new BlockPos(current.pos.getX(), node.pos.getY(), current.pos.getZ()), world)
                || !iswalkthrogh(new BlockPos(node.pos.getX(), node.pos.getY() + 3, node.pos.getZ()), world)
                || !iswalkthrogh(new BlockPos(current.pos.getX(), node.pos.getY() + 3, current.pos.getZ()), world))){
            return;
        }
        node.parent = current;
        GetCost(node);
        openList.add(node);
    }

    private static boolean openListContainsPos(BlockPos pos) {
        for (Node n : openList) {
            if (n.pos.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    private static boolean myFullBlock(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block.isFullBlock() || block instanceof BlockStairs) {
            return true;
        } else if (block instanceof BlockSlab) {
            // Only single slabs have HALF
            if (state.getProperties().containsKey(BlockSlab.HALF)) {
                BlockSlab.EnumBlockHalf half = (BlockSlab.EnumBlockHalf) state.getValue(BlockSlab.HALF);
                return half != BlockSlab.EnumBlockHalf.BOTTOM;
            } else {
                // It's a double slab, treat it as full
                return true;
            }
        }

        return false;
    }

    private static boolean iswalkthrogh(BlockPos pos, World world){
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        return block.isAir(world, pos)
                || (!block.isFullCube()
                && !block.getMaterial().isSolid()
                && !(block instanceof BlockSlab)
                && !(block instanceof BlockStairs)
                && !(block instanceof BlockLiquid));
    }

    private static boolean isBottomSlab(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof BlockSlab) {
            // Only single slabs have HALF; double slabs don't
            if (state.getProperties().containsKey(BlockSlab.HALF)) {
                BlockSlab.EnumBlockHalf half = (BlockSlab.EnumBlockHalf) state.getValue(BlockSlab.HALF);
                return half == BlockSlab.EnumBlockHalf.BOTTOM;
            }
        }

        return false;
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null){return;}
        if (event.phase == TickEvent.Phase.END || activeParticles.isEmpty()) return;

        for (BlockPos p : activeParticles) {
            mc.theWorld.spawnParticle(
                    EnumParticleTypes.FLAME,
                    p.getX() + 0.5,
                    p.getY() + 1.5,
                    p.getZ() + 0.5,
                    0, 0, 0
            );
        }
    }
    public static void ShowPath(Node node){
        activeParticles.clear();
        Node p = node;
        while(!p.start){
            activeParticles.add(p.pos);
            p = p.parent;
        }
    }

}
