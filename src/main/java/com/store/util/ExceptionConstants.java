package com.store.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ExceptionConstants {
    public static final String BANK_NOT_FND = "The requested bank account does not exist";
    public static final String ITEM_NOT_FND = "The requested item does not exist";
    public static final String WORK_NOT_FND = "The requested worker does not exist";
    public static final String INSUFFICIENT_FUNDS = "The process failed because of insufficient funds in an account";
    public static final String TYPE_HEADER = "The TYPE_HEADER header is mandatory and need to be valid";
    public static final String MALFORMED = "Request object JSON malformed";
    public static final String ILLEGAL_SALE = "You can not buy any sold or expired item";
    public static final String ILLEGAL_TRANSACTION = "You can not use the bank Account if its not active";
    public static final String RESOURCE_NOT_FOUND = "There is no such endpoint";
    public static final String BAD_CREDENTIALS = "The username or password is incorrect";
    public static final String ACC_STATUS = "The account is locked";
    public static final String ACCESS_DENIED= "You are not authorized to access this resource";
    public static final String INVALID_JWT= "The JWT signature is invalid";
    public static final String EXPIRED_JWT= "The JWT signature has expired";
    public static final String CONFLICT= "This request violates some constraints.";

}
