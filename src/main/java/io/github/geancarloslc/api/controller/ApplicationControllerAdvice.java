package io.github.geancarloslc.api.controller;

import io.github.geancarloslc.api.ApiErros;
import io.github.geancarloslc.exception.PedidoNaoEncontradoExecption;
import io.github.geancarloslc.exception.RegraNegocioException;
import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    // Aponta que o metodo é um trator de erros
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErros handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();

        return new ApiErros(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoExecption.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErros handlePedidoNotFoundExecption(PedidoNaoEncontradoExecption ex){
        String mensagemErro = ex.getMessage();

        return new ApiErros(mensagemErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErros handleMethodNotValidException(MethodArgumentNotValidException exception){
        List<String> erros = exception.getBindingResult().getAllErrors()
                .stream()
                .map( erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErros(erros);
    }

}
