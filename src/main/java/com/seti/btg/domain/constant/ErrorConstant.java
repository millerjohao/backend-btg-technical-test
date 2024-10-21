package com.seti.btg.domain.constant;

public class ErrorConstant {
    public static final String CUSTOMER_ALREADY_EXISTS = "Cliente ya existente";
    public static final String CUSTOMER_NOT_FOUND = "No existe el usuario con el id %s";
    public static final String FUND_NOT_FOUND = "No existe fondo con el id %s";
    public static final String FUND_ALREADY_EXISTS = "Fondo ya existente";
    public static final String CUSTOMER_NOT_IN_FUND = "El cliente no está asociado al fondo";
    public static final String UNFUNDED_SUBSCRIPTION = "No tiene saldo disponible para vincularse al fondo %s";
    public static final String ENDPOINT_NOT_IMPLEMENTED = "Endpoint no implementado";
    public static final String NOT_VALID_NUMBER = "Número no verificado por capa gratuita";
    public static final String USER_ALREADY_IN_FUND = "El cliente ya está vinculado a este fondo";
    public static final String MIN_AMOUNT_NO_REACHED = "Monto mínimo de fondo no alcanzado";
}
