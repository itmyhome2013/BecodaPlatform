package com.becoda.bkms.util;

import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.becoda.bkms.common.exception.BkmsException;

/**
 * Desc: 工具类 User: kangdw Date: 2015-5-23 Time: 20:44:55
 */
public class Tools {
    public static String getInSql(Collection idSet) {
        if (idSet != null && idSet.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("'");
            Iterator it = idSet.iterator();
            while (it.hasNext()) {
                String id = (String) it.next();
                sb.append(id);
                sb.append("','");
            }
            sb.delete(sb.length() - 2, sb.length());
            return sb.toString();
        }
        return "";
    }
    
    // 取得本机地址,判断定时程序是否可由本机服务执行
	// 除loadSysCache之外所有的定时方法都需要根据此方法返回值判断是否应该执行
//	public static boolean confirmTimerHost() {
//		boolean flag = false;
//		try {
//			String timeHostName = StaticVariable.get("TIMER_HOST_NAME");
//			InetAddress address = InetAddress.getLocalHost();
//			if (address != null && timeHostName != null) {
//				System.out.println(address.getHostName());
//				flag = address.getHostName().equals(timeHostName);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	public static String methodInvoke(String className, String methodName,
			Class[] argtypes, Object[] datas) {
		try {
			Class loadedClass = Class.forName(className);
			Method method = loadedClass.getMethod(methodName, argtypes);
			Object returned = method.invoke(loadedClass.newInstance(), datas);
			return returned == null ? "" : returned.toString();
		} catch (Exception e) {
			new BkmsException("方法调用失败", e, Math.class);
		}
		return "";
	}

	public static Method getInvokeMethod(String className, String methodName,
			Class[] argtypes) {
		try {
			Class loadedClass = Class.forName(className);
			return loadedClass.getMethod(methodName, argtypes);
		} catch (Exception e) {
			new BkmsException("方法调用失败", e, Math.class);
		}
		return null;
	}

	/**
	 * 调用org.apache.commons.beanutils.BeanUtils 的方法 copyProperties（）
	 *
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            初始对象
	 */
	public static void copyProperties(Object dest, Object orig) {
		try {
			if (orig == null || dest == null)
				return;
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当orig的属性为 null 的就不拷贝到 dest by sunmh
	 *
	 * @param dest
	 * @param orig
	 */
	public static void copyPropertiesWithoutNull(Object dest, Object orig) {
		try {
			// 得到两个Class 的所有成员变量
			Field[] destFields = dest.getClass().getDeclaredFields();
			Field[] origFields = orig.getClass().getDeclaredFields();

			// 设置访问权限
			AccessibleObject.setAccessible(destFields, true);
			AccessibleObject.setAccessible(origFields, true);

			Object value = null;
			String name = null;
			String returnType = null;

			for (int i = 0; i < origFields.length; i++) {
				name = origFields[i].getName();
				returnType = origFields[i].getType().getName();
				for (int j = 0; j < destFields.length; j++) {
					if (name.equals(destFields[j].getName())
							&& returnType.equals(destFields[j].getType()
									.getName())) {
						value = origFields[i].get(orig);
						if (value != null) {
							destFields[j].set(dest, value);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数组复制 dest orig长度必须一致
	 *
	 * @param cl
	 *            目标类
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            初始对象
	 */
	public static void copyArrayObject(Class cl, Object[] dest, Object[] orig) {
		try {
			int count = orig.length;
			for (int i = 0; i < count; i++) {
				if (dest[i] == null) {
					Object o = cl.newInstance();
					dest[i] = o;
				}
				BeanUtils.copyProperties(dest[i], orig[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数组复制 dest orig长度必须一致
	 *
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            初始对象
	 */
	public static void copyArrayObject(String destClassName, Object[] dest,
			Object[] orig) {
		try {
			int count = orig.length;
			for (int i = 0; i < count; i++) {

				if (dest[i] == null) {
					Class cl = Class.forName(destClassName);
					Object o = cl.newInstance();
					dest[i] = o;
				}
				BeanUtils.copyProperties(dest[i], orig[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将字符串进行MD5加密
	 *
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String md5(final String input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(input.getBytes());
		BigInteger bi = new BigInteger(digest);
		return bi.toString(16);
	}

	/**
	 * 字符串滤空
	 *
	 * @param str
	 *            需要过滤得字符串
	 * @return 过滤后的字符串
	 */
	public static String filterNull(final String str) {
		String s = "";
		if (str == null || "null".equals(str.trim())) {
			s = "";
		} else {
			s = new String(str.trim());
		}

		return s;
	}

	/**
	 * 判断字符串是否为 空
	 * @param str
	 * @return 为空  true
	 */
	public static boolean stringIsNull(String str){
		boolean b = false;
		if(filterNull(str) == null || filterNull(str).length() <= 0){
			b = true;
		}
		return b;
	}
	/**
	 * 字符串滤空
	 *
	 * @param str
	 *            需要过滤得字符串
	 * @return 过滤后的字符串
	 */
	public static String filterNull(final Object str) {
		String rs = str == null ? "" : str.toString().trim();
		return rs.equals("null") ? "" : rs;
	}

	public static String filterNullToZero(final Object stro) {
		String s = filterNull(stro);
		if ("".equals(s)) {
			s = "0";
		}
		return s;
	}

	/**
	 * 对对象进行虑空操作
	 *
	 * @param cl
	 *            对象类
	 * @param obj
	 *            需要虑空的对象,若为空对象 返回一个新的对象
	 * @return 过滤后的对象
	 */
	public static Object filterNull(Class cl, Object obj) {
		try {
			if (obj == null) {
				obj = cl.newInstance();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 对对象数组进行虑空操作
	 *
	 * @param cl
	 *            需要滤空的对象类
	 * @param obj
	 *            需要滤空的对象数组
	 * @return 若为空，返回一个长度为1的对象数组。
	 */
	public static Object[] filterNull(Class cl, Object[] obj) {
		try {
			if (obj == null) {
				obj = new Object[] { cl.newInstance() };
				return obj;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 得到按指定格式的系统当前时间
	 *
	 * @param dateFormat
	 *            日期格式
	 * @return 格式化的日期字符串
	 */
	public static String getSysDate(String dateFormat) {
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = sdf.format(date.getTime());
		return dateStr;
	}

	/**
	 * 将日期字符转换为指定格式日期字符.缺省格式为yyyy-MM-dd
	 *
	 * @param dateStr
	 *            日期
	 * @param dateFormat
	 *            日期格式
	 * @return 按指定格式返回日期
	 */
	public static String getDateByFormat(String dateStr, String dateFormat) {
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		String str = "";
		try {
			if (dateStr != null && !"".equals(dateStr)) {
				dateStr = dateStr.replaceAll("年", "-");
				dateStr = dateStr.replaceAll("月", "-");
				dateStr = dateStr.replaceAll("日", "");
				dateStr = dateStr.replaceAll("/", "-");
				java.sql.Date dt = java.sql.Date.valueOf(dateStr);
				SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				str = sdf.format(dt);
			} else {
				str = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 日期加天
	 *
	 * @param date
	 *            日期
	 * @param daynum
	 *            天数 (正数为 加 ，负数为 减)
	 * @return 日期
	 */
	public static String plusMinusDay(final String date, final long daynum) {
		if (date == null || "".equals(date))
			return "";
		java.sql.Date dt = java.sql.Date.valueOf(date);
		long dl = dt.getTime();
		dl = dl + 24 * 60 * 60 * 1000 * daynum;
		java.sql.Date dt2 = new java.sql.Date(dl);
		return dt2.toString();
	}

	/**
	 * 计算日期之间的天数
	 *
	 * @param date1
	 *            被减日期
	 * @param date2
	 *            减的日期
	 * @return 天数
	 */
	public static long betweenDays(final String date1, final String date2) {
		if (date1 == null || "".equals(date1) || date2 == null
				|| "".equals(date2))
			return 0;
		java.sql.Date dt1 = java.sql.Date.valueOf(date1);
		java.sql.Date dt2 = java.sql.Date.valueOf(date2);
		long dl = dt1.getTime() - dt2.getTime();
		long daynum = dl / (24 * 60 * 60 * 1000);
		return daynum;
	}

	/**
	 * 计算六位日期之间的月数
	 *
	 * @param date1
	 * @param date2
	 * @return 月数
	 */
	public static int betweenMonths(String date1, String date2) {
		int months = 0;
		try {
			int date1_year = (new Integer(date1.substring(0, 4))).intValue();
			int date2_year = (new Integer(date2.substring(0, 4))).intValue();
			int date1_month = (new Integer(date1.substring(5, 7))).intValue();
			int date2_month = (new Integer(date2.substring(5, 7))).intValue();

			if (date1_month > date2_month)
				months = (date1_year - date2_year) * 12
						+ ((new Integer(date1_month - date2_month)).intValue());
			else
				months = (date1_year - date2_year - 1)
						* 12
						+ ((new Integer(date1_month + 12 - date2_month))
								.intValue());
		} catch (Exception e) {

		}
		return months;

	}

	/**
	 * 计算8位日期之间的月数,不足一个月按0月算
	 *
	 * @param date1
	 * @param date2
	 * @return 月数
	 */
	public static int monthsBetweenDates(String date1, String date2) {
		int months = 0;
		try {
			if (date1.equals("") || date2.equals(""))
				return months;
			int date1_year = (new Integer(date1.substring(0, 4))).intValue();
			int date2_year = (new Integer(date2.substring(0, 4))).intValue();
			int date1_month = (new Integer(date1.substring(5, 7))).intValue();
			int date2_month = (new Integer(date2.substring(5, 7))).intValue();
			int date1_date = (new Integer(date2.substring(8, 10))).intValue();
			int date2_date = (new Integer(date2.substring(8, 10))).intValue();

			if (date1_month > date2_month)
				months = (date1_year - date2_year) * 12
						+ ((new Integer(date1_month - date2_month)).intValue());
			else
				months = (date1_year - date2_year - 1)
						* 12
						+ ((new Integer(date1_month + 12 - date2_month))
								.intValue());

			if (months >= 1 && date1_date > date2_date)
				months--;
		} catch (Exception e) {

		}
		return months;

	}

	/**
	 * 字符串日期转成Calendar
	 *
	 * @param sdate
	 * @return Calendar
	 */
	public static java.util.Calendar convertToCalendar(String sdate) {
		Calendar c = Calendar.getInstance();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = df.parse(sdate);
			c.setTime(dt);
		} catch (Exception e) {

		}
		return c;
	}

	/**
	 * 替换字符串的内容 查找内容和替换内容中尽量不出现正则表达式的修饰符如:[] . ^ * ? () 等
	 *
	 * @param str
	 *            字符串
	 * @param part
	 *            查找的内容
	 * @param replacement
	 *            替换的内容
	 * @return
	 * @deprecated
	 */
	public static String replaceAll(String str, String part, String replacement) {
		if (part == null || replacement == null || str == null)
			return str;
		int pos = 0;
		if (str.length() == part.length()) {
			if (str.equals(part))
				return replacement;
			else
				return str;
		}
		while ((pos = str.indexOf(part)) != -1) {
			String tp = str.substring(0, pos);
			tp += replacement;
			tp += str.substring(pos + part.length(), str.length());
			str = tp;
		}
		return str;
	}

	/**
	 * 由于in子句中超过1000个表达式出错，先用此方法把in子句拆分成每500一段。
	 *
	 * @param ids
	 *            in子句中要排列的字符串数组
	 * @param fieldName
	 *            in子句中字段的名称
	 * @return 拆分拼写好的sql语句
	 */
	public static String splitInSql(String[] ids, String fieldName) {
		StringBuffer sqlStr = new StringBuffer();
		if (ids == null || ids.length == 0 || fieldName == null
				|| "".equals(fieldName))
			return null;
		int len = ids.length;
		int num = 1;
		if (len > 500)
			num = (len % 500 == 0) ? len / 500 : (len / 500) + 1;
		int j = 0;
		int i = 1;
		Hashtable hash = new Hashtable();
		for (; i <= num; i++) {
			StringBuffer sb = new StringBuffer();
			String str = "";
			for (; j < i * 500 && j < len; j++) {
				if (ids[j] == null || "".equals(ids[j])
						|| hash.containsKey(ids[j]))
					continue;
				sb.append("'").append(ids[j]).append("',");
				hash.put(ids[j], "");
			}
			str = sb.toString();
			str = str.substring(0, str.length() - 1);
			sqlStr.append("or ").append(fieldName).append(" in (").append(str)
					.append(") ");
		}
		hash.clear();
		hash = null;
		if (sqlStr.length() > 0) {
			sqlStr.delete(0, 2);
			return "(" + sqlStr.toString() + ")";
		} else
			return "(" + fieldName + " in (''))";
	}

	/**
	 * string 数组按指定分隔符号 转换为字符串
	 *
	 * @param strArray
	 *            字符串数组
	 * @param delim
	 *            分隔符
	 * @return 指定格式字符串
	 */
	public static String StrArray2String(String[] strArray, String delim) {
		if (strArray == null)
			return "";
		int size = strArray.length;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(strArray[i]).append(delim);
		}
		return sb.toString();
	}

	/**
	 * string 按指定分隔符号 转换为字符串数组
	 *
	 * @param inStrList
	 *            需要进行拆分的字符串
	 * @param inStrDeli
	 *            分隔符
	 * @return 拆分后的数组
	 */
	public static String[] getStringArray(String inStrList, String inStrDeli) {
		String[] strRes = null;
		int iLength = 0;
		int i = 0;
		StringTokenizer strToken = new StringTokenizer(inStrList, inStrDeli);
		iLength = strToken.countTokens();
		strRes = new String[iLength];
		for (i = 0; i < iLength; i++) {
			strRes[i] = strToken.nextToken();
		}
		return strRes;
	}

	public static String[] getStringArray(List list) {
		String[] strRes = null;
		if (list != null && list.size() > 0) {
			strRes = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				strRes[i] = (String) list.get(i);
			}
		}
		return strRes;
	}

	/**
	 * 将数字字符串加上逗号
	 *
	 * @param old_num
	 * @return String
	 */
	public static String FormatNum(String old_num) {
		double data = 0;
		String str = "0";
		try {
			if (old_num != null && !old_num.equals("")) {
				data = Double.parseDouble(old_num);
				NumberFormat formater = NumberFormat.getInstance();
				str = formater.format(data);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 检测指标集或指标项的序号是否在国标范围内
	 *
	 * @param infoId
	 * @return true 在国标范围内 false 不在国标范围内
	 */
	public static boolean checkInGBScale(int infoId) {
		return infoId < 200;
	}

	/**
	 * 检测指标集或指标项的序号是否在系统使用指标范围内
	 *
	 * @param infoId
	 * @return true 在国标范围内 false 不在国标范围内
	 */
	public static boolean checkInProgramScale(int infoId) {
		return infoId >= 700;
	}

	/**
	 * 过滤xml字符串中的非法字符 将字符转换成LRO。 in the Unicode specification
	 *
	 * @param s
	 * @return 过滤后的串
	 */
	public static String filterXML(String s) {
		char[] ch = s.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (Character.getType(ch[i]) == 15) {
				ch[i] = '\u0020';
			}
		}
		s = new String(ch);
		return s;
	}

	/**
	 * 日期加上月份
	 *
	 * @param date
	 * @param months
	 * @return result
	 */
	public static String plusMonth(String date, int months) {
		String result = "";
		if (filterNull(date).equals(""))
			return "";
		int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		// int month = Integer.parseInt(date.substring(date.indexOf("-") + 1,
		// date.length()));
		int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date
				.indexOf("-") + 3));
		GregorianCalendar firstFlight = new GregorianCalendar(year, month, 01);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		firstFlight.add(GregorianCalendar.MONTH, months - 1);
		Date d = firstFlight.getTime();
		result = formatter.format(d);
		return result;
	}

	/**
	 * 日期加上月份
	 *
	 * @param date
	 *            8位日期
	 * @param months
	 * @return result
	 */
	public static String plusMonth2(String date, int months) {
		String result = "";
		if (filterNull(date).equals(""))
			return "";
		int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		// int month = Integer.parseInt(date.substring(date.indexOf("-") + 1,
		// date.length()));
		int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date
				.indexOf("-") + 3));
		int day = Integer.parseInt(date.substring(date.indexOf("-", 7) + 1,
				date.indexOf("-", 7) + 3));
		GregorianCalendar firstFlight = new GregorianCalendar(year, month, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		firstFlight.add(GregorianCalendar.MONTH, months - 1);
		Date d = firstFlight.getTime();
		result = formatter.format(d);
		return result;
	}

	/**
	 * 日期减去月份
	 *
	 * @param date
	 * @param months
	 * @return result
	 */
	public static String minusMonth(String date, int months) {
		String result = "";
		if (filterNull(date).equals(""))
			return "";
		int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date
				.length()));
		GregorianCalendar firstFlight = new GregorianCalendar(year, month, 01);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		firstFlight.add(GregorianCalendar.MONTH, -months - 1);
		Date d = firstFlight.getTime();
		result = formatter.format(d);
		return result;
	}

	/**
	 * 判断字符串是否为数字
	 *
	 * @param str
	 * @return true 真 false 不是数字
	 */
	public static boolean isNum(String str) {
		boolean ret = true;
		try {
			new Long(str);
		} catch (NumberFormatException e) {
			ret = false;
		} finally {
			return ret;
		}
	}

	/**
	 * 按中文表达方式 得到系统日期 如二OO九年二月十八日
	 *
	 * @return 系统日期 .
	 */
	public static String getSysChnDate() {
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		String month = String.valueOf(date.get(Calendar.MONTH) + 1);
		String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
		year = year.replaceAll("0", "○");
		year = year.replaceAll("1", "一");
		year = year.replaceAll("2", "二");
		year = year.replaceAll("3", "三");
		year = year.replaceAll("4", "四");
		year = year.replaceAll("5", "五");
		year = year.replaceAll("6", "六");
		year = year.replaceAll("7", "七");
		year = year.replaceAll("8", "八");
		year = year.replaceAll("9", "九");
		if (month.length() < 2) {
			month = month.replaceAll("1", "一");
			month = month.replaceAll("2", "二");
			month = month.replaceAll("3", "三");
			month = month.replaceAll("4", "四");
			month = month.replaceAll("5", "五");
			month = month.replaceAll("6", "六");
			month = month.replaceAll("7", "七");
			month = month.replaceAll("8", "八");
			month = month.replaceAll("9", "九");
		} else {
			month = month.replaceAll("10", "十");
			month = month.replaceAll("11", "十一");
			month = month.replaceAll("12", "十二");
		}
		if (day.length() < 2) {
			day = day.replaceAll("1", "一");
			day = day.replaceAll("2", "二");
			day = day.replaceAll("3", "三");
			day = day.replaceAll("4", "四");
			day = day.replaceAll("5", "五");
			day = day.replaceAll("6", "六");
			day = day.replaceAll("7", "七");
			day = day.replaceAll("8", "八");
			day = day.replaceAll("9", "九");
		} else {
			day = day.replaceAll("10", "十");
			day = day.replaceAll("11", "十一");
			day = day.replaceAll("12", "十二");
			day = day.replaceAll("13", "十三");
			day = day.replaceAll("14", "十四");
			day = day.replaceAll("15", "十五");
			day = day.replaceAll("16", "十六");
			day = day.replaceAll("17", "十七");
			day = day.replaceAll("18", "十八");
			day = day.replaceAll("19", "十九");
			day = day.replaceAll("20", "二十");
			day = day.replaceAll("21", "二十一");
			day = day.replaceAll("22", "二十二");
			day = day.replaceAll("23", "二十三");
			day = day.replaceAll("24", "二十四");
			day = day.replaceAll("25", "二十五");
			day = day.replaceAll("26", "二十六");
			day = day.replaceAll("27", "二十七");
			day = day.replaceAll("28", "二十八");
			day = day.replaceAll("29", "二十九");
			day = day.replaceAll("30", "三十");
			day = day.replaceAll("31", "三十一");

		}
		String sdate = year + "年" + month + "月" + day + "日";
		return sdate;
	}

	/**
	 * 根据指定的比较器进行排序
	 *
	 * @param list
	 * @param c
	 */
	public static void sort(List list, Comparator c) {
		Object[] os = list.toArray();
		Arrays.sort(os, c);
		list.clear();
		for (int i = 0; i < os.length; i++) {
			list.add(os[i]);
		}
	}

	/**
	 * 返回当前年份
	 *
	 * @return String
	 */
	public static String getSysYear() {
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		return year;
	}

	public static String str2Html(String str) {
		if ("".equals(str) || str == null) {
			return "&nbsp;";
		} else
			return str;
	}

	/**
	 * @param id
	 *            数组{1,2}
	 * @return '1','2'
	 */
	public static String getInSql(String[] id) {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		for (int i = 0; i < id.length; i++) {
			String s1 = id[i];
			sb.append(s1);
			sb.append("','");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}

	/**
	 * 年龄计算公式：年龄＝IF MONTH(~人员基本情况.出生日期~) < MONTH(TODAY()) ~人员基本情况.年龄~ :=
	 * YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~) ELSE IF MONTH(~人员基本情况.出生日期~) >
	 * MONTH(TODAY()) ~人员基本情况.年龄~ := YEAR(TODAY()) - YEAR(~人员基本情况.出生日期~) - 1
	 * ELSE IF DAY(~人员基本情况.出生日期~) <= DAY(TODAY()) ~人员基本情况.年龄~ := YEAR(TODAY()) -
	 * YEAR(~人员基本情况.出生日期~) ELSE ~人员基本情况.年龄~ := YEAR(TODAY()) -
	 * YEAR(~人员基本情况.出生日期~) - 1 ENDIF ENDIFENDIF
	 *
	 * @param birth
	 * @return String
	 */
	public static String age(final String birth) {
		String age = "";
		if (birth == null || "".equals(birth.trim())) {
			return age;
		}
		String dt = Tools.getSysDate("yyyy-MM-dd");
		String[] curday = dt.split("-");
		String[] birthday = birth.split("-");

		if (Integer.parseInt(birthday[1]) < Integer.parseInt(curday[1])) {// MM<
			age = String.valueOf(Integer.parseInt(curday[0])
					- Integer.parseInt(birthday[0]));
		} else {
			if (Integer.parseInt(birthday[1]) > Integer.parseInt(curday[1])) {// MM>
				age = String.valueOf(Integer.parseInt(curday[0])
						- Integer.parseInt(birthday[0]) - 1);
			} else {// MM==
				if (Integer.parseInt(birthday[2]) <= Integer
						.parseInt(birthday[2])) {// dd<
					age = String.valueOf(Integer.parseInt(curday[0])
							- Integer.parseInt(birthday[0]));
				} else {// dd>=
					age = String.valueOf(Integer.parseInt(curday[0])
							- Integer.parseInt(birthday[0]) - 1);
				}
			}
		}
		return age;
	}

	/**
	 * 日期加天
	 *
	 * @param date
	 *            日期
	 * @param daynum
	 *            天数
	 * @return 日期
	 */
	public static String plusDay(String date, long daynum) {
		if (date == null || "".equals(date))
			return "";
		java.sql.Date dt = java.sql.Date.valueOf(date);
		long dl = dt.getTime();
		dl = dl + 24 * 60 * 60 * 1000 * daynum;
		java.sql.Date dt2 = new java.sql.Date(dl);
		return dt2.toString();
	}

	/**
	 * 比较两个日期的大小
	 *
	 * @param dateStr1
	 * @param dateStr2
	 * @param format
	 * @return dateStr1>dateStr2,return>0; dateStr1=dateStr2,return=0; dateStr1<dateStr2,return<0;
	 * @throws ParseException
	 */
	public static int compareTo(String dateStr1, String dateStr2, String format)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		Date date1 = df.parse(dateStr1);
		Date date2 = df.parse(dateStr2);
		return date1.compareTo(date2);
	}






	//created by qianjinlei 2015-01-12
	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;

	}

	public static boolean isNull(String str) {
        return str == null;
	}

    public static int parseInt(Object o) {
        o = filterNullToZero(o);
        return Integer.parseInt(o.toString());
    }
    public static String filterXManMK(final String str) {
        String s = "";
        if (str == null || "null".equals(str.trim())) {
            s = "";
        } else {
            if (str.trim().equals("JGGL")) {
                s = "jigou";
            }
            if (str.trim().equals("RYGL")) {
                s = "renyuan";
            }
            if (str.trim().equals("BMGL")) {
                s = "banmian";
            }
            if (str.trim().equals("XMGL")) {
                s = "xiangmu";
            }
            if (str.trim().equals("YXGL")) {
                s = "yunxing";
            }
            if (str.trim().equals("XTGL")) {
                s = "xitong";
            }
        }

        return s;
    }
     public static String filterXManMKICO(final String str) {
        String s = "";
        if (str == null || "null".equals(str.trim())) {
            s = "";
        } else {
            if (str.trim().equals("JGGL")) {
                s = "ico_jigou";
            }
            if (str.trim().equals("RYGL")) {
                s = "ico_renyuan";
            }
            if (str.trim().equals("BMGL")) {
                s = "ico_banmian";
            }
            if (str.trim().equals("XMGL")) {
                s = "ico_xiangmu";
            }
            if (str.trim().equals("YXGL")) {
                s = "ico_yunxing";
            }
            if (str.trim().equals("XTGL")) {
                s = "ico_xitong";
            }
        }

        return s;
    }
    // 获取星期几
    public static String Timexq(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    public static String filterXMK(final String str) {
        String s = "";
        if (str == null || "null".equals(str.trim())) {
            s = "";
        } else {
            if (str.trim().equals("JGGL")) {
                s = "btn_jigou";
            }
            if (str.trim().equals("RYGL")) {
                s = "btn_renyuan";
            }
            if (str.trim().equals("BMGL")) {
                s = "btn_banmian";
            }
            if (str.trim().equals("XMGL")) {
                s = "btn_xiangmu";
            }
            if (str.trim().equals("YXGL")) {
                s = "btn_yunxing";
            }
            if (str.trim().equals("XTGL")) {
                s = "btn_xitong";
            }
        }

        return s;
    }
    
    public static String filterMainCss(final String str) {
        String s = "";
        if (str == null || "null".equals(str.trim())) {
            s = "";
        } else {
            if (str.trim().equals("SJGL")) {
                s = "SJGL";
            }
            if (str.trim().equals("XQGL")) {
                s = "XQGL";
            }
            if (str.trim().equals("YJZH")) {
                s = "YJZH";
            }
            if (str.trim().equals("RKGL")) {
                s = "RKGL";
            }
            if (str.trim().equals("JCSJ")) {
                s = "JCSJ";
            }
            if (str.trim().equals("ZZWW")) {
                s = "ZZWW";
            }
            if (str.trim().equals("GZJD")) {
                s = "GZJD";
            }
            if (str.trim().equals("JCZC")) {
            	s = "JCZC";
            }
            if (str.trim().equals("BMGL")) {
            	s = "BMGL";
            }
            if (str.trim().equals("XTGL")) {
            	s = "XTGL";
            }
            if (str.trim().equals("XMGL")) {
            	s = "XMGL";
            }
        }

        return s;
    }
    
    public static String filterHeaderCss(final String str) {
        String s = "";
        if (str == null || "null".equals(str.trim())) {
            s = "";
        } else {
        	if (str.trim().equals("SJGL")) {
                s = "SJGL";
            }
            if (str.trim().equals("XQGL")) {
                s = "XQGL";
            }
            if (str.trim().equals("YJZH")) {
                s = "YJZH";
            }
            if (str.trim().equals("RKGL")) {
                s = "RKGL";
            }
            if (str.trim().equals("JCSJ")) {
                s = "JCSJ";
            }
            if (str.trim().equals("ZZWW")) {
                s = "ZZWW";
            }
            if (str.trim().equals("GZJD")) {
                s = "GZJD";
            }
            if (str.trim().equals("JCZC")) {
            	s = "JCZC";
            }
            if (str.trim().equals("BMGL")) {
            	s = "BMGL";
            }
            if (str.trim().equals("XTGL")) {
            	s = "XTGL";
            }
            if (str.trim().equals("XMGL")) {
            	s = "XMGL";
            }
        }

        return s;
    }
    
    public static String getState(final String str) {
        String s = "";
        if (str == null || "null".equals(str.trim())) {
            s = "";
        } else {
            if (str.trim().equals("0")) {
                s = "未提交";
            }
            if (str.trim().equals("1")) {
                s = "待审核";
            }
            if (str.trim().equals("2")) {
                s = "待审批";
            }
            if (str.trim().equals("3")) {
                s = "待分派";
            }
            if (str.trim().equals("4")) {
                s = "待整改";
            }
            if (str.trim().equals("5")) {
                s = "已整改";
            }
            if (str.trim().equals("6")) {
                s = "待复查";
            }
            if (str.trim().equals("7")) {
                s = "待办结";
            }
            if (str.trim().equals("8")) {
                s = "已办结";
            }
            if (str.trim().equals("9")) {
                s = "驳回";
            }
        }
        return s;
    }
    
    public static String getSubStr(final String str,int num) {
    	String s = "";
    	if (str == null || "null".equals(str.trim())) {
			s = "";
		} else {
			s = new String(str.trim());
			if(num!=0){
				if(str.length()>num){
					s = s.substring(0,num)+"...";
				}
			}else{
				if(str.length()>20){
					s = s.substring(0,20)+"...";
				}
			}
			
		}
    	return s;
    }
    /**
     * 时间加减小时   格式：yyyy-MM-dd HH:mm
     * @param datestr
     * @param hourint
     * @return
     * @throws ParseException
     */
    public static String modifiedHour(String datestr,int hourint) throws ParseException{
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	Date date =sdf.parse(datestr);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.HOUR, hourint); //减填负数
    	Date nowTime=calendar.getTime();
    	return sdf.format(nowTime);
    }
    /**
     * 判断是否为本月最后一天
     * @param datestr
     * @return
     * @throws ParseException
     */
	public static boolean judgeMonthEnd(String datestr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = sdf.parse(datestr);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.setTime(date);
		if (calendar.get(Calendar.DATE) == lastDay) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 获取前月最后一天
	 * @return
	 */
	public static String getUpMonthEnd() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	//获取某年某月的最后一天
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}
	/**
	 * 时间加减分钟
	 * @param datestr
	 * @param minu
	 * @return
	 * @throws ParseException
	 */
	public static String minusMinute(String datestr,int minu) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date =sdf.parse(datestr);
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(date);
		nowTime.add(nowTime.MINUTE, minu);
		return sdf.format(nowTime.getTime());
	}
	
	public static void main(String[] args) throws ParseException {
//		try  
//        {  
//            ProcessBuilder proc = new ProcessBuilder("D:\\Chrome_Portable_Xp580\\chrome.exe", "http://60.194.185.72:10005");  
//            proc.start();  
//        }  
//        catch (Exception e)  
//        {  
//            System.out.println("Error executing progarm.");  
//        }  
		
		  //启用cmd运行IE的方式来打开网址。  
		System.out.println(compareTo("2018-4","2018-3","yyyy-MM"));
	}
}
