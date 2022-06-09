package xyz.apex.forge.fantasyfurniture.block.base.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import xyz.apex.forge.apexcore.lib.multiblock.MultiBlock;
import xyz.apex.forge.apexcore.lib.multiblock.MultiBlockPattern;

import javax.annotation.Nullable;

public class WaterLoggedMultiBlock extends MultiBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public WaterLoggedMultiBlock(Properties properties, MultiBlockPattern pattern)
	{
		super(properties, pattern);

		registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState blockState, BlockGetter level, BlockPos pos)
	{
		return !blockState.getValue(WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState blockState)
	{
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	@Override
	public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingBlockState, LevelAccessor level, BlockPos pos, BlockPos facingPos)
	{
		if(blockState.getValue(WATERLOGGED))
			level.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		return super.updateShape(blockState, facing, facingBlockState, level, pos, facingPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(BlockPlaceContext ctx, BlockState defaultBlockState)
	{
		var stateForPlacement = super.getPlacementState(ctx, defaultBlockState);

		if(stateForPlacement != null)
		{
			var fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
			var waterLogged = fluidState.is(FluidTags.WATER) && fluidState.isSource();
			return stateForPlacement.setValue(WATERLOGGED, waterLogged);
		}

		return null;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(WATERLOGGED);
		super.createBlockStateDefinition(builder);
	}
}
