package bot.ayasimple.event;

import net.dv8tion.jda.core.entities.Message;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class Help {

    public static void run(Message msg){
        msg.getChannel().sendMessage("Test!").queue();
    }
}
