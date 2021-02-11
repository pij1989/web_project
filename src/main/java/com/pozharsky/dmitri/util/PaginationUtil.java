package com.pozharsky.dmitri.util;

public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static int defineAmountPage(int amountEntity, int perPage) {
        return (amountEntity % perPage == 0 ? amountEntity / perPage : amountEntity / perPage + 1);
    }
}
