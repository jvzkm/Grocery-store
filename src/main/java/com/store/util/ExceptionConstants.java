package com.store.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ExceptionConstants {
    public static final String BANK_NOT_FND = "The requested bank account does not exist";
    public static final String ITEM_NOT_FND = "The requested item does not exist";
    public static final String WORK_NOT_FND = "The requested worker does not exist";
    public static final String INSUFFICIENT_FUNDS = "The process failed because of insufficient funds in an account";
    public static final String TYPE_HEADER = "The TYPE header is mandatory and need to be valid";
    public static final String MALFORMED = "Request object JSON malformed";
    public static final String ILLEGAL = "You can not buy any sold or expired item";

}
