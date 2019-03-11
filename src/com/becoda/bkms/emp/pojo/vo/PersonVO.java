package com.becoda.bkms.emp.pojo.vo;


/**
 * User: Administrator
 * Date: 2015-7-3
 * Time: 9:51:59
 */
public class PersonVO  {
    private String personId;     //ID
    private String orgId;        //A001701
    private String deptId;       //A001705
    private String postId;      //A001715
    private String postTime;//担任该岗位的时间
    private String name;       //A001001
    private String sex;        //A001007
    private String birth;      //A001011
    private String age;//年龄       A001205
    private String personCode;  //A001735
    private String orgTreeId;    //A001728
    private String deptTreeId;   //A001738
    private String workYears;//工作年限    A001710
    private String partyTreeId;      //A001740
    private String groupTreeId;// 所属团组织treeId   A001741
    private String labourTreeId;//所属工会treeId     A001742
    private String partyFigure; //政治面貌 A001201
    private String partyTime;// 入党（团）时间 A001718
    private String personType;//在岗人员类别 A001214
    private String status;//当前状态    A001725
    private String homeTown;//籍贯      A001212
    private String homePlace;//出生地   A001215
    private String folk;//民族   A001765
    private String workTime;//参加工作时间  A001041
    private String unitTime;//来本行时间    A001207
    private String idNum;//身份证号       A001077
    private String photo;//照片           A001225
    private String sort;//排序
    private String cancel;//是否逻辑删除            A001730
    private String deptSort;//是否逻辑删除            A001743
    private String marriage; //婚姻状况  A001240
    private String health;//健康状况A001229
    //新增
    private String labourMember;//是否工会会员  A001255
    private String economicYear;//经济工作从业年限  A001200
    private String elevelKind;//                                         学历性质
    private String evername;//A001226                           曾用名
    private String financeTime;//A001236          金融工作从业年限
    private String mobilePhone;//A001236          金融工作从业年限

    private String format;//入行形式
    private String inretireDate;// 内退时间
    private String postCall1;//  最高职称1
    private String homePhone;//A001241 家庭电话
    private String retireDate;//A001243 退休时间
    private String nameSpell;//姓名拼音
    private String strongPoint;//专长 A001054
    private String hobby;//A001239 爱好
    private String familyAdd; // 家庭地址 A001238

    private String changeType;   //A016010 增减员类别
    private String changeDate;  //A016020  增减员日期
    private String changeUnit;  //A016030   来往单位
    private String tractDate;   //A016040   办理日期
    private String tractPerson; //A016045   办理人

    //增减类别
    private String A016010;//增减类别
    private String A016040;//增减时间
    private String A016020;//增减原因
    private String A016030;//原单位
    private String A016200;//备注

    
    //add by hudl 2015-10-14 
    private String newPassword;//密码
    
    public static String age_Field = "A001205";
    public static String strongPoint_Field = "A001054";
    public static String hobby_Field = "A001239";
    public static String orgId_Field = "A001701";
    public static String deptId_Field = "A001705";
    public static String postId_Field = "A001715";
    public static String name_Field = "A001001";
    public static String sex_Field = "A001007";
    public static String birth_Field = "A001011";
    public static String personCode_Field = "A001735";
    public static String orgTreeId_Field = "A001728";
    public static String deptTreeId_Field = "A001738";
    public static String partyFigure_Field = "A001201";
    public static String status_Field = "A001725";
    public static String mobilePhone_Field = "A001206";
    public static String partyTime_Field = "A001718";
    public static String economicYear_Field = "A001200";
    public static String marryStatus_Field = "A001240";
    public static String workTime_Field = "A001041";
    public static String workTime_years = "A001710";
    public static String sysTime_Field = "A001044";
    public static String unitTime_Field = "A001207";
    public static String idNum_Field = "A001077";
    public static String photo_Field = "A001225";
    public static String memo_Field = "A001230";
    public static String residence_Field = "A001227";
    public static String speciality_Field = "A001232";
    public static String sort_Field = "A001745";
    public static String cancel_Field = "A001730";
    public static String deptSortl_Field = "A001743";
    public static String retireCancel_Field = "A001755";
    public static String evernName_Field = "A001226";
    public static String personType_Field = "A001214";
    public static String homeTown_Field = "A001212";
    public static String homePlace_Field = "A001215";
    public static String homePhone_Field = "A001241";
    public static String folk_Field = "A001765";
    public static String financeTime_Field = "A001236";
    public static String health_Field = "A001229";
    public static String familyAdd_Field = "A001238";
    
    
    public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getA016010() {
        return A016010;
    }

