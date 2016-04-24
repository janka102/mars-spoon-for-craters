package com.github.janka102.minecraft;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class MarsChunkGenerator extends ChunkGenerator {	
	@Override
	public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biome) {
		ChunkGenerator.ChunkData chunkData = createChunkData(world);
		
		int imgX = (Math.abs(x) / 256) % 64;
		int imgY = (Math.abs(y) / 256) % 31;
		if (MarsSpoon.tiles[imgY][imgX] == null) {
			MarsSpoon.loadTile(imgY, imgX);
		}
		// code needs to be modified to use imgX and imgY.
		// negative values should "wrap around" to the opposite side of the image
		

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
				height = MarsSpoon.img.getRaster().getPixel(offsetX + (xExtra + i*xAdd), offsetZ + zExtra + j*zAdd, new int[4])[0];
				chunkData.setRegion(i, 0, j, i + 1, height, j + 1, Material.STONE);
			}
		}
		
		return chunkData;
	}
}
