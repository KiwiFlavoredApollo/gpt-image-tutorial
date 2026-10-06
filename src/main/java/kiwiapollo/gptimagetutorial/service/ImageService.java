package kiwiapollo.gptimagetutorial.service;

import com.openai.client.OpenAIClient;
import com.openai.core.MultipartField;
import com.openai.models.images.ImageEditParams;
import com.openai.models.images.ImagesResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class ImageService {

    private final OpenAIClient openAIClient;

    public ImageService(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    public byte[] editClothing(
            MultipartFile personImage,
            MultipartFile clothingImage
    ) throws IOException {

        String prompt = """
                Edit the first image.

                Replace the person's clothing with the clothing shown
                in the second image.

                Preserve the person's:
                - identity and face
                - body proportions
                - pose
                - hairstyle
                - facial expression
                - camera angle
                - background

                Use the second image only as the clothing reference.

                Make the clothing fit naturally on the person.
                Preserve the original lighting and image composition.
                """;

        try (InputStream person = personImage.getInputStream();
             InputStream clothing = clothingImage.getInputStream()) {

            ImageEditParams params = ImageEditParams.builder()
                    .model("gpt-image-2.5-sunburst")
                    .prompt(prompt)
                    .image(MultipartField.<ImageEditParams.Image>builder()
                            .value(ImageEditParams.Image.ofInputStreams(
                                    List.of(person, clothing)))
                            .filename("image.jpg")
                            .contentType("application/octet-stream")
                            .build())
                    .build();

            ImagesResponse response = openAIClient.images().edit(params);

            return Base64.getDecoder().decode(
                    response.data().orElseThrow()
                            .get(0).b64Json().orElseThrow());
        }
    }

    private ImageEditParams.Image toImagePart(
            MultipartFile file
    ) throws IOException {

        InputStream inputStream = file.getInputStream();

        return ImageEditParams.Image.ofInputStream(inputStream);
    }
}