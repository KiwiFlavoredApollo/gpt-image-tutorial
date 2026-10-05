package kiwiapollo.gptimagetutorial.controller;

import kiwiapollo.gptimagetutorial.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(
            value = "/clothing-transfer",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public ResponseEntity<byte[]> clothingTransfer(
            @RequestPart("person") MultipartFile person,
            @RequestPart("clothing") MultipartFile clothing
    ) throws IOException {

        byte[] result = imageService.editClothing(
                person,
                clothing
        );

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(result);
    }
}
