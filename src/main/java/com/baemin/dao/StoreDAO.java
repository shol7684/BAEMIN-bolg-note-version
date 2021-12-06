package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.dto.Store;

public interface StoreDAO {

	List<Store> storeList(Map<String, Object> map);

}
