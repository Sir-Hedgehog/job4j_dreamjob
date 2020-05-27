package ru.job4j.crud.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 27.05.2020
 */

public class UploadS3Servlet extends HttpServlet {
    private String nameOfFile = "Фото не выбрано";
    private static final String S3_BUCKET_NAME = "cloud-cube-eu/t5ehz7b614ih/public/avatars";
    private static final Logger LOG = LoggerFactory.getLogger(UploadS3Servlet.class);
    private static final int MAX_FILE_SIZE = 1024 * 250;
    private static final String AMAZON_ACCESS_KEY = "AKIA37SVVXBHTX27GO6M";
    private static final String AMAZON_SECRET_KEY = "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";
    private static final String REGION = "eu-west-1";

    /**
     * Метод загружает имя выбранной картинки в форму для создания пользователя
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("file", nameOfFile);
        request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
    }

    /**
     * Метод добавляет аватар пользователя в хранилище aws s3 (amazon)
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(REGION))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    ObjectMetadata om = new ObjectMetadata();
                    om.setContentLength(item.getSize());
                    nameOfFile = item.getName().substring(item.getName().lastIndexOf("\\") + 1);
                    try {
                        s3Client.putObject(new PutObjectRequest(S3_BUCKET_NAME, nameOfFile, item.getInputStream(), om));
                    } catch (AmazonServiceException ase) {
                        LOG.error("AmazonServiceException:" + ase.getMessage());
                    }
                }
            }
        } catch (FileUploadException ex) {
            LOG.error("FileUploadException: " + ex.getMessage());
        }
        doGet(request, response);
    }
}
