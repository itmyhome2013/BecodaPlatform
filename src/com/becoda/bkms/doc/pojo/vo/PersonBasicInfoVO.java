package com.becoda.bkms.doc.pojo.vo;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-9
 * Time: 18:09:25
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.List;

public class PersonBasicInfoVO {
    private String personId;     //ID
    //A001表
    private String name;       //A001001
    private String sex;        //A001007
    private String birth;      //A001011
    private String homeTown;//籍贯      A001014
    private String partyFigure;  //政治面貌 A001201
    private String partyTime;//参加党派时间  A001718
    private String homePlace;//出生地   A001017
    private String folk;//民族   A001021
    private String marry;//婚姻状况  A001027
    private String health;//健康状况            A001223
    private String workTime;//参加工作时间  A001041
    private String unitTime;//来本行时间    A001207

    private String orgId;        //A001701 机构id
    private String deptId;       //A001705 部门id

    private String status;//当前状态    A001725

    private String idNum;//身份证号       A001077
    private String personCode;  //员工编号 A001735
//    private String postTime;//担任该岗位的时间  A001254
    private String homeAddress;//家庭住址   A001721

    private String memo;//备注            A001230

    private String photo;//照片           A001225
    private String hkkind;//户口性质  A001218
    private String residence;//户口所在地            A001227
    private String street;//所在街道   A001253
    private String english;//外语水平   掌握何种外语
    private String secEnglish;//第二外语  熟练程度
    private String computer;//计算机能力
    // A241
    private String height;//
    private String blood;//
    private String interest;//特长爱好
    private String strongPoint;//专长
    // A282
    private String postalCode;// A282200  邮政编码
    private String mobile;//
    private String familyphone;  //
    private String email;//


//    //A211
//    private String postCall1;//最高专业技术资格
//    private String postCallTime; //取得时间
//    private String postImportant; //重要职务
//    private String personLev;//行业等级
//    private String profession;//专业技术
//    private String professionTime;//取得时间
//    //A202职务信息子集
//    private String postTime;//任职时间
//    private String levPostTime;//任同级时间


    //A705表 任职情况子集
    private String duty;//A705205 职务名称
    private String dutyList;//A705201 职务序列
    private String dutyRate;//A705208 职等
    private String postAndDept;//现任职部门及职务
    //    private String dutySameBeginDate;//A705228 任同职等职务时间
    private String dutyStartDate;//聘任起始时间 A705222
    private String dutyExpireDate;//解聘或免职时间 A705260


    //A246 学历
    private String studyRecord;//A246205 学历
    private String studySchool;//A004035 毕业学校
    private String studyStartDate;//A004015 入学时间
    private String studyGraduateDate; //A004030 毕业时间
    private String studyMajor;//A004012 专业
    private String studySchoolAndMajor; //毕业院校及专业

    private String studyRecordAW;//A004005 在职学历
    private String studySchoolAW;//A004035 在职毕业学校
    private String studyStartDateAW;//A004015 入学时间
    private String studyGraduateDateAW; //A004030 毕业时间
    private String studyMajorAW;//A004012 在职专业
    private String studySchoolAndMajorAW; //在职毕业院校及专业

    private String degree;//A840701 学位
    private String degreeAW;//A840701 学位

    //A019 工作经历
    private List resumeList;//简历列表
    private String resumeListString;//简历列表字符串用<br>换行
    //A019 学习经历
    private List studyList;//简历列表
    private String studyListString;//简历列表字符串用<br>换行
    //证书信息
    private List letterList;
    private String letterListString;
    //奖惩信息
    private List rwList;
    private String rwListString;
    //  A715
    //A079	出国信息子集
    private List abroadList = new ArrayList();//出国信息列表

    //A079	家庭成员子集
    private List familyList = new ArrayList();//家庭成员列表

    

    public String getPostAndDept() {
        return postAndDept;
    }

    public void setPostAndDept(String postAndDept) {
        this.postAndDept = postAndDept;
    }

    public String getStrongPoint() {
        return strongPoint;
    }

    public void setStrongPoint(String strongPoint) {
        this.strongPoint = strongPoint;
    }

    

    

    /**
     * @return Returns the birth.
     */
    public String getBirth() {
        return birth;
    }

    /**
     * @param birth The birth to set.
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * @return Returns the degree.
     */
    public String getDegree() {
        return degree;
    }

    /**
     * @param degree The degree to set.
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * @return Returns the deptId.
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * @param deptId The deptId to set.
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * @return Returns the duty.
     */
    public String getDuty() {
        return duty;
    }

    /**
     * @param duty The duty to set.
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * @return Returns the dutyExpireDate.
     */
    public String getDutyExpireDate() {
        return dutyExpireDate;
    }

    /**
     * @param dutyExpireDate The dutyExpireDate to set.
     */
    public void setDutyExpireDate(String dutyExpireDate) {
        this.dutyExpireDate = dutyExpireDate;
    }

