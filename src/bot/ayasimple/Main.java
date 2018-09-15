package bot.ayasimple;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author michlu
 * @sience 14.09.2018
 */
public class Main {
    private static JDA jda;

    public static void main(String[] args) {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            String filename = "config.properties";
            input = Main.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Error, unable to find " + filename);
                return;
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(prop.getProperty("token"));
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new Command());

        try {
            jda = builder.buildBlocking();
        }catch (LoginException ex) {
            ex.printStackTrace();
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }catch (RateLimitedException ex) {
            ex.printStackTrace();
        }
    }
}
