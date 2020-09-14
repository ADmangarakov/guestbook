package com.github.admangarakov.guestbook.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLConverter implements Converter<String, URL> {
    @Override
    public Result<URL> convertToModel(String fieldValue, ValueContext context) {
        try {
            if (fieldValue == null || fieldValue.equals("")) {
                return Result.ok(null);
            }
            return Result.ok(new URL(fieldValue));
        } catch (MalformedURLException e) {
            return Result.error("Enter a valid URL");
        }
    }

    @Override
    public String convertToPresentation(URL url, ValueContext context) {
        return String.valueOf(url);
    }
}