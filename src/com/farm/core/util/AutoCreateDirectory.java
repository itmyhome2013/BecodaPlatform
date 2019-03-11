package com.farm.core.util;

import java.io.File;

/**
 * 自动生成目录结构
 * @author huxuxu
 *
 */
public class AutoCreateDirectory {

	public static void generate(String f) {

		//需创建的各个包名
		String fs[] = { "/bo", "/dao", "/service", "/ucc", "/ucc/impl", "/web" };
		for (int i = 0; i < fs.length; i++) {
			File file = new File(f + fs[i]);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	public static void main(String[] args) {

		String path = System.getProperty("user.dir").toString().replace("\\", "/") + "/src/com/becoda/bkms/bus/";
		//模块名称
		String filePath = path + "fileStation";
		generate(filePath);
		System.out.println("创建成功！");
	}
}
