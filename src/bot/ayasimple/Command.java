package bot.ayasimple;

import bot.ayasimple.event.Help;
import bot.ayasimple.event.Mute;
import bot.ayasimple.event.Online;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class Command extends ListenerAdapter {
    Mute mute = new Mute();

    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContentRaw().startsWith("!") && !event.getMember().getUser().isBot()){
            String[] args = event.getMessage().getContentRaw().replaceFirst("!", "").split(" ");

                switch(args[0].toLowerCase()){
                    case"help":
                        Help.run(event.getMessage());
                        break;
                    case"online":
                        Online.run(event);
                        break;
                }
        }
    }
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if(event.getMessage().getContentRaw().startsWith("!") && !event.getMember().getUser().isBot()){
            String[] args = event.getMessage().getContentRaw().replaceFirst("!", "").split(" ");

            switch(args[0].toLowerCase()){
                case"mute":
                    mute.run(event, args);
                    break;
            }
        }
    }
}
