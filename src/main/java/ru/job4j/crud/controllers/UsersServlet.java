package ru.job4j.crud.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.crud.models.Validate;
import ru.job4j.crud.models.ValidateService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 7.0
 * @since 29.03.2020
 */

public class UsersServlet extends HttpServlet {
    private static final String AMAZON_ACCESS_KEY = "AKIA37SVVXBHTX27GO6M";
    private static final String AMAZON_SECRET_KEY = "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";
    private static final String REGION = "eu-west-1";
    private static final String S3_BUCKET_NAME = "cloud-cube-eu/t5ehz7b614ih/public/avatars";
    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);

    private final Validate collection = ValidateService.getInstance();

    /**
     * Метод формирует список существующих пользователей
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        request.setAttribute("clients", collection.findAll());
        if (session.getAttribute("role").equals("администратор")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/adminList.jsp");
            dispatcher.forward(request, response);
        } else if (session.getAttribute("role").equals("пользователь")) {
            request.setAttribute("currentId", session.getAttribute("id"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/userList.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Метод удаляет пользовательский аккаунт вместе с картинкой при нажатии на кнопку "Удалить"
     * @param request - запрос серверу
     * @param response - ответ сервера
     */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.deleteS3Picture(request);
        this.doGet(request, response);
    }

    /**
     * Реализация удаления
     * @param request - запрос серверу
     */

    private void deletePicture(HttpServletRequest request) {
        boolean resultOfDelete = true;
        for (File file : Objects.requireNonNull(new File("/bin/images/").listFiles())) {
            if (file.getName().equals(collection.findById(Integer.valueOf(request.getParameter("id"))).getPhotoId())) {
                resultOfDelete = file.delete();
            }
        }
        if (resultOfDelete) {
            collection.delete(Integer.valueOf(request.getParameter("id")));
        }
    }

    /**
     * Реализация удаления из хранилища CloudCube
     * @param request - запрос серверу
     */

    private void deleteS3Picture(HttpServletRequest request) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(REGION))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        String fileName = collection.findById(Integer.valueOf(request.getParameter("id"))).getPhotoId();
        LOG.info("Deleting file with name = " + fileName);
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(S3_BUCKET_NAME, fileName);
        s3Client.deleteObject(deleteObjectRequest);
        collection.delete(Integer.valueOf(request.getParameter("id")));
        LOG.info("File deleted successfully.");
    }
}