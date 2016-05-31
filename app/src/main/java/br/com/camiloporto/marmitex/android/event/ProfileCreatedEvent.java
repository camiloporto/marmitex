package br.com.camiloporto.marmitex.android.event;

import br.com.camiloporto.marmitex.android.event.AbstractMarmitexEvent;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by ur42 on 09/05/2016.
 */
public class ProfileCreatedEvent extends AbstractMarmitexEvent {

    private Profile createdProfile;

    public ProfileCreatedEvent(boolean success) {
        super(success);
    }

    public ProfileCreatedEvent() {
        super();
    }

    public ProfileCreatedEvent(MarmitexException e) {
        super(e);
    }


    public Profile getCreatedProfile() {
        return createdProfile;
    }

    public void setCreatedProfile(Profile createdProfile) {
        this.createdProfile = createdProfile;
    }
}
