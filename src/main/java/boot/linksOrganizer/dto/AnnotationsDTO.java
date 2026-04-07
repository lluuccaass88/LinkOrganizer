package boot.linksOrganizer.dto;

import boot.linksOrganizer.enums.AnnotationSlug;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnnotationsDTO {
  private AnnotationSlug slug;
  private String content;
}
