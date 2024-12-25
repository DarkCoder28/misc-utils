/*
 * MIT License
 *
 * Copyright (c) 2024 Aiden Sheeran
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.aidensheeran.miscUtils

import com.aidensheeran.miscUtils.customItems.CustomItems
import com.aidensheeran.miscUtils.eventHandlers.HammerHandler
import com.aidensheeran.miscUtils.eventHandlers.RitualHandler
import com.aidensheeran.miscUtils.recipeManager.RecipeDefinitions
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin


class MiscUtils : JavaPlugin() {

    companion object {
        lateinit var plugin: MiscUtils;
        lateinit var customItemContainer: CustomItems;
        lateinit var customItems: Map<String, ItemStack>;
    }

    override fun onEnable() {
        // Plugin startup logic
        plugin = this;
        customItemContainer = CustomItems(this);
        customItems = customItemContainer.items;
        RecipeDefinitions().registerRecipes(this);
        Bukkit.getPluginManager().registerEvents(HammerHandler(), this);
        Bukkit.getPluginManager().registerEvents(RitualHandler(), this);
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
