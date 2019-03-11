package com.becoda.bkms.parkingms.special.service;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.dao.SpecialDAO;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialFile;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;

public class SpecialService {

	private SpecialDAO specDao;
	private GenericDAO genericDAO;
	
	public SpecialDAO getSpecDao() {
		return specDao;
	}

	public void setSpecDao(SpecialDAO specDao) {
		this.specDao = specDao;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	public void editSpecial(ParmsSpecialInfo po)throws RollbackableException{
		if(po.getSpecialId()==null||"".equals(po.getSpecialId())){
			String uuid = SequenceGenerator.getUUID();
			po.setSpecialId(uuid);
			genericDAO.createBo(po);
			List <String> list = po.getFilePars();
			if(null!=list&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					String par [] = list.get(i).split(",");
					ParmsSpecialFile file = new ParmsSpecialFile();
					file.setFileId(SequenceGenerator.getUUID());
					file.setSpecialId(uuid);
					file.setCreateTime(po.getCreateTime());
					file.setFilePath(par[1]);
					file.setFileName(par[0]);
					file.setFilePathUUID(po.getFilePathUUID());
					file.setType("0");
					genericDAO.createBo(file);
				}
			}
			/*if(po.getState().equals("1")){
				ISpecialSocreUCC socreUCC;
				try {
					socreUCC = (ISpecialSocreUCC)BkmsContext.getBean("specialsorceUCC");
					socreUCC.editSorce(po);
				} catch (BkmsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}*/
		}else{
			genericDAO.updateBo(po.getSpecialId(), po);
			if(po.getState().equals("1")||po.getState().equals("0")){
				List <String> list = po.getFilePars();
				if(null!=list&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						String par [] = list.get(i).split(",");
						ParmsSpecialFile file = new ParmsSpecialFile();
						file.setFileId(SequenceGenerator.getUUID());
						file.setSpecialId(po.getSpecialId());
						file.setCreateTime(po.getCreateTime());
						file.setFilePath(par[1]);
						file.setFileName(par[0]);
						file.setFilePathUUID(po.getFilePathUUID());
						file.setType("0");
						genericDAO.createBo(file);
					}
				}
			}else if(po.getState().equals("5")){
				List <String> list = po.getFilePars();
				if(null!=list&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						String par [] = list.get(i).split(",");
						ParmsSpecialFile file = new ParmsSpecialFile();
						file.setFileId(SequenceGenerator.getUUID());
						file.setSpecialId(po.getSpecialId());
						file.setCreateTime(po.getCreateTime());
						file.setFilePath(par[1]);
						file.setFileName(par[0]);
						file.setFilePathUUID(po.getFilePathUUID());
						file.setType("1");
						genericDAO.createBo(file);
					}
				}
			}
		}
	}
	
	public List querySpecailList(PageVO vo,ParmsSpecialInfoVo sepVo)throws RollbackableException {
		return specDao.querySpecailList(vo, sepVo);
	}
	
	public ParmsSpecialInfo getSpecialInfo(String specialId)throws RollbackableException{
		return (ParmsSpecialInfo)genericDAO.findBo(ParmsSpecialInfo.class, specialId);
	}
	
	public Map queryReceiveOrg(String pid)throws RollbackableException {
		String sql = " select t.id,t.b001005 from B001 t where B001002 = '"+pid+"' ";
		return genericDAO.queryMap(sql);
	}
	
	public void saveSpecialFile(ParmsSpecialFile po)throws RollbackableException{
		genericDAO.createBo(po);
	}
	
	public List querySpecailFileList(String specialId)throws RollbackableException{
		String hql = "  from ParmsSpecialFile t where specialId = '"+specialId+"' ";
		return genericDAO.queryHqlList(hql);
	}
	
	public ParmsSpecialFile getSpecialFile(String fileId)throws RollbackableException{
		return (ParmsSpecialFile)genericDAO.findBo(ParmsSpecialFile.class, fileId);
	}
	
	public void deleteSpecialFile(ParmsSpecialFile po)throws RollbackableException{
		genericDAO.deleteBo(po);
	}
	
	public void deleteSpecial(ParmsSpecialInfo po)throws RollbackableException{
		genericDAO.deleteBo(po);
	}
}
