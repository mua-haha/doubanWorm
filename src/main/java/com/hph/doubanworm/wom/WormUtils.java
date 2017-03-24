package com.hph.doubanworm.wom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hph.doubanworm.bean.DoubanUser;
import com.hph.doubanworm.service.DoubanService;

@RequestMapping("/worm")
@Controller
public class WormUtils {

	@Autowired
	private DoubanService doubanService;

	private String url = "https://www.douban.com/explore/";

	private int missNum = 0;
	private int addNum = 0;
	private int forNum = 0;

	// 不重复的username
	private Set<String> nameSet = new HashSet<String>();

	private List<DoubanUser> list0 = new ArrayList<>();
	private List<DoubanUser> list1 = new ArrayList<>();

	@RequestMapping(value = "/doWorm", method = RequestMethod.POST)
	public void start(@RequestBody DoubanUser d) throws Exception {

		// 获得初始数据
		List<DoubanUser> list = new ArrayList<>();
		list.add(d);
		list0 = getNextList(list);

		for (;;) {
			forNum++;
			System.out.println("第 " + forNum + " 次循环");
			if(list0.size()==0 && list1.size() ==0){
				list0 = doubanService.get100New();
			}
			if (list0.size() == 0) {
				list0 = getNextList(list1);
				list1.clear();
			} else {
				list1 = getNextList(list0);
				list0.clear();
			}

		}

	}

	public List<DoubanUser> getOrginalList() throws Exception {
		Document doc = Jsoup.connect(url).timeout(0).get();
		Elements es = doc.select("div[class=article]").get(0).select("div[class=hd]");
		List<DoubanUser> list = new ArrayList<>();
		for (Element e : es) {
			Element aLabel = e.select("a").get(1);
			DoubanUser d = new DoubanUser();
			nameSet.add(aLabel.text());
			d.setName(aLabel.text());
			d.setHref(aLabel.attr("href"));
			list.add(d);
		}
		return list;
	}

	public List<DoubanUser> getNextList(List<DoubanUser> list) throws Exception {
		List<DoubanUser> l = new ArrayList<>();
		// list 0 与1 在一次循环中 ，一个作为当前遍历的list1，一个作为当前爬出来保存的list0，循环结束，清空list1，递归
		// list0
		// 爬更多 循环开始
		for (DoubanUser d : list) {
			// 休息5秒
			Thread.sleep(2000);
			try {
				Document doc = Jsoup.connect(d.getHref()).timeout(0).get();
				if(doc.select("div[class=infobox]").size() ==0){
					System.out.println("进登录页面了");
				}
				// 获得用户详细信息
				Element user = doc.select("div[class=infobox]").get(0);
				d.setBasic_info(user.select("div[class=basic-info]").text());
				d.setContent(user.select("form").text());
				// 增加用户
				if(doubanService.isContains(d) == 0){
					doubanService.addUser(d);
					System.out.println(addNum++);
				}

				// 获得更多用户
				Element e = doc.select("div[id=friend]").get(0);
				Elements es = e.select("dl");
				for (Element s : es) {
					DoubanUser u = new DoubanUser();
					u.setName(s.select("a").get(1).text());
					u.setHref(s.select("a").get(0).attr("href"));
					if(doubanService.isContains(u) == 0){
						l.add(u);
					}
				}

			} catch (Exception e) {
				continue;
			}

		}
		return l;
	}

}
