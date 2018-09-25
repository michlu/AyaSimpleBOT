package bot.ayasimple;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class ReadyListener extends ListenerAdapter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public void onReady(ReadyEvent event){
        String out = System.lineSeparator() + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " [AyaSimpleBOT] This bot is running on the following servers: \n";

        for(Guild guild : event.getJDA().getGuilds()){
            out += guild.getName() + " (" + guild.getId() + ")" + System.lineSeparator();
        }
        System.out.println(ANSI_GREEN + out + ANSI_GREEN);
        System.out.println(ANSI_RESET);
    }

}
