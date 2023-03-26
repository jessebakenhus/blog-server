package com.jessebakenhus.blog.management.util;

import com.jessebakenhus.blog.management.model.Blog;
import com.jessebakenhus.blog.management.model.BlogDto;
import com.jessebakenhus.blog.management.model.Settings;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContentFactory {

    private final GitHub gitHubApi;

    private final Settings settings;

    public ContentFactory(GitHub gitHubApi, Settings settings) {
        this.gitHubApi = gitHubApi;
        this.settings = settings;
    }

    public void createContent(String slug, String content, boolean draft) {

        try {

            GHRepository repository = gitHubApi.getRepository(settings.getRepository());

            repository.createContent()
                    .branch(settings.getBranch())
                    .path(String.format("content/%s/%s.md",
                            draft ? "drafts" : "posts",
                            slug))
                    .message(String.format("📃 Published '%s' as '%s'", slug, draft ? "draft" : "post"))
                    .content(content)
                    .commit();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteContent(String slug, boolean draft) {

        try {

            GHRepository repository = gitHubApi.getRepository(settings.getRepository());

            repository.getFileContent(String.format("content/%s/%s.md",
                                        draft ? "drafts" : "posts",
                                        slug))
                            .delete(String.format("🧨 Delete '%s' as '%s'", slug, draft ? "draft" : "post"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<BlogDto> getContent() {

        try {

            GHRepository repository = gitHubApi.getRepository(settings.getRepository());

            List<BlogDto> drafts = repository.getDirectoryContent("content/drafts").stream()
                    .map(ghContent -> new BlogDto(ghContent.getName().substring(0, ghContent.getName().length() - 3), true))
                    .toList();

            List<BlogDto> posts = repository.getDirectoryContent("content/posts").stream()
                    .map(ghContent -> new BlogDto(ghContent.getName().substring(0, ghContent.getName().length() - 3), false))
                    .toList();

            return Stream.concat(drafts.stream(), posts.stream()).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Blog getContent(String slug, boolean draft) {

        try {

            GHRepository repository = gitHubApi.getRepository(settings.getRepository());

            GHContent fileContent = repository.getFileContent(String.format("content/%s/%s.md",
                    draft ? "drafts" : "posts",
                    slug));

            return new Blog(
                    slug,
                    draft,
                    fileContent.getContent()
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
