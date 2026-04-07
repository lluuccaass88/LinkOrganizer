package boot.linksOrganizer.service;

import boot.linksOrganizer.dto.AnnotationsDTO;
import boot.linksOrganizer.dto.MessageDTO;
import boot.linksOrganizer.enums.AnnotationSlug;
import boot.linksOrganizer.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnotationService {

  public List<AnnotationsDTO> getAnnotations(List<MessageDTO> annotations) {
    return annotations.stream().map(annotation -> {
      return AnnotationsDTO.builder()
              .slug(getSlug(annotation))
              .content(getContent(annotation))
              .build();
    }).toList();
  }

  private AnnotationSlug getSlug(MessageDTO reels) {
    String slug = reels.getContent() != null ? StringUtils.splitFirstPosition(reels.getContent()) : "Sem Titulo";

    if(slug == null){
      return null;
    }

    return AnnotationSlug.fromSlug(slug);
  }

  private String getContent(MessageDTO reels) {
    String content = StringUtils.splitSecondPosition(reels.getContent());
    return content != null ? content : reels.getContent();
  }
}
