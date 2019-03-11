package com.becoda.bkms.qry.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.dao.QueryStaticDAO;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryStaticBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticResultItemVO;
import com.becoda.bkms.qry.pojo.vo.StaticResultVO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

import java.util.List;
import java.util.Map;

public class StaticService {
    private ActivePageService activePageService;
    private QueryStaticDAO staticdao;
    private ConditionItemService conditionitemservice;

    /**
     * 根据staticId查询一个staticBO
     *
     * @param staticId
     * @return com.becoda.bkms.qry.pojo.bo.QueryStaticBO
     * @roseuid 44711FC10224
     */
    public QueryStaticBO findStatic(String staticId) throws BkmsException {
        return (QueryStaticBO) staticdao.findBoById(QueryStaticBO.class, staticId);
    }

    /**
     * 根据qryId查询一个或多个static
     *
     * @param qryId
     * @return com.becoda.bkms.qry.pojo.bo.QueryStaticBO[]
     * @roseuid 44711FE402C4
     */
    public QueryStaticBO[] queryStatic(String qryId) throws BkmsException {
        return staticdao.queryStatic(qryId);
    }

    /**
     * 根据 qryId 查询一个staticVO[],vo中需要包含conditionBO[]
     *
     * @param qryId
     * @return com.becoda.bkms.qry.pojo.vo.StaticVO
     * @roseuid 44711FF50229
     */
    public StaticVO[] queryStaticVO(String qryId) throws RollbackableException {
        if (qryId == null || "".equals(qryId))
            return null;

        try {
            QueryStaticBO[] staticbos = this.queryStatic(qryId);
            if (staticbos == null || staticbos.length <= 0)
                return null;
            StaticVO[] svo = new StaticVO[staticbos.length];
            for (int i = 0; i < svo.length; i++) {
                svo[i] = new StaticVO();
                svo[i].setStatics(staticbos[i]);
                svo[i].setCondi(conditionitemservice.queryCondition(staticbos[i].getStaticId()));
            }
            return svo;
            /******** add by wanglijun 2015-7-26 ***********/
        } catch (RollbackableException ex) {
            throw ex;
            /************ end add ******************/
        } catch (Exception e) {
            throw new RollbackableException("", e, getClass());
        }
    }

    public String[] createStatic(StaticVO[] vos, String qryId) throws BkmsException {
        if (qryId == null || vos == null || vos.length <= 0)
            throw new RollbackableException("无法保存static", null, getClass());
        try {
            this.deleteStatic(qryId);
            String[] ids = new String[vos.length];
            for (int i = 0; i < vos.length; i++) {
                StaticVO vo = vos[i];
                if (Tools.filterNull(vo.getStatics().getName()).length() > 200)
                    throw new RollbackableException("分项名称过长，无法保存", getClass());
                String staticId = SequenceGenerator.getKeyId("qry_static");
                vo.getStatics().setStaticId(staticId);
                vo.getStatics().setQryId(qryId);
                staticdao.createBo(vo.getStatics());
                //保存条件
                conditionitemservice.createConditions(vo.getCondi(), staticId);
                ids[i] = staticId;
            }
            return ids;
        } catch (Exception e) {
            throw new RollbackableException("保存条件出错", e, getClass());
        }
    }

    public void deleteStatic(String qryId) throws BkmsException {
        QueryStaticBO[] bos = this.queryStatic(qryId);
        if (bos != null) {
            staticdao.deleteStatic(qryId);
            for (int i = 0; i < bos.length; i++)
                conditionitemservice.deleteCondition(bos[i].getStaticId());
        }
    }

    private String getStaticName(StaticVO vo) {
        if (vo.getStatics().getName() == null || "".equals(vo.getStatics().getName())) {
            String group = Tools.filterNull(vo.getStatics().getGroup());
            group = group.replaceAll("与", "并且");
            group = group.replaceAll("\\)", " ）");
            group = group.replaceAll("\\(", "（");

            if (vo.getCondi() != null) {
                for (int i = 0; i < vo.getCondi().length; i++) {
                    group = group.replaceAll(vo.getCondi()[i].getGroupId(), getConditionName(vo.getCondi()[i]));
                }
            }
            vo.getStatics().setName(group);
        }
        return vo.getStatics().getName();
    }

    private String getConditionName(QueryConditionBO bo) {
        if (bo == null)
            return "";
        StringBuffer sb = new StringBuffer();

        InfoItemBO info = SysCacheTool.findInfoItem(null, bo.getItemId());
        bo.getOperator();
        sb.append(info.getItemName()).append(" ").append(getOperator(bo.getOperator())).append(" ").append(bo.getText());
        return sb.toString();
    }

