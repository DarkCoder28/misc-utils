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

package com.aidensheeran.miscUtils.eventHandlers

import com.aidensheeran.miscUtils.MiscUtils
import com.aidensheeran.miscUtils.customItems.CustomItems
import com.aidensheeran.miscUtils.rituals.Rituals
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class RitualHandler: Listener {

    @EventHandler(ignoreCancelled = true)
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.hasItem()) {
            val clickItem = e.item!!;
            val clickItemData = clickItem.persistentDataContainer;
            if (clickItemData.has(MiscUtils.customItemContainer.keys[CustomItems.RITUAL_STARTER]!!)) {
                e.isCancelled = true;
                for (ritual in Rituals.entries) {
                    if (ritual.getRitual().checkValid(e.player, e.clickedBlock!!)) {
                        ritual.getRitual().executeRitual(e.player, e.clickedBlock!!);
                        return;
                    }
                }
                e.player.sendMessage("Malformed Ritual");
            }
        }
    }


}