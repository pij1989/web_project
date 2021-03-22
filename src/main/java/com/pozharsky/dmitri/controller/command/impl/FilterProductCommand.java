package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class FilterProductCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FilterProductCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String priceFrom = request.getParameter(RequestParameter.PRICE_FROM);
        String priceTo = request.getParameter(RequestParameter.PRICE_TO);
        String inStock = request.getParameter(RequestParameter.IN_STOCK);
        String notAvailable = request.getParameter(RequestParameter.NOT_AVAILABLE);
        logger.debug("Price from: " + priceFrom + " Price to: " + priceTo + " In stock: " + inStock + " Not available: " + notAvailable);
        return null;
    }
}
