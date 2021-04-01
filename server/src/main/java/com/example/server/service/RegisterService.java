package com.example.server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;

public class RegisterService implements IRegisterService {
    private static class PasswordHasher {
        String hashPassword(String password) {
            //This is hashing code based on the code they provided (adjusted from copied and pasted)
            String salt = getSalt();
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(salt.getBytes());
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte aByte : bytes) {
                    sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "FAILED TO HASH PASSWORD";
        }

        private static String getSalt() {
            try {
                SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
                byte[] salt = new byte[16];
                sr.nextBytes(salt);
                return Base64.getEncoder().encodeToString(salt);
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
            }
            return "FAILED TO GET SALT";
        }
    }

    public RegisterResponse register(RegisterRequest request) throws IOException {
        PasswordHasher passwordHasher = new PasswordHasher();
        String hashedPassword = passwordHasher.hashPassword(request.getPassword());
        request.setPassword(hashedPassword);

        RegisterResponse registerResponse = getRegisterDao().register(request);

        return registerResponse;
    }

    public static UsersTableDAO getRegisterDao() {
        return new UsersTableDAO();
    }
}