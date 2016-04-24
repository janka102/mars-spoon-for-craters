package com.github.janka102.minecraft;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class MarsSpoon extends JavaPlugin {
	
	static BufferedImage img = null;

    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
    	try {
		    img = ImageIO.read(new File("7-20.png"));
		} catch (IOException e) {
		    System.out.println("error");
		}
    	
        return new MarsChunkGenerator();
    }
}
