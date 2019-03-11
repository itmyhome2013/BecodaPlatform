package com.becoda.bkms.pcs.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import math.geom2d.line.LinearShape2D;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

 
import com.becoda.bkms.common.web.GenericAction;
/**
 * 流程模型控制器
 * 
 * @author henryyan
 */
@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class ModelAction extends GenericAction{

  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  RepositoryService repositoryService;
  private String modelId;
  private String name;
  private String description;
  private String key;
  

  /**
   * 模型列表
   */


  /**
   * 创建模型
   */
  public void create() {
    try {
    	System.out.println("name======================"+name);
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode editorNode = objectMapper.createObjectNode();
      editorNode.put("id", "canvas");
      editorNode.put("resourceId", "canvas");
      ObjectNode stencilSetNode = objectMapper.createObjectNode();
      stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
      editorNode.put("stencilset", stencilSetNode);
      Model modelData = repositoryService.newModel();

      ObjectNode modelObjectNode = objectMapper.createObjectNode();
      modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
      modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
      description = StringUtils.defaultString(description);
      modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
      modelData.setMetaInfo(modelObjectNode.toString());
      modelData.setName(name);
      modelData.setKey(StringUtils.defaultString(key));

      repositoryService.saveModel(modelData);
      repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
      
    //  this.getresponse().sendRedirect(this.getrequest().getContextPath() + "/service/editor?id=" + modelData.getId());
    } catch (Exception e) {
      logger.error("创建模型失败：", e);
    }
    //return null;
  }

  /**
   * 根据Model部署流程
   */
  public void deploy() {
    try {
    	//LinearShape2D d=null;
      Model modelData = repositoryService.getModel(modelId);
      ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
      byte[] bpmnBytes = null;
      BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
      bpmnBytes = new BpmnXMLConverter().convertToXML(model);
      String processName = modelData.getKey() + ".bpmn20.xml"; 
      InputStream is = new ByteArrayInputStream(bpmnBytes);
     
      Deployment deployment = repositoryService.createDeployment().addInputStream(processName, is)
    		                                                      .name(modelData.getName())
    		                                                      .deploy();
      
  
     // this.getresponse().sendRedirect(this.getrequest().getContextPath() + "/workflowAction_deployHome.action");
     
    } catch (Exception e) {
      logger.error("根据模型部署流程失败：modelId={}", modelId, e);
    }
    //return "null";
  }
  
  public void delete() {
	     repositoryService.deleteModel(modelId);
	     //try {
		//	this.getresponse().sendRedirect(this.getrequest().getContextPath() + "/workflowAction_deployHome.action");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	    //return null;
	  }
  public void export() {
	    try {
	      Model modelData = repositoryService.getModel(modelId);
	      BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
	      JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
	      BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
	      BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
	      byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

	      ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
	      //IOUtils.copy(in, this.getresponse().getOutputStream());
	    
	      String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
	   
	    //  this.getresponse().setHeader("Content-Disposition", "attachment; filename=" + filename);
	     // this.getresponse().flushBuffer();
	    
	    } catch (Exception e) {
	      logger.error("导出model的xml文件失败：modelId={}", modelId, e);
	    }
	    //return null;
	  }
public RepositoryService getRepositoryService() {
	return repositoryService;
}

public void setRepositoryService(RepositoryService repositoryService) {
	this.repositoryService = repositoryService;
}

public String getModelId() {
	return modelId;
}

public void setModelId(String modelId) {
	this.modelId = modelId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}



public Logger getLogger() {
	return logger;
}



public void setLogger(Logger logger) {
	this.logger = logger;
}



public String getDescription() {
	return description;
}



public void setDescription(String description) {
	this.description = description;
}



public String getKey() {
	return key;
}



public void setKey(String key) {
	this.key = key;
}

}
