package bot.ayasimple;

import bot.ayasimple.event.Clear;
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
    private Mute mute = new Mute();
    private Clear clear = new Clear();

    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContentRaw().startsWith("!") && !event.getMember().getUser().isBot()){
            String[] args = event.getMessage().getContentRaw().replaceFirst(ConfigurationBot.getBotPrefix(), "").split(" ");

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
            String[] args = event.getMessage().getContentRaw().replaceFirst(ConfigurationBot.getBotPrefix(), "").split(" ");

            switch(args[0].toLowerCase()){
                case"mute":
                    mute.run(event, args);
                    break;
                    case"clear":
                    clear.run(event, args);
                    break;
            }
        }
    }
}
