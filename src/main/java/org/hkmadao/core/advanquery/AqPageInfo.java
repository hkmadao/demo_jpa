package org.hkmadao.core.advanquery;

import java.util.List;

/**
 * 高级查询分页信息
 */
public class AqPageInfo<T>{

    private AqPageInfoInput pageInfoInput;

    /**
     * 列表数据
     */
    private List<T> dataList;

    public AqPageInfoInput getPageInfoInput() {
        return pageInfoInput;
    }

    public void setPageInfoInput(AqPageInfoInput pageInfoInput) {
        this.pageInfoInput = pageInfoInput;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
