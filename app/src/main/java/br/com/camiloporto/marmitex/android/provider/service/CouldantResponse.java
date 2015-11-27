package br.com.camiloporto.marmitex.android.provider.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by camiloporto on 21/11/15.
 */
public class CouldantResponse {

    @SerializedName("ok")
    private String status;

    @SerializedName("rev")
    private String revId;

    @SerializedName("id")
    private String id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRevId() {
        return revId;
    }

    public void setRevId(String revId) {
        this.revId = revId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
