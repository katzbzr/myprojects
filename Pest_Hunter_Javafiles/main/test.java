package main;


import func.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import static func.Go.setKeyBindPressed;


@Mod(modid = "entityscanner", version = "1.0", name = "Entity Scanner")
public class    test {

    private static KeyBinding keyU;
    private static KeyBinding keyJ;
    private static KeyBinding keyY;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new RightClick());
        keyY = new KeyBinding("Press Y", Keyboard.KEY_Y, "Boaz Mod");
        keyU = new KeyBinding("Press U", Keyboard.KEY_U, "Boaz Mod");
        keyJ = new KeyBinding("Press J", Keyboard.KEY_J, "Boaz Mod");
    }


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.theWorld == null || mc.thePlayer == null) return;
        if (keyY.isPressed()){
            Hunt.HuntPests();
        }
        if(keyU.isPressed()) {
            Hunt.HuntStop();
        }
        if(keyJ.isPressed()){

        }
    }





}
