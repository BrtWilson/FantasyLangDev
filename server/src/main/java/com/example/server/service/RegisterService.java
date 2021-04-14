package com.example.server.service;



import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

public class RegisterService implements IRegisterService {

    public RegisterResponse register(RegisterRequest request) throws IOException {
        /*HASH PASSWORD*/
        PasswordHasher passwordHasher = new PasswordHasher();
        String hashedPassword = passwordHasher.hashPassword(request.getPassword());
        //request.setPassword(hashedPassword);

        /*STORE PROFILE IMAGE IN S3 BUCKET AND STORE URL IN REQUEST*/
        request.setEncodedImage(sendPhotoToS3(request));

        return getRegisterDao().register(request);
    }

    /**
     * Decode and store the new user's profile image in the s3 bucket
     * @param request the register request object
     * @return the URL for the new user's profile photo
     */
    private String sendPhotoToS3(RegisterRequest request) {
        //decode image into byte array stream
        String encodedImage = request.getEncodedImage();
        byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(imageBytes);

        //set photo metadata
        ObjectMetadata metadata = new ObjectMetadata();
        Map<String,String> map = new TreeMap<>();
        map.put("ImageType","PNG");
        metadata.setUserMetadata(map);
        metadata.setContentLength(imageBytes.length);

        //create s3 bucket client
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();

        //set putObjectRequest params
        String bucketName = "jamesblakebrytontweeterimages";
        String objKeyName = request.getUserName() + ".png";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,objKeyName,byteStream,metadata);

        //store photo in bucket
        s3Client.putObject(putObjectRequest);

        return  "https://jamesblakebrytontweeterimages.s3.us-east-1.amazonaws.com/" + objKeyName;
    }

    public static UsersTableDAO getRegisterDao() {
        return new UsersTableDAO();
    }
}