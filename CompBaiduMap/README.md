## 百度地图库

详细文档地址： [http://documents.hoge.cn/docs/show/362](http://documents.hoge.cn/docs/show/362 "百度地图核心库文档")

## 使用方法

使用方式为变更，若将原有的引用方式升级注意一下几点：

1、百度地图工具类 全局替换

原引用

    import com.hoge.android.factory.util.BaiduMapUtils;
新引用
    
    import com.hoge.android.library.baidumap.BaiduMapUtils;



2、地理信息解析工具类 全局替换

原引用

    import com.hoge.android.factory.util.BaiduMapUtilByRacer;
新引用

    import com.hoge.android.library.baidumap.BaiduMapUtilByRacer;

3、定位工具类 全局替换

原引用

    import com.hoge.android.factory.util.location.LocationUtil
新引用

    import com.hoge.android.library.baidumap.util.LocationUtil
    
4、搜索工具类 新增

    import com.hoge.android.library.baidumap.BaiduSearchUtil
    
提供路径规划搜索、兴趣点搜索、搜索框智能提示、坐标反向解析地理信息、公交线路搜索、行政区搜索等功能


## 使用场景

|工程名称|模块|工程地址|负责人|
|:----    |:---|:----- |-----   |
| MXU| 全局 | https://git.hoge.cn/mxu/MXU4_Android |李徐蓉



## 版本修改记录


|修订人|修订日期|修订说明|文档版本|
|:----    |:---|:----- |-----   |
|吴国进 |2017年11月5日|百度地图库 |1.0.0   |
|吴国进 |2017年11月17日|CoreUtil使用+方式引用，不再使用具体版本号； |1.0.2   |
|王亚 |2017年11月27日|依赖版本整体修改Android Library支持AS3.0 |1.1.0   |
|吴国进 |2017年11月29日|SDK jar包改为compileOnly声明依赖关系 |1.1.1   |
|吴国进 |2017年12月01日|新增百度地图搜索工具类 BaiduSearchUtil |1.1.2   |
|吴国进 |2017年12月06日|解除地图核心库对CoreUtil库依赖，新建assets文件夹，将百度jar包中assets文件拷进来，解决jar在library中引用无法将assets打包进aar的问题 |1.1.3   |
|吴国进 |2017年12月13日|Map和Search工具类提供public的构造器方法，并在onDestroy中手动将其静态对象设为null |1.1.4   |
|吴国进 |2017年12月14日|百度Map工具类由于每次构建的MapView都必须为新的，取消单例方法，改为每次都new一个新的对象 |1.1.5   |
|宋智航 |2017年12月22日|1.在路径规划中暴露更多方法，可用来修改路径规划中的样式。2.新增mydrivingroute包下内容，作用：驾车路径中返回打车信息为空的问题，自己做数据解析|1.1.6   |
|宋智航 |2017年12月27日|1.transit路径规划中的加个信息2.添加路径中节点点击事件的暴露方法|1.1.7   |