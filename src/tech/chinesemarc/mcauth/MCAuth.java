package tech.chinesemarc.mcauth;

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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.chinesemarc.mcauth.listeners.Listener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MCAuth extends JavaPlugin implements CommandExecutor {
    private MCAuth instance;
    private Listener listener;
    private ConsoleCommandSender consoleSender;
    private String CraftyVerifyLink = "http://127.0.0.1/test";
    @Override
    public void onEnable() {
        instance = this;
        consoleSender = Bukkit.getConsoleSender();
        consoleSender.sendMessage(ChatColor.AQUA + "[MCAuth] enabled.");
        try {
            listener = new Listener(instance);
            consoleSender.sendMessage(ChatColor.AQUA + "Registering commands.");
            getCommand("verifymc").setExecutor(instance);
            consoleSender.sendMessage(ChatColor.AQUA + "/verifymc command registered.");
            consoleSender.sendMessage(ChatColor.AQUA + "Registering listeners.");
            Bukkit.getPluginManager().registerEvents(listener, instance);
        } catch (Error err) {
            consoleSender.sendMessage(ChatColor.RED + "Error happened while registering command or listeners.\n" + err);
        }
    }
    @Override
    public void onDisable() {
        instance = null;
        consoleSender.sendMessage(ChatColor.AQUA + "[MCAuth] disabled.");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
        Player toVerify = (Player)sender;
        String discordID;
        if (args.length == 1) {
            discordID = args[0];
        } else {
            toVerify.sendMessage(ChatColor.RED + "Error while running command");
            return true;
        }
        UUID uuidToVerify = toVerify.getUniqueId();
        URL url = new URL(CraftyVerifyLink);
        String s = "{" + "uuid:" + uuidToVerify.toString() + "," + "discordID:" + discordID + "}";
        byte[] out = s.getBytes(StandardCharsets.UTF_8);
        int lenght = out.length;
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setFixedLengthStreamingMode(lenght);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();
        try(OutputStream os = http.getOutputStream())
        {
            os.write(out);
        }
        toVerify.sendMessage(http.getInputStream().toString());
        return true;
        } catch(Error | IOException err) {
            final Player player = (Player) sender;
            player.sendMessage(ChatColor.RED + "Error while running command");
            return true;
        }
    }
}