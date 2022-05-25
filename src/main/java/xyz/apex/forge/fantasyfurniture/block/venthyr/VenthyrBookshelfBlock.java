package xyz.apex.forge.fantasyfurniture.block.venthyr;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import xyz.apex.forge.apexcore.lib.block.VoxelShaper;
import xyz.apex.forge.apexcore.lib.multiblock.MultiBlockPattern;
import xyz.apex.forge.fantasyfurniture.block.base.set.SetBookshelfBlock;

public final class VenthyrBookshelfBlock extends SetBookshelfBlock
{
	public static final VoxelShape SHAPE = VoxelShaper.or(
			box(-16D, 0D, 2D, 16D, 3D, 16D),
			box(-15D, 3D, 3D, 15D, 29D, 16D),
			box(-16D, 29D, 2D, 16D, 32D, 16D)
	);

	public static final VoxelShaper SHAPER = VoxelShaper.forHorizontal(SHAPE, Direction.NORTH);

	public VenthyrBookshelfBlock(Properties properties, MultiBlockPattern pattern)
	{
		super(properties, pattern);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader level, BlockPos pos, ISelectionContext ctx)
	{
		Direction facing = blockState.getValue(FACING);
		VoxelShape shape = SHAPER.get(facing);
		int index = pattern.getIndex(blockState);

		if(index == 1 || index == 3)
		{
			Direction other = facing.getClockWise();
			shape = shape.move(other.getStepX(), 0D, other.getStepZ());
		}

		if(index == 2 || index == 3)
			shape = shape.move(0D, -1D, 0D);

		return shape;
	}
}
