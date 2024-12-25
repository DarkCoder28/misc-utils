package com.aidensheeran.miscUtils.rituals

import org.bukkit.Material
import org.bukkit.block.Beacon
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ForcedEnchanting: Ritual {
    private lateinit var ritualType: Enchantment;
    private lateinit var targetItemEntity: Item;
    private lateinit var targetItem: ItemStack;

    override fun checkValid(executor: Player, target: Any): Boolean {
        if (target !is Block) return false;
        val block: Block = target;
        if (block.type != Material.BEACON) return false;
        val beacon: Beacon = block.state as Beacon;
        if (beacon.tier < 2) return false;
        val blockN = offsetBlockLocation(block, 0, 0, -1);
        val blockE = offsetBlockLocation(block, 1, 0, 0);
        val blockS = offsetBlockLocation(block, 0, 0, 1);
        val blockW = offsetBlockLocation(block, -1, 0, 0);
        if (!blockN.type.isBlock) return false;
        if (!blockE.type.isBlock) return false;
        if (!blockS.type.isBlock) return false;
        if (!blockW.type.isBlock) return false;
        if (blockN.type != blockE.type || blockE.type != blockS.type || blockS.type != blockW.type || blockW.type != blockN.type) return false;
        ritualType = when(blockN.type) {
            Material.REDSTONE_BLOCK -> Enchantment.EFFICIENCY
            else -> return false;
        }
        val nearbyItems = block.world.getNearbyEntitiesByType(Item::class.java, block.location, 2.0);
        if (nearbyItems.isEmpty()) return false;
        val targetEntity = nearbyItems.first();
        targetItemEntity = targetEntity;
        targetItem = targetEntity.itemStack;
        return true;
    }

    override fun executeRitual(executor: Player, target: Any) {
        targetItemEntity.remove();
        val block: Block = target as Block;
        val outputLocation = block.location.clone();
        outputLocation.y+=1;
        // Delete Blocks
        removeCatalysts(block);
        // Enchant Item
        val itemMeta = targetItem.itemMeta;
        if (itemMeta.hasEnchant(ritualType)) {
            val enchantLvl = itemMeta.getEnchantLevel(ritualType);
            itemMeta.removeEnchant(ritualType);
            itemMeta.addEnchant(ritualType, enchantLvl + 1, true);
        } else {
            itemMeta.addEnchant(ritualType, 1, true);
        }
        targetItem.setItemMeta(itemMeta);
        // Output Item
        block.world.dropItemNaturally(outputLocation, targetItem);
    }


    @Suppress("SameParameterValue")
    private fun offsetBlockLocation(block: Block, xOffset: Int, yOffset: Int, zOffset: Int): Block {
        val blockLocation = block.location.clone().toBlockLocation();
        blockLocation.x += xOffset;
        blockLocation.y += yOffset;
        blockLocation.z += zOffset;
        return blockLocation.block;
    }

    private fun removeCatalysts(center: Block) {
        val blockN = offsetBlockLocation(center, 0, 0, -1);
        val blockE = offsetBlockLocation(center, 1, 0, 0);
        val blockS = offsetBlockLocation(center, 0, 0, 1);
        val blockW = offsetBlockLocation(center, -1, 0, 0);
        blockN.type = Material.AIR;
        blockE.type = Material.AIR;
        blockS.type = Material.AIR;
        blockW.type = Material.AIR;
    }
}