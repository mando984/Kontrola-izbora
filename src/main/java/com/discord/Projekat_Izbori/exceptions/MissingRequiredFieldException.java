package com.discord.Projekat_Izbori.exceptions;

public class MissingRequiredFieldException extends RuntimeException{
    public MissingRequiredFieldException(String message) {
        super(message);
    }
}
