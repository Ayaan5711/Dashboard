package com.sales_record.sales_record.advices;

import com.sales_record.sales_record.exception.FileProcessingException;
import com.sales_record.sales_record.exception.InvalidFileFormatException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFileFormat(InvalidFileFormatException ex) {
        log.error("Invalid file format: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage(), "INVALID_FILE_FORMAT"));
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileProcessing(FileProcessingException ex) {
        log.error("File processing error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ex.getMessage(), "FILE_PROCESSING_ERROR"));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.error("File size exceeded limit");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("File size exceeds the allowed limit", "FILE_SIZE_LIMIT_EXCEEDED"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred", "INTERNAL_SERVER_ERROR"));
    }
}