package com.becoda.bkms.qry.pojo.bo;

public class QryWageItemBO implements java.io.Serializable {
    private String qryId;
    private String qryItemId;
    private String setId;
    private String itemId;

    private int showId = 0;

    public String getQryId() {
        return qryId;
    }

    public void setQryId(String qryId) {
        this.qryId = qryId;
    }

    public String getQryItemId() {
        return qryItemId;
    }

    public void setQryItemId(String qryItemId) {
        this.qryItemId = qryItemId;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }


}
