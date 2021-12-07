package com.server.chat.services;

import io.minio.messages.Bucket;

import java.io.InputStream;
import java.util.List;

public interface MinioService {

    List<Bucket> getAllBuckets();

    void upload(String folder, String name, InputStream data);

    void delete(String url);

    byte[] getBytes(String key);
}
