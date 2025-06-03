package com.larashores.laraspipes.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import org.slf4j.Logger;

import javax.annotation.Nullable;
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
     * if there is an entity in the direction the Block is facing with a specified capability. If there is, it is
     * returned.
     *
     * @param <T> The type of the capability to get.
     * @param capability The capability to get.
     * @param level The level the BlockPos belongs to.
     * @param pos The BlockPos to check for adjacent capability from.
     *
     * @return The adjacent capability.
     */
    @Nullable
    public static <T> T getFacingCapability(Capability<T> capability, Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        var facing = state.getOptionalValue(BlockStateProperties.FACING);
        if (facing.isPresent()) {
            var opposite = facing.get().getOpposite();
            var facingPos = pos.relative(facing.get());
            var facingEntity = level.getBlockEntity(facingPos);

            if (facingEntity != null) {
                var cap = facingEntity.getCapability(capability, opposite);
                return cap.resolve().orElse(null);
            }
        }
        return null;
    }

    /**
     * Transfers as many items as possible from a source item handler to a destination item handler.
     *
     * @param filters If specified, only transfers items in the set of filters. Otherwise, transfers all types of items.
     * @param from The item handler to remove items from.
     * @param to The item handler to transfer items to.
     */
    public static void transferItems(Set<Item> filters, IItemHandler from, IItemHandler to) {
        for (var i = 0; i < from.getSlots(); i++) {
            var limit = from.getSlotLimit(i);
            var fromStack = from.extractItem(i, limit, true);
            var fromCount = fromStack.getCount();
            if (!fromStack.isEmpty() && (filters.isEmpty() || filters.contains(fromStack.getItem()))) {
                // Try to top off any existing stacks.
                for (int j = 0; j < to.getSlots() && !fromStack.isEmpty(); j++) {
                    var toStack = to.getStackInSlot(j);
                    if (
                        Objects.equals(fromStack.getItem(), toStack.getItem())
                        && Objects.equals(fromStack.getTag(), toStack.getTag())
                    ) {
                        fromStack = to.insertItem(j, fromStack, false);
                    }
                }
                // Otherwise add to any empty stacks.
                for (int j = 0; j < to.getSlots() && !fromStack.isEmpty(); j++) {
                    var toStack = to.getStackInSlot(j);
                    if (toStack.isEmpty()) {
                        fromStack = to.insertItem(j, fromStack, false);
                    }
                }
                from.extractItem(i, fromCount - fromStack.getCount(), false);
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
