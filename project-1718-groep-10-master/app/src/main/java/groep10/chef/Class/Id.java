package groep10.chef.Class;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thoma on 13/04/2018.
 */

public class Id implements Serializable {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
