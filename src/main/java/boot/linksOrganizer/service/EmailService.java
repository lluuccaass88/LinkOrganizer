package boot.linksOrganizer.service;

import boot.linksOrganizer.dto.AnnotationsDTO;
import boot.linksOrganizer.dto.NewsSumaryDTO;
import boot.linksOrganizer.dto.ReelInfoDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;  // injetado automaticamente pelo Thymeleaf

  @Value("${email.recipient}")
  private String recipient;

  public void sendDailyEmail(List<NewsSumaryDTO> news, List<ReelInfoDTO> reels, List<AnnotationsDTO> annotations) {
    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setTo(recipient);
      helper.setSubject("📰 Resumo diário — Notícias e Reels");
      helper.setText(buildEmailBody(news, reels, annotations), true);
      mailSender.send(message);
      log.info("Email enviado com sucesso!");
    } catch (Exception e) {
      throw new RuntimeException("Erro ao enviar e-mail", e);
    }
  }

  private String buildEmailBody(List<NewsSumaryDTO> news, List<ReelInfoDTO> reels, List<AnnotationsDTO> annotations) {
    Context context = new Context();
    context.setVariable("date", LocalDate.now()
            .format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"))));
    context.setVariable("newsList", news);
    context.setVariable("reels", reels);
    context.setVariable("annotations", annotations);

    return templateEngine.process("email-template", context);
  }
}