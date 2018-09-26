package bot.ayasimple.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author michlu
 * @sience 24.09.2018
 */
public class Tempmute {

    private static final String MUTED_ROLE_NAME = "@muted";
    private static final String INFO_CHANNEL_NAME_ID = "493448380719038466";

    private int counter = 0; // only test, need database


    public void sendErrorMessage(TextChannel textChannel, Member member) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invalid Usage!");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#EA2027"));
        builder.setDescription("{} = Required, [] = Optional");
        builder.addField("Proper usage: !tempmute {@user} {time} [reason]", "", false);
        builder.setDescription("Time usage: e.g. 60s / 5m / 1h");
        textChannel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

    public void log(Member muted, Member muter, String reason, TextChannel channel) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Mute Report");
        builder.setColor(Color.decode("#0652DD"));
        builder.addField("Muted User:", muted.getAsMention(), false);
        builder.addField("Muter:", muter.getAsMention(), false);
        builder.addField("Date:", sdf.format(date), false);
        builder.addField("Time:", stf.format(date), false);
        builder.addField("Reason:", reason, false);
        channel.sendMessage(builder.build()).queue();
    }


    public void run(GuildMessageReceivedEvent event, String[] args) {
        if (args.length <= 2) {
            sendErrorMessage(event.getChannel(), event.getMember());
        } else {
            String time = args[2];
            String regex = "[0-9]+[SsMmHh]$";       // e.g. 120s / 60m / 1h
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(time);

            TimeUnit unit = null;
            Character timeUnit = Character.toLowerCase(time.charAt(time.length() - 1));
            int parsedAmount = 0;

            if (matcher.matches()) {
                if (timeUnit == 's') {
                    unit = TimeUnit.SECONDS;
                } else if (timeUnit == 'm') {
                    unit = TimeUnit.MINUTES;
                } else if (timeUnit == 'h') {
                    unit = TimeUnit.HOURS;
                }

                parsedAmount = Integer.parseInt(time.substring(0, time.length() - 1));
                Member target = event.getMessage().getMentionedMembers().get(0);

                tempmute(target, parsedAmount, unit);

                if (args.length >= 4) {
                    String reason = "";
                    for (int i = 3; i < args.length; i++) {
                        reason += args[i] + " ";
                        log(target, event.getMember(), reason, event.getGuild().getTextChannelById(INFO_CHANNEL_NAME_ID));
                    }
                } else {
                    log(target, event.getMember(), "", event.getGuild().getTextChannelById(INFO_CHANNEL_NAME_ID));
                }
            }
            else {
                sendErrorMessage(event.getChannel(), event.getMember());
            }
        }
    }

    //TODO add database or hashmap
    private synchronized void tempmute(Member target, int time, TimeUnit unit) {
        Role muted = target.getGuild().getRolesByName(MUTED_ROLE_NAME, true).get(0);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                counter++;
                System.out.println("Counter: " + counter);
                target.getGuild().getController().addSingleRoleToMember(target, muted).queue();
                if (counter == 2) {
                    target.getGuild().getController().removeRolesFromMember(target, muted).queue();
                    this.cancel();
                }
            }
        };
        switch (unit) {
            case SECONDS:
                timer.schedule(task, 0, time * 1000);
                break;
            case MINUTES:
                timer.schedule(task, 0, (time * 1000) * 60);
                break;
            case HOURS:
                timer.schedule(task, 0, (time * 1000) * 60 * 60);
                break;
        }
    }
}
