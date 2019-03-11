package com.becoda.bkms.csu.classInfo.ucc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.csu.classInfo.pojo.KpClass;
import com.becoda.bkms.csu.examinfo.pojo.KpQuestion;


/**
 */
public interface KpClassSpaceUCC {
    //添加
    public void saveKpClass(KpClass kpClass) throws RollbackableException;
    // 删除
	public String deleteKpClass(KpClass kpClass) throws RollbackableException;
	//修改
    public KpClass updateKpClass(KpClass kpClass,String id) throws RollbackableException;
    
    public KpClass getKpClass(String id);
    
    public Map queryClassInfoByType(Integer page,Integer rows,KpClass kpClass) throws RollbackableException;
    
    public int deletKpClassByIds(String[] ids) throws RollbackableException;
    
    public int disbandKpClassByIds(String[] ids) throws RollbackableException;
}
