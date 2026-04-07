package boot.linksOrganizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
  private String server;
  private String channel;
  private String author;
  private String content;
  private String timestamp;
}