package com.becoda.bkms.pms.pojo.bo;

/**
 * Created by IntelliJ IDEA.
 * User: lirg
 * Date: 2015-7-25
 * Time: 16:31:16
 * To change this template use File | Settings | File Templates.
 */
public class ZoneCodeBO {
    private String zoneCode;
    private String zoneName;
    private String regionalismCode;
    private String orgId;

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getRegionalismCode() {
        return regionalismCode;
    }

    public void setRegionalismCode(String regionalismCode) {
        this.regionalismCode = regionalismCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
