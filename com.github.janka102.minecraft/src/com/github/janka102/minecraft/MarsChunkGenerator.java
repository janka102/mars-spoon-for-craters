package com.github.janka102.minecraft;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

public class MarsChunkGenerator extends ChunkGenerator {
	static int[][] blocks = {
		{160, 83, 43, 1}, {148, 88, 108, 2}, {185, 132, 47, 4}, {160, 79, 80, 6},
		{58, 42, 36, 7}, {134, 107, 98, 8}, {86, 90, 91, 9}, {117, 71, 86, 10},
		{76, 51, 36, 12}, {142, 61, 50, 14}, {37, 23, 16, 15}, {126, 148, 183, 16}
	};

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

		int height;
		int pixel[] = new int[4];
		short closest;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				pixel = tile.getRaster().getPixel(offsetX + i, offsetZ + j, pixel);
				// Height is in the alpha bit
				// Then try to normalize it against the horizontal meter/pixel by dividing by 2
				height = pixel[3] / 2 + 72;
				closest = closestBlock(pixel[0], pixel[1], pixel[2]);
				if (closest != 16) {
					chunkData.setRegion(i, 1, j, i + 1, height, j + 1, new ItemStack(Material.STAINED_CLAY, 1, closest).getData());
				} else {
					chunkData.setRegion(i, 1, j, i + 1, height, j + 1, new ItemStack(Material.PACKED_ICE).getData());
				}
			}
		}

		// Set bottom to bedrock so no one can dig through
		chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.BEDROCK);

		return chunkData;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// Start at Olympus Mons
		int x = 2368;
		int z = 3459;
		int y = world.getHighestBlockYAt(x, z);

		return new Location(world, x, y, z);
	}

	public static double colorDist(double r1, double g1, double b1, double r2, double g2, double b2) {
		return Math.pow(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2), 0.5);
	}

	public static short closestBlock(int r, int g, int b) {
		// blocks[i][3] refers to the id of stain - except for 16, which refers to packed ice
		short min = -1;
		double minVal = Double.MAX_VALUE;
		double current;
		for (int i = 0; i < blocks.length; i++) {
			current = colorDist(r, g, b, blocks[i][0], blocks[i][1], blocks[i][2]);
			if (current < minVal) {
				min = (short) blocks[i][3];
				minVal = current;
			}
		}

		return min;
	}
}
