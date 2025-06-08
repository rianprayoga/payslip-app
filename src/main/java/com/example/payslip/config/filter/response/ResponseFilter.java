package com.example.payslip.config.filter.response;

import com.example.payslip.errors.ErrorResponse;
import com.example.payslip.errors.http.HttpException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class ResponseFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public ResponseFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(request, response);
            responseWrapper.copyBodyToResponse();
        } catch (HttpException e) {
            writeResponse(response, e.getStatus(), e.getMessage());
        } catch (Exception e) {
            writeResponse(
                    response,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something unexpected happened. Please try again later.");
        }
    }

    private void writeResponse(
            HttpServletResponse response, HttpStatus httpStatus,  String message) {
        response.setStatus(httpStatus.value());
        response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        try {
            PrintWriter writer = response.getWriter();
            ErrorResponse error = new ErrorResponse(message);
            writer.println(objectMapper.writeValueAsString(error));
        } catch (Exception e) {
            log.error("Failed to write error response.", e);
        }
    }
}
