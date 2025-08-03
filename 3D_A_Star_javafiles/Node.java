import net.minecraft.util.BlockPos;

public class Node {

    public BlockPos pos;
    public Node parent;

    public double gCost = 0;
    public double hCost;
    public double fCost;


    public boolean start;
    public boolean goal;
    public boolean checked;


    public Node(BlockPos p){
        pos = p;
    }
    public void SetAsStart(){
        start = true;
    }
    public void SetAsGoal(){
        goal = true;
    }
    public void SetAsChecked(){
        checked = true;
    }



}
