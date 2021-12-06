package com.baemin.service;

import java.util.List;

import com.baemin.dto.Store;

public interface StoreService {
	List<Store> storeList(int category, int address);
}
