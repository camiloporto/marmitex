package br.com.camiloporto.marmitex.android.event;

import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by ur42 on 02/05/2016.
 */
public class LoginFinishedEvent {
    private boolean success = false;
    private MarmitexException exception;

    public LoginFinishedEvent(boolean success) {
        this.success = success;
    }

    public LoginFinishedEvent(MarmitexException e) {
        this(false);
        exception = e;
    }

    public boolean isSuccess() {
        return success;
    }

    public MarmitexException getException() {
        return exception;
    }
}