    public void setA016010(String a016010) {
        A016010 = a016010;
    }

    public String getA016040() {
        return A016040;
    }

    public void setA016040(String a016040) {
        A016040 = a016040;
    }

    public String getA016020() {
        return A016020;
    }

    public void setA016020(String a016020) {
        A016020 = a016020;
    }

    public String getA016030() {
        return A016030;
    }

    public void setA016030(String a016030) {
        A016030 = a016030;
    }

    public String getA016200() {
        return A016200;
    }

    public void setA016200(String a016200) {
        A016200 = a016200;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFamilyAdd() {
        return familyAdd;
    }

    public void setFamilyAdd(String familyAdd) {
        this.familyAdd = familyAdd;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getHomePlace() {
        return homePlace;
    }

    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    public String getEconomicYear() {
        return economicYear;
    }

    public void setEconomicYear(String economicYear) {
        this.economicYear = economicYear;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getStrongPoint() {
        return strongPoint;
    }

    public void setStrongPoint(String strongPoint) {
        this.strongPoint = strongPoint;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public static String getAge_Field() {
        return age_Field;
    }

    public static void setAge_Field(String age_Field) {
        PersonVO.age_Field = age_Field;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(String orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public String getDeptTreeId() {
        return deptTreeId;
    }

    public void setDeptTreeId(String deptTreeId) {
        this.deptTreeId = deptTreeId;
    }

    public String getPartyFigure() {
        return partyFigure;
    }

    public void setPartyFigure(String partyFigure) {
        this.partyFigure = partyFigure;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPartyTime() {
        return partyTime;
    }

    public void setPartyTime(String partyTime) {
        this.partyTime = partyTime;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPartyTreeId() {
        return partyTreeId;
    }

    public void setPartyTreeId(String partyTreeId) {
        this.partyTreeId = partyTreeId;
    }


    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }


    public String getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(String unitTime) {
        this.unitTime = unitTime;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getDeptSort() {
        return deptSort;
    }

    public void setDeptSort(String deptSort) {
        this.deptSort = deptSort;
    }

    public String getElevelKind() {
        return elevelKind;
    }

    public void setElevelKind(String elevelKind) {
        this.elevelKind = elevelKind;
    }

    public String getEvername() {
        return evername;
    }

    public void setEvername(String evername) {
        this.evername = evername;
    }

    public String getFinanceTime() {
        return financeTime;
    }

    public void setFinanceTime(String financeTime) {
        this.financeTime = financeTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getInretireDate() {
        return inretireDate;
    }

    public void setInretireDate(String inretireDate) {
        this.inretireDate = inretireDate;
    }

    public String getPostCall1() {
        return postCall1;
    }

    public void setPostCall1(String postCall1) {
        this.postCall1 = postCall1;
    }

    public String getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(String retireDate) {
        this.retireDate = retireDate;
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

    public static String getOrgId_Field() {
        return orgId_Field;
    }

    public static void setOrgId_Field(String orgId_Field) {
        PersonVO.orgId_Field = orgId_Field;
    }

    public static String getDeptId_Field() {
        return deptId_Field;
    }

    public static void setDeptId_Field(String deptId_Field) {
        PersonVO.deptId_Field = deptId_Field;
    }

    public static String getPostId_Field() {
        return postId_Field;
    }

    public static void setPostId_Field(String postId_Field) {
        PersonVO.postId_Field = postId_Field;
    }

    public static String getName_Field() {
        return name_Field;
    }

    public static void setName_Field(String name_Field) {
        PersonVO.name_Field = name_Field;
    }

    public static String getSex_Field() {
        return sex_Field;
    }

    public static void setSex_Field(String sex_Field) {
        PersonVO.sex_Field = sex_Field;
    }

    public static String getBirth_Field() {
        return birth_Field;
    }

    public static void setBirth_Field(String birth_Field) {
        PersonVO.birth_Field = birth_Field;
    }

    public static String getPersonCode_Field() {
        return personCode_Field;
    }

    public static void setPersonCode_Field(String personCode_Field) {
        PersonVO.personCode_Field = personCode_Field;
    }

    public static String getOrgTreeId_Field() {
        return orgTreeId_Field;
    }

    public static void setOrgTreeId_Field(String orgTreeId_Field) {
        PersonVO.orgTreeId_Field = orgTreeId_Field;
    }

    public static String getDeptTreeId_Field() {
        return deptTreeId_Field;
    }

    public static void setDeptTreeId_Field(String deptTreeId_Field) {
        PersonVO.deptTreeId_Field = deptTreeId_Field;
    }
    /* public static String getHomeAddress_Field() {
        return homeAddress_Field;
    }

    public static void setHomeAddress_Field(String homeAddress_Field) {
        PersonVO.homeAddress_Field = homeAddress_Field;
    }*/

    public static String getWorkTime_Field() {
        return workTime_Field;
    }

    public static void setWorkTime_Field(String workTime_Field) {
        PersonVO.workTime_Field = workTime_Field;
    }

    public static String getSysTime_Field() {
        return sysTime_Field;
    }

    public static void setSysTime_Field(String sysTime_Field) {
        PersonVO.sysTime_Field = sysTime_Field;
    }

    public static String getUnitTime_Field() {
        return unitTime_Field;
    }

    public static void setUnitTime_Field(String unitTime_Field) {
        PersonVO.unitTime_Field = unitTime_Field;
    }

    public static String getIdNum_Field() {
        return idNum_Field;
    }

    public static void setIdNum_Field(String idNum_Field) {
        PersonVO.idNum_Field = idNum_Field;
    }

    public static String getPhoto_Field() {
        return photo_Field;
    }

    public static void setPhoto_Field(String photo_Field) {
        PersonVO.photo_Field = photo_Field;
    }

    public static String getMemo_Field() {
        return memo_Field;
    }

    public static void setMemo_Field(String memo_Field) {
        PersonVO.memo_Field = memo_Field;
    }

    public static String getHealth_Field() {
        return health_Field;
    }

    public static void setHealth_Field(String health_Field) {
        PersonVO.health_Field = health_Field;
    }

    public static String getResidence_Field() {
        return residence_Field;
    }

    public static void setResidence_Field(String residence_Field) {
        PersonVO.residence_Field = residence_Field;
    }

    public static String getSpeciality_Field() {
        return speciality_Field;
    }

    public static void setSpeciality_Field(String speciality_Field) {
        PersonVO.speciality_Field = speciality_Field;
    }

    public static String getSort_Field() {
        return sort_Field;
    }

    public static void setSort_Field(String sort_Field) {
        PersonVO.sort_Field = sort_Field;
    }

    public static String getCancel_Field() {
        return cancel_Field;
    }

    public static void setCancel_Field(String cancel_Field) {
        PersonVO.cancel_Field = cancel_Field;
    }

    public static String getDeptSortl_Field() {
        return deptSortl_Field;
    }

    public static void setDeptSortl_Field(String deptSortl_Field) {
        PersonVO.deptSortl_Field = deptSortl_Field;
    }

    public static String getRetireCancel_Field() {
        return retireCancel_Field;
    }

    public static void setRetireCancel_Field(String retireCancel_Field) {
        PersonVO.retireCancel_Field = retireCancel_Field;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLabourMember() {
        return labourMember;
    }

    public void setLabourMember(String labourMember) {
        this.labourMember = labourMember;
    }


    public String getNameSpell() {
        return nameSpell;
    }

    public void setNameSpell(String nameSpell) {
        this.nameSpell = nameSpell;
    }

    public String getGroupTreeId() {
        return groupTreeId;
    }

    public void setGroupTreeId(String groupTreeId) {
        this.groupTreeId = groupTreeId;
    }

    public String getLabourTreeId() {
        return labourTreeId;
    }

    public void setLabourTreeId(String labourTreeId) {
        this.labourTreeId = labourTreeId;
    }
}
