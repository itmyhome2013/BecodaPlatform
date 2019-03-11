package com.becoda.bkms.csu.examinfo.ucc;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.csu.examinfo.pojo.KpQuestion;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:44:28
 * To change this template use File | Settings | File Templates.
 */
public interface KpQuestionUCC {
    /**
     * 根据信息类型获得信息
     *
     * @param type
     * @return
     * @throws RollbackableException
     */
    public Map queryInfoByType(Integer page,Integer rows,KpQuestion kpQuestion) throws RollbackableException;
}