    private String getOperator(String operator) {
        if ("equal".equals(operator))
            return "等于";
        else if ("notequal".equals(operator))
            return "不等于";
        else if ("morethan".equals(operator))
            return "大于";
        else if ("lessthan".equals(operator))
            return "小于";
        else if ("moreequal".equals(operator))
            return "大于等于";
        else if ("lessequal".equals(operator))
            return "小于等于";
        else if ("in".equals(operator))
            return "在列表中";
        else if ("notin".equals(operator))
            return "不在列表中";
        else if ("like".equals(operator))
            return "匹配";
        else if ("notlike".equals(operator))
            return "不匹配";
        else if ("exist".equals(operator))
            return "存在";
        else if ("notexist".equals(operator))
            return "不存在";
        else if ("isnull".equals(operator))
            return "为空";
        else if ("notisnull".equals(operator))
            return "不为空";
        else if ("allvalue".equals(operator))
            return "取全值";
        else if("equalsysdate".equals(operator))
            return "等于系统日期";
        else if("morethansysdate".equals(operator))
            return "大于系统日期+(天)";
        else if("lessthansysdate".equals(operator))
            return "小于系统日期+(天)";
        else if("likesysdateMD".equals(operator))
            return "匹配系统月日+(天)";
        else if("likesysdateM".equals(operator))
            return "匹配系统月+(月)";
        else if("likesysdateY".equals(operator))
            return "匹配系统年";
        else
            return "";
    }


    public StaticResultVO[] executeStatic(User user, QueryVO vo) throws BkmsException {
        StaticResultVO[] rt = new StaticResultVO[vo.getStatics().length];
        try {
            QueryBO bo = new QueryBO();
            Tools.copyProperties(bo, vo);
            QueryUtil qu = new QueryUtil();
            String[] sql = qu.buildStaticSql(user, vo.getStatics(), bo);
            if (sql != null) {
                for (int i = 0; i < rt.length; i++) {
                    rt[i] = new StaticResultVO();
                    String allValueField = null;
                    if (sql[i].indexOf("group by") != -1) {
                        sql[i] = sql[i].trim();
                        allValueField = sql[i].substring(sql[i].length() - 7, sql[i].length());
                    }

                    List list = activePageService.queryForList(sql[i]);
                    if (list.isEmpty())
                        return null;
                    StaticResultItemVO[] resultItemVO;
                    Tools.copyProperties(rt[i], bo);
                    rt[i].setName(getStaticName(vo.getStatics()[i]));
                    rt[i].setItemId(bo.getStaticItemId());

                    if (list.isEmpty()) {
                        resultItemVO = new StaticResultItemVO[1];
                        StaticResultItemVO ri = new StaticResultItemVO();
                        ri.setAllValue("没有数据");     //只有取全值的时候list才会为空
                        ri.setAllValueField(allValueField);
                        resultItemVO[0] = ri;
                        rt[i].setItems(resultItemVO);
                    } else {
                        resultItemVO = new StaticResultItemVO[list.size()];
                        int count = list.size();
                        for (int n = 0; n < count; n++) {
                            StaticResultItemVO ri = new StaticResultItemVO();
                            Map map = (Map) list.get(n);
                            ri.setAllValue((String) map.get("allvalue"));
                            ri.setAllValueField(allValueField);

                            Object o = map.get("avg");
                            if (o != null)
                                ri.setAvg(o.toString());
                            o = map.get("max");
                            if (o != null)
                                ri.setMax(o.toString());
                            o = map.get("min");
                            if (o != null)
                                ri.setMin(o.toString());
                            o = map.get("count");
                            if (o != null)
                                ri.setCount(Integer.parseInt(o.toString()));
                            resultItemVO[n] = ri;
                        }
                        rt[i].setItems(resultItemVO);
                    }
                }
            }
            return rt;
        } catch (Exception e) {
            throw new BkmsException("统计出错", e, this.getClass());
        }
    }

    public QueryStaticDAO getStaticdao() {
        return staticdao;
    }

    public void setStaticdao(QueryStaticDAO staticdao) {
        this.staticdao = staticdao;
    }

    public ConditionItemService getConditionitemservice() {
        return conditionitemservice;
    }

    public void setConditionitemservice(ConditionItemService conditionitemservice) {
        this.conditionitemservice = conditionitemservice;
    }

    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }
}
