package com.becoda.bkms.common.web;


import com.becoda.bkms.util.Tools;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-7-26
 * Time: 16:02:59
 * To change this template use File | Settings | File Templates.
 */
public class PageTag extends TagSupport {

    private String textStyle;
    private String buttonStyle;
    private String submitMethod = "document.forms[0].submit(); ";
    private String buttonName = "跳转";
    private String type ="numberPage"; // 分页类型 numberPage 数字模式     tradition 传统模式
    private String inputSize ="1"; //输入框大小
    
    public String getInputSize() {
		return inputSize;
	}

	public void setInputSize(String inputSize) {
		this.inputSize = inputSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(Tools.stringIsNull(type)){
			//默认类型   
			type = "tradition";
		}
		this.type = type;
	}

	public void setTextStyle(String style) {
        this.textStyle = style;
    }

    public void setButtonStyle(String style) {
        this.buttonStyle = style;
    }
    
    
    public void setSubmitMethod(String method) {
        if (method != null && !"".equals(method))
            this.submitMethod = method.replaceAll("\"", "'");
    }

    public void setButtonName(String name) {
        if (name != null && !"".equals(name))
            this.buttonName = name;
    }


    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        PageVO vo = (PageVO) request.getAttribute("pageVO");
        if (vo == null)
            return SKIP_BODY;
        try {
            JspWriter out = pageContext.getOut();
            //输出排序内容
            if(vo.getTotalPage()==0){
            	vo.setCurrentPage(1);
            }
            if(type!=null&&type.equals("numberPage")){
	            StringBuffer outStr = new StringBuffer("");
	            outStr.append(" <link rel=\"stylesheet\" type=\"text/css\" href=\""+pageContext.getSession().getServletContext().getRealPath("")+"/css/platformInternal/page/pageCss.css\"> ");
	            outStr.append(" <input type='hidden' name='PAGE_TAG_orderField' value='" + Tools.filterNull(vo.getOrderField()) + "'> ");
	            outStr.append(" <input type='hidden' name='PAGE_TAG_isASC' value='" + Tools.filterNull(vo.getASC()) + "'> ");
	            outStr.append(" <input type='hidden' name='PAGE_TAG_totalPage' value='" + String.valueOf(vo.getTotalPage() + "'> "));
	            outStr.append(" <input type='hidden' name='PAGE_TAG_totalRecord' value='" + String.valueOf(vo.getTotalRecord() + "'> "));
	            outStr.append(" <div class=\"sabrosus\"> ");
	            outStr.append("每页显示<input type='text' size='"+inputSize+"' name='PAGE_TAG_pageSize' value='" + String.valueOf(vo.getPageSize()) + "' onkeyup=\"value=value.replace(/[^\\d]/g,'')\" onkeypress=\"if (event.keyCode == 13) {  toCheck(this);  }\" >行&nbsp;");
	            outStr.append(" <a href=\"javascript:void(0);\" title='首页' id=\"goFirst\" ");
	            if(vo.getCurrentPage()==1){
	            	outStr.append(" class=\"disabled\"   ") ;
	            }else{
	            	outStr.append(" onclick=\"toPage(1);\" ");
	            }
	            outStr.append(">&lt;&lt; </a> ");
	            outStr.append(" <a href=\"javascript:void(0);\" title='上一页' id=\"goPrev\" ");
	            if(vo.getCurrentPage()==1){
	            	outStr.append(" class=\"disabled\"   ") ;
	            }else{
	            	outStr.append(" onclick=\"toPage(parseInt(document.getElementById('PAGE_TAG_currentPage').value)-1);\" ");
	            }
	            outStr.append(">&lt; </a> ");
	            
	            if(vo.getCurrentPage()+15<=vo.getTotalPage()+6){
	            	for(int i=vo.getCurrentPage()>3?vo.getCurrentPage()-3:1;i<(vo.getCurrentPage()>3?vo.getCurrentPage()+7:11);i++){
		            	outStr.append(" <a name='npag' href=\"javascript:void(0);\" onclick=\"toPage("+i+")\" ");
		            	if(i==vo.getCurrentPage()){
		            		outStr.append(" class=\"current\" ");
		            	}
		            	outStr.append(" >"+i+"</a>  ");
		            }
	            	outStr.append("...");
	            	for(int i=vo.getTotalPage()-1;i<=vo.getTotalPage();i++){
		            	outStr.append(" <a name='npag' href=\"javascript:void(0);\" onclick=\"toPage("+i+")\" ");
		            	outStr.append(" >"+i+"</a>  ");
		            }
	            }else{
		            for(int i=vo.getTotalPage()-11>1?vo.getTotalPage()-11:1;i<=vo.getTotalPage();i++){
		            	outStr.append(" <a name='npag' href=\"javascript:void(0);\" onclick=\"toPage("+i+")\" ");
		            	if(i==vo.getCurrentPage()){
		            		outStr.append(" class=\"current\" ");
		            	}
		            	outStr.append(" >"+i+"</a>  ");
		            }
	            }
	            outStr.append(" <input type=\"text\" size=\""+inputSize+"\" id='PAGE_TAG_currentPage' onkeyup=\"value=value.replace(/[^\\d]/g,'')\" name='PAGE_TAG_currentPage' value='"+vo.getCurrentPage()+"' onkeypress=\"if (event.keyCode == 13) { toPage(document.getElementById('PAGE_TAG_currentPage').value); }\" > ");
	            
	            outStr.append(" <a href=\"javascript:void(0);\" id='goNext' title='下一页' ");
	            if(vo.getCurrentPage()==vo.getTotalPage()){
	            	outStr.append(" class=\"disabled\"  ") ;
	            }else if(vo.getTotalRecord() == 0){
	            	outStr.append(" class=\"disabled\"  ") ;
	            }else{
	            	outStr.append(" onclick=\"toPage(parseInt(document.getElementById('PAGE_TAG_currentPage').value)+1);\" ");
	            }
	            outStr.append("> &gt; </a> ");
	            outStr.append(" <a href=\"javascript:void(0);\" id='end' title='尾页' ");
	            if(vo.getCurrentPage()==vo.getTotalPage()){
	            	outStr.append(" class=\"disabled\"  ") ;
	            }else if(vo.getTotalRecord() == 0){
	            	outStr.append(" class=\"disabled\"  ") ;
	            }else{
	            	outStr.append(" onclick=\"toPage("+vo.getTotalPage()+");\" ");
	            }
	            outStr.append("> &gt;&gt; </a> ");
	            outStr.append(" </div> ");
	            outStr.append("<script language='javascript'>\n"+
	            "function toPage(num){\n" +
	            "\t var frm = document.forms[0]; if(parseInt(frm.PAGE_TAG_totalRecord.value)==0){ frm.PAGE_TAG_currentPage.value=1;}  \n" +
	            "\t if(parseInt(frm.PAGE_TAG_totalPage.value)==0){ sumNum = 1;frm.PAGE_TAG_currentPage.value=1;  } if(parseInt(frm.PAGE_TAG_totalPage.value)!=0&&parseInt(frm.PAGE_TAG_currentPage.value)>parseInt(parseInt(frm.PAGE_TAG_totalPage.value))){ alert(\"总页数为\"+frm.PAGE_TAG_totalPage.value+\"不能输入超过最大页数！\"); frm.PAGE_TAG_currentPage.value = frm.PAGE_TAG_totalPage.value; } \n" +
	            "\t\t frm.PAGE_TAG_currentPage.value = num;\n" +
	            "\t\t" + submitMethod + " ;\n" +
	            " }\n"+
	            "function formToPage(){\n" +
	            "\t var frm = document.forms[0]; if(parseInt(frm.PAGE_TAG_totalRecord.value)==0){ frm.PAGE_TAG_currentPage.value=1; }  \n" +
	            "\t if(parseInt(frm.PAGE_TAG_totalPage.value)==0){ sumNum = 1;frm.PAGE_TAG_currentPage.value=1;  } if(parseInt(frm.PAGE_TAG_totalPage.value)!=0&&parseInt(frm.PAGE_TAG_currentPage.value)>parseInt(parseInt(frm.PAGE_TAG_totalPage.value))){ alert(\"总页数为\"+frm.PAGE_TAG_totalPage.value+\"不能输入超过最大页数！\"); frm.PAGE_TAG_currentPage.value = frm.PAGE_TAG_totalPage.value; } \n" +
	            "\t\t" + submitMethod + " ;\n" +
	            " }\n"+
	            "function toCheck(obj){\n" +
	            "\t if(parseInt(obj.value)>500){ alert(\"请输入500以下数字！\"); obj.value=20; return false; }else{ toPage(1);  } \n" +
	            " }\n"+
	            " </script> "
	            );
	            out.print(outStr.toString());
            }else{
            
            out.println("<input type='hidden' name='PAGE_TAG_orderField' value='" + Tools.filterNull(vo.getOrderField()) + "'>");
            out.println("<input type='hidden' name='PAGE_TAG_isASC' value='" + Tools.filterNull(vo.getASC()) + "'>");
            //输出分页内容
            out.println("每页显示<input type='text' size='"+inputSize+"' title='" + String.valueOf(vo.getPageSize()) + "'  style='width:40;align:center;' name='PAGE_TAG_pageSize' value='" + String.valueOf(vo.getPageSize()) + "' class='" + textStyle + "' onkeyup=\"value=value.replace(/[^\\d]/g,'')\" onkeypress='return event.keyCode>=48&&event.keyCode<=57' onblur='if(parseInt(this.value)>500){alert(\"请输入500以下数字！\");this.value=20;return false;} else document.forms[0].PAGE_TAG_currentPage.value=1; '>行&nbsp;");
            out.println("<input type='hidden' name='PAGE_TAG_totalPage' value='" + String.valueOf(vo.getTotalPage() + "'>"));
            out.println("<input type='hidden' name='PAGE_TAG_totalRecord' value='" + String.valueOf(vo.getTotalRecord() + "'>"));
            out.println("共 " + vo.getTotalPage() + " 页 ");
            out.println("共 " + vo.getTotalRecord() + " 行 ");
            out.println("&nbsp;&nbsp;&nbsp;");
            out.println("<a href='javascript:goFirst();' " );
            if(vo.getCurrentPage()==1){
            	out.println(" disabled = 'true'  ") ;
            }
            out.println(" >首页</a>&nbsp;&nbsp;");
            out.println("<a href='javascript:goPrev();'");
            if(vo.getCurrentPage()==1){
            	out.println(" disabled = 'true'  ") ;
            }
            out.println(" >上一页</a>&nbsp;&nbsp; ");
            out.println("<a href='javascript:goNext();'");
            if(vo.getCurrentPage()==vo.getTotalPage()){
            	out.println(" disabled = 'true'  ") ;
            }else if(vo.getTotalRecord() == 0){
            	out.println(" disabled = 'true'  ") ;
            }
            out.println(" >下一页</a>&nbsp;&nbsp; ");
            out.println("<a href='javascript:goEnd();'");
            if(vo.getCurrentPage()==vo.getTotalPage()){
            	out.println(" disabled = 'true'  ") ;
            }else if(vo.getTotalRecord() == 0){
            	out.println(" disabled = 'true'  ") ;
            }
            out.println(" >尾页</a>&nbsp;&nbsp; ");
            out.println("第<input type='text' style='width:40;' size='"+inputSize+"' id='PAGE_TAG_currentPage' name='PAGE_TAG_currentPage' title='" + vo.getCurrentPage() + "' value='" + vo.getCurrentPage() + "' class='" + textStyle + "'  onkeyup=\"value=value.replace(/[^\\d]/g,'')\"   onkeypress='return event.keyCode>=48&&event.keyCode<=57'>页&nbsp;");
            out.println("<input type=\"button\" value=\"" + buttonName + "\" onclick=\"formToPage();\" class=\"" + buttonStyle + "\">");
            out.println("<script language='javascript'>\n" +
                    "function goPrev(){\n" +
                    "\t var frm = document.forms[0];\n" +
                    "\t if(parseInt(frm.PAGE_TAG_currentPage.value) > 1){\n" +
                    "\t\t frm.PAGE_TAG_currentPage.value = parseInt(frm.PAGE_TAG_currentPage.value) - 1;\n" +
                    "\t\t" + submitMethod + " ;\n" +
                    "\t }else{alert(\"已经是第一页！\")} \n" +
                    " }\n" +
                    "function goFirst(){\n" +
                    "\t var frm = document.forms[0];\n" +
                    "\t\t frm.PAGE_TAG_currentPage.value = 1;\n" +
                    "\t\t" + submitMethod + " ;\n" +
                    " }\n" +
                    "function goEnd(){\n" +
                    "\t var frm = document.forms[0];\n" +
                    "\t\t frm.PAGE_TAG_currentPage.value = "+vo.getTotalPage()+";\n" +
                    "\t\t" + submitMethod + " ;\n" +
                    " }\n" +
                    "function formToPage(){\n" +
                    "\t var frm = document.forms[0]; if(parseInt(frm.PAGE_TAG_totalRecord.value)==0){ frm.PAGE_TAG_currentPage.value=1; }  \n" +
                    "\t\t if(parseInt(frm.PAGE_TAG_totalPage.value)==0){ sumNum = 1;frm.PAGE_TAG_currentPage.value=1;  }  if(parseInt(frm.PAGE_TAG_totalPage.value)!=0&&parseInt(frm.PAGE_TAG_currentPage.value)>parseInt(parseInt(frm.PAGE_TAG_totalPage.value))){ alert(\"总页数为\"+frm.PAGE_TAG_totalPage.value+\"不能输入超过最大页数！\"); frm.PAGE_TAG_currentPage.value = frm.PAGE_TAG_totalPage.value; }  \n" +
                    "\t\t" + submitMethod + " ;\n" +
                    " }\n" +
                    "function goNext(){\n" +
                    "\t var frm = document.forms[0];\n" +
                    "\t if(parseInt(frm.PAGE_TAG_totalPage.value) > parseInt(frm.PAGE_TAG_currentPage.value)){\n" +
                    "\t\t frm.PAGE_TAG_currentPage.value = parseInt(frm.PAGE_TAG_currentPage.value) + 1;\n" +
                    "\t\t" + submitMethod + " ;\n" +
                    "\t }else{alert(\"已经是最后一页！\")}\n" +
                    " }\n" +
                    "function PAGE_TAG_ORDER_SUBMIT(orderField,asc){\n" +
                    "\tdocument.all('PAGE_TAG_orderField').value = orderField;\n" +
                    "\tdocument.all('PAGE_TAG_isASC').value = asc;\n" +
                    "\t\t" + submitMethod + " ;\n" +
                    "}" +
                    "</script>");
            }
        } catch (Exception e) {

        }
        return EVAL_PAGE;
    }
}
