package com.jessebakenhus.blog.management.configuration;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class GitHubApiConfiguration {

    @Bean
    public GitHub gitHubApi() {

        Map<String, String> getenv = System.getenv();

        try {

            return new GitHubBuilder()
                    .withOAuthToken(getenv.get("GITHUB_TOKEN"))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
