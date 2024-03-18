package com.exwundee.discordchat.listeners;

import com.exwundee.discordchat.DiscordChat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MessageListener extends ListenerAdapter {

    DiscordChat plugin;

    public MessageListener(DiscordChat plugin){
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()
                && event.getChannel().getId().equalsIgnoreCase(plugin.getConfig().getString("channel-id"))) {
            Bukkit.getServer().broadcastMessage(
                    ChatColor.translateAlternateColorCodes(
                            '&', plugin.getConfig().getString("disc2mc-message")
                                    .replaceAll("%name%", event.getAuthor().getName())
                                    .replaceAll("%global_name%", event.getAuthor().getGlobalName())
                                    .replaceAll("%effective_name%", event.getAuthor().getEffectiveName())
                                    .replaceAll("%message%", event.getMessage().getContentRaw())
                    ));
        }
    }

}
