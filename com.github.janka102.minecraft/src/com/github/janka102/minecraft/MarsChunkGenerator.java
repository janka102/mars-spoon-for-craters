package com.github.janka102.minecraft;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class MarsChunkGenerator extends ChunkGenerator {	
	@Override
	public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biome) {
		ChunkGenerator.ChunkData chunkData = createChunkData(world);
		
		// x and z wraps around to the other side for negative values
		if (x < 0) {
			x += 1024;
		}
		if (z < 0) {
			z += 512;
		}
		
		int imgX = (Math.abs(x) / 16) % 64;
		int imgZ = (Math.abs(z) / 16) % 32;

		if (MarsSpoon.tiles[imgZ][imgX] == null) {
			MarsSpoon.loadTile(imgZ, imgX);
		}
		
		BufferedImage tile = MarsSpoon.tiles[imgZ][imgX];

		int offsetX = Math.abs(x * 16) % 256;
		int offsetZ = Math.abs(z * 16) % 256;
		int absZ = imgZ * 256;
		
		int height;
		int pixel[] = new int[4];

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				height = tile.getRaster().getPixel(offsetX + i, offsetZ + j, pixel)[3]; // Alpha bit
				chunkData.setRegion(i, 0, j, i + 1, height, j + 1, Material.STAINED_CLAY);
				
				if (imgZ < 2 && random.nextInt(512) > (absZ + offsetZ + j)) {
					chunkData.setBlock(i, height, j, Material.SNOW);
				}
			}
		}
		
		return chunkData;
	}
}
