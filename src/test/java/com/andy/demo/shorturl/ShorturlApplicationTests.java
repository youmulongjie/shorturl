package com.andy.demo.shorturl;

import com.andy.demo.shorturl.bean.SinaConfig;
import com.andy.demo.shorturl.redis.RedisStringUtil;
import com.andy.demo.shorturl.service.IShortUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShorturlApplicationTests {

	@Autowired
	private SinaConfig acessToken;

	@Autowired
	private IShortUrl iShortUrl;

	@Autowired
	private RedisStringUtil redisStringUtil;

	@Test
	public void contextLoads() {
		String longUrl = "http://www.huanbaoxia.com/goods-280.html";
		String res = iShortUrl.shortUrl(longUrl);
		System.out.println(res);

		res = iShortUrl.shortUrl(longUrl);
		System.out.println(res);

		longUrl = "http://www.huanbaoxia.com/goods-281.html";
		res = iShortUrl.shortUrl(longUrl);
		System.out.println(res);

		res = iShortUrl.shortUrl(longUrl);
		System.out.println(res);

	}



}
