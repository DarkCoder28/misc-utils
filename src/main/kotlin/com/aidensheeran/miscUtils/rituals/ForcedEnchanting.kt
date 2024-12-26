package com.aidensheeran.miscUtils.rituals

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.Beacon
import org.bukkit.block.Block
import org.bukkit.block.data.type.Candle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ForcedEnchanting: Ritual {
    private lateinit var ritualType: Enchantment;
    private lateinit var targetItemEntity: Item;
    private lateinit var targetItem: ItemStack;

    private val ritualTypes = mapOf(
        Material.REDSTONE_BLOCK to Enchantment.EFFICIENCY,
        Material.DRIED_KELP_BLOCK to Enchantment.LURE,
        Material.IRON_BARS to Enchantment.BREACH,
        Material.POINTED_DRIPSTONE to Enchantment.THORNS,
        Material.FIRE to Enchantment.FLAME,
        Material.WATER to Enchantment.AQUA_AFFINITY,
        Material.MUD to Enchantment.BANE_OF_ARTHROPODS,
        Material.CHAIN to Enchantment.BINDING_CURSE,
        Material.TNT to Enchantment.BLAST_PROTECTION,
        Material.LIGHTNING_ROD to Enchantment.CHANNELING,
        Material.DEEPSLATE to Enchantment.DENSITY,
        Material.SEA_PICKLE to Enchantment.DEPTH_STRIDER,
        Material.WHITE_WOOL to Enchantment.FEATHER_FALLING,
        Material.LAVA to Enchantment.FIRE_ASPECT,
        Material.OBSIDIAN to Enchantment.FIRE_PROTECTION,
        Material.EMERALD_BLOCK to Enchantment.FORTUNE,
        Material.BLUE_ICE to Enchantment.FROST_WALKER,
        Material.BAMBOO_FENCE to Enchantment.IMPALING,
        Material.RESPAWN_ANCHOR to Enchantment.INFINITY,
        Material.PISTON to Enchantment.KNOCKBACK,
        Material.DIAMOND_BLOCK to Enchantment.LOOTING,
        Material.VERDANT_FROGLIGHT to Enchantment.LOYALTY,
        Material.GOLD_BLOCK to Enchantment.LUCK_OF_THE_SEA,
        Material.OXIDIZED_CHISELED_COPPER to Enchantment.MENDING,
        Material.DISPENSER to Enchantment.MULTISHOT,
        Material.SMITHING_TABLE to Enchantment.PIERCING,
        Material.MAGMA_BLOCK to Enchantment.POWER,
        Material.TARGET to Enchantment.PROJECTILE_PROTECTION,
        Material.CRYING_OBSIDIAN to Enchantment.PROTECTION,
        Material.STICKY_PISTON to Enchantment.PUNCH,
        Material.MANGROVE_ROOTS to Enchantment.RESPIRATION,
        Material.DEEPSLATE_REDSTONE_ORE to Enchantment.QUICK_CHARGE,
        Material.GRINDSTONE to Enchantment.SHARPNESS,
        Material.RAW_GOLD to Enchantment.SILK_TOUCH,
        Material.ZOMBIE_HEAD to Enchantment.SMITE,
        Material.SOUL_SOIL to Enchantment.SOUL_SPEED,
        Material.STONECUTTER to Enchantment.SWEEPING_EDGE,
        Material.HONEY_BLOCK to Enchantment.SWIFT_SNEAK,
        Material.NETHERITE_BLOCK to Enchantment.UNBREAKING,
        Material.OAK_LEAVES to Enchantment.VANISHING_CURSE,
        Material.BONE_BLOCK to Enchantment.WIND_BURST
    );

    override fun checkValid(executor: Player, target: Any): Boolean {
        if (target !is Block) return false;
        val block: Block = target;
        if (block.type != Material.BEACON) return false;
        val beacon: Beacon = block.state as Beacon;
        if (beacon.tier < 2) return false;
        if (!checkCandles(block)) return false;
        val blockN = offsetBlockLocation(block, 0, 0, -1);
        val blockE = offsetBlockLocation(block, 1, 0, 0);
        val blockS = offsetBlockLocation(block, 0, 0, 1);
        val blockW = offsetBlockLocation(block, -1, 0, 0);
        if (!blockN.type.isBlock) return false;
        if (!blockE.type.isBlock) return false;
        if (!blockS.type.isBlock) return false;
        if (!blockW.type.isBlock) return false;
        if (blockN.type != blockE.type || blockE.type != blockS.type || blockS.type != blockW.type || blockW.type != blockN.type) return false;
        ritualTypes[blockN.type]?.let {
            ritualType = it;
            val nearbyItems = block.world.getNearbyEntitiesByType(Item::class.java, block.location, 2.0);
            if (nearbyItems.isEmpty()) return false;
            val targetEntity = nearbyItems.first();
            targetItemEntity = targetEntity;
            targetItem = targetEntity.itemStack;
            return true;
        }
        return false;
    }

    override fun executeRitual(executor: Player, target: Any) {
        targetItemEntity.remove();
        val block: Block = target as Block;
        val outputLocation = block.location.clone();
        outputLocation.y+=1;
        executor.sendMessage("Performing 'The Ritual of Über Enchantment'!");
        // Play Whoosh Sound
        block.world.playSound(outputLocation, Sound.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.5f, 0.1f);
        // Hush Candles
        hushCandles(block);
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

    private fun checkCandles(block: Block): Boolean {
        val whiteCandles = listOf(
            Triple(1,0,-1),
            Triple(1,0,1),
            Triple(-1,0,-1),
            Triple(-1,0,1),
        );
        val cyanCandles = listOf(
            Triple(0, -1, -2),
            Triple(2, -1, 0),
            Triple(0, -1, 2),
            Triple(-2, -1, 0),
        );
        val redCandles = listOf(
            Triple(-1, -1, -2),
            Triple(2, -1, -1),
            Triple(1, -1, 2),
            Triple(-2, -1, 1),
        )
        val purpleCandles = listOf(
            Triple(1, -1, -2),
            Triple(2, -1, 1),
            Triple(-1, -1, 2),
            Triple(-2, -1, -1),
        )
        return checkCandleColour(block, whiteCandles, Material.WHITE_CANDLE) &&
            checkCandleColour(block, cyanCandles, Material.CYAN_CANDLE) &&
            checkCandleColour(block, redCandles, Material.RED_CANDLE) &&
            checkCandleColour(block, purpleCandles, Material.PURPLE_CANDLE);
    }
    private fun checkCandleColour(block: Block, offsets: List<Triple<Int, Int, Int>>, candleMaterial: Material): Boolean {
        for (candleOffset in offsets) {
            val candleBlock = offsetBlockLocation(block, candleOffset.first, candleOffset.second, candleOffset.third);
            if (candleBlock.type != candleMaterial) return false;
            val candle = candleBlock.blockData as Candle;
            if (!candle.isLit) return false;
        }
        return true;
    }

    private fun hushCandles(block: Block) {
        val candleOffsets = listOf(
            Triple(1,0,-1),
            Triple(1,0,1),
            Triple(-1,0,-1),
            Triple(-1,0,1),
            Triple(0, -1, -2),
            Triple(2, -1, 0),
            Triple(0, -1, 2),
            Triple(-2, -1, 0),
            Triple(-1, -1, -2),
            Triple(2, -1, -1),
            Triple(1, -1, 2),
            Triple(-2, -1, 1),
            Triple(1, -1, -2),
            Triple(2, -1, 1),
            Triple(-1, -1, 2),
            Triple(-2, -1, -1),
        );
        for (offset in candleOffsets) {
            val candleBlock = offsetBlockLocation(block, offset.first, offset.second, offset.third);
            if (candleBlock.blockData !is Candle) continue;
            val candle = candleBlock.blockData as Candle;
            candle.isLit = false;
            candleBlock.blockData = candle;
        }
    }
}