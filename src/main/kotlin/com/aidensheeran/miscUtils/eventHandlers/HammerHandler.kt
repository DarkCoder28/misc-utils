package com.aidensheeran.miscUtils.eventHandlers

import com.aidensheeran.miscUtils.MiscUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

class HammerHandler() : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) {
        val heldItem: ItemStack = e.player.inventory.itemInMainHand;
        val itemMeta = heldItem.itemMeta;
        val dataContainer = itemMeta.persistentDataContainer;
        if (dataContainer.has(MiscUtils.customItemContainer.keys["hammer_type"]!!)) {
            val block = e.block;
            val location = e.player.location;
            var yaw: Int = location.yaw.toInt();
            var pitch: Int = location.pitch.toInt();
            if (yaw < 0)
                yaw += 360;
            yaw = (yaw + 45) / 90;
            var damageCount = 0;
            if (pitch <= -45 && pitch >= -90 || pitch >= 45 && pitch <= 90) {
                damageCount += breakBlock(block.getRelative(BlockFace.NORTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.EAST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.SOUTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.WEST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.NORTH_EAST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.SOUTH_EAST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.NORTH_WEST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.SOUTH_WEST), heldItem);
            } else if (yaw % 2 == 1) {
                damageCount += breakBlock(block.getRelative(BlockFace.UP), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.DOWN), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.NORTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.SOUTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH), heldItem);
            } else if (yaw % 2 == 0) {
                damageCount += breakBlock(block.getRelative(BlockFace.UP), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.DOWN), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.EAST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.WEST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST), heldItem);
                damageCount += breakBlock(block.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST), heldItem);
            }
            var item = e.player.inventory.itemInMainHand;
            var itemData = item.itemMeta as Damageable;
            itemData.damage += damageCount / 3;
            item.setItemMeta(itemData);
            e.player.inventory.setItemInMainHand(item);
        }
    }

    private fun breakBlock(block: Block, item: ItemStack): Int {
        if (
            !block.isEmpty
            && !block.isLiquid
            && block.type != Material.BEDROCK
            && block.type != Material.END_PORTAL
            && block.type != Material.END_PORTAL_FRAME
            && block.type != Material.BARRIER
            && block.type != Material.STRUCTURE_BLOCK
            && block.type != Material.COMMAND_BLOCK
            && block.type != Material.CHAIN_COMMAND_BLOCK
            && block.type != Material.REPEATING_COMMAND_BLOCK
        ) {
            return if (block.breakNaturally(item)) 1 else 0;
        }
        return 0;
    }

}