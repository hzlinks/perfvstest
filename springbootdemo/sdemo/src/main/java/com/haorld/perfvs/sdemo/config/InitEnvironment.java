package com.haorld.perfvs.sdemo.config;

import com.haorld.perfvs.sdemo.dao.ItemDao;
import com.haorld.perfvs.sdemo.dao.StockDao;
import com.haorld.perfvs.sdemo.model.StockDo;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class InitEnvironment implements InitializingBean{

    @Resource
    private ItemDao itemDao;

    @Resource
    private StockDao stockDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("init 1w data config");
        //insert item_info
        String[] itemColName = {"item_name", "market_price"};
        Map<Integer, String> itemValueMap = new HashMap<>();
        itemValueMap.put(1, "String");
        itemValueMap.put(2, "int");
        itemDao.batchInsertData(100000, "item_info", itemColName, itemValueMap);
        //insert item_stock
        stockDao.batchStock(100000);
    }


}
