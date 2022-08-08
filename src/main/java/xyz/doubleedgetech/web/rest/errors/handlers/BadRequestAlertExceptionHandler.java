package xyz.doubleedgetech.web.rest.errors.handlers;

import xyz.doubleedgetech.web.rest.errors.BadRequestAlertException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import jakarta.inject.Singleton;

@Singleton
public class BadRequestAlertExceptionHandler extends ProblemHandler implements ExceptionHandler<BadRequestAlertException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, BadRequestAlertException exception) {
        return create(exception, request, exception);
    }
}
