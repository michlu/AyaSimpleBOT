package bot.ayasimple.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author michlu
 * @sience 24.09.2018
 */
public class Clear {
    private static final String INFO_CHANNEL_NAME_ID = "493448380719038466";


    public void run (GuildMessageReceivedEvent event, String[] args){
        if(args.length <= 2 || !args[2].chars().allMatch(x -> Character.isDigit(x))){
            sendErrorMessage(event.getChannel(), event.getMember());

        }
        else {
            event.getMessage().delete().queue();
            TextChannel target = event.getMessage().getMentionedChannels().get(0);
            purgeMessages(target, Integer.parseInt(args[2]));
            if(args.length > 3){
                String reason = "";
                for (int i = 3; i < args.length; i++) {
                    reason += args[i] + " ";
                }
                log(event.getMember(), args[2], reason, event.getGuild().getTextChannelById(INFO_CHANNEL_NAME_ID), target);
            }
            else{
                log(event.getMember(), args[2], "", event.getGuild().getTextChannelById(INFO_CHANNEL_NAME_ID), target);
            }
        }
    }

    public void sendErrorMessage(TextChannel textChannel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invalid Usage!");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#EA2027"));
        builder.setDescription("{} = Required, [] = Optional");
        builder.addField("Proper usage: !clear {#channel} {number} [reason]", "", false);
        textChannel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

    public void log(Member clearer, String number, String reason, TextChannel incident, TextChannel cleared){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Cleared Channel");
        builder.setColor(Color.decode("#0652DD"));
        builder.addField("Cleared Channel:", cleared.getAsMention(), false);
        builder.addField("Number of Messages Cleared::", number, false);
        builder.addField("Clearer:", clearer.getAsMention(), false);
        builder.addField("Date:", sdf.format(date), false);
        builder.addField("Time:", stf.format(date), false);
        builder.addField("Reason:", reason, false);
        incident.sendMessage(builder.build()).queue();
    }

    private void purgeMessages(TextChannel channel, int number){
        MessageHistory history = new MessageHistory(channel);
        List<Message> msgs;

        msgs = history.retrievePast(number).complete();
        channel.deleteMessages(msgs).queue();
    }

}
