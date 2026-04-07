package boot.linksOrganizer.controller;

import boot.linksOrganizer.service.DiscordService;
import boot.linksOrganizer.service.ProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class testController {

  private final ProcessorService processorService;

  @GetMapping
  public void getMessages() {
   processorService.start();
  }
}
