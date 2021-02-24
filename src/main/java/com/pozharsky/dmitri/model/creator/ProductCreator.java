package com.pozharsky.dmitri.model.creator;

import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class ProductCreator {
    private static final Logger logger = LogManager.getLogger(ProductCreator.class);

    private ProductCreator() {
    }

    public static Product createProduct(Map<String, String> productForm, Part part) {
        try {
            String productName = productForm.get(RequestParameter.PRODUCT_NAME);
            String price = productForm.get(RequestParameter.PRICE);
            String isActive = productForm.get(RequestParameter.IS_ACTIVE_PRODUCT);
            String description = productForm.get(RequestParameter.DESCRIPTION);
            String creatingTime = productForm.get(RequestParameter.TIME_CREATE);
            String category = productForm.get(RequestParameter.CATEGORY);
            byte[] image = part.getInputStream().readAllBytes();
            BigDecimal productPrice = BigDecimal.valueOf(Double.parseDouble(price));
            boolean status = Boolean.parseBoolean(isActive);
            LocalDateTime productCreatingTime = LocalDateTime.parse(creatingTime);
            long categoryId = Long.parseLong(category);
            return new Product(productName, productPrice, status, description, image, productCreatingTime, categoryId);
        } catch (IOException e) {
            logger.fatal("Impossible read image from stream", e);
            throw new RuntimeException(e);
        }
    }
}
