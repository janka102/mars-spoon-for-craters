package com.github.janka102.minecraft;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class MarsSpoon extends JavaPlugin {
	static BufferedImage[][] tiles = new BufferedImage[32][64];
	
	public static void loadTile(int imgZ, int imgX) {
		try {
			tiles[imgZ][imgX] = ImageIO.read(MarsSpoon.class.getResource("/" + imgZ + "-" + imgX + ".png"));
		} catch (IOException e) {
			System.out.println("error" + " " + imgZ + "-" + imgX + ".png ");
		}
	}
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new MarsChunkGenerator();
    }
}
