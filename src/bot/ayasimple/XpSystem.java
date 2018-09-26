package bot.ayasimple;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author michlu
 * @sience 25.09.2018
 */
public class XpSystem extends ListenerAdapter {

    HashMap<Member, Integer> playerXp = new HashMap<>();
    HashMap<Member, Integer> playerTimer = new HashMap<>();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");
        String command = args[0];

        if(command.equalsIgnoreCase(ConfigurationBot.getBotPrefix() + "xp")){
            event.getChannel().sendMessage("You have " + getPlayerXp(event.getMember()) + "xp").queue();
        }

        if(canGetXp(event.getMember())){
            randXp(event.getMember());
            setPlayerTimer(event.getMember(), 3);
        }
    }

    private int getPlayerXp(Member member){
        return playerXp.get(member);
    }

    private void setPlayerXp(Member member, int num){
        playerXp.put(member, num);
    }

    private int getPlayerTime(Member member){
        return playerTimer.get(member);
    }

    private void setPlayerTimer(Member member, int num){
        playerTimer.put(member, num);
    }

    private void randXp(Member member){
        Random r = new Random();
        if(!playerXp.containsKey(member))
            setPlayerXp(member, 0);
        setPlayerXp(member, getPlayerXp(member) + r.nextInt(25));
    }

    private boolean canGetXp(Member member){
        if(!playerTimer.containsKey(member)){
            return  true;
        }else {
            return false;
        }
    }

    public void statTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Member member : playerTimer.keySet()) {
                    setPlayerTimer(member, getPlayerTime(member) - 1);
                    if(getPlayerTime(member) == 0){
                        playerTimer.remove(member);
                    }
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    private void debug(String text){

    }

}
