package com.example.demo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CardMapper {

	@Insert("INSERT INTO card VALUES(#{card_number}, #{card_info})")
	int create(Card card);
	
	@Select("SELECT * FROM card")
	List<Card> selectEveryCard();
}
