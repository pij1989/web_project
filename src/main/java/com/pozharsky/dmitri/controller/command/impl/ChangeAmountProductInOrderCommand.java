package com.pozharsky.dmitri.controller.command.impl;

import com.pozharsky.dmitri.controller.command.Command;
import com.pozharsky.dmitri.controller.command.RequestParameter;
import com.pozharsky.dmitri.controller.command.Router;
import com.pozharsky.dmitri.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeAmountProductInOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeAmountProductInOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String orderProductId = request.getParameter(RequestParameter.ORDER_PRODUCT_ID);
        String amountProduct = request.getParameter(RequestParameter.AMOUNT_PRODUCT);
        logger.debug("Id: " + orderProductId + " amount: " + amountProduct);
        return null;
    }
}
