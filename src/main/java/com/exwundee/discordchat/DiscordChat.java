package com.exwundee.discordchat;

import com.exwundee.discordchat.listeners.MessageListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordChat extends JavaPlugin implements Listener {

    JDA jda = null;
    boolean LOADED = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            JDABuilder builder = JDABuilder.createDefault(getConfig().getString("bot-token"))
                    .addEventListeners(new MessageListener(this))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setStatus(OnlineStatus.DO_NOT_DISTURB);
            jda = builder.build();
        } catch (Exception e) {
            LOADED = false;
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp() &&
        !LOADED) {
            event.getPlayer().sendMessage(ChatColor.RED + "Invalid bot token, please change in config.yml and restart.");
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String message = event.signedMessage().message();
        jda.getGuildById(getConfig().getString("guild-id"))
                .getTextChannelById(getConfig().getString("channel-id"))
                .sendMessage(event.getPlayer().getName() + ": " + message)
                .queue();
    }

}
