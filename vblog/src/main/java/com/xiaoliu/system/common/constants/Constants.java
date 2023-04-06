package com.xiaoliu.system.common.constants;

/**
 * @author: 61分
 * @date: 2022/7/31 19:37
 * @description:
 */
public class Constants {
// 当一个常数或字符串我们需要在程序里反复反复使用的时候，我们就可以把它定义为static final，这样内存就不用重复的申请和释放空间。
   public static final  String USER = "user";
    public static final Integer STATUS_NORMAL = 0; //状态正常
    public static final String BLOG_ID = "BLOG_ID";//文章详情缓存
    public static final String BLOG_ALL_PAGE = "BLOG_ALL_PAGE";    //文章总数缓存
    public static final String BLOG_TOTAL = "BLOG_TOTAL";    //归档页总数缓存
    public static final String BLOG_ARCHIVE_PAGE = "BLOG_ARCHIVE_PAGE";    //归档页缓存
    public static final String BLOG_SEARCH_BY_CATEGORY_ID = "BLOG_SEARCH_BY_CATEGORY_ID";    //根据标签id查找博客表
    public static final String BLOG_SEARCH_BY_CATEGORY_TOTAL = "BLOG_SEARCH_BY_CATEGORY_TOTAL";    //根据标签id查找博客表总数
    public static final String BLOG_SEARCH_BY_TAG_TOTAL = "BLOG_SEARCH_BY_TAG_TOTAL";    //根据类别id查找博客表总数
    public static final String BLOG_SEARCH_BY_TAG_ID = "BLOG_SEARCH_BY_CATEGORY_ID";    //根据标签id查找博客表
    public static final String CHAT = "CHAT";    //chat缓存
    public static final String COMMENT = "COMMENT";    //COMMENT缓存
    public static final String TAG_TOTAL= "TAG_TOTAL";    //类别总数缓存
    public static final String TAG_USER_COUNT= "TAG_USER_COUNT";    //类别数缓存
    public static final Integer PHOTO =1;    //是为图片
    public static final Integer NOPHOTO =0;//不是图片
    public static final String BLOGVIECOUT = "blog:viewCount";//文章浏览量
    public static final String CATEGORYTOTAL = "categoryTotal";    //便签总数
    public static final String TOKEN = "TOKEN";    //标签使用数
    public static final String LIST_PHOTO = "LIST_PHOTO";    //查询图片
    public static final String LIST_LINK = "LIST_LINK";    //查询友链
    public static final String LIST_TAG = "LIST_TAG";    //查询类别表
    public static final String SOURCE_LIST = "SOURCE_LIST";    //查询类别表


}
