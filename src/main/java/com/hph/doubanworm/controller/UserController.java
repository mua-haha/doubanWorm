package com.hph.doubanworm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hph.doubanworm.bean.DoubanUser;
import com.hph.doubanworm.service.DoubanService;

@RequestMapping("/user")
@Controller
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private DoubanService doubanService;

	@RequestMapping("/getList")
	public String getStus(Model model) {
		logger.info("从数据库读取User集合");
		List<DoubanUser> list =  doubanService.get100New();
		model.addAttribute("list",list);
		return "list";
	}
	
	@RequestMapping("/count")
	public String  mapCount(Model model) {
		List<Map<String,Object>> list =  doubanService.getAddressCount();
		model.addAttribute("list",list);
		return "douBanUserMap";
	}
	
	
	@RequestMapping("/addProvince")
	public void addProvince(){
		doubanService.addProvince();
	}
	
	
	@RequestMapping("/getAddress")
	public void getAddress(){
		String[] all =  { "广东", "青海", "四川", "海南", "陕西", "甘肃", "云南", "湖南", "湖北", "黑龙江","贵州", "山东", "江西", "河南", "河北","山西", "安徽", "福建", "浙江", "江苏", "吉林", "辽宁", "台湾","新疆", "广西", "宁夏", "内蒙古", "西藏", "北京", "天津", "上海", "重庆","香港", "澳门"};
		List<DoubanUser> list =  doubanService.getList();
		List<DoubanUser> l = new ArrayList<>();
		for(DoubanUser d:list){
			for(int i=0;i<all.length;i++){
				if(d.getBasic_info().indexOf(all[i]) != -1){
					DoubanUser u  = new DoubanUser();
					u.setId(d.getId());
					u.setAddress(all[i]);
					l.add(u);
					System.out.println(d.getName()+" : "+all[i] +" : "+d.getBasic_info());
				}
			}
		}
		doubanService.updateAddress(l);
	}
	
	
	

}
