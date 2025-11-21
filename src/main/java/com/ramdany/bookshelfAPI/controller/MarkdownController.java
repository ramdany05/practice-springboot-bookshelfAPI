package com.ramdany.bookshelfAPI.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

@RestController
@RequestMapping("/docs")
public class MarkdownController {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    @GetMapping(value = "/{name}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getMarkdownAsHtml(@PathVariable String name) throws IOException {
        Resource res = new ClassPathResource("markdown/" + name + ".md");
        if (!res.exists()) {
            return ResponseEntity.notFound().build();
        }
        String md = Files.readString(res.getFile().toPath(), StandardCharsets.UTF_8);
        String html = renderer.render(parser.parse(md));
        return ResponseEntity.ok(html);
    }

    @GetMapping(value = "/raw/{name}", produces = "text/markdown")
    public ResponseEntity<String> getRawMarkdown(@PathVariable String name) throws IOException {
        Resource res = new ClassPathResource("markdown/" + name + ".md");
        if (!res.exists()) {
            return ResponseEntity.notFound().build();
        }
        String md = Files.readString(res.getFile().toPath(), StandardCharsets.UTF_8);
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/markdown")).body(md);
    }
}
