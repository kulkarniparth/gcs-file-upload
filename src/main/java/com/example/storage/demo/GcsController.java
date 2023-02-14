package com.example.storage.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/file")
public class GcsController {

    @Value("gs://file-upload-bucket-parth-kulkarni-unique-hopefully/hello2.txt")
    private Resource gcsFile;

    @GetMapping("/{id}")
    public String readGcsFile(@PathVariable String id) throws IOException {
        return StreamUtils.copyToString(
                gcsFile.getInputStream(),
                Charset.defaultCharset()
        );
    }

    @PostMapping
    public String writeGcs(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) gcsFile).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "file was updated\n";
    }
}
