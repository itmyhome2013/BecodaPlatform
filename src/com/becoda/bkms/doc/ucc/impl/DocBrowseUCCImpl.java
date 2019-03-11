package com.becoda.bkms.doc.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.doc.DocConstants;
import com.becoda.bkms.doc.pojo.vo.*;
import com.becoda.bkms.doc.ucc.IDocBrowseUCC;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.service.PersonService;
import com.becoda.bkms.emp.EmpConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.sys.util.HrmsMath;
import com.becoda.bkms.util.Tools;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-21
 * Time: 14:25:59
 */
public class DocBrowseUCCImpl implements IDocBrowseUCC {
    private ActivePageService activePageService;
    private PersonService personService;

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }


    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

//    public List queryTimePoints(String bakTimeStart, String bakTimeEnd) throws BkmsException {
//        return orgHisService.queryTimePoints(bakTimeStart, bakTimeEnd);

    //    }
    /* (non-Javadoc)
      * @function 查询机构基本信息
      * @see cn.ccb.hrdc.doc.ucc.IDocBrowseUCC#queryOrgInfo(java.lang.String)
      */

    public OrgBasicInfoVO queryOrgInfo(String orgID) throws BkmsException {

        try {
//			D001 机构信息
            InfoSetBO set = SysCacheTool.findInfoSet("B001");
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId("B001").toArray(new InfoItemBO[SysCacheTool.queryInfoItemBySetId("B001").size()]);
            TableVO tempVO = new TableVO();
            String[] r = activePageService.findRecord(set, header, orgID);
            tempVO.setHeader(header);
            OrgBasicInfoVO orgVO = new OrgBasicInfoVO();
            if (r != null && r.length > 0) {
                orgVO.setName(r[tempVO.getCol("B001005")]);
                orgVO.setOrgCode(r[tempVO.getCol("B001010")]);
                orgVO.setSuperId(getOrgRelation(r[tempVO.getCol("B001002")]) + orgVO.getName());
                orgVO.setOrgClass(SysCacheTool.interpretCode(r[tempVO.getCol("B001075")]));
                orgVO.setOrgLevel(SysCacheTool.interpretCode(r[tempVO.getCol("B001050")]));
                orgVO.setSuperId(SysCacheTool.interpretOrg(r[tempVO.getCol("B001002")]));
                orgVO.setFactPer(SysCacheTool.interpretPerson(r[tempVO.getCol("B001203")]));
                orgVO.setPostCode(r[tempVO.getCol("B001240")]);
                orgVO.setFactPer(r[tempVO.getCol("B001203")]);
                orgVO.setOrgAddress(r[tempVO.getCol("B001225")]);
                orgVO.setOffice(r[tempVO.getCol("B001200")]);
                orgVO.setOrgArea(r[tempVO.getCol("B001214")]);
                orgVO.setPhone(r[tempVO.getCol("B001208")]);
                orgVO.setFax(r[tempVO.getCol("B001212")]);
                String benny = r[tempVO.getCol("B001771")];
                String beny = r[tempVO.getCol("B001772")];
            }
            return orgVO;
        } catch (Exception e) {
            throw new RollbackableException("读取机构基本信息出错", e, this.getClass());
        }
    }

    /**
     * @return 查找机构的隶属关系，以1-->2-->3方式表示
     */
    public String getOrgRelation(String orgID) throws BkmsException {
        String higherOrgId = orgID;
        String strResult = "";
        if (orgID == null || "".equalsIgnoreCase(orgID))
            return "机构隶属关系有错：";

        try {
            TableVO tempVO = new TableVO();
            while (!"-1".equalsIgnoreCase(higherOrgId)) {
                InfoSetBO set = SysCacheTool.findInfoSet("B001");
                InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId("B001").toArray(new InfoItemBO[0]);
                String[] r = activePageService.findRecord(set, header, higherOrgId);
                tempVO.setHeader(header);
                if (r != null && r.length > 0) {
                    strResult = r[tempVO.getCol("B001005")] + "→" + strResult;
                    higherOrgId = r[tempVO.getCol("B001002")];
                } else {
                    strResult = "机构隶属关系有错：";
                    break;
                }
            }
            return strResult;
        } catch (Exception e) {
            throw new RollbackableException("读取机构隶属关系组织信息出错", e, this.getClass());
        }
    }


    public PersonBasicInfoVO queryPersonBasicInfoSet(String personID) throws BkmsException {
        try {
            GenericDAO dao = personService.getPersonDAO();
            //查询人员基本信息
            PersonBasicInfoVO basicInfoVO = new PersonBasicInfoVO();
            PersonBO pbo = personService.findPerson(personID);
            if (pbo != null) {
                basicInfoVO.setPersonId(personID);
                basicInfoVO.setName(Tools.filterNull(pbo.getName()));
                basicInfoVO.setSex(Tools.filterNull(SysCacheTool.interpretCode(pbo.getSex())));
                basicInfoVO.setBirth(Tools.filterNull(pbo.getBirth()));
                basicInfoVO.setBirth(basicInfoVO.getBirth().replaceAll("-", "."));
                basicInfoVO.setPartyFigure(Tools.filterNull(SysCacheTool.interpretCode(pbo.getPartyFigure())));

                basicInfoVO.setPartyTime(Tools.filterNull(pbo.getPartyTime()));
                basicInfoVO.setPartyTime(basicInfoVO.getPartyTime().replaceAll("-", "."));
                basicInfoVO.setWorkTime(Tools.filterNull(pbo.getWorkTime()));
                basicInfoVO.setWorkTime(basicInfoVO.getWorkTime().replaceAll("-", "."));

                basicInfoVO.setUnitTime(Tools.filterNull(pbo.getUnitTime()));
                basicInfoVO.setUnitTime(basicInfoVO.getUnitTime().replaceAll("-", "."));

                basicInfoVO.setOrgId(Tools.filterNull(SysCacheTool.interpretOrg(pbo.getOrgId())));//orgId
                basicInfoVO.setDeptId(Tools.filterNull(SysCacheTool.interpretOrg(pbo.getDeptId())));
                basicInfoVO.setIdNum(Tools.filterNull(pbo.getIdCard()));
                basicInfoVO.setPersonCode(Tools.filterNull(pbo.getPersonCode()));
                basicInfoVO.setPhoto(Tools.filterNull(pbo.getImageId()));
                basicInfoVO.setFolk(SysCacheTool.interpretCode(pbo.getFolk()));
                basicInfoVO.setHomeTown(SysCacheTool.interpretCode(pbo.getHomeTown()));
                basicInfoVO.setHomePlace(SysCacheTool.interpretCode(pbo.getHomePlace()));
                basicInfoVO.setMarry(SysCacheTool.interpretCode(pbo.getMarriage()));
                basicInfoVO.setStrongPoint(Tools.filterNull(pbo.getStrongPoint()));
                Map map = new HashMap();

                

                //		A019 工作经历
                String sql = new StringBuffer("select * from A019 where id = '").append(personID).append("' order by A019005 ASC").toString();
                List list = dao.getJdbcTemplate().queryForList(sql);
                if (list != null && !list.isEmpty()) {
                    String tStr = "&nbsp;&nbsp;&nbsp;&nbsp;<br>";
                    ResumeInfoVO tVO = new ResumeInfoVO();
                    for (int i = 0; i < list.size(); i++) {
                        map = (Map) list.get(i);
                        tStr = tStr + "&nbsp;&nbsp;&nbsp;&nbsp;";
                        tVO.setResumeBeginDate(getString(map, "A019005"));
                        tVO.setResumeBeginDate(tVO.getResumeBeginDate().replaceAll("-", "."));
                        tVO.setResumeEndDate(getString(map, "A019010"));
                        tVO.setResumeEndDate(tVO.getResumeEndDate().replaceAll("-", "."));
                        tVO.setResumeUnit(getString(map, "A019015"));
                        tVO.setResumeDept(getString(map, "A019020"));
                        tVO.setResumeDuty(getString(map, "A019205"));
                        if (tVO.getResumeBeginDate() != null) {
                            tStr = tStr + tVO.getResumeBeginDate().replace('-', '.').substring(0, tVO.getResumeBeginDate().length() < 7 ? tVO.getResumeBeginDate().length() : 7);
                        } else {
                            tStr = tStr + "&nbsp;&nbsp;";
                        }
                        if (tVO.getResumeEndDate() != null && !"".equalsIgnoreCase(tVO.getResumeEndDate())) {
                            tStr = tStr + "--" + tVO.getResumeEndDate().replace('-', '.').substring(0, tVO.getResumeEndDate().length() < 7 ? tVO.getResumeEndDate().length() : 7);
                        } else if (i + 1 == list.size()) {
                            tStr = tStr + "至今&nbsp;&nbsp;&nbsp;&nbsp;";
                        } else {
                            tStr = tStr + "--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                        tStr = tStr + "&nbsp;&nbsp;" + tVO.getResumeUnit() + "&nbsp;&nbsp;" + tVO.getResumeDept() + "&nbsp;&nbsp;" + tVO.getResumeDuty() + "<br>";
                    }
                    tStr = tStr + "&nbsp;&nbsp;<br>";
                    basicInfoVO.setResumeListString(tStr.substring(0, tStr.length() - 4));
                }
                basicInfoVO.setStudyRecord("");
                basicInfoVO.setStudyRecordAW("");
                basicInfoVO.setStudySchool("");
                basicInfoVO.setStudyGraduateDate("");
                basicInfoVO.setStudyMajor("");
                basicInfoVO.setDegree("");
                basicInfoVO.setStudySchoolAndMajor("");
                basicInfoVO.setStudySchoolAW("");
                basicInfoVO.setStudyGraduateDateAW("");
                basicInfoVO.setStudyMajorAW("");
                basicInfoVO.setDegreeAW("");
                basicInfoVO.setStudySchoolAndMajorAW("");
                //	A246 学习经历
                sql = "select * from A246 where id = '" + personID + "' order by A246201 asc";
                list = dao.getJdbcTemplate().queryForList(sql);
                if (list != null && !list.isEmpty()) {
                    String tStr = "&nbsp;&nbsp;&nbsp;&nbsp;<br>";
                    StudyVO tVO = new StudyVO();
                    for (int i = 0; i < list.size(); i++) {
                        map = (Map) list.get(i);
                        tStr = tStr + "&nbsp;&nbsp;&nbsp;&nbsp;";
                        tVO.setStudyBeginDate(getString(map, "A246200"));
                        tVO.setStudyBeginDate(tVO.getStudyBeginDate().replaceAll("-", "."));
                        tVO.setStudyEndDate(getString(map, "A246201"));
                        tVO.setStudyEndDate(tVO.getStudyEndDate().replaceAll("-", "."));
                        tVO.setSchool(getString(map, "A246202"));
                        tVO.setAcademy(getString(map, "A246203"));
                        tVO.setSubject(getString(map, "A246207"));

                        tVO.setStudyRecord(interpretCode(map, "A246205"));
                        tVO.setStudyDegree(interpretCode(map, "A246206"));
                        tVO.setProperty(interpretCode(map, "A246207"));
                        //System.out.println(">简历时间>"+tVO.getResumeBeginDate()+">>"+tVO.getResumeEndDate());
                        if (tVO.getStudyBeginDate() != null) {
                            tStr = tStr + tVO.getStudyBeginDate().replace('-', '.').substring(0, tVO.getStudyBeginDate().length() < 7 ? tVO.getStudyBeginDate().length() : 7);
                        } else {
                            tStr = tStr + "&nbsp;&nbsp;";
                        }
                        if (tVO.getStudyEndDate() != null && !"".equalsIgnoreCase(tVO.getStudyEndDate())) {
                            tStr = tStr + "--" + tVO.getStudyEndDate().replace('-', '.').substring(0, tVO.getStudyEndDate().length() < 7 ? tVO.getStudyEndDate().length() : 7);
                        } else if (i + 1 == list.size()) {//最后一个
                            tStr = tStr + "至今&nbsp;&nbsp;&nbsp;&nbsp;";
                        } else {
                            tStr = tStr + "--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                        tStr = tStr + "&nbsp;" + tVO.getSchool() + "&nbsp;" + tVO.getAcademy() + "&nbsp;" + tVO.getSubject() + "&nbsp;"
//                                + tVO.getStudyRecord() + "&nbsp;" + tVO.getStudyDegree() + "&nbsp;" + tVO.getProperty()
                                + "<br>";
                        String sType = getString(map, "A246219");
                        if (EmpConstants.FULL_TIME.equals(sType) && "".equals(basicInfoVO.getStudyRecord())) {
                            basicInfoVO.setStudyRecord(interpretCode(map, "A246205"));
                            basicInfoVO.setDegree(interpretCode(map, "A246206"));
                            basicInfoVO.setStudyGraduateDate(getString(map, "A246201").replaceAll("-","."));
                            basicInfoVO.setStudySchoolAndMajor(tVO.getSchool() + tVO.getAcademy() + tVO.getProperty());
                        }
                        if (!EmpConstants.FULL_TIME.equals(sType) && "".equals(basicInfoVO.getStudyRecordAW())) {
                            basicInfoVO.setStudyRecordAW(interpretCode(map, "A246205"));
                            basicInfoVO.setDegreeAW(interpretCode(map, "A246206"));
                            basicInfoVO.setStudyGraduateDateAW(getString(map, "A246201").replaceAll("-","."));
                            basicInfoVO.setStudySchoolAndMajorAW(tVO.getSchool() + tVO.getAcademy() + tVO.getProperty());
                        }
                    }
                    tStr = tStr + "&nbsp;&nbsp;<br>";
                    basicInfoVO.setStudyListString(tStr.substring(0, tStr.length() - 4));
                }
               
            }
            return basicInfoVO;
        } catch (Exception e) {
            throw new BkmsException("读取人员基本信息出错！", e, this.getClass());
        }
    }


    private String getString(Map map, String key) {
        return Tools.filterNull(map.get(key));
    }

    private String interpretCode(Map map, String key) {
        return Tools.filterNull(SysCacheTool.interpretCode(Tools.filterNull(map.get(key))));
    }

    public TableVO queryPageInfo(String setId, String pk, String personId, User user, String where, String orderby) throws BkmsException {
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        TableVO table = new TableVO();
        List head = SysCacheTool.queryInfoItemBySetId(set.getSetId());
        InfoItemBO[] header = (InfoItemBO[]) head.toArray(new InfoItemBO[head.size()]);
        if (where != null && where.trim().length() > 0) {
            where = where + " and ID = '" + personId + "' ";
        } else {
            where = " ID = '" + personId + "' ";
        }
        String rows[][] = activePageService.queryRecordByCond(set, header, where, orderby);
        table.setInfoSet(set);
        table.setHeader(header);
        table.setRows(rows);
        PmsAPI pms = new PmsAPI();
        pms.checkPostRecord(user, table);
        return table;
    }

    public Hashtable queryPersonDataSet(String personID) throws BkmsException {
        try {
            Hashtable ht = new Hashtable();
            PersonBO bo = personService.findPerson(personID);
            if (bo != null) {
                ht.put("<面姓名>", filterNull(bo.getName()));
                ht.put("<面时间>", filterNull(Tools.getSysDate("yyyy年MM月d日")));
                ht.put("<姓名>", filterNull(bo.getName()));
                ht.put("<曾用名>", filterNull(bo.getEvername()));
//                ht.put("<照片>",filterNull());
                ht.put("<性别>", filterNull(SysCacheTool.interpretCode(bo.getSex())));
                ht.put("<出生日期>", filterNull(bo.getBirth()).replace('-', '.'));
                ht.put("<婚姻状况>", filterNull(SysCacheTool.interpretCode(bo.getMarriage())));
                ht.put("<健康状况>", filterNull(bo.getHealth()));
                ht.put("<籍贯>", filterNull(SysCacheTool.interpretCode(bo.getHomeTown())));
                ht.put("<出生地>", filterNull(SysCacheTool.interpretCode(bo.getHomePlace())));
                ht.put("<民族>", filterNull(SysCacheTool.interpretCode(bo.getFolk())));
//                ht.put("<出生日期>", filterNull(bo.getBirth()));
                ht.put("<身份证号码>", filterNull(bo.getIdCard()));
                ht.put("<家庭住址>", filterNull(bo.getFamilyAdd()));
                ht.put("<参加工作时间>", filterNull(bo.getWorkTime()).replace('-', '.'));
                ht.put("<爱好>", filterNull(bo.getHobby()));
                ht.put("<有何专长>", filterNull(bo.getPersonType()));
                ht.put("<政治面貌>", filterNull(SysCacheTool.interpretCode(bo.getPartyFigure())));
                ht.put("<入党团时间>", filterNull(bo.getPartyTime()).replace('-', '.'));
            }
            GenericDAO dao = personService.getPersonDAO();
            String sql = "select A246205,A246206 from A246 where id = '" + personID + "' and A246219='3109400475' order by A246200 desc";
            List list = dao.getJdbcTemplate().queryForList(sql);
            if (list != null && !list.isEmpty()) {
                Map map = (Map) list.get(0);
                ht.put("<学历全>", SysCacheTool.interpretCode(getString(map, "A246205")));
                ht.put("<学位全>", SysCacheTool.interpretCode(getString(map, "A246206")));
            }
            sql = "select A246205,A246206 from A246 where id = '" + personID + "'and A246219<>'3109400475' order by A246200 desc";
            list = dao.getJdbcTemplate().queryForList(sql);
            if (list != null && !list.isEmpty()) {
                Map map = (Map) list.get(0);
                ht.put("<学历在职>", SysCacheTool.interpretCode(getString(map, "A246205")));
                ht.put("<学位在职>", SysCacheTool.interpretCode(getString(map, "A246206")));
            }
            Map map = null;

            //	A202 职务信息子集
            sql = "select A202202 from A202 where id = '" + personID + "' order by A202202 desc";
            list = dao.getJdbcTemplate().queryForList(sql);
            if (list != null && !list.isEmpty()) {
                map = (Map) list.get(0);
                ht.put("<任职时间>", Tools.filterNull(getString(map, "A202202")).replace('-', '.'));
            }
           

            //	A246 教育背景
            sql = "select A246200,A246201,A246202,A246203,A246204,A246206,A246215,A246214,A246219" +
                    " from A246 where id = '" + personID + "' order by A246201 asc";
            list = dao.getJdbcTemplate().queryForList(sql);
            if (list != null && !list.isEmpty()) {
                int num = list.size();
                StudyVO tVO = new StudyVO();
                for (int i = 0; i < num; i++) {
                    String tStr = "";
                    map = (Map) list.get(i);
                    tVO.setStudyBeginDate(getString(map, "A246200"));
                    tVO.setStudyBeginDate(tVO.getStudyBeginDate().replaceAll("-", "."));
                    tVO.setStudyEndDate(getString(map, "A246201"));
                    tVO.setStudyEndDate(tVO.getStudyEndDate().replaceAll("-", "."));
                    tVO.setSchool(getString(map, "A246202"));
                    tVO.setAcademy(getString(map, "A246203"));
                    tVO.setSubject(interpretCode(map, "A246204"));

                    tVO.setStudyDegree(interpretCode(map, "A246206"));
//                    tVO.setGraduate(interpretCode(map, "A246215"));
                    tVO.setProperty(interpretCode(map, "A246219"));
                    tVO.setStudyMethod(interpretCode(map, "A246214"));
                    //System.out.println(">简历时间>"+tVO.getResumeBeginDate()+">>"+tVO.getResumeEndDate());
                    if (tVO.getStudyBeginDate() != null) {
                        tStr = tStr + tVO.getStudyBeginDate().replace('-', '.').substring(0, tVO.getStudyBeginDate().length() < 7 ? tVO.getStudyBeginDate().length() : 7);
                    } else {
                        tStr = tStr + " ";
                    }
                    if (tVO.getStudyEndDate() != null && !"".equalsIgnoreCase(tVO.getStudyEndDate())) {
                        tStr = tStr + "--" + tVO.getStudyEndDate().replace('-', '.');
                    } else if (i + 1 == list.size()) {//最后一个
                        tStr = tStr + "至今  ";
                    } else {
                        tStr = tStr + "-- ";
                    }
                    ht.put("<教育起止" + i + ">", tStr);
                    ht.put("<毕业" + i + ">", interpretCode(map, "A246215"));
                    ht.put("<教育类型" + i + ">", tVO.getProperty());
                    ht.put("<学习方式" + i + ">", tVO.getStudyMethod());
                    ht.put("<学校专业" + i + ">", tVO.getSchool() + "" + tVO.getAcademy() + " " + tVO.getSubject());
                }
            }

            
            
            
            //		A019 工作经历
            sql = new StringBuffer("select A019010,A019005,A019015,A019020,A019205,A019201 from A019 where id = '").append(personID).append("' order by A019005 ASC").toString();
            list = dao.getJdbcTemplate().queryForList(sql);
            if (list != null && !list.isEmpty()) {
                ResumeInfoVO tVO = new ResumeInfoVO();
                for (int i = 0; i < list.size(); i++) {
                    String tStr = "";
                    map = (Map) list.get(i);
                    tVO.setResumeBeginDate(getString(map, "A019005"));
                    tVO.setResumeBeginDate(tVO.getResumeBeginDate().replaceAll("-", "."));
                    tVO.setResumeEndDate(getString(map, "A019010"));
                    tVO.setResumeEndDate(tVO.getResumeEndDate().replaceAll("-", "."));
                    tVO.setResumeUnit(getString(map, "A019015"));
                    tVO.setResumeDept(getString(map, "A019020"));
                    tVO.setResumeDuty(getString(map, "A019205"));
                    if (tVO.getResumeBeginDate() != null) {
                        tStr = tStr + tVO.getResumeBeginDate().replace('-', '.');
                    } else {
                        tStr = tStr + " ";
                    }
                    if (tVO.getResumeEndDate() != null && !"".equalsIgnoreCase(tVO.getResumeEndDate())) {
                        tStr = tStr + " -- " + tVO.getResumeEndDate().replace('-', '.');
                    } else if (i + 1 == list.size()) {
                        tStr = tStr + " --  至今";
                        ht.put("<面部门>", tVO.getResumeDept());
                        ht.put("<面单位>", tVO.getResumeUnit());
                        ht.put("<面职务>", tVO.getResumeDuty());
                        ht.put("<现任部门及职务>", tVO.getResumeUnit() + tVO.getResumeDept() + tVO.getResumeDuty());
                    } else {
                        tStr = tStr + "--  ";
                    }
                    ht.put("<工作起止" + i + ">", tStr);
                    ht.put("<工作单位" + i + ">", tVO.getResumeUnit() + tVO.getResumeDept() + " " + tVO.getResumeDuty());
                    ht.put("<工作备注" + i + ">", getString(map, "A019201"));
                }
            }
            return ht;
        } catch (Exception e) {
            throw new BkmsException("读取人员信息出错！", e, this.getClass());
        }
    }

    public Hashtable getAreaMap() {
        Hashtable map = new Hashtable();
        map.put("<面部门>", "3,21");
        map.put("<面职务>", "3,25");
        map.put("<面单位>", "3,17");
        map.put("<面姓名>", "3,29");
        map.put("<面时间>", "5,47");

        map.put("<姓名>", "2,108");
        map.put("<性别>", "4,108");
        map.put("<民族>", "6,108");

        map.put("<曾用名>", "2,109");
        map.put("<出生日期>", "5,109");

        map.put("<籍贯>", "2,110");
        map.put("<出生地>", "5,110");

        map.put("<身份证号码>", "3,111");

        map.put("<家庭住址>", "3,112");

        map.put("<户籍所在地>", "3,113");
        map.put("<户口所在派出所>", "7,113");

        map.put("<学历全>", "2,114");
        map.put("<学位全>", "7,114");

        map.put("<学历在职>", "2,115");
        map.put("<学位在职>", "7,115");

        map.put("<参加工作时间>", "2,116");
        map.put("<入行时间>", "7,116");

        map.put("<政治面貌>", "2,117");
        map.put("<入党团时间>", "7,117");

        map.put("<现任部门及职务>", "2,118");
        map.put("<工资档次>", "7,118");

        map.put("<任职时间>", "2,119");

        map.put("<专业技术职务>", "2,120");
        map.put("<取得时间>", "7,120");

        map.put("<婚姻状况>", "2,121");
        map.put("<健康状况>", "7,121");

        map.put("<爱好>", "2,122");
        map.put("<有何专长>", "7,122");
        map.put("<何种外语0>", "0,124");
        map.put("<练程度0>", "2,124");
        map.put("<熟练程度0>", "5,124");
        map.put("<何种外语1>", "0,125");
        map.put("<练程度1>", "2,125");
        map.put("<熟练程度1>", "5,125");
        map.put("<何种外语2>", "0,126");
        map.put("<练程度2>", "2,126");
        map.put("<熟练程度2>", "5,126");
        map.put("<何种外语3>", "0,127");
        map.put("<练程度3>", "2,127");
        map.put("<熟练程度3>", "5,127");

        map.put("<教育起止0>", "1,129");
        map.put("<学校专业0>", "3,129");
        map.put("<毕业0>", "7,129");
        map.put("<教育类型0>", "8,129");
        map.put("<学习方式0>", "9,129");

        map.put("<教育起止1>", "1,130");
        map.put("<学校专业1>", "3,130");
        map.put("<毕业1>", "7,130");
        map.put("<教育类型1>", "8,130");
        map.put("<学习方式1>", "9,130");

        map.put("<教育起止2>", "1,131");
        map.put("<学校专业2>", "3,131");
        map.put("<毕业2>", "7,131");
        map.put("<教育类型2>", "8,131");
        map.put("<学习方式2>", "9,131");

        map.put("<教育起止3>", "1,132");
        map.put("<学校专业3>", "3,132");
        map.put("<毕业3>", "7,132");
        map.put("<教育类型3>", "8,132");
        map.put("<学习方式3>", "9,132");

        map.put("<教育起止4>", "1,133");
        map.put("<学校专业4>", "3,133");
        map.put("<毕业4>", "7,133");
        map.put("<教育类型4>", "8,133");
        map.put("<学习方式4>", "9,133");

        map.put("<教育起止5>", "1,134");
        map.put("<学校专业5>", "3,134");
        map.put("<毕业5>", "7,134");
        map.put("<教育类型5>", "8,134");
        map.put("<学习方式5>", "9,134");

        map.put("<工作起止0>", "1,136");
        map.put("<工作单位0>", "3,136");
        map.put("<工作备注0>", "9,136");

        map.put("<工作起止1>", "1,137");
        map.put("<工作单位1>", "3,137");
        map.put("<工作备注1>", "9,137");

        map.put("<工作起止2>", "1,138");
        map.put("<工作单位2>", "3,138");
        map.put("<工作备注2>", "9,138");

        map.put("<工作起止3>", "1,139");
        map.put("<工作单位3>", "3,139");
        map.put("<工作备注3>", "9,139");

        map.put("<工作起止4>", "1,140");
        map.put("<工作单位4>", "3,140");
        map.put("<工作备注4>", "9,140");

        map.put("<工作起止5>", "1,141");
        map.put("<工作单位5>", "3,141");
        map.put("<工作备注5>", "9,141");

        map.put("<工作起止6>", "1,142");
        map.put("<工作单位6>", "3,142");
        map.put("<工作备注6>", "9,142");

        map.put("<工作起止7>", "1,143");
        map.put("<工作单位7>", "3,143");
        map.put("<工作备注7>", "9,143");

        map.put("<工作起止8>", "1,144");
        map.put("<工作单位8>", "3,144");
        map.put("<工作备注8>", "9,144");

        map.put("<工作起止9>", "1,145");
        map.put("<工作单位9>", "3,145");
        map.put("<工作备注9>", "9,145");

        map.put("<工作起止10>", "1,146");
        map.put("<工作单位10>", "3,146");
        map.put("<工作备注10>", "9,146");

        map.put("<工作起止11>", "1,147");
        map.put("<工作单位11>", "3,147");
        map.put("<工作备注11>", "9,147");

        map.put("<工作起止12>", "1,148");
        map.put("<工作单位12>", "3,148");
        map.put("<工作备注12>", "9,148");

        map.put("<工作起止13>", "1,149");
        map.put("<工作单位13>", "3,149");
        map.put("<工作备注13>", "9,149");

        map.put("<工作起止14>", "1,150");
        map.put("<工作单位14>", "3,150");
        map.put("<工作备注14>", "9,150");

        map.put("<工作起止15>", "1,151");
        map.put("<工作单位15>", "3,151");
        map.put("<工作备注15>", "9,151");

        map.put("<培训起止0>", "1,153");
        map.put("<培训详情0>", "3,153");
        map.put("<培训天数0>", "9,153");

        map.put("<培训起止1>", "1,154");
        map.put("<培训详情1>", "3,154");
        map.put("<培训天数1>", "9,154");

        map.put("<培训起止2>", "1,155");
        map.put("<培训详情2>", "3,155");
        map.put("<培训天数2>", "9,155");

        map.put("<培训起止3>", "1,156");
        map.put("<培训详情3>", "3,156");
        map.put("<培训天数3>", "9,156");

        map.put("<培训起止4>", "1,157");
        map.put("<培训详情4>", "3,157");
        map.put("<培训天数4>", "9,157");

        map.put("<培训起止5>", "1,158");
        map.put("<培训详情5>", "3,158");
        map.put("<培训天数5>", "9,158");

        map.put("<培训起止6>", "1,159");
        map.put("<培训详情6>", "3,159");
        map.put("<培训天数6>", "9,159");

        map.put("<培训起止7>", "1,160");
        map.put("<培训详情7>", "3,160");
        map.put("<培训天数7>", "9,160");

        map.put("<证书名称0>", "0,162");
        map.put("<证书时间0>", "5,162");
        map.put("<发证单位0>", "7,162");

        map.put("<证书名称1>", "0,163");
        map.put("<证书时间1>", "5,163");
        map.put("<发证单位1>", "7,163");

        map.put("<证书名称2>", "0,164");
        map.put("<证书时间2>", "5,164");
        map.put("<发证单位2>", "7,164");

        map.put("<证书名称3>", "0,165");
        map.put("<证书时间3>", "5,165");
        map.put("<发证单位3>", "7,165");

        map.put("<证书名称4>", "0,166");
        map.put("<证书时间4>", "5,166");
        map.put("<发证单位4>", "7,166");

        map.put("<证书名称5>", "0,167");
        map.put("<证书时间5>", "5,167");
        map.put("<发证单位5>", "7,167");

        map.put("<项目名称0>", "1,171");
        map.put("<项目时间0>", "5,171");
        map.put("<担任角色0>", "6,171");
        map.put("<合作者0>", "7,171");
        map.put("<出版机构0>", "8,171");

        map.put("<项目名称1>", "1,172");
        map.put("<项目时间1>", "5,172");
        map.put("<担任角色1>", "6,172");
        map.put("<合作者1>", "7,172");
        map.put("<出版机构1>", "8,172");

        map.put("<项目名称2>", "1,173");
        map.put("<项目时间2>", "5,173");
        map.put("<担任角色2>", "6,173");
        map.put("<合作者2>", "7,173");
        map.put("<出版机构2>", "8,173");

        map.put("<出国起止0>", "1,175");
        map.put("<去何国家0>", "3,175");
        map.put("<出国情况0>", "5,175");
        map.put("<出国天数0>", "9,175");

        map.put("<出国起止1>", "1,176");
        map.put("<去何国家1>", "3,176");
        map.put("<出国情况1>", "5,176");
        map.put("<出国天数1>", "9,176");

        map.put("<出国起止2>", "1,177");
        map.put("<去何国家2>", "3,177");
        map.put("<出国情况2>", "5,177");
        map.put("<出国天数2>", "9,177");

        map.put("<出国起止3>", "1,178");
        map.put("<去何国家3>", "3,178");
        map.put("<出国情况3>", "5,178");
        map.put("<出国天数3>", "9,178");

        map.put("<出国起止4>", "1,179");
        map.put("<去何国家4>", "3,179");
        map.put("<出国情况4>", "5,179");
        map.put("<出国天数4>", "9,179");

        map.put("<奖励时间0>", "1,183");
        map.put("<奖励原因0>", "3,183");
        map.put("<奖励单位0>", "7,183");

        map.put("<奖励时间1>", "1,184");
        map.put("<奖励原因1>", "3,184");
        map.put("<奖励单位1>", "7,184");

        map.put("<奖励时间2>", "1,185");
        map.put("<奖励原因2>", "3,185");
        map.put("<奖励单位2>", "7,185");

        map.put("<奖励时间3>", "1,186");
        map.put("<奖励原因3>", "3,186");
        map.put("<奖励单位3>", "7,186");

        map.put("<奖励时间4>", "1,187");
        map.put("<奖励原因4>", "3,187");
        map.put("<奖励单位4>", "7,187");

        map.put("<奖励时间5>", "1,188");
        map.put("<奖励原因5>", "3,188");
        map.put("<奖励单位5>", "7,188");

        map.put("<奖励时间6>", "1,189");
        map.put("<奖励原因6>", "3,189");
        map.put("<奖励单位6>", "7,189");

        map.put("<处分时间0>", "1,191");
        map.put("<处分原因0>", "3,191");
        map.put("<处分单位0>", "6,191");
        map.put("<撤销时间0>", "8,191");

        map.put("<处分时间1>", "1,192");
        map.put("<处分原因1>", "3,192");
        map.put("<处分单位1>", "6,192");
        map.put("<撤销时间1>", "8,192");

        map.put("<处分时间2>", "1,193");
        map.put("<处分原因2>", "3,193");
        map.put("<处分单位2>", "6,193");
        map.put("<撤销时间2>", "8,193");

        map.put("<处分时间3>", "1,194");
        map.put("<处分原因3>", "3,194");
        map.put("<处分单位3>", "6,194");
        map.put("<撤销时间3>", "8,194");

        map.put("<成员关系0>", "1,196");
        map.put("<成员姓名0>", "2,196");
        map.put("<成员性别0>", "3,196");
        map.put("<成员生日0>", "4,196");
        map.put("<成员民族0>", "5,196");
        map.put("<成员面貌0>", "6,196");
        map.put("<成员学历0>", "7,196");
        map.put("<成员工作0>", "8,196");

        map.put("<成员关系1>", "1,197");
        map.put("<成员姓名1>", "2,197");
        map.put("<成员性别1>", "3,197");
        map.put("<成员生日1>", "4,197");
        map.put("<成员民族1>", "5,197");
        map.put("<成员面貌1>", "6,197");
        map.put("<成员学历1>", "7,197");
        map.put("<成员工作1>", "8,197");

        map.put("<成员关系2>", "1,198");
        map.put("<成员姓名2>", "2,198");
        map.put("<成员性别2>", "3,198");
        map.put("<成员生日2>", "4,198");
        map.put("<成员民族2>", "5,198");
        map.put("<成员面貌2>", "6,198");
        map.put("<成员学历2>", "7,198");
        map.put("<成员工作2>", "8,198");

        map.put("<成员关系3>", "1,199");
        map.put("<成员姓名3>", "2,199");
        map.put("<成员性别3>", "3,199");
        map.put("<成员生日3>", "4,199");
        map.put("<成员民族3>", "5,199");
        map.put("<成员面貌3>", "6,199");
        map.put("<成员学历3>", "7,199");
        map.put("<成员工作3>", "8,199");

        map.put("<成员关系4>", "1,200");
        map.put("<成员姓名4>", "2,200");
        map.put("<成员性别4>", "3,200");
        map.put("<成员生日4>", "4,200");
        map.put("<成员民族4>", "5,200");
        map.put("<成员面貌4>", "6,200");
        map.put("<成员学历4>", "7,200");
        map.put("<成员工作4>", "8,200");

        map.put("<成员关系5>", "1,201");
        map.put("<成员姓名5>", "2,201");
        map.put("<成员性别5>", "3,201");
        map.put("<成员生日5>", "4,201");
        map.put("<成员民族5>", "5,201");
        map.put("<成员面貌5>", "6,201");
        map.put("<成员学历5>", "7,201");
        map.put("<成员工作5>", "8,201");

        map.put("<成员关系6>", "1,202");
        map.put("<成员姓名6>", "2,202");
        map.put("<成员性别6>", "3,202");
        map.put("<成员生日6>", "4,202");
        map.put("<成员民族6>", "5,202");
        map.put("<成员面貌6>", "6,202");
        map.put("<成员学历6>", "7,202");
        map.put("<成员工作6>", "8,202");

        map.put("<成员关系7>", "1,203");
        map.put("<成员姓名7>", "2,203");
        map.put("<成员性别7>", "3,203");
        map.put("<成员生日7>", "4,203");
        map.put("<成员民族7>", "5,203");
        map.put("<成员面貌7>", "6,203");
        map.put("<成员学历7>", "7,203");
        map.put("<成员工作7>", "8,203");

        map.put("<成员关系8>", "1,204");
        map.put("<成员姓名8>", "2,204");
        map.put("<成员性别8>", "3,204");
        map.put("<成员生日8>", "4,204");
        map.put("<成员民族8>", "5,204");
        map.put("<成员面貌8>", "6,204");
        map.put("<成员学历8>", "7,204");
        map.put("<成员工作8>", "8,204");

        map.put("<成员关系9>", "1,205");
        map.put("<成员姓名9>", "2,205");
        map.put("<成员性别9>", "3,205");
        map.put("<成员生日9>", "4,205");
        map.put("<成员民族9>", "5,205");
        map.put("<成员面貌9>", "6,205");
        map.put("<成员学历9>", "7,205");
        map.put("<成员工作9>", "8,205");

        map.put("<成员关系10>", "1,206");
        map.put("<成员姓名10>", "2,206");
        map.put("<成员性别10>", "3,206");
        map.put("<成员生日10>", "4,206");
        map.put("<成员民族10>", "5,206");
        map.put("<成员面貌10>", "6,206");
        map.put("<成员学历10>", "7,206");
        map.put("<成员工作10>", "8,206");

        map.put("<成员关系11>", "1,207");
        map.put("<成员姓名11>", "2,207");
        map.put("<成员性别11>", "3,207");
        map.put("<成员生日11>", "4,207");
        map.put("<成员民族11>", "5,207");
        map.put("<成员面貌11>", "6,207");
        map.put("<成员学历11>", "7,207");
        map.put("<成员工作11>", "8,207");

        map.put("<社会起止0>", "1,209");
        map.put("<社会机构0>", "3,209");
        map.put("<社会职务0>", "8,209");

        map.put("<社会起止1>", "1,210");
        map.put("<社会机构1>", "3,210");
        map.put("<社会职务1>", "8,210");

        map.put("<社会起止2>", "1,211");
        map.put("<社会机构2>", "3,211");
        map.put("<社会职务2>", "8,211");


        map.put("<会议起止0>", "0,215");
        map.put("<会议名称0>", "2,215");
        map.put("<与会身份0>", "8,215");

        map.put("<会议起止1>", "0,216");
        map.put("<会议名称1>", "2,216");
        map.put("<与会身份1>", "8,216");

        map.put("<会议起止2>", "0,217");
        map.put("<会议名称2>", "2,217");
        map.put("<与会身份2>", "8,217");

        map.put("<考年0>", "2,180");
        map.put("<考年结果0>", "2,181");
        map.put("<考年1>", "4,180");
        map.put("<考年结果1>", "4,181");
        map.put("<考年2>", "6,180");
        map.put("<考年结果2>", "6,181");
        map.put("<考年3>", "8,180");
        map.put("<考年结果3>", "8,181");

        return map;
    }

    private static String filterNull(String s) {
        if (s == null)
            return "";
        else
            return s;

    }
}
