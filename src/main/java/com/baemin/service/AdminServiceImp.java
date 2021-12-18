package com.baemin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.dao.AdminDAO;
import com.baemin.dto.Cart;
import com.baemin.dto.Food;
import com.baemin.dto.OrderCancle;
import com.baemin.dto.OrderList;
import com.baemin.dto.Sales;
import com.baemin.dto.SalesToday;
import com.baemin.dto.Store;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.util.Page;
import com.google.gson.Gson;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	
	@Override
	public List<Store> myStore(long userId) {
		return adminDAO.myStore(userId);
	}


	@Override
	public void storeInfoUpdate(Store store) {
		adminDAO.storeInfoUpdate(store);
	}


	@Transactional
	@Override
	public void addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice) {
		long foodId = adminDAO.addMenu(food);
		
		if(foodOption != null) {
			List<Map<String, Object>> optionList = new ArrayList<>();
			
			for(int i=0;i<foodOption.length;i++) {
				Map<String, Object> optionMap = new HashMap<>();
				optionMap.put("optionName", foodOption[i]);
				optionMap.put("optionPrice", foodOptionPrice[i]);
				optionMap.put("foodId", foodId);
				optionList.add(optionMap);
			}
			
			adminDAO.addMenuOption(optionList);
		}
	}
	
	
	
	@Transactional
	@Override
	public void updateMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId) {
		Map<String, Object> map = new HashMap<>();
		if(foodOption == null) {
			adminDAO.deleteMenuOption(food.getId());
		} else {
			List<Map<String, Object>> optionList = new ArrayList<>();
			
			for(int i=0;i<foodOption.length;i++) {
				Map<String, Object> optionMap = new HashMap<>();
				long oid = -1;
				if(optionId.length != 0 && optionId[i] != null) {
					oid = optionId[i];
				}
				
				optionMap.put("optionId", oid);
				optionMap.put("optionName", foodOption[i]);
				optionMap.put("optionPrice", foodOptionPrice[i]);
				
				optionList.add(optionMap);
			}
			
			map.put("optionList", optionList);
		}
		map.put("food", food);
		adminDAO.updateMenu(map);
	}


	@Override
	public void deleteMenu(long storeId, long[] deleteNumber) {
		adminDAO.deleteMenu(storeId, deleteNumber);
	}


	@Override
	public String bossComment(long storeId, String orderNum, String bossComment) {
		bossComment = bossComment.replace("\n","<br>").replaceAll(" ", "&nbsp");
		adminDAO.bossComment(storeId, orderNum, bossComment);
		return bossComment; 
	}


	@Override
	public List<Long> getMyStoreId(long userId) {
		return adminDAO.getMyStoreId(userId);
	}


	@Override
	public List<OrderList> order(long storeId, String list, int page) {
		Page p = new Page(page);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("storeId", storeId);
		map.put("list", list);
		map.put("firstList", p.getFirstList());
		map.put("lastList", p.getLastList());
		
		return adminDAO.order(map);
	}
	
	
	@Override
	public void orderAccept(String orderNum, int time,long userId) {
		adminDAO.orderAccept(orderNum, time,userId);
	}

	
	@Override
	public void orderCancle(OrderCancle orderCancle) {
		adminDAO.orderCancle(orderCancle);
	}
	
	@Override
	public void orderComplete(String orderNum, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("userId", userId);
		adminDAO.orderComplete(map);
	}
	
	
	@Override
	public List<Sales> sales(String time,String month) {
		
//		if(time.equals("year")) {
//			List<Sales> year = adminDAO.salesYear();
//			
//			return year;
//		}
		
		return adminDAO.sales(time,month);
	}


	@Override
	public Map<String, Object> salseToday(long storeId) {
		List<SalesToday> salesToday = adminDAO.salesToday(storeId);
		long total = 0;
		
		List<Cart> menuList = new ArrayList<>();
		
		for(int i=0;i<salesToday.size();i++) {
			List<Cart> cartList = FoodInfoFromJson.foodInfoFromJson(salesToday.get(i).getFoodInfo());
			
			for(int j=0;j<cartList.size();j++) {
				Cart cart = cartList.get(j);
				if(menuList.contains(cart)) {
					
					int index = menuList.indexOf(cart);
					int amount = cart.getAmount();
					int price = cart.getTotalPrice();
					
					menuList.get(index).setAmount(amount + menuList.get(index).getAmount());
					menuList.get(index).setTotalPrice(price + menuList.get(index).getTotalPrice());
					
				} else {
					menuList.add(cartList.get(j));
				}
			}
			
			total += salesToday.get(i).getTotalPrice();
		}
		
		Map<String, Object> map = new HashMap<>();

		Collections.sort(menuList, new Comparator<Cart>() {
			@Override
			public int compare(Cart o1, Cart o2) {
				String name1= o1.getFoodName();
				String name2= o2.getFoodName();

				if(name1.compareTo(name2) == 0) {
					String[] option1 = o1.getOptionName();
					String[] option2 = o2.getOptionName();
					if(option1 == null) {
						return 1;
					}
					if(option2 == null) {
						return -1;
					}
					
					return option1[0].compareTo(option2[0]);
				}
				
				return o1.getFoodName().compareTo(o2.getFoodName());
			}
		});
		
		map.put("menuList", menuList);
		map.put("total", total);
		
		return map;
	}
	
}
