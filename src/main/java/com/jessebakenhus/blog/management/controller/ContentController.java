package com.jessebakenhus.blog.management.controller;

import com.jessebakenhus.blog.management.model.Blog;
import com.jessebakenhus.blog.management.model.BlogDto;
import com.jessebakenhus.blog.management.model.Settings;
import com.jessebakenhus.blog.management.util.ContentFactory;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ContentController {

    @Autowired
    private Settings settings;

    @Autowired
    private GitHub gitHubApi;

    @PostMapping("/content/{slug}")
    public ResponseEntity<Object> createContent(@PathVariable String slug,
                                                @RequestBody String content,
                                                @RequestParam boolean draft) {

        new ContentFactory(gitHubApi, settings).createContent(slug, content, draft);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/content/{slug}")
    public ResponseEntity<Blog> getContent(@PathVariable String slug,
                                              @RequestParam boolean draft) {

        Blog blog = new ContentFactory(gitHubApi, settings).getContent(slug, draft);

        return ResponseEntity.ok(blog);
    }

    @GetMapping("/content")
    public ResponseEntity<Collection<BlogDto>> getContent() {
        return ResponseEntity.ok(new ContentFactory(gitHubApi, settings).getContent());
    }

    @DeleteMapping("/content/{slug}")
    public ResponseEntity<Void> deleteContent(@PathVariable String slug, @RequestParam boolean draft) {

        new ContentFactory(gitHubApi, settings).deleteContent(slug, draft);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/content/{slug}")
    public ResponseEntity<Void> updateContent(@PathVariable String oldSlug,
                                              @RequestParam String newSlug,
                                              @RequestBody String content,
                                              @RequestParam boolean oldDraft,
                                              @RequestParam boolean newDraft) {

        ContentFactory contentFactory = new ContentFactory(gitHubApi, settings);

        contentFactory.deleteContent(oldSlug, oldDraft);
        contentFactory.createContent(newSlug, content, newDraft);

        return ResponseEntity.ok().build();
    }
}
