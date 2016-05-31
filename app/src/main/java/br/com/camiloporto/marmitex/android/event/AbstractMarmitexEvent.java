package br.com.camiloporto.marmitex.android.event;

import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by ur42 on 09/05/2016.
 */
public class AbstractMarmitexEvent {

    protected boolean success;
    protected MarmitexException exception;

    public AbstractMarmitexEvent(boolean success) {
        this.success = success;
    }

    public AbstractMarmitexEvent(MarmitexException exception) {

        this.exception = exception;
    }

    public AbstractMarmitexEvent() {
        this(true);
    }

    public boolean isSuccess() {
        return success;
    }
}
