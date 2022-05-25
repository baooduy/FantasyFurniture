package xyz.apex.forge.fantasyfurniture.block.venthyr;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import xyz.apex.forge.apexcore.lib.block.VoxelShaper;
import xyz.apex.forge.apexcore.lib.multiblock.MultiBlockPattern;
import xyz.apex.forge.fantasyfurniture.block.base.set.SetBedDoubleBlock;

public final class VenthyrBedDoubleBlock extends SetBedDoubleBlock
{
	public static final VoxelShape SHAPE = VoxelShaper.or(
			box(-16D, 0D, 0D, -13D, 2D, 3D),
			box(-15.5D, 2D, .5D, -13.5D, 12D, 2.5D),
			box(-15.5D, 2D, 29.5D, -13.5D, 12D, 31.5D),
			box(13.5D, 2D, 29.5D, 15.5D, 12D, 31.5D),
			box(13.5D, 2D, .5D, 15.5D, 12D, 2.5D),
			box(-16D, 12D, 0D, -13D, 14D, 3D),
			box(-16D, 12D, 29D, -13D, 14D, 32D),
			box(13D, 12D, 29D, 16D, 14D, 32D),
			box(13D, 12D, 0D, 16D, 14D, 3D),
			box(13D, 0D, 0D, 16D, 2D, 3D),
			box(13D, 0D, 29D, 16D, 2D, 32D),
			box(-16D, 0D, 29D, -13D, 2D, 32D),
			box(-15.5D, 0D, 2.5D, 15.5D, 5D, 29.5D),
			box(-13.5D, 0D, 29.5D, 13.5D, 12D, 31.5D),
			box(-13.5D, 0D, .5D, 13.5D, 14D, 2.5D),
			box(-14.5D, 0D, 2.5D, 14.5D, 8D, 29.5D)
	);

	public static final VoxelShaper SHAPER = VoxelShaper.forHorizontal(SHAPE, Direction.NORTH);

	public VenthyrBedDoubleBlock(Properties properties, MultiBlockPattern pattern)
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
			shape = shape.move(facing.getStepX(), 0D, facing.getStepZ());

		return shape;
	}
}
