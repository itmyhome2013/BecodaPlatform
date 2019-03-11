package com.becoda.bkms.emp.pojo.vo;



/**
 * 增减情况子集
 * User: yxm
 * Date: 2015-7-3
 * Time: 11:30:37
 */
public class PersonChangeVO  {
    private String personId;
    private String changeType;   //A016010 增减员类别
    private String shpDate;//   A016200  审批日期
    private String changeDate;  //A016020  增减员日期
    private String changeUnit;  //A016030   来往单位
    private String relationNum;//A016201   相关文号
    private String tractDate;   //A016040   办理日期
    private String tractPerson; //A016045   办理人
    private String leaveReason;//离职原因

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeUnit() {
        return changeUnit;
    }

    public void setChangeUnit(String changeUnit) {
        this.changeUnit = changeUnit;
    }

    public String getTractDate() {
        return tractDate;
    }

    public void setTractDate(String tractDate) {
        this.tractDate = tractDate;
    }

    public String getTractPerson() {
        return tractPerson;
    }

    public void setTractPerson(String tractPerson) {
        this.tractPerson = tractPerson;
    }

    public String getShpDate() {
        return shpDate;
    }

    public void setShpDate(String shpDate) {
        this.shpDate = shpDate;
    }

    public String getRelationNum() {
        return relationNum;
    }

    public void setRelationNum(String relationNum) {
        this.relationNum = relationNum;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }
}
