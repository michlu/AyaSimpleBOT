package bot.ayasimple;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author michlu
 * @sience 23.09.2018
 */
public class ConfigurationBot {
    private Boolean isBotBeta = false;
    private static String botPrefix = "!";
    private static String token = "";
    private static Properties prop;

    private ConfigurationBot() {
        loadPropertiesToken();
        ConfigurationBot.token = prop.getProperty("token"); // name properties token
    }

    public static ConfigurationBot getInstance() {
        return ConfigurationBotHolder.INSTANCE;
    }

    private static class ConfigurationBotHolder {
        private static final ConfigurationBot INSTANCE = new ConfigurationBot();
    }

    public Boolean getBotBeta() {
        return isBotBeta;
    }

    public static String getBotPrefix() {
        return botPrefix;
    }

    public static String getToken() {
        return token;
    }

    /**
     * Load token from file roseources/config.properties
     */
    private static void loadPropertiesToken() {
        prop = new Properties();
        InputStream input = null;

        try {
            String filename = "config.properties";
            input = Main.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.err.println("[ConfigurationBot] Error, unable to find " + filename);
                return;
            }
            prop.load(input);
            System.out.println("[ConfigurationBot] File: " + filename + "load");
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
    }
}
