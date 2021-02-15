package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.*;
import com.pozharsky.dmitri.exception.CommandException;
import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.service.ProductService;
import com.pozharsky.dmitri.model.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CreateProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateProductCommand.class);
    private static final String UPLOAD_DIR = "images";
    private static final String EMPTY = "";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        try {
            String productName = request.getParameter(RequestParameter.PRODUCT_NAME);
            String category = request.getParameter(RequestParameter.CATEGORY);
            String price = request.getParameter(RequestParameter.PRICE);
            String isActive = request.getParameter(RequestParameter.IS_ACTIVE_PRODUCT);
            String description = request.getParameter(RequestParameter.DESCRIPTION);
            String creatingTime = request.getParameter(RequestParameter.TIME_CREATE);
            String applicationDir = request.getServletContext().getRealPath(EMPTY);
            HttpSession session = request.getSession();
            logger.debug("Application dir: " + applicationDir);
            logger.debug("Product name: " + productName + " Category: " + category + " Price: " + price +
                    " isActive: " + isActive + " Description: " + description + " Creating time: " + creatingTime);
            String uploadFileDir = applicationDir + File.separator + UPLOAD_DIR + File.separator;
            File fileSaveDir = new File(uploadFileDir);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            Part part = request.getPart(RequestParameter.IMAGE);
            String path = part.getSubmittedFileName();
            logger.debug("Path: " + path);
            String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
            String filePath = uploadFileDir + randFilename;
            byte[] image = part.getInputStream().readAllBytes();
            ProductService productService = ProductServiceImpl.getInstance();
            boolean result = productService.createProduct(productName, category, price, isActive, description, image, creatingTime);
            if (result) {
                session.setAttribute(SessionAttribute.CREATE_PRODUCT, "Success");
            } else {
                session.setAttribute(SessionAttribute.CREATE_PRODUCT, "Error");
            }
            session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.CREATE_PRODUCT);
            return new Router(PagePath.CREATE_PRODUCT);
        } catch (ServiceException | ServletException | IOException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
