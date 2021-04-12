package com.pozharsky.dmitri.model.creator;

import com.pozharsky.dmitri.model.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

/**
 * Creator is used to create a Product object.
 *
 * @author Dmitri Pozharsky
 */
public class ProductCreator {
    private static final Logger logger = LogManager.getLogger(ProductCreator.class);

    private ProductCreator() {
    }

    public static Product createProduct(Map<String, String> productForm, Part part) {
        try {
            String productName = productForm.get(PRODUCT_NAME);
            String price = productForm.get(PRICE);
            String amount = productForm.get(AMOUNT);
            String isActive = productForm.get(IS_ACTIVE_PRODUCT);
            String description = productForm.get(DESCRIPTION);
            String creatingTime = productForm.get(TIME_CREATE);
            String category = productForm.get(CATEGORY);
            byte[] image = part.getInputStream().readAllBytes();
            BigDecimal productPrice = new BigDecimal(price);
            int productAmount = Integer.parseInt(amount);
            boolean status = Boolean.parseBoolean(isActive);
            LocalDateTime productCreatingTime = LocalDateTime.parse(creatingTime);
            long categoryId = Long.parseLong(category);
            return new Product(productName, productPrice, productAmount, status, description, image, productCreatingTime, categoryId);
        } catch (IOException e) {
            logger.fatal("Impossible read image from stream", e);
            throw new RuntimeException(e);
        }
    }
}
