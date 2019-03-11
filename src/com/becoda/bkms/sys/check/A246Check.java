package com.becoda.bkms.sys.check;

import com.becoda.bkms.util.Tools;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-5
 * Time: 20:16:11
 * To change this template use File | Settings | File Templates.
 */
public class A246Check extends AbstractCheck {
    public String doCheck(Map dataMap, String setId) {
        String info = "";
        String A246205 = Tools.filterNull((String) dataMap.get("A246205"));//学历
        String A246204 = Tools.filterNull((String) dataMap.get("A246204"));//专业
        String A246206 = Tools.filterNull((String) dataMap.get("A246206"));//学位
        String A246211 = Tools.filterNull((String) dataMap.get("A246211"));//是否最高学位
        try {
            if (!"".equals(A246206)) {
                if ("".equals(A246211)) {
                    info += "如果学位字段不为空，则是否最高学位也为必填项;";
//                    TableVO table = api.queryPageInfo("A067", id, null);
//                    if (table != null) {
//                        RecordVO[] r = table.getRowData();
//                        if (r != null && r.length > 0) {
//                            String A067701 = Tools.filterNull(((CellVO) r[0].cellArray2Hash().get("A067701")).getValue());
//                            if (!"".equals(A067701)) {
//                                if (Tools.betweenDays(A220201, A067701) < 0) {
//                                    info += "参训时间必须大于等于认定为后备人才时间!\n";
//                                }
//                            }
//
//                        }
//                    }
                }
            }
//           int i=  Tools.compareCodeItem(A246205,"036549"); todo
//            if(i>=0){
//                if("".equals(A246204))
//                info+="如果是中专以上学历，则专业也为必填项!";
//            }

        } catch (Exception e) {

        }
        return info;
    }
}
