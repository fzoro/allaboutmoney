package zoret4.allaboutmoney.order.web.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import zoret4.allaboutmoney.order.model.exception.ValidationException

@ControllerAdvice
class ResponseExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handlePreConditionFailure(ex: RuntimeException) = ResponseEntity(arrayOf(ex.message), HttpStatus.PRECONDITION_FAILED)

}