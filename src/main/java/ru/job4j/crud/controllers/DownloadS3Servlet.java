package ru.job4j.crud.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 27.05.2020
 */

public class DownloadS3Servlet extends HttpServlet {
    private static final String AMAZON_ACCESS_KEY = "AKIA37SVVXBHTX27GO6M";
    private static final String AMAZON_SECRET_KEY = "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";
    private static final String REGION = "eu-west-1";
    private static final String S3_BUCKET_NAME = "cloud-cube-eu/t5ehz7b614ih/public/avatars";

    /**
     * Метод отображает аватар пользователя, полученный из хранилища aws s3 (amazon)
     * @param request  - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        response.setContentType("name=" + name);
        response.setContentType("image/jpg");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(REGION))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        S3Object object = s3Client.getObject(new GetObjectRequest(S3_BUCKET_NAME, name));
        try (InputStream reader = object.getObjectContent()) {
            response.getOutputStream().write(reader.readAllBytes());
        }
    }
}