package com.example.server.service;



import com.amazonaws.services.dynamodbv2.xspec.B;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Base64;
import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class RegisterService implements IRegisterService {

    public RegisterResponse register(RegisterRequest request) throws IOException {
        /*HASH PASSWORD*/
        PasswordHasher passwordHasher = new PasswordHasher();
        String hashedPassword = passwordHasher.hashPassword(request.getPassword());
        request.setPassword(hashedPassword);

        /*STORE PROFILE IMAGE IN S3 BUCKET AND STORE URL IN REQUEST*/
        String encodedImage = request.getEncodedImage();
        request.setEncodedImage(setImageURL(request));

        RegisterResponse response = getRegisterDao().register(request);

        if (response.isSuccess())
            sendPhotoToS3(encodedImage, request);

        return getRegisterDao().register(request);
    }

    private String setImageURL(RegisterRequest registerRequest) {
        return  "https://jamesblakebrytontweeterimages.s3.amazonaws.com/%40" + registerRequest.getUserName().substring(1) + ".jpg";
    }

    /**
     * Decode and store the new user's profile image in the s3 bucket
     * @param request the register request object
     * @return the URL for the new user's profile photo
     */
    private void sendPhotoToS3(String encodedImage, RegisterRequest request) {

        if (request.getEncodedImage().equals(""))
            return;
        try {
            //decode image into byte array stream
            byte[] imageBytes = Base64.decode(encodedImage);
            ByteArrayInputStream byteStream = new ByteArrayInputStream(imageBytes);

            //set photo metadata
            ObjectMetadata metadata = new ObjectMetadata();
            Map<String,String> map = new TreeMap<>();
            map.put("ImageType","JPG");
            metadata.setUserMetadata(map);
            metadata.setContentLength(imageBytes.length);

            //create s3 bucket client
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();

            //set putObjectRequest params
            String bucketName = "jamesblakebrytontweeterimages";
            String objKeyName = request.getUserName() + ".jpg";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,objKeyName,byteStream,metadata).withCannedAcl(CannedAccessControlList.PublicRead);

            //store photo in bucket
            s3Client.putObject(putObjectRequest);

            byteStream.close();
        } catch (IOException e) {
            return;
        }
    }

    public static UsersTableDAO getRegisterDao() {
        return new UsersTableDAO();
    }
}