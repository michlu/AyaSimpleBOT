package bot.ayasimple;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class Main {
    private static JDA jda;

    public static void main(String[] args) {

        initDiscord();
    }


    private static void initDiscord() {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        try {
            builder.setToken(ConfigurationBot.getInstance().getToken());
            builder.setGame(Game.playing("AYA BOT"));
            builder.setAutoReconnect(true);
            builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

            // Listener:
            builder.addEventListener(new ReadyListener());
            builder.addEventListener(new Command());

            jda = builder.buildBlocking();
        }catch (LoginException ex) {
            ex.printStackTrace();
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            builder.setStatus(OnlineStatus.OFFLINE);
        }
    }

}
