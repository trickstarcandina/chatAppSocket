package com.server.chat.core.configs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MinioConfig {

    @Value("${minio.access.name}")
    String accessKey;

    @Value("${minio.access.secret}")
    String accessSecret;

    @Value("${minio.url}")
    String minioUrl;

    @Value("${minio.bucket.name}")
    String bucket;

    @Bean
    MinioClient generateMinioClient(){
        try {
            return new MinioClient.Builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey,accessSecret)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
