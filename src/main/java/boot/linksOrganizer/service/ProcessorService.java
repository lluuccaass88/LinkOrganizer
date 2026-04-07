package boot.linksOrganizer.service;

import boot.linksOrganizer.client.GeminiClient;
import boot.linksOrganizer.dto.AnnotationsDTO;
import boot.linksOrganizer.dto.MessageDTO;
import boot.linksOrganizer.dto.NewsSumaryDTO;
import boot.linksOrganizer.dto.ReelInfoDTO;
import boot.linksOrganizer.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessorService {
  private final DiscordService discordService;

  private final GeminiClient geminiClient;

  private final ReelService reelService;

  private final AnnotationService annotationService;

  private final EmailService emailService;

  @Scheduled(cron = "0 55 23 * * *")
  public void start() {
    Map<String, List<MessageDTO>> discordResponse = discordService.readDiscord();
    List<MessageDTO> news = discordResponse.get("news");
    List<MessageDTO> reels = discordResponse.get("reels");
    List<MessageDTO> annotations = discordResponse.get("annotation");
    List<NewsSumaryDTO> newsProcessed = List.of();
    List<ReelInfoDTO> reellsProcessed = List.of();
    List<AnnotationsDTO> annotationsProcessed = List.of();

    if(!news.isEmpty()){
      newsProcessed = processorNews(news);
    }

    if(!reels.isEmpty()){
      reellsProcessed = processorReels(reels);
    }

    if(!annotations.isEmpty()){
      annotationsProcessed = processorAnnotations(annotations);
    }

    emailService.sendDailyEmail(newsProcessed, reellsProcessed, annotationsProcessed);

    // TODO Conectar com a api que envia o email - Tirar Tokens para subir pro github.

  }

  private List<NewsSumaryDTO> processorNews(List<MessageDTO> news) {
    String response = geminiClient.getNewsSummarize(news);
    return NewsMapper.newsSumaryDTO(response);
  }

  private List<ReelInfoDTO> processorReels(List<MessageDTO> reels) {
    return reelService.getReelsInfoList(reels);
  }

  private List<AnnotationsDTO> processorAnnotations(List<MessageDTO> annotations) {
    return annotationService.getAnnotations(annotations);
  }

}
