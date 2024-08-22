
package com.v2retail.dotvik.modal.material;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


@Keep
public class MaterialModal implements Serializable {

    @SerializedName("ET_PACK_MAT")
    private List<ETPACKMAT> mETPACKMAT;
    @SerializedName("EX_RETURN")
    private EXRETURN mEXRETURN;

    public List<ETPACKMAT> getETPACKMAT() {
        return mETPACKMAT;
    }

    public void setETPACKMAT(List<ETPACKMAT> eTPACKMAT) {
        mETPACKMAT = eTPACKMAT;
    }

    public EXRETURN getEXRETURN() {
        return mEXRETURN;
    }

    public void setEXRETURN(EXRETURN eXRETURN) {
        mEXRETURN = eXRETURN;
    }

}
