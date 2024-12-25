package com.aidensheeran.miscUtils.recipeManager

import com.aidensheeran.miscUtils.MiscUtils
import com.aidensheeran.miscUtils.customItems.CustomItems
import com.aidensheeran.miscUtils.customItems.ItemBuilder
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.Recipe
import org.bukkit.plugin.java.JavaPlugin

class RecipeDefinitions {
    private val customRecipes: Set<Recipe> = setOf(
        ShapelessRecipeBuilder(
            NamespacedKey(MiscUtils.plugin, CustomItems.ARMORED_ELYTRA),
            MiscUtils.customItems[CustomItems.ARMORED_ELYTRA]!!
        )
            .addIngredient(Material.ELYTRA)
            .addIngredient(Material.DIAMOND_CHESTPLATE)
            .build(),
        makeHammerRecipe(CustomItems.WOODEN_HAMMER, Material.OAK_PLANKS),
        makeHammerRecipe(CustomItems.STONE_HAMMER, Material.COBBLESTONE),
        makeHammerRecipe(CustomItems.GOLDEN_HAMMER, Material.GOLD_INGOT),
        makeHammerRecipe(CustomItems.IRON_HAMMER, Material.IRON_INGOT),
        makeHammerRecipe(CustomItems.DIAMOND_HAMMER, Material.DIAMOND),
        makeHammerRecipe(CustomItems.NETHERITE_HAMMER, Material.NETHER_STAR),
        ShapelessRecipeBuilder(
            NamespacedKey(MiscUtils.plugin, "experience_bottle"),
            ItemBuilder(Material.EXPERIENCE_BOTTLE, 10).build()
        )
            .addIngredient(Material.EMERALD)
            .build(),
        ShapedRecipeBuilder(MiscUtils.customItemContainer.keys[CustomItems.RITUAL_STARTER]!!, MiscUtils.customItems[CustomItems.RITUAL_STARTER]!!)
            .setSlot(0, Material.REDSTONE)
            .setSlot(1, Material.GLOWSTONE_DUST)
            .setSlot(2, Material.REDSTONE)
            .setSlot(3, Material.GLOWSTONE_DUST)
            .setSlot(4, Material.BLAZE_ROD)
            .setSlot(5, Material.GLOWSTONE_DUST)
            .setSlot(6, Material.REDSTONE)
            .setSlot(7, Material.GLOWSTONE_DUST)
            .setSlot(8, Material.REDSTONE)
            .build(),
    )

    fun registerRecipes(plugin: JavaPlugin) {
        for (recipe in customRecipes) {
            plugin.server.addRecipe(recipe);
        }
    }

    private fun makeHammerRecipe(itemID: String, material: Material): Recipe {
        return ShapedRecipeBuilder(
            NamespacedKey(MiscUtils.plugin, itemID),
            MiscUtils.customItems[itemID]!!
        )
            .setSlot(0, material)
            .setSlot(1, material)
            .setSlot(2, material)
            .setSlot(3, material)
            .setSlot(4, Material.STICK)
            .setSlot(5, material)
            .setSlot(7, Material.STICK)
            .build()
    }
}