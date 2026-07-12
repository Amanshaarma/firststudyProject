package com.tms.Main.Expection;

import javax.xml.transform.sax.SAXResult;

public class ConstraintViolationException  extends  RuntimeException
{
    private final String errorCode;


    public ConstraintViolationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
