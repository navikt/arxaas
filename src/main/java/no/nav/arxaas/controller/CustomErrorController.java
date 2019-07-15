package no.nav.arxaas.controller;

import no.nav.arxaas.exception.ExceptionResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ExceptionResponse> handleError(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        ExceptionResponse exceptionResponse = ExceptionResponse.now(status.toString(), request.getServletPath());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(status));

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}