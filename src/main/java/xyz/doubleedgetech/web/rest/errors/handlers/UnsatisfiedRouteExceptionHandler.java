package xyz.doubleedgetech.web.rest.errors.handlers;

import io.micronaut.http.server.exceptions.UnsatisfiedRouteHandler;
import xyz.doubleedgetech.util.HeaderUtil;
import xyz.doubleedgetech.web.rest.errors.BadRequestAlertException;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
//import io.micronaut.http.server.netty.converters.UnsatisfiedRouteHandler;
import io.micronaut.web.router.exceptions.UnsatisfiedRouteException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import jakarta.inject.Singleton;

@Replaces(UnsatisfiedRouteHandler.class)
@Singleton
public class UnsatisfiedRouteExceptionHandler extends ProblemHandler implements ExceptionHandler<UnsatisfiedRouteException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, UnsatisfiedRouteException exception) {
        Problem problem = Problem.builder()
            .withStatus(Status.BAD_REQUEST)
            .build();
        return create(problem, request, exception);
    }
}
