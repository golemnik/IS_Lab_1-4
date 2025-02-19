package com.golem.lab.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;

@Service
public class CloudServiceImpl implements CloudService {
    private static final String STAGE_KEY = "stage";
    private static final String FILE_NAME_KEY = "fileName";
    private static final String DESCRIPTION_KEY = "description";

    @Value("${s3.endpoint}")
    private String endpoint;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Value("${s3.access-key-id}")
    private String accessKeyId;

    @Value("${s3.secret-access-key}")
    private String secretAccessKey;

    @Override
    public void uploadFile(int fio, byte[] bytes) throws ServiceException {
        try {
            System.out.println("upload to S3: " + fio);
            S3Client s3 = getClient();
            Tagging theTags = Tagging.builder().tagSet(
                    Tag.builder().key(STAGE_KEY).value("NOT_COMMITTED").build()
            ).build();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getKey(fio))
                    .tagging(theTags)
                    .build();
            RequestBody body = RequestBody.fromBytes(bytes);
            s3.putObject(request, body);
        } catch (AwsServiceException | SdkClientException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeFile(int fio) throws ServiceException {
        try {
            System.out.println("remove from S3: " + fio);
            S3Client s3 = getClient();
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getKey(fio))
                    .build();
            s3.deleteObject(request);
        } catch (AwsServiceException | SdkClientException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void commitFile(int fio, String fileName, String description) throws ServiceException {
        try {
            System.out.println("commit at S3: " + fio);
            S3Client s3 = getClient();
            Tagging theTags = Tagging.builder().tagSet(
                    Tag.builder().key(STAGE_KEY).value("COMMITED").build()
//                    Tag.builder().key(FILE_NAME_KEY).value(escape(fileName)).build(),
//                    Tag.builder().key(DESCRIPTION_KEY).value(escape(description)).build()
            ).build();
            PutObjectTaggingRequest request = PutObjectTaggingRequest.builder()
                    .bucket(bucketName)
                    .key(getKey(fio))
                    .tagging(theTags)
                    .build();
            s3.putObjectTagging(request);
        } catch (AwsServiceException | SdkClientException e) {
            throw new ServiceException(e);
        }
    }

    private S3Client getClient() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(endpoint))
                .build();
    }

    private String escape(String val) {
        return val;
    }

    private String getKey(int fio) {
        return String.valueOf(fio);
    }
}
