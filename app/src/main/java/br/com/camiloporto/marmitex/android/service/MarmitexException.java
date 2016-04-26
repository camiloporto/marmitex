package br.com.camiloporto.marmitex.android.service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ur42 on 26/04/2016.
 */
public class MarmitexException extends RuntimeException {

    private List<String> errors;

    public MarmitexException(List<String> errors) {
        this.errors = errors;
    }

    public MarmitexException(String error) {
        super(error);
        errors = Arrays.asList(error);
    }

    public List<String> getErrors() {
        return errors;
    }
}
