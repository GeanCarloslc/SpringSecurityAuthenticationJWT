package io.github.geancarloslc.api;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


public class ApiErros {

    @Getter
    private List<String> errors;

    public ApiErros(List<String> errors) {
        this.errors = errors;
    }

    public ApiErros(String mensaagemErro){
        this.errors = Arrays.asList(mensaagemErro);
    }
}
