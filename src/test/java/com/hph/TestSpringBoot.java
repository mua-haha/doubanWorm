package com.hph;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hph.doubanworm.bean.DoubanUser;
import com.hph.doubanworm.service.DoubanService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBoot {
	@Autowired
	private DoubanService doubanService;

	@Test
	public void test() {
		DoubanUser d = new DoubanUser();
		d.setName("阿萨飒飒所");
		d.setBasic_info("www");
		d.setContent("aaaaaaaaaaaaaaa");
		d.setHref("hhhhhhhhhhhhhhh");
		doubanService.addUser(d);
	}

}
