package com.aidensheeran.miscUtils.recipeManager

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

@Suppress("unused")
class ShapedRecipeBuilder(private val recipeKey: NamespacedKey, private val output: ItemStack) {
    private var shape: Array<Array<Char>> =
        arrayOf(arrayOf(' ', ' ', ' '), arrayOf(' ', ' ', ' '), arrayOf(' ', ' ', ' '));
    private var ingredientMap: MutableMap<Material, Char> = mutableMapOf();
    private var letterCount = 0;

    fun setSlot(slot: Int, material: Material) = apply {
        if (slot in 0..8) {
            if (material !in ingredientMap.keys) {
                val randomChar = generateRandomLetter();
                ingredientMap[material] = randomChar;
            }
            val slotMarker = ingredientMap[material]!!;
            val shapeLine = slot / 3;
            val shapeSpot = slot % 3;
            shape[shapeLine][shapeSpot] = slotMarker;
        }
    };

    fun build(): ShapedRecipe {
        val recipe = ShapedRecipe(recipeKey, output);
        recipe.shape(*convertShape())
        for (ingredient in ingredientMap.entries) {
            recipe.setIngredient(ingredient.value, ingredient.key);
        }
        return recipe;
    }

    private fun convertShape(): Array<String> {
        val shape: MutableList<String> = mutableListOf();
        for (line in this.shape) {
            shape.add(line.joinToString(""));
        }
        return shape.toTypedArray();
    }

    private fun generateRandomLetter(): Char {
        val letters = ('a'..'z') + ('A'..'Z')  // Combine lowercase and uppercase letters
        return letters[letterCount++];
    }
}