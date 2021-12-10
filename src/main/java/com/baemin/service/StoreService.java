package com.baemin.service;

import java.util.List;

import com.baemin.dto.FoodOption;
import com.baemin.dto.Review;
import com.baemin.dto.Store;
import com.baemin.dto.StoreDetail;

public interface StoreService {
	List<Store> storeList(int category, int address);
	
	List<Store> storeList(int category, int address, String sort, int page);

	StoreDetail storeDetail(long id, long userId);
	
	// 해당 메뉴의 옵션 가져오기
	List<FoodOption> foodOption(int foodId);
	
	void reviewWrite(Review review);
	
	void reviewModify(Review review);
	
	// 찜
	void likes(long storeId, String likes, long userId);
}

