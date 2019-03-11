package com.becoda.bkms.emp.pojo.bo;


/**
 * User: Administrator
 * Date: 2015-6-7
 * Time: 14:25:45
 */
public class PersonBO {

    private String personId;  //人员编号  ID
    private String postId; //所属岗位 A001715
    private String postTime;//担任该岗位的时间  A001254
    private String name; //姓名   A001001
    private String personCode; //工号   A001735
    private String orgId; //所属机构   A001701
    private String deptId;//所属部门   A001705
    private String sex;//性别    A001007
    private String birth; //出生日期  A001011
    private String age;//年龄       A001205
    private String orgTreeId; //所属机构treeId  A001728
    private String deptTreeId;//所属部门treeId  A001738
    private String partyTreeId; //所属党组织treeId   A001740
    private String groupTreeId;// 所属团组织treeId   A001741
    private String labourTreeId;//所属工会treeId     A001742
    private String workYears;//工作年限    A001710
    private String partyFigure; //政治面貌 A001201
    private String partyTime;// 参加党派时间 A001718
    private String labourMember;//是否工会会员  A001255
    private String economicYear;//职级  A001200
    private String personCancel;//人员是否减员  A001730
    private String sort;//人员排序    A001745
    private String deptSort;//人员部门排序 A001743
    private String gongZiGX; //工资关系所在单位  A001204
    private String imageId; // 人员的照片Id A001225
    private String workTime;//参加工作时间  A001041
    private String unitTime; //来本单位时间  A001207
    private String idCard;  //身份证号 A001077
    private String state; //状态 A001725
    private String personType; //在岗人员类别 A001214
    private String retireCancel;//A001755
    //新增
    private String nameSpell;//A001206   姓名拼音
    private String evername;//A001226                           曾用名
    private String financeTime;//A001236          金融从业时间
    private String format;//入行形式
    private String hobby;//A001239 爱好
    private String postCall1;
    private String homePhone;//A001241 家庭电话
    private String retireDate;//A001243 退休时间
    private String evaUnit;//A001208          考核单位
    private String labourId;
    private String familyAdd; // 家庭地址 A001238
    private String health;//健康状况A001229

    private String homeTown; //籍贯   A001212
    private String homePlace;//出生地   A001215
    private String folk;//民族  A001765
    private String marriage; //婚姻状况  A001240
    private String strongPoint;//专长 A001054

    public String getLabourId() {
        return labourId;
    }

    public void setLabourId(String labourId) {
        this.labourId = labourId;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getFamilyAdd() {
        return familyAdd;
    }

    public void setFamilyAdd(String familyAdd) {
        this.familyAdd = familyAdd;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    public String getHomePlace() {
        return homePlace;
    }

    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getEvaUnit() {
        return evaUnit;
    }

    public void setEvaUnit(String evaUnit) {
        this.evaUnit = evaUnit;
    }

    public String getNameSpell() {
        return nameSpell;
    }

    public void setNameSpell(String nameSpell) {
        this.nameSpell = nameSpell;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
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


    public String getPersonCancel() {
        return personCancel;
    }

    public void setPersonCancel(String personCancel) {
        this.personCancel = personCancel;
    }

    public String getGongZiGX() {
        return gongZiGX;
    }

    public void setGongZiGX(String gongZiGX) {
        this.gongZiGX = gongZiGX;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPartyTime() {
        return partyTime;
    }

    public void setPartyTime(String partyTime) {
        this.partyTime = partyTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getUnitTime() {
        return unitTime;
    }

    public void setUnitTime(String unitTime) {
        this.unitTime = unitTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPartyTreeId() {
        return partyTreeId;
    }

    public void setPartyTreeId(String partyTreeId) {
        this.partyTreeId = partyTreeId;
    }

    public String getRetireCancel() {
        return retireCancel;
    }

    public void setRetireCancel(String retireCancel) {
        this.retireCancel = retireCancel;
    }

    public String getDeptSort() {
        return deptSort;
    }

    public void setDeptSort(String deptSort) {
        this.deptSort = deptSort;
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

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getLabourMember() {
        return labourMember;
    }

    public void setLabourMember(String labourMember) {
        this.labourMember = labourMember;
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

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getEconomicYear() {
        return economicYear;
    }

    public void setEconomicYear(String economicYear) {
        this.economicYear = economicYear;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
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
}
