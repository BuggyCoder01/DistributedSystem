package BuggyCoder01.DistributedSystem.coordinator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;

@Service
public class NodeService {

    @Value("${node.qa.url}") private String qaUrl;
    @Value("${node.design.url}") private String designUrl;
    @Value("${node.development.url}") private String developmentUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void uploadFile(String department, MultipartFile file) {
        String url = getNodeUrl(department) + "/api/files/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        restTemplate.postForEntity(url, new HttpEntity<>(body, headers), String.class);
    }

    private String getNodeUrl(String department) {
        return switch (department) {
            case "QA" -> qaUrl;
            case "Design" -> designUrl;
            case "Development" -> developmentUrl;
            default -> throw new IllegalArgumentException("Invalid department");
        };
    }
}