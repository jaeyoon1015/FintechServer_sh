package com.example.demo;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO user VALUES(#{id}, #{pw})")
	int create(User user);
	
	@Select("SELECT * FROM user WHERE id = #{id}")
	String check(User user);
	
	@Select("SELECT * FROM user")
	List<User> select();
	
}
