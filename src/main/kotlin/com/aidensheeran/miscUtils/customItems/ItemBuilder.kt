package com.aidensheeran.miscUtils.customItems

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.attribute.AttributeModifier.Operation
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class ItemBuilder(private var material: Material, private var count: Int = 1) {
    private var displayName: Component? = null;
    private var enchantments: MutableSet<EnchantmentDefinition> = mutableSetOf();
    private var loreLines: MutableList<Component> = mutableListOf();
    private var attributes: MutableSet<AttributeModifierDefinition> = mutableSetOf();
    private var unbreakable: Boolean = false;
    private var flags: MutableSet<ItemFlag> = mutableSetOf();
    private var customFields: MutableMap<NamespacedKey, String> = mutableMapOf();

    fun setCount(count: Int) = apply { this.count = count };
    fun setDisplayName(displayName: Component) = apply { this.displayName = displayName };
    fun addEnchantment(enchantment: EnchantmentDefinition) = apply { this.enchantments.add(enchantment) };
    fun addLoreLines(vararg loreLine: Component) = apply {
        for (line in loreLine) {
            loreLines.add(line)
        }
    };

    fun addAttribute(attribute: AttributeModifierDefinition) = apply { this.attributes.add(attribute) };
    fun setUnbreakable(unbreakable: Boolean) = apply { this.unbreakable = unbreakable };
    fun addFlag(flag: ItemFlag) = apply { this.flags.add(flag) };
    fun setCustomField(key: NamespacedKey, value: String) = apply { this.customFields[key] = value };

    fun build(): ItemStack {
        // Make item with material and count
        val newItem = ItemStack(material, count);
        // Extract item metadata
        val newItemMeta = newItem.itemMeta;
        // Set display name if needed
        if (displayName != null) {
            newItemMeta.displayName(displayName);
        }
        // Apply enchantments
        for (enchantment in enchantments) {
            newItemMeta.addEnchant(enchantment.enchantment, enchantment.level, true);
        }
        // Add custom lore lines
        var lore: MutableList<Component> = mutableListOf();
        if (newItemMeta.hasLore()) {
            lore = newItemMeta.lore()!!;
        }
        for (loreLine in loreLines) {
            lore.add(loreLine);
        }
        newItemMeta.lore(lore);
        // Add attributes
        for (attribute in attributes) {
            @Suppress("UnstableApiUsage")
            newItemMeta.addAttributeModifier(
                attribute.attribute,
                AttributeModifier(
                    attribute.namespacedKey,
                    attribute.value,
                    attribute.attributeOperation,
                    attribute.slot
                )
            );
        }
        // Set unbreakable
        newItemMeta.isUnbreakable = unbreakable;
        // Add flags
        newItemMeta.addItemFlags(*flags.toTypedArray());
        // Add custom fields
        for (customField in customFields) {
            newItemMeta.persistentDataContainer.set(customField.key, PersistentDataType.STRING, customField.value);
        }
        // Apply Metadata
        newItem.setItemMeta(newItemMeta);
        return newItem;
    }

    @Suppress("UnstableApiUsage")
    class AttributeModifierDefinition(
        val attribute: Attribute,
        val namespacedKey: NamespacedKey,
        val attributeOperation: Operation,
        val value: Double,
        val slot: EquipmentSlotGroup,
    ) {
        class AttributeModifierBuilder(
            val plugin: JavaPlugin,
            private val attribute: Attribute,
            private val name: String,
        ) {
            private var operation: Operation = Operation.ADD_NUMBER;
            private var value: Double = 0.0;
            private var slot: EquipmentSlotGroup = EquipmentSlotGroup.ANY;
            fun setOperation(operation: Operation) = apply { this.operation = operation };
            fun setValue(value: Double) = apply { this.value = value };
            fun setSlot(slot: EquipmentSlotGroup) = apply { this.slot = slot };
            fun build(): AttributeModifierDefinition =
                AttributeModifierDefinition(attribute, NamespacedKey(plugin, name), operation, value, slot);
        }
    }

    class EnchantmentDefinition(val enchantment: Enchantment, val level: Int) {
        class EnchantmentBuilder(private val enchantment: Enchantment) {
            private var level: Int = 1;
            fun setLevel(level: Int) = apply { this.level = level };
            fun build(): EnchantmentDefinition = EnchantmentDefinition(enchantment, level);
        }
    }
}