package bot.ayasimple;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class ReadyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event){
        String out = "\nThis bot is running on the following servers: \n";

        for(Guild guild : event.getJDA().getGuilds()){
            out += guild.getName() + " (" + guild.getId() + ") \n";
        }
        System.out.println(out);
    }

}
