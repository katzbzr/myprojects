package func;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.*;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static func.Pos.PlayerDistance;
import static net.minecraft.init.Items.string;

public class InfoPest {
    public static final Map<Integer, Pos> plotMap = new HashMap<Integer, Pos>() {{
        int y = 80;
        put(0,  new Pos(0,    y,    0));
        put(1,  new Pos(0,    y,  -96));
        put(2,  new Pos(-96,  y,    0));
        put(3,  new Pos(96,   y,    0));
        put(4,  new Pos(0,    y,   96));
        put(5,  new Pos(-96,  y,  -96));
        put(6,  new Pos(96,  y,   -96));
        put(7,  new Pos(-96,   y, 96));
        put(8,  new Pos(96,   y,   96));
        put(9,  new Pos(0,    y, -192));
        put(10, new Pos(-192, y,   0));
        put(11, new Pos(192,  y,   0));
        put(12, new Pos(0,    y,  192));
        put(13, new Pos(-96,  y, -192));
        put(14, new Pos(96,   y, -192));
        put(15, new Pos(-192, y, -96));
        put(16, new Pos(192,  y, -96));
        put(17, new Pos(-192, y,  96));
        put(18, new Pos(192,  y,  96));
        put(19, new Pos(-96,  y,  192));
        put(20, new Pos(96,   y,  192));
        put(21, new Pos(-192, y, -192));
        put(22, new Pos(192,  y, -192));
        put(23, new Pos(-192, y,  192));
        put(24, new Pos(192,  y,  192));
    }};

    public static List<String> getScoreboardLines() {
        List<String> lines = new ArrayList<String>();

        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1); // Sidebar

        if (objective == null) return lines;


        List<Score> scores = new ArrayList<Score>(scoreboard.getSortedScores(objective));
        for (Score score : scores) {
            if (score == null || score.getPlayerName() == null) continue;

            String line = ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score.getPlayerName()), score.getPlayerName()).trim();
            if (!line.isEmpty() && !line.startsWith("§")) {
                lines.add(line);
            }
        }

        return lines;
    }

    public static int HowManyPests() {
        List<String> lines = getScoreboardLines();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return -1;
        for (String line : lines) {
            if (line.toLowerCase().contains("gard")){
                return extractLastNumber(line);
            }
        }
        return -1;
    }

    public static  boolean AreTherePests(){
        return (HowManyPests() > 0);
    }

    public static int extractLastNumber(String input) {
        Matcher matcher = Pattern.compile("(\\d+)").matcher(input);
        int last = -1;
        while (matcher.find()) {
            last = Integer.parseInt(matcher.group());
        }
        return last;
    }

    public static int[] extractAllNumbers(String input) {
        List<Integer> numberList = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            try {
                numberList.add(Integer.parseInt(matcher.group()));
            } catch (NumberFormatException ignored) {}
        }

        // Convert List<Integer> to int[]
        int[] result = new int[numberList.size()];
        for (int i = 0; i < numberList.size(); i++) {
            result[i] = numberList.get(i);
        }
        return result;
    }

    public static Pos[] AllPestsInRender() {

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return null;
        mc.thePlayer.addChatMessage(new ChatComponentText("111"));
        int size = 0;
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity == mc.thePlayer) continue;
            String customName = entity.getCustomNameTag();
            if(customName == null) continue;
            if (entity instanceof EntityArmorStand && entity.posY != 71.25 && entity.posY != 71.625 && (entity.posX < -46 || entity.posX > 44 || entity.posZ < -46 || entity.posZ > 44) ) {
                size++;
            }
        }
        Pos[] arr = new Pos[size];
        int k = 0;
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity == mc.thePlayer) continue;
            String customName = entity.getCustomNameTag();
            if(customName == null) continue;
            if (entity instanceof EntityArmorStand && entity.posY != 71.25 && entity.posY != 71.625 && (entity.posX < -46 || entity.posX > 44 || entity.posZ < -46 || entity.posZ > 44) ) {
                arr[k] = new Pos(entity.posX, entity.posY, entity.posZ);
                mc.thePlayer.addChatMessage(new ChatComponentText("pest: " + entity.posX + "---" + entity.posY + "---" + entity.posZ));
                k++;
            }
        }
        return arr;
    }

    public static boolean ArePestsInRender(){return (AllPestsInRender() != null && Objects.requireNonNull(AllPestsInRender()).length != 0);}

    public static Pos ClosestPest(){
        Pos[] arr = AllPestsInRender();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return null;

        if( arr == null || arr.length == 0) return  null;
        int indexsmallest = 0;

        for(int i = 0; i < arr.length; i++){
            if(PlayerDistance(arr[i]) < PlayerDistance(arr[indexsmallest])) indexsmallest = i;
        }
        if(arr[indexsmallest] == null) return null;
        mc.thePlayer.addChatMessage(new ChatComponentText("Closest Pest:" + arr[indexsmallest].x + arr[indexsmallest].y + arr[indexsmallest].z));
        return arr[indexsmallest];
    }

    public static int PlotPlayer() {
        List<String> lines = getScoreboardLines();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return -1;
        for (String line : lines) {
            if (line.toLowerCase().contains("plot")){
                return extractAllNumbers(line)[1];
            }
        }
        return -1;
    }

    public static int[] PlotsWithPests() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return null;

        Collection<NetworkPlayerInfo> tabEntries = mc.getNetHandler().getPlayerInfoMap();
        for (NetworkPlayerInfo info : tabEntries) {
            String rawName = info.getGameProfile().getName();
            String unformatted = info.getDisplayName() != null ? info.getDisplayName().getUnformattedText() : rawName;
            if(unformatted.toLowerCase().contains("plots")){
                return extractAllNumbers(unformatted) ;
            }
        }
        return null;
    }

    public static Pos ClosestPlotPest(){
        int[] pestplot = PlotsWithPests();
        if(pestplot == null) return null;
        int closestpestplot = pestplot[0];
        for(int n : pestplot){
            if( Pos.PlayerDistance(getPlotPos(n)) < Pos.PlayerDistance(getPlotPos(closestpestplot))){
                closestpestplot = n;
            }
        }
        return getPlotPos(closestpestplot);
    }

    public static Pos getPlotPos(int plotId) {
        return plotMap.get(plotId);
    }
}
