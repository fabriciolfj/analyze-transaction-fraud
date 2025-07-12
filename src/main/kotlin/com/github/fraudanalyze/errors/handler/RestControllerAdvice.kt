package com.github.fraudanalyze.errors.handler

import com.github.fraudanalyze.errors.dto.ErrorDTO
import com.github.fraudanalyze.errors.dto.ErrorDetailsDTO
import jakarta.validation.ConstraintViolationException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestControllerAdvice(private val messageSource: MessageSource) {

    companion object {
        private const val MESSAGE_VALIDATION = "validation field request"
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorDTO(code = HttpStatus.BAD_REQUEST.value(),
                    message = MESSAGE_VALIDATION,
                    details = mappingError(e)
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorDTO(code = HttpStatus.BAD_REQUEST.value(),
                    message = MESSAGE_VALIDATION,
                    details = mappingError(e)
                )
            )
    }

    private fun mappingError(e: ConstraintViolationException): List<ErrorDetailsDTO> {
        return e.constraintViolations
            .map { obj ->
                val name = if (obj is FieldError) {
                    obj.field
                } else {
                    obj.propertyPath.toString()
                }

                ErrorDetailsDTO(field = name, message = obj.message)
            }.toList()
    }

    private fun mappingError(e: MethodArgumentNotValidException): List<ErrorDetailsDTO> {
        return e.bindingResult.allErrors
            .map { obj ->
                val name = if (obj is FieldError) {
                    obj.field
                } else {
                    obj.objectName
                }

                ErrorDetailsDTO(field = name, message = messageSource.getMessage(obj, LocaleContextHolder.getLocale()))
            }.toList()
    }
}