package com.becoda.bkms.util;

import java.util.List;

import com.becoda.bkms.pms.util.MenuObj;

public class MenuUtil {
	
	public static String getMenuUrl(MenuObj menu,String moduleId){
		String menuUrl = "/contentIntroMain.action?moduleid="+moduleId;
		if(menu.getUrl()!=null&&!"".equals(menu.getUrl())&&menu.getUrl().length()>0){
			menuUrl = menu.getUrl();
	    }else{
	    	List memuChildsList = menu.getMenus();
		    if(memuChildsList!=null&&memuChildsList.size() >0){
		    	MenuObj memuChild = (MenuObj)memuChildsList.get(0);
		    	menuUrl = getMenuUrl(memuChild,moduleId);
		    }else{
		    	return menuUrl;
		    }
	    }
		return menuUrl;
	}

}
