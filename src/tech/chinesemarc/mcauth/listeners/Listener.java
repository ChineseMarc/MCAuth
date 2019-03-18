package tech.chinesemarc.mcauth.listeners;

/*
 * This file is part of MCAuth, licensed under the MIT License.
 *
 *  Copyright (c) Chinese_Marc <admin@chinesemarc.tech>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import tech.chinesemarc.mcauth.MCAuth;

public class Listener implements org.bukkit.event.Listener {
    private MCAuth instance;
    private ConsoleCommandSender consoleSender;
    public Listener(MCAuth p) {
        instance = p;
        consoleSender = Bukkit.getConsoleSender();
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.getPlayer().sendMessage(ChatColor.RED + "This server is for verifying and not for playing.");
        e.setCancelled(true);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        e.getPlayer().sendMessage(ChatColor.RED + "This server is for verifying and not for playing.");
        e.setCancelled(true);
    }
}