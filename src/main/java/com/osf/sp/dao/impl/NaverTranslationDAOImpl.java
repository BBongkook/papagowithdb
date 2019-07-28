package com.osf.sp.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class NaverTranslationDAOImpl {

	@Resource
	private SqlSession ss;
	public List<Map<String,Object>> selectTranslationHisList(){
		return ss.selectList("com.osf.sp.mapper.NaverTranslationMapper.selectList");
	}
	
	public Map<String,Object> selectTranslationHisOne(Map<String,Object> param){
		return ss.selectOne("com.osf.sp.mapper.NaverTranslationMapper.selectOne", param);
	}
	public Integer insertTranslationHisOne(Map<String,Object> param){
		return ss.insert("com.osf.sp.mapper.NaverTranslationMapper.insertOne", param);
	}
	public Integer updateTranslationHisOne(Map<String,Object> param){
		return ss.update("com.osf.sp.mapper.NaverTranslationMapper.updateCnt", param);
	}
	public List<Map<String,Object>> selectCount(){
		return ss.selectList("com.osf.sp.mapper.NaverTranslationMapper.selectCount");
	}
	
}
