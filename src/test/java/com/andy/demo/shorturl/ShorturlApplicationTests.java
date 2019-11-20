package com.andy.demo.shorturl;

import com.andy.demo.shorturl.config.QrCodeInfo;
import com.andy.demo.shorturl.config.SinaOpenConfig;
import com.andy.demo.shorturl.service.ICreateCode;
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
    private QrCodeInfo qrcodeInfo;
    @Autowired
    private SinaOpenConfig sinaOpenConfig;

    @Test
    public void test() {
        System.out.println(qrcodeInfo);
        System.out.println(sinaOpenConfig);
    }

    @Autowired
    private IShortUrl iShortUrl;

    // 测试短地址（官方功能已下线）
    /*@Test
    public void testShortUrl() {
        String longUrl = "https://www.cnblogs.com/shaohsiung/p/9649845.html";
        String res = iShortUrl.shortUrl(longUrl);
        System.out.println(res);

        res = iShortUrl.shortUrl("https://my.oschina.net/andy1989/blog/3120943");
        System.out.println(res);
    }*/

    @Autowired
    private ICreateCode iCreateCode;

    // 测二维码
    @Test
    public void testQrCode() throws Exception {
        String text = "0101020322";
        String res = iCreateCode.qrCode(text);
        System.out.println(res);

        res = iCreateCode.qrCodeWithDefaultLogo(text);
        System.out.println(res);
    }

    // 测条形码
    @Test
    public void testBarCode() throws Exception {
        String text = "0101020322";
        String res = iCreateCode.barCode(text);
        System.out.println(res);

        res = iCreateCode.barCodeWithWord(text);
        System.out.println(res);
    }

    @Test
    public void getSysInfo() {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("file.separator"));
    }

}
