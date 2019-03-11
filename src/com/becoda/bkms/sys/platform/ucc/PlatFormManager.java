package com.becoda.bkms.sys.platform.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

public class PlatFormManager {
	private static final PlatFormManager manager = new PlatFormManager();

	public static PlatFormManager getInstance() {
		return manager;
	}

	public static IPlatFormUCC getPlatFormBean() throws BkmsException {
		return (IPlatFormUCC) BkmsContext.getBean("platFormUCC");
	}

}
