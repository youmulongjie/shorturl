shorturl
================================
1) 根据长链接 转化短链接（依赖 新浪API）；
2) 根据长连接 生成二维码、条形码（依赖 谷歌com.google.zxing）

## 长链接 转化短链接

- 注册新浪开发者，平台上创建应用，获取Acess Token
- 调用 short_url接口，参考文档 http://open.weibo.com/wiki/2/short_url/shorten
- 实现类 ShortUrlImpl

## 长连接 生成二维码

- 依赖 谷歌com.google.zxing
   ```java
   <!-- 条形码、二维码生成 -->
   <dependency>
       <groupId>com.google.zxing</groupId>
       <artifactId>core</artifactId>
       <version>${com.google.zxing.version}</version>
   </dependency>
   <dependency>
       <groupId>com.google.zxing</groupId>
       <artifactId>javase</artifactId>
       <version>${com.google.zxing.version}</version>
   </dependency>
   ```
- 实现类 CreateCodeImpl.java

## Andy.wang

<img src="doc/594580820.jpg" width="15%" alt="Andy.wang的QQ"/>


