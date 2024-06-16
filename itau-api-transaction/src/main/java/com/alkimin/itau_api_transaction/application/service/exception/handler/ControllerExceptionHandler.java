package com.alkimin.itau_api_transaction.application.service.exception.handler;

import com.alkimin.itau_api_transaction.application.service.exception.*;
import com.alkimin.itau_api_transaction.application.service.exception.handler.response.ErrorResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.TooManyListenersException;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> clienteNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Cliente não encontrado.","Não encontramos nenhum cliente em nossa base com o ID informado."));
    }

    @ExceptionHandler(ContaDestinoNaoEncontradaException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> contaDestinoNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Conta Destino não encontrada.","Não encontramos em nossa base a conta destino com o ID informado."));
    }

    @ExceptionHandler(ContaOrigemNaoEncontradaException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> contaOrigemNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Conta Origem não encontrada.","Não encontramos em nossa base a conta origem com o ID informado."));
    }

    @ExceptionHandler(LimiteDiarioException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> limitExceededException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Limite Diário excedido.","O limite diário da conta foi excedido."));
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> saldoInsuficienteException(){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse("Saldo insuficiente.","O saldo da conta é insuficiente para operação."));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<ErrorResponse> toManyRequestBacenException(){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ErrorResponse("Falha de comunicação Bacen","Falha na comunicação com Bacen."));
    }

    @ExceptionHandler(RequestNotPermitted.class)
    @ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<ErrorResponse> toManyRequestException(RequestNotPermitted ex){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ErrorResponse("Rate Limite Excedido.","Você excedeu o limite de solicitações permitido. Por favor, aguarde alguns momentos antes de tentar novamente."));
    }
}
