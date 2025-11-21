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

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    @GetMapping(value = "/{name}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getMarkdownAsHtml(@PathVariable String name) throws IOException {
        // Validate name to prevent path traversal attacks
        if (!isValidFileName(name)) {
            return ResponseEntity.badRequest().build();
        }
        
        Resource res = new ClassPathResource("markdown/" + name + ".md");
        if (!res.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        // Use InputStream to support reading from JAR files
        String md;
        try (InputStream is = res.getInputStream()) {
            md = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        
        String html = renderer.render(parser.parse(md));
        return ResponseEntity.ok(html);
    }

    @GetMapping(value = "/raw/{name}", produces = "text/markdown")
    public ResponseEntity<String> getRawMarkdown(@PathVariable String name) throws IOException {
        // Validate name to prevent path traversal attacks
        if (!isValidFileName(name)) {
            return ResponseEntity.badRequest().build();
        }
        
        Resource res = new ClassPathResource("markdown/" + name + ".md");
        if (!res.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        // Use InputStream to support reading from JAR files
        String md;
        try (InputStream is = res.getInputStream()) {
            md = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/markdown")).body(md);
    }

    /**
     * Validates that the filename contains only safe characters to prevent path traversal attacks.
     * Allows alphanumeric characters, hyphens, and underscores only.
     */
    private boolean isValidFileName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9_-]+$");
    }
}
