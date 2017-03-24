package com.hph.doubanworm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hph.doubanworm.bean.DoubanUser;

@Service
public class DoubanService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<DoubanUser> getList() {
		String sql = "select id,name,content,create_time,href,basic_info from douban_table";
		return (List<DoubanUser>) jdbcTemplate.query(sql, new RowMapper<DoubanUser>() {

			@Override
			public DoubanUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				DoubanUser stu = new DoubanUser();
				stu.setId(rs.getInt("ID"));
				stu.setName(rs.getString("NAME"));
				stu.setContent(rs.getString("CONTENT"));
				stu.setCreate_time(rs.getTimestamp("CREATE_TIME"));
				stu.setHref(rs.getString("HREF"));
				stu.setBasic_info(rs.getString("BASIC_INFO"));
				return stu;
			}

		});
	}
	public List<Map<String,Object>> getAddressCount() {
		String sql = "select count(t.address) as cou,t.address from douban_table t where t.address is not null GROUP BY t.address";
		return (List<Map<String,Object>>) jdbcTemplate.query(sql, new RowMapper<Map<String,Object>>() {
			
			@Override
			public Map<String,Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,Object> map = new HashMap<>();
				
				map.put("name", rs.getString("ADDRESS"));
				map.put("value", rs.getInt("COU"));
				return map;
			}
			
		});
	}
	public List<DoubanUser> get100New() {
		String sql = "select id,name,content,create_time,href,basic_info from douban_table order by create_time desc limit 100 ";
		return (List<DoubanUser>) jdbcTemplate.query(sql, new RowMapper<DoubanUser>() {
			
			@Override
			public DoubanUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				DoubanUser stu = new DoubanUser();
				stu.setId(rs.getInt("ID"));
				stu.setName(rs.getString("NAME"));
				stu.setContent(rs.getString("CONTENT"));
				stu.setCreate_time(rs.getTimestamp("CREATE_TIME"));
				stu.setHref(rs.getString("HREF"));
				stu.setBasic_info(rs.getString("BASIC_INFO"));
				return stu;
			}
			
		});
	}
	public int isContains(DoubanUser d) {
		String sql = "select count(*) as COU from douban_table where name = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{d.getName()},  new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt("COU");
			}
			
		});
	}
	public void addUser(DoubanUser d) {
		String sql = "INSERT into  douban_table (name,content,href,basic_info) values(?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{d.getName(),d.getContent(),d.getHref(),d.getBasic_info()});
	}
	
	public void addProvince(){
		String[] all =  { "广东", "青海", "四川", "海南", "陕西", "甘肃", "云南", "湖南", "湖北", "黑龙江","贵州", "山东", "江西", "河南", "河北","山西", "安徽", "福建", "浙江", "江苏", "吉林", "辽宁", "台湾","新疆", "广西", "宁夏", "内蒙古", "西藏", "北京", "天津", "上海", "重庆","香港", "澳门"};
		String sql = "INSERT into  china_province (name) values(?)";
		List<Object[]> batchArgs = new ArrayList<>();
		for(int i=0;i<all.length;i++){
			Object[] o = new Object[]{all[i]};
			batchArgs.add(o);
		}
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	public void updateAddress(List<DoubanUser> l) {
		String sql = "update douban_table set address = ? where id = ?";
		List<Object[]> batchArgs = new ArrayList<>();
		for(DoubanUser d:l){
			Object[] o = new Object[]{d.getAddress(),d.getId()};
			batchArgs.add(o);
		}
		jdbcTemplate.batchUpdate(sql, batchArgs);
		
	}
	

}
