package boot.linksOrganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsSumaryDTO {
  private String title;
  private String summary;
  private String link;
}