    /**
     * @return Returns the dutyList.
     */
    public String getDutyList() {
        return dutyList;
    }

    /**
     * @param dutyList The dutyList to set.
     */
    public void setDutyList(String dutyList) {
        this.dutyList = dutyList;
    }

    /**
     * @return Returns the dutyRate.
     */
    public String getDutyRate() {
        return dutyRate;
    }

    /**
     * @param dutyRate The dutyRate to set.
     */
    public void setDutyRate(String dutyRate) {
        this.dutyRate = dutyRate;
    }

    /**
     * @return Returns the dutyStartDate.
     */
    public String getDutyStartDate() {
        return dutyStartDate;
    }

    /**
     * @param dutyStartDate The dutyStartDate to set.
     */
    public void setDutyStartDate(String dutyStartDate) {
        this.dutyStartDate = dutyStartDate;
    }

    /**
     * @return Returns the folk.
     */
    public String getFolk() {
        return folk;
    }

    /**
     * @param folk The folk to set.
     */
    public void setFolk(String folk) {
        this.folk = folk;
    }

    /**
     * @return Returns the health.
     */
    public String getHealth() {
        return health;
    }

    /**
     * @param health The health to set.
     */
    public void setHealth(String health) {
        this.health = health;
    }

    /**
     * @return Returns the homeAddress.
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * @param homeAddress The homeAddress to set.
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * @return Returns the homePlace.
     */
    public String getHomePlace() {
        return homePlace;
    }

    /**
     * @param homePlace The homePlace to set.
     */
    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    /**
     * @return Returns the homeTown.
     */
    public String getHomeTown() {
        return homeTown;
    }

    /**
     * @param homeTown The homeTown to set.
     */
    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    /**
     * @return Returns the idNum.
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * @param idNum The idNum to set.
     */
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    /**
     * @return Returns the memo.
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo The memo to set.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the orgId.
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId The orgId to set.
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return Returns the partyFigure.
     */
    public String getPartyFigure() {
        return partyFigure;
    }

    /**
     * @param partyFigure The partyFigure to set.
     */
    public void setPartyFigure(String partyFigure) {
        this.partyFigure = partyFigure;
    }

    /**
     * @return Returns the partyTime.
     */
    public String getPartyTime() {
        return partyTime;
    }

    /**
     * @param partyTime The partyTime to set.
     */
    public void setPartyTime(String partyTime) {
        this.partyTime = partyTime;
    }

    /**
     * @return Returns the personCode.
     */
    public String getPersonCode() {
        return personCode;
    }

    /**
     * @param personCode The personCode to set.
     */
    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    /**
     * @return Returns the personId.
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * @param personId The personId to set.
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * @return Returns the photo.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo to set.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }


    /**
     * @return Returns the sex.
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex The sex to set.
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the studyMajor.
     */
    public String getStudyMajor() {
        return studyMajor;
    }

    /**
     * @param studyMajor The studyMajor to set.
     */
    public void setStudyMajor(String studyMajor) {
        this.studyMajor = studyMajor;
    }

    /**
     * @return Returns the studyRecord.
     */
    public String getStudyRecord() {
        return studyRecord;
    }

    /**
     * @param studyRecord The studyRecord to set.
     */
    public void setStudyRecord(String studyRecord) {
        this.studyRecord = studyRecord;
    }

    /**
     * @return Returns the studySchool.
     */
    public String getStudySchool() {
        return studySchool;
    }

    /**
     * @param studySchool The studySchool to set.
     */
    public void setStudySchool(String studySchool) {
        this.studySchool = studySchool;
    }

    /**
     * @return Returns the studySchoolAndMajor.
     */
    public String getStudySchoolAndMajor() {
        return studySchoolAndMajor;
    }

    /**
     * @param studySchoolAndMajor The studySchoolAndMajor to set.
     */
    public void setStudySchoolAndMajor(String studySchoolAndMajor) {
        this.studySchoolAndMajor = studySchoolAndMajor;
    }

   

    /**
     * @return Returns the unitTime.
     */
    public String getUnitTime() {
        return unitTime;
    }

    /**
     * @param unitTime The unitTime to set.
     */
    public void setUnitTime(String unitTime) {
        this.unitTime = unitTime;
    }

    /**
     * @return Returns the workTime.
     */
    public String getWorkTime() {
        return workTime;
    }

    /**
     * @param workTime The workTime to set.
     */
    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    /**
     * @return Returns the resumeList.
     */
    public List getResumeList() {
        return resumeList;
    }

    /**
     * @param resumeList The resumeList to set.
     */
    public void setResumeList(List resumeList) {
        this.resumeList = resumeList;
    }

    /**
     * @return Returns the resumeListString.
     */
    public String getResumeListString() {
        return resumeListString;
    }

