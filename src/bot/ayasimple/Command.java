package bot.ayasimple;

import bot.ayasimple.event.Help;
import bot.ayasimple.event.Online;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class Command extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getMessage().getContent().startsWith("!")){
            String[] args = event.getMessage().getContent().replaceFirst("!", "").split(" ");

            if(!event.getMember().getUser().isBot()){
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
    }
}
