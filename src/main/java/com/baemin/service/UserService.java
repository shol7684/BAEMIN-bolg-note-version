package com.baemin.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.baemin.dto.Join;
import com.baemin.dto.Point;
import com.baemin.dto.Review;
import com.baemin.login.LoginService;

public interface UserService {
	void join(Join join);

	int overlapCheck(String value, String valueType);

	List<Point> myPoint(long id);

	ResponseEntity<Map<String, Object>> pointRegist(String giftCardNum, LoginService user);

	List<Review> myReviewList(long id);

	void deleteReview(long id, String orderNum);

	void modifyInfo(long userId, String valueType, String value);
}
