package com.aidensheeran.miscUtils.recipeManager

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe

@Suppress("unused")
class ShapelessRecipeBuilder(private val recipeKey: NamespacedKey, private val output: ItemStack) {
    private var ingredients: MutableSet<Material> = mutableSetOf();

    fun addIngredient(ingredient: Material) = apply { ingredients.add(ingredient) };

    fun build(): ShapelessRecipe {
        val recipe = ShapelessRecipe(recipeKey, output);
        for (ingredient in ingredients) {
            recipe.addIngredient(ingredient);
        }
        return recipe;
    }
}