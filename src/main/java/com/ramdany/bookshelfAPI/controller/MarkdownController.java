package com.ramdany.bookshelfAPI.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

@RestController
@RequestMapping("/docs")
public class MarkdownController {

    private static final String TEXT_MARKDOWN = "text/markdown";
    
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    @GetMapping(value = "/{name}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getMarkdownAsHtml(@PathVariable String name) throws IOException {
        // Validate name to prevent path traversal attacks
        if (!isValidFileName(name)) {
            return ResponseEntity.badRequest().build();
        }
        
        String md = readMarkdownFile(name);
        if (md == null) {
            return ResponseEntity.notFound().build();
        }
        
        String html = renderer.render(parser.parse(md));
        return ResponseEntity.ok(html);
    }

    @GetMapping(value = "/raw/{name}", produces = TEXT_MARKDOWN)
    public ResponseEntity<String> getRawMarkdown(@PathVariable String name) throws IOException {
        // Validate name to prevent path traversal attacks
        if (!isValidFileName(name)) {
            return ResponseEntity.badRequest().build();
        }
        
        String md = readMarkdownFile(name);
        if (md == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(md);
    }

    /**
     * Reads a markdown file from the classpath.
     * Uses InputStream to support reading from JAR files.
     * 
     * @param name the filename without extension
     * @return the file content as String, or null if the file doesn't exist
     * @throws IOException if an error occurs reading the file
     */
    private String readMarkdownFile(String name) throws IOException {
        Resource res = new ClassPathResource("markdown/" + name + ".md");
        if (!res.exists()) {
            return null;
        }
        
        try (InputStream is = res.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * Validates that the filename contains only safe characters to prevent path traversal attacks.
     * Allows alphanumeric characters, hyphens, and underscores only.
     */
    private boolean isValidFileName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9_-]+$");
    }
}
