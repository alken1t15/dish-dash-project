package com.example.dishdash.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
public class BotConfig {
    private String botName = "PolytechLoveBot";
    private String token = "5709677086:AAEPG9h4qEhWZJKTQvQS_faUuIfpZeY5GjQ";
}