package com.larashores.laraspipes.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Collection of various status methods for use by other classes.
 */
public class Utils {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Given a Level and BlockPos, checks to see if there is a Block with a FACING property. If so, checks to see
     * if there is a Chest in the direction the Block is facing and returns its container if so.
     *
     * @param level The level the BlockPos belongs to.
     * @param pos The BlockPos to check for adjacent chests from.
     *
     * @return The container of the adjacent chest.
     */
    public static Container getFacingChest(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        var facing = state.getOptionalValue(BlockStateProperties.FACING);
        if (facing.isPresent()) {
            var facingPos = pos.relative(facing.get());
            var facingEntity = level.getBlockEntity(facingPos);
            var facingState = level.getBlockState(facingPos);
            var facingBlock = facingState.getBlock();

            if (
                facingState.is(Blocks.CHEST)
                && facingEntity instanceof ChestBlockEntity
                && facingBlock instanceof ChestBlock chestBlock
            ) {
                return ChestBlock.getContainer(chestBlock, facingState, level, facingPos, false);
            }
        }
        return null;
    }

    /**
     * Transfers as many items as possible from a source container to a destination chest.
     *
     * @param filters If specified, only transfers items in the set of filters. Otherwise, transfers all types of items.
     * @param from The container to remove items from.
     * @param to The container to transfer items to.
     */
    public static void transferItems(Set<Item> filters, Container from, Container to) {
        for (var i = 0; i < from.getContainerSize() && !from.isEmpty(); i++) {
            var fromStack = from.getItem(i);
            if (filters.isEmpty() || filters.contains(fromStack.getItem())) {
                // Try to top off any existing stacks.
                for (int j = 0; j < to.getContainerSize() && !fromStack.isEmpty(); j++) {
                    var toStack = to.getItem(j);
                    if (
                        Objects.equals(fromStack.getItem(), toStack.getItem())
                        && Objects.equals(fromStack.getTag(), toStack.getTag())
                    ) {
                        var count = Math.min(fromStack.getCount(), toStack.getMaxStackSize() - toStack.getCount());
                        toStack.grow(count);
                        fromStack.shrink(count);
                    }
                }
                // Otherwise add to any empty stacks.
                for (int j = 0; j < to.getContainerSize() && !fromStack.isEmpty(); j++) {
                    var toStack = to.getItem(j);
                    if (toStack.isEmpty()) {
                        var stack = fromStack.copyAndClear();
                        to.setItem(j, stack);
                    }
                }
            }
        }
    }

    /**
     * Returns the amount of rotation needed to turn a south facing block to face a direction.
     *
     * @param direction The direction to face.
     *
     * @return The x and y degrees needed to rotate the block to face the specified direction
     */
    public static ScreenRotation getRotation(Direction direction) {
        return new ScreenRotation(
            direction == Direction.UP ? 90 : direction == Direction.DOWN ? -90 : 0,
            direction.getAxis().isVertical() ? 0 : (int) direction.toYRot() % 360
        );
    }

    /**
     * Given a direction, returns a list of all directions sorted relative to the specified direction in this order: the
     * direction, left of the direction, right of the direction, above the direction, below the direction, and the
     * opposite direction.
     *
     * @param direction The sort direction.
     *
     * @return List of directions sorted by the specified sort direction.
     */
    public static List<Direction> getDirections(Direction direction) {
        var directions = new ArrayList<Direction>();
        directions.add(direction);
        for (var axis : Direction.Axis.values()) {
            if (axis != direction.getAxis()) {
                directions.add(direction.getCounterClockWise(axis));
                directions.add(direction.getClockWise(axis));
            }
        }
        directions.add(direction.getOpposite());
        return directions;
    }
}
