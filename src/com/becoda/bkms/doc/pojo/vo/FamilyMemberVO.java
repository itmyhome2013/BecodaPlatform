package com.becoda.bkms.doc.pojo.vo;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-13
 * Time: 10:41:57
 */
public class FamilyMemberVO {
    //	A079	家庭成员子集
    private String familyRelation;//称谓 A079010
    private String familyName;//家属名字 A079005
    private String familySex;//性别 A079202
    private String folk;//性别 A079203
    private String familyBirth;//出生日期 A079015
    private String familyPartyFigure;//政治面貌 A079025
    private String familyWorkInfo;//工作单位及职务 A079020
    private String familyStudyRec;//学历
    private String familyphone;//电话
    private String familyType;//成员类别
    private String familyNow;//成员现状

    public String getFamilySex() {
        return familySex;
    }

    public void setFamilySex(String familySex) {
        this.familySex = familySex;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    /**
     * @return Returns the familyBirth.
     */
    public String getFamilyBirth() {
        return familyBirth;
    }

    /**
     * @param familyBirth The familyBirth to set.
     */
    public void setFamilyBirth(String familyBirth) {
        this.familyBirth = familyBirth;
    }

    /**
     * @return Returns the familyName.
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName The familyName to set.
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return Returns the familyPartyFigure.
     */
    public String getFamilyPartyFigure() {
        return familyPartyFigure;
    }

    /**
     * @param familyPartyFigure The familyPartyFigure to set.
     */
    public void setFamilyPartyFigure(String familyPartyFigure) {
        this.familyPartyFigure = familyPartyFigure;
    }

    /**
     * @return Returns the familyRelation.
     */
    public String getFamilyRelation() {
        return familyRelation;
    }

    /**
     * @param familyRelation The familyRelation to set.
     */
    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public String getFamilyStudyRec() {
        return familyStudyRec;
    }

    public void setFamilyStudyRec(String familyStudyRec) {
        this.familyStudyRec = familyStudyRec;
    }

    public String getFamilyphone() {
        return familyphone;
    }

    public void setFamilyphone(String familyphone) {
        this.familyphone = familyphone;
    }

    /**
     * @return Returns the familyWorkInfo.
     */
    public String getFamilyWorkInfo() {
        return familyWorkInfo;
    }

    /**
     * @param familyWorkInfo The familyWorkInfo to set.
     */
    public void setFamilyWorkInfo(String familyWorkInfo) {
        this.familyWorkInfo = familyWorkInfo;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getFamilyNow() {
        return familyNow;
    }

    public void setFamilyNow(String familyNow) {
        this.familyNow = familyNow;
    }
}
