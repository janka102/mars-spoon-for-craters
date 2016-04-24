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
		
		if (x < 0) {
			x = 1024 - x;
		}
		if (z < 0) {
			z = 512 - z;
		}
		
		int imgX = (Math.abs(x) / 16) % 64;
		int imgZ = (Math.abs(z) / 16) % 31;

		if (MarsSpoon.tiles[imgZ][imgX] == null) {
			MarsSpoon.loadTile(imgZ, imgX);
		}
		// code needs to be modified to use imgX and imgY.
		// negative values should "wrap around" to the opposite side of the image
		
		BufferedImage tile = MarsSpoon.tiles[imgZ][imgX];

		int offsetX = Math.abs(x * 16) % 256;
		int offsetZ = Math.abs(z * 16) % 256;
		
		int xAdd = 1;
		int zAdd = 1;
		int xExtra = 0;
		int zExtra = 0;

		if (x < 0) {
			xAdd = -1;
			xExtra = 15;
		}

		if (z < 0) {
			zAdd = -1;
			zExtra = 15;
		}
		
		int height;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				height = tile.getRaster().getPixel(offsetX + (xExtra + i*xAdd), offsetZ + zExtra + j*zAdd, new int[4])[0];
				chunkData.setRegion(i, 0, j, i + 1, height, j + 1, Material.STAINED_CLAY);
				
				if (imgZ < 2 && random.nextBoolean()) {
					chunkData.setBlock(i, height, j, Material.SNOW);
				}
			}
		}
		
		return chunkData;
	}
}
