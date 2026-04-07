package boot.linksOrganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReelsSumaryDTO {
  private String title;
  private String category;
  private String link;
}
