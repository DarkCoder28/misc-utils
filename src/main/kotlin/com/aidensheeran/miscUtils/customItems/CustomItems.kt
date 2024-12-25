@file:Suppress("UnstableApiUsage")

package com.aidensheeran.miscUtils.customItems

import com.aidensheeran.miscUtils.MiscUtils
import com.aidensheeran.miscUtils.customItems.ItemBuilder.AttributeModifierDefinition.AttributeModifierBuilder
import com.aidensheeran.miscUtils.customItems.ItemBuilder.EnchantmentDefinition.EnchantmentBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlotGroup

class CustomItems(plugin: MiscUtils) {

    @Suppress("unused")
    companion object {
        const val ARMORED_ELYTRA = "armored_elytra";
        const val WOODEN_HAMMER = "wooden_hammer";
        const val STONE_HAMMER = "stone_hammer";
        const val GOLDEN_HAMMER = "golden_hammer";
        const val IRON_HAMMER = "iron_hammer";
        const val DIAMOND_HAMMER = "diamond_hammer";
        const val NETHERITE_HAMMER = "netherite_hammer";
        const val CUSTOM_HAMMER_TYPE = "hammer_type";
        const val RITUAL_STARTER = "ritual_starter";
    }

    val keys = mapOf(
        CUSTOM_HAMMER_TYPE to NamespacedKey(plugin, CUSTOM_HAMMER_TYPE),
        RITUAL_STARTER to NamespacedKey(plugin, RITUAL_STARTER),
    );
    val items = mapOf(
        ARMORED_ELYTRA to
                ItemBuilder(Material.ELYTRA, 1)
                    .addEnchantment(
                        EnchantmentBuilder(Enchantment.MENDING)
                            .build()
                    )
                    .addAttribute(
                        AttributeModifierBuilder(plugin, Attribute.GENERIC_ARMOR, "ElytraArmor")
                            .setSlot(EquipmentSlotGroup.CHEST)
                            .setValue(8.0)
                            .build()
                    )
                    .addAttribute(
                        AttributeModifierBuilder(
                            plugin,
                            Attribute.GENERIC_ARMOR_TOUGHNESS,
                            "ElytraArmorToughness"
                        )
                            .setSlot(EquipmentSlotGroup.CHEST)
                            .setValue(2.0)
                            .build()
                    )
                    .build(),
        WOODEN_HAMMER to
                ItemBuilder(Material.WOODEN_PICKAXE, 1)
                    .setDisplayName(Component.text("Wooden Hammer").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[CUSTOM_HAMMER_TYPE]!!, WOODEN_HAMMER)
                    .build(),
        STONE_HAMMER to
                ItemBuilder(Material.STONE_PICKAXE, 1)
                    .setDisplayName(Component.text("Stone Hammer").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[CUSTOM_HAMMER_TYPE]!!, STONE_HAMMER)
                    .build(),
        GOLDEN_HAMMER to
                ItemBuilder(Material.GOLDEN_PICKAXE, 1)
                    .setDisplayName(Component.text("Golden Hammer").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[CUSTOM_HAMMER_TYPE]!!, GOLDEN_HAMMER)
                    .build(),
        IRON_HAMMER to
                ItemBuilder(Material.IRON_PICKAXE, 1)
                    .setDisplayName(Component.text("Iron Hammer").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[CUSTOM_HAMMER_TYPE]!!, IRON_HAMMER)
                    .build(),
        DIAMOND_HAMMER to
                ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                    .setDisplayName(Component.text("Diamond Hammer").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[CUSTOM_HAMMER_TYPE]!!, DIAMOND_HAMMER)
                    .build(),
        NETHERITE_HAMMER to
                ItemBuilder(Material.NETHERITE_PICKAXE, 1)
                    .setDisplayName(Component.text("Netherite Hammer").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[CUSTOM_HAMMER_TYPE]!!, NETHERITE_HAMMER)
                    .build(),
        RITUAL_STARTER to
                ItemBuilder(Material.BLAZE_ROD, 1)
                    .setDisplayName(Component.text("Ritual Starter").decoration(TextDecoration.ITALIC, false))
                    .setCustomField(keys[RITUAL_STARTER]!!, RITUAL_STARTER)
                    .build(),
    );
}