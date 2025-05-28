package com.discord.Projekat_Izbori.exceptions;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message, Throwable e) {
        super(message, e);
    }
}