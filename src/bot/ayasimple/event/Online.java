package bot.ayasimple.event;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author michlu
 * @sience 15.09.2018
 */
public class Online {
    public static void run(MessageReceivedEvent event){
        int onlineUsers = 0;
        int members = event.getGuild().getMembers().size();
        for( int i = 0; i < members; i++){
            if(event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.ONLINE || event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB){
                onlineUsers++;
            }
        }
        event.getChannel().sendMessage("There are " + onlineUsers + " users online. There are " + members + " members in Discord.").queue();
    }
}
