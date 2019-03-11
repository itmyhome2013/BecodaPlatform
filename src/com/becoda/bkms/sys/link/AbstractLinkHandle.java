package com.becoda.bkms.sys.link;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.sys.service.ILinkHandle;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-20
 * Time: 14:59:54
 */
public abstract class AbstractLinkHandle implements ILinkHandle {
    protected ActivePageService activePageService;

    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    public void whenAdd(TableVO table) throws BkmsException {
        if (!isValidTable(table, getSetId())) {
            return;
        }
        add(table);
    }

    protected abstract String getSetId();

    public void whenUpdate(TableVO table, String[] oldValue) throws BkmsException {
        if (!isValidTable(table, getSetId())) {
            return;
        }
        update(table, oldValue);
    }

    protected abstract void add(TableVO table) throws BkmsException;

    protected abstract void update(TableVO table, String[] oldValue) throws BkmsException;

    protected boolean isValidTable(TableVO tableVO, String setId) {
        if (tableVO == null) {
            return false;
        }
        String lsetId = tableVO.getInfoSet().getSetId();
        String[][] rows = tableVO.getRows();
        return !(lsetId == null || !setId.equals(lsetId) || rows == null || rows.length < 1);
    }

    protected String getRowItem(TableVO tableVO, int row, String itemId) {
        if (tableVO.getRows().length < row + 1) {
            return null;
        }
        int colItem = tableVO.getCol(itemId);
        if (colItem < 0) {
            return null;
        }
        return tableVO.getRows()[row][colItem];
    }

    protected void setRowItem(TableVO tableVO, int row, String itemId, String value) {
        if (tableVO.getRows().length < row + 1) {
            return;
        }
        int colItem = tableVO.getCol(itemId);
        if (colItem < 0) {
            return;
        }
        tableVO.getRows()[row][colItem] = value;
    }
}
