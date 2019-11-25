package com.example.demo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PassMapper {

	@Insert("INSERT INTO pass(from_id, to_id, flag, limit_price, card_num) VALUES( #{from_id},  #{to_id}, 1,  #{limit_price}, #{card_num})")
	int createPass(Pass pass);
	
	@Select("SELECT * FROM pass")
	List<Pass> select();
	
	@Select("SELECT * FROM pass WHERE to_id = #{id}")
	List<Pass> selectUserPass(User user);
	
	@Select("SELECT flag FROM pass WHERE from_id = #{from_id}")
	boolean checkFlag(Pass pass);
	
	@Select("SELECT * from pass WHERE pass_id = #{pass_id}") // 해당 pass id의 이용권이 존재하는지
	Pass checkPassId(String pass_id);
	
	@Delete("DELETE FROM pass WHERE pass_id = #{pass_id}") // 해당 pass id 제거
	boolean deletePass(String pass_id);
}
