package com.andy.demo.shorturl;

import com.andy.demo.shorturl.service.IQrCode;
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
	private IShortUrl iShortUrl;
	@Autowired
	private IQrCode iQrCode;

	// 测试短地址
	@Test
	public void testShortUrl() {
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

	// 测二维码
	@Test
	public void testQrcode(){
		String text = "http://www.huanbaoxia.com/goods-280.html";
		String res = iQrCode.qrCode(text);
		System.out.println(res);

		res = iQrCode.qrCode(text);
		System.out.println(res);

		text = "http://www.huanbaoxia.com/goods-281.html";
		res = iQrCode.qrCode(text);
		System.out.println(res);

		res = iQrCode.qrCode(text);
		System.out.println(res);
	}

	@Test
	public void getSysInfo(){
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("file.separator"));
	}

}
