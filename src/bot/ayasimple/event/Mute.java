package bot.ayasimple.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author michlu
 * @sience 23.09.2018
 */
public class Mute extends ListenerAdapter {

    private static final String MUTED_ROLE_NAME = "@muted";
    private static final String INFO_CHANNEL_NAME_ID = "493448380719038466";

    public void sendErrorMessage(TextChannel textChannel, Member member){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invalid Usage!");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#EA2027"));
        builder.setDescription("{} = Required, [] = Optional");
        builder.addField("Proper usage: !mute {@user} [reason]", "", false);
        textChannel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }


    public void log(Member muted, Member muter, String reason, TextChannel channel){
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


    public void run(GuildMessageReceivedEvent event, String[] args){
        if(args.length <= 1){
            sendErrorMessage(event.getChannel(), event.getMember());
        }
        else {

            Member target = event.getMessage().getMentionedMembers().get(0);
            Role muted = event.getGuild().getRolesByName(MUTED_ROLE_NAME, true).get(0);

            event.getGuild().getController().addSingleRoleToMember(target, muted).queue();

            if(args.length >= 3){
                String reason = "";
                for (int i = 2; i < args.length; i++) {
                    reason += args[i] + " ";
                }
                log(target, event.getMember(), reason, event.getGuild().getTextChannelById(INFO_CHANNEL_NAME_ID));
            }
            else{
                log(target, event.getMember(), "", event.getGuild().getTextChannelById(INFO_CHANNEL_NAME_ID));
            }
        }
    }
}
