
package com.v2retail.dotvik.modal;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@Keep
public class SelectDateResponse implements Serializable  {

    @SerializedName("ES_RETURN")
    private ESRETURN mESRETURN;
    @SerializedName("ET_DATA")
    private List<ETDATum> mETDATA;

    public ESRETURN getESRETURN() {
        return mESRETURN;
    }

    public void setESRETURN(ESRETURN eSRETURN) {
        mESRETURN = eSRETURN;
    }

    public List<ETDATum> getETDATA() {
        return mETDATA;
    }

    public void setETDATA(List<ETDATum> eTDATA) {
        mETDATA = eTDATA;
    }

}