    /**
     * @param resumeListString The resumeListString to set.
     */
    public void setResumeListString(String resumeListString) {
        this.resumeListString = resumeListString;
    }

    /**
     * @return Returns the familyList.
     */
    public List getFamilyList() {
        return familyList;
    }

    /**
     * @param familyList The familyList to set.
     */
    public void setFamilyList(List familyList) {
        this.familyList = familyList;
    }

    /**
     * @return Returns the studyGraduateDate.
     */
    public String getStudyGraduateDate() {
        return studyGraduateDate;
    }

    /**
     * @param studyGraduateDate The studyGraduateDate to set.
     */
    public void setStudyGraduateDate(String studyGraduateDate) {
        this.studyGraduateDate = studyGraduateDate;
    }

    /**
     * @return Returns the studyGraduateDateAW.
     */
    public String getStudyGraduateDateAW() {
        return studyGraduateDateAW;
    }

    /**
     * @param studyGraduateDateAW The studyGraduateDateAW to set.
     */
    public void setStudyGraduateDateAW(String studyGraduateDateAW) {
        this.studyGraduateDateAW = studyGraduateDateAW;
    }

    /**
     * @return Returns the studyMajorAW.
     */
    public String getStudyMajorAW() {
        return studyMajorAW;
    }

    /**
     * @param studyMajorAW The studyMajorAW to set.
     */
    public void setStudyMajorAW(String studyMajorAW) {
        this.studyMajorAW = studyMajorAW;
    }

    /**
     * @return Returns the studyRecordAW.
     */
    public String getStudyRecordAW() {
        return studyRecordAW;
    }

    /**
     * @param studyRecordAW The studyRecordAW to set.
     */
    public void setStudyRecordAW(String studyRecordAW) {
        this.studyRecordAW = studyRecordAW;
    }

    /**
     * @return Returns the studySchoolAW.
     */
    public String getStudySchoolAW() {
        return studySchoolAW;
    }

    /**
     * @param studySchoolAW The studySchoolAW to set.
     */
    public void setStudySchoolAW(String studySchoolAW) {
        this.studySchoolAW = studySchoolAW;
    }

    /**
     * @return Returns the studyStartDate.
     */
    public String getStudyStartDate() {
        return studyStartDate;
    }

    /**
     * @param studyStartDate The studyStartDate to set.
     */
    public void setStudyStartDate(String studyStartDate) {
        this.studyStartDate = studyStartDate;
    }

    /**
     * @return Returns the studyStartDateAW.
     */
    public String getStudyStartDateAW() {
        return studyStartDateAW;
    }

    /**
     * @param studyStartDateAW The studyStartDateAW to set.
     */
    public void setStudyStartDateAW(String studyStartDateAW) {
        this.studyStartDateAW = studyStartDateAW;
    }

    /**
     * @return Returns the degreeAW.
     */
    public String getDegreeAW() {
        return degreeAW;
    }

    /**
     * @param degreeAW The degreeAW to set.
     */
    public void setDegreeAW(String degreeAW) {
        this.degreeAW = degreeAW;
    }

    /**
     * @return Returns the studySchoolAndMajorAW.
     */
    public String getStudySchoolAndMajorAW() {
        return studySchoolAndMajorAW;
    }

    /**
     * @param studySchoolAndMajorAW The studySchoolAndMajorAW to set.
     */
    public void setStudySchoolAndMajorAW(String studySchoolAndMajorAW) {
        this.studySchoolAndMajorAW = studySchoolAndMajorAW;
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    public String getHkkind() {
        return hkkind;
    }

    public void setHkkind(String hkkind) {
        this.hkkind = hkkind;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getSecEnglish() {
        return secEnglish;
    }

    public void setSecEnglish(String secEnglish) {
        this.secEnglish = secEnglish;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFamilyphone() {
        return familyphone;
    }

    public void setFamilyphone(String familyphone) {
        this.familyphone = familyphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public List getStudyList() {
        return studyList;
    }

    public void setStudyList(List studyList) {
        this.studyList = studyList;
    }

    public String getStudyListString() {
        return studyListString;
    }

    public void setStudyListString(String studyListString) {
        this.studyListString = studyListString;
    }

   
    public List getAbroadList() {
        return abroadList;
    }

    public void setAbroadList(List abroadList) {
        this.abroadList = abroadList;
    }

    public List getLetterList() {
        return letterList;
    }

    public void setLetterList(List letterList) {
        this.letterList = letterList;
    }

    public String getLetterListString() {
        return letterListString;
    }

    public List getRwList() {
        return rwList;
    }

    public void setRwList(List rwList) {
        this.rwList = rwList;
    }

    public String getRwListString() {
        return rwListString;
    }

    public void setRwListString(String rwListString) {
        this.rwListString = rwListString;
    }

    public void setLetterListString(String letterListString) {
        this.letterListString = letterListString;
    }


}

