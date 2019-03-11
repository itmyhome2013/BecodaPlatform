package com.becoda.bkms.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 本类用于机构,党组织,代码的翻译等与代码相关的处理.
 * User: kang
 * Date: 2015-6-5
 * Time: 9:26:19
 */

public class CodeUtil {

    /**
     * 传入代码返回代码名称.
     *
     * @param dict_num   代码类型 OU|PA|PE|PO|null null表示代码项
     * @param codeItemId id
     * @return 代码名称
     */
    public static String interpretCode(String dict_num, String codeItemId) {

        if (Tools.filterNull(codeItemId).indexOf(",") == -1) {
            return interpret(dict_num, codeItemId);
        } else {
            String[] codes = codeItemId.split(",");
            String tmp = "";
            String name = "";
            if (codes != null && codes.length > 0) {
                for (int i = 0; i < codes.length; i++) {
                    tmp += interpret(dict_num, codes[i]) + ",";
                }
                name = tmp.substring(0, tmp.length() - 1);
            }
            return name;
        }

    }

    private static String interpret(String dict_num, String code) {
        String name = "";
        if ("CODE".equals(dict_num) || "".equals(dict_num) || dict_num == null)
            name = SysCacheTool.interpretCode(code);
        else if ("OU".equals(dict_num))
            name = SysCacheTool.interpretOrg(code);
        else if ("PER".equals(dict_num))
            name = SysCacheTool.interpretPerson(code);
        else if ("PO".equals(dict_num))
            name = SysCacheTool.interpretPost(code);
        return name;
    }

    /**
     * 批量翻译,根据传入的指定字段翻译数组中的每一个对象.并将翻译完成的数组返回
     *
     * @param objArray 待翻译数组 内部应该包含bo
     * @param fields   待翻译字段列表,使用","分隔. 如"field1,field2,field3". 若只翻译一个字段不加逗号
     * @param dict_num 标记代码的类型是机构或代码或组织关系.逗号分隔,如"OU,OU,PO,PE,CODE,WAGE"
     * @return 翻译完成的数组
     */
    public static Object[] codeInterpret(Object[] objArray, String fields, String dict_num) throws BkmsException {
        if (fields == null || "".equals(fields) || objArray == null)
            return objArray;
        if (dict_num != null)
            dict_num = dict_num.toUpperCase();

        String[] fieldArray = fields.split(",");
        String[] dictArray = dict_num.split(",");
        if (dictArray.length != fieldArray.length)
            throw new BkmsException("字段数量与字典号长度不匹配", null);
        for (int i = 0; i < objArray.length; i++) {
            Object obj = objArray[i];
            try {
                Object newobj = obj.getClass().newInstance();
                Tools.copyProperties(newobj, obj);
                obj = newobj;
            } catch (Exception e) {
            }
            for (int j = 0; j < fieldArray.length; j++) {
                fieldArray[j] = fieldArray[j].substring(0, 1).toUpperCase() + fieldArray[j].substring(1);
                try {
                    String dict = dictArray[j];
                    Method getter = obj.getClass().getMethod("get" + fieldArray[j], null);
                    Method setter = obj.getClass().getMethod("set" + fieldArray[j], new Class[]{String.class});
                    String ret = (String) getter.invoke(obj, null);
                    if ("CODE".equalsIgnoreCase(dict))
                        dict = "";
                    ret = interpretCode(dict, ret);

                    setter.invoke(obj, new Object[]{ret});
                } catch (Exception e) {
                    new BkmsException("代码翻译出错,指定的翻译字段不存在", e, null);
                }
            }
            objArray[i] = obj;
        }

        return objArray;
    }

    /**
     * 批量翻译,根据传入的指定字段翻译list中的每一个对象.并将翻译完成的list返回
     *
     * @param list     待翻译list 内部应该包含bo
     * @param fields   待翻译字段列表,使用","分隔. 如"field1,field2,field3". 若只翻译一个字段不加逗号
     * @param dict_num 标记代码的类型是机构或代码或组织关系.标记代码的类型是机构或代码或组织关系.逗号分隔,如"OU,OU,PO,PE,CODE,WAGE"
     * @return 翻译完成的list
     */
    public static List codeInterpret(List list, String fields, String dict_num) throws BkmsException {
        if (fields == null || "".equals(fields) || list == null)
            return list;

        String[] fieldArray = fields.split(",");
        String[] dictArray = dict_num.split(",");
        if (dictArray.length != fieldArray.length)
            throw new BkmsException("字段数量与字典号长度不匹配");
        int count = list.size();
        int length = fieldArray.length;
        for (int i = 0; i < count; i++) {
            Object obj = list.get(i);
            try {
                Object newobj = obj.getClass().newInstance();
                Tools.copyProperties(newobj, obj);
                obj = newobj;
            } catch (Exception e) {
            }
            for (int j = 0; j < length; j++) {
                fieldArray[j] = fieldArray[j].substring(0, 1).toUpperCase() + fieldArray[j].substring(1);
                try {
                    String dict = dictArray[j];
                    Method getter = obj.getClass().getMethod("get" + fieldArray[j], null);
                    Method setter = obj.getClass().getMethod("set" + fieldArray[j], new Class[]{String.class});
                    String ret = (String) getter.invoke(obj, null);
                    if ("CODE".equalsIgnoreCase(dict))
                        dict = "";
                    ret = interpretCode(dict, ret);
                    setter.invoke(obj, new Object[]{ret});
                } catch (Exception e) {
                    new BkmsException("代码翻译出错,指定的翻译字段不存在");
                }
            }
            list.set(i, obj);
        }
        return list;
    }


}
