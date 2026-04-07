package boot.linksOrganizer.service;

import boot.linksOrganizer.client.DiscordClient;
import boot.linksOrganizer.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DiscordService {

  private final DiscordClient discordClient;

  public Map<String, List<MessageDTO>> readDiscord() {
    return Map.of(
            "news", discordClient.findNews(),
            "reels", discordClient.findReels(),
            "annotation", discordClient.findAnnotation()
    );
  }

}