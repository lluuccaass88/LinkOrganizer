package boot.linksOrganizer.client;

import boot.linksOrganizer.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DiscordClient {

  private final JDA jda;

  @Value("${discord.channel-news-name}")
  private String channelNews;

  @Value("${discord.channel-reels-name}")
  private String channelReels;

  @Value("${discord.channel-annotation-name}")
  private String channelAnnotations;

  public List<MessageDTO> findNews() {
    return fetchMessages(channelNews);
  }

  public List<MessageDTO> findReels() {
    return fetchMessages(channelReels);
  }

  public List<MessageDTO> findAnnotation() {
    return fetchMessages(channelAnnotations);
  }

  public List<MessageDTO> fetchMessages(String channelName) {
    OffsetDateTime yesterday = OffsetDateTime.now().minusHours(24);

    return jda.getTextChannelsByName(channelName, true)
            .stream()
            .findFirst()
            .map(channel -> channel.getHistory()
                    .retrievePast(100).complete()
                    .stream()
                    .filter(msg -> msg.getTimeCreated().isAfter(yesterday))
                    .map(msg -> MessageDTO.builder()
                            .server(channel.getGuild().getName())
                            .channel(channel.getName())
                            .author(msg.getAuthor().getName())
                            .content(msg.getContentDisplay())
                            .timestamp(msg.getTimeCreated().toString())
                            .build())
                    .toList())
            .orElse(Collections.emptyList());
  }

}