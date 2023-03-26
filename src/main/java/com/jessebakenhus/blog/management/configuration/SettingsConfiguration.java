package com.jessebakenhus.blog.management.configuration;

import com.google.gson.Gson;
import com.jessebakenhus.blog.management.model.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

@Configuration
public class SettingsConfiguration {

    private final Gson GSON = new Gson();

    @Bean
    public Settings settings() {

        ClassLoader classLoader = SettingsConfiguration.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("settings.json");

        Objects.requireNonNull(inputStream);

        try(Reader reader = new InputStreamReader(inputStream)) {
            return GSON.fromJson(reader, Settings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
