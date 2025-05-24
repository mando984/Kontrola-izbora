package com.discord.Projekat_Izbori.exceptions;

public class InvalidJsonFormatException extends RuntimeException{
    public InvalidJsonFormatException(String message, Throwable cause){
        super(message, cause);
    }
    public InvalidJsonFormatException(String message) {
        super(message);
    }
}
