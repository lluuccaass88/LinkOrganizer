package boot.linksOrganizer.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordConfig {

  @Value("${discord.token}")
  private String token;

  @Bean
  public JDA jda() throws Exception {
    return JDABuilder.createDefault(token)
            .enableIntents(
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MESSAGES
            )
            .build()
            .awaitReady();
  }
}