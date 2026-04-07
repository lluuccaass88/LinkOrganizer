package boot.linksOrganizer.service;

import boot.linksOrganizer.dto.MessageDTO;
import boot.linksOrganizer.dto.ReelInfoDTO;
import boot.linksOrganizer.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReelService {

  public List<ReelInfoDTO> getReelsInfoList(List<MessageDTO> reels) {

    return reels.stream().map(reel -> {
      return ReelInfoDTO.builder()
              .url(getUrl(reel))
              .title(getTitle(reel))
              .build();
    }).toList();

  }

  private String getTitle(MessageDTO reels) {
    return reels.getContent() != null ? StringUtils.splitFirstPosition(reels.getContent()) : "Sem Titulo";
  }

  private String getUrl(MessageDTO reels) {
    String description = StringUtils.splitSecondPosition(reels.getContent());
    return description != null ? description : reels.getContent();
  }

}