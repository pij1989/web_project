package com.pozharsky.dmitri.controller;

import com.pozharsky.dmitri.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FileUploadingServlet.class);
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationDir = req.getServletContext().getRealPath("");
        logger.debug("Application dir: " + applicationDir);
        String uploadFileDir = applicationDir + File.separator + UPLOAD_DIR + File.separator;
        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        req.getParts().forEach(part -> {
            try {
                String path = part.getSubmittedFileName();
                logger.debug("Path: " + path);
                String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
                part.write(uploadFileDir + randFilename);
                req.setAttribute("upload_result", "upload successfully");
            } catch (IOException e) {
                logger.error(e);
                req.setAttribute("upload_result", "upload failed");
            }
        });
        req.getRequestDispatcher(PagePath.UPLOAD).forward(req, resp);
    }
}
