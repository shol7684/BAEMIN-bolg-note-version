package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.dto.Food;
import com.baemin.dto.OrderList;
import com.baemin.dto.Store;

public interface AdminDAO {
	
	int pointUpdate(long userId, String info, int point);

	List<Store> myStore(long userId);

	void storeInfoUpdate(Store store);
	
	long addMenu(Food food);

	void addMenuOption(List<Map<String, Object>> optionList);

	void updateMenu(Map<String, Object> map);

	void deleteMenuOption(long foodId);

	void deleteMenu(long storeId, long[] deleteNumber);

	void bossComment(long storeId, String orderNum, String bossComment);

	List<Long> getMyStoreId(long userId);

	List<OrderList> order(Map<String, Object> map);
	


}
