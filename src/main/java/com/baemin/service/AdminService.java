package com.baemin.service;

import java.util.List;
import java.util.Map;

import com.baemin.dto.Food;
import com.baemin.dto.OrderCancle;
import com.baemin.dto.OrderList;
import com.baemin.dto.Sales;
import com.baemin.dto.Store;

public interface AdminService {

	List<Store> myStore(long userId);

	void storeInfoUpdate(Store store);

	void addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice);

	void updateMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId);

	void deleteMenu(long storeId, long[] deleteNumber);

	String bossComment(long storeId, String orderNum, String bossComment);

	List<Long> getMyStoreId(long userId);

	List<OrderList> order(long storeId, String list, int page);

	void orderAccept(String orderNum, int time, long userId);

	void orderCancle(OrderCancle orderCancle);

	void orderComplete(String orderNum, long userId);

	List<Sales> sales(String time, String month);

	Map<String, Object> salseToday(long storeId);

}
