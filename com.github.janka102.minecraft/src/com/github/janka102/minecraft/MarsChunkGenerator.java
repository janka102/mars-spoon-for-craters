package com.github.janka102.minecraft;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class MarsChunkGenerator extends ChunkGenerator {
	 @Override
	 public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biome) {
		 ChunkGenerator.ChunkData chunkData = super.createChunkData(world);

		 chunkData.setRegion(0, 0, 0, 16, chunkData.getMaxHeight() / 8, 16, Material.STONE);
		 
		 return chunkData;
	 }
}
