package com.icar.utils;

public class RequestType {
	
	

	public static final String HTTP_CONNECT_ERRO = "http_connect_error";
	public static final String HTTP_RECEIVE_DATA = "http_receive_data";
	public static final String REQUEST_TYPE_LOGIN = "0601";
	public static final String REQUEST_TYPE_REGISTER = "0602";
	public static final String REQUEST_TYPE_LOGOUT = "0603"; //0603

	public static final String REQUEST_TYPE_ARTICLEBYAID = "0001";
	public static final String REQUEST_TYPE_ARTICLEBYGID = "0002";
	public static final String REQUEST_TYPE_ARTICLELIST = "0003";

	public static final String REQUEST_TYPE_SESSIONID = "0004";
	public static final String REQUEST_TYPE_POSTARTICLEPERMISSION = "0007";

	public static final String REQUEST_TYPE_POSTARTICLE = "0701";

	public static final String REQUEST_TYPE_DELETEARTICLE = "0022";

	public static final String REQUEST_TYPE_FIXARTICLE = "0023";
	
	public static final String REQUEST_TYPE_MyARTICLE = "0024";
	
	//注册
	public static final String REQUEST_TYPE_REGIST_EMAIL = "100005";
	public static final String REQUEST_TYPE_REGIST_USERNAME = "100007";
	//
	public static final String REQUEST_TYPE_LOST_PASSWORD = "100012";
	//修改
	public static final String REQUEST_TYPE_CHANGE_PASSWORD_OR_NICKNAME = "100003";

	/**
	 * reqType 0101.置顶文章 0102 十大热门话题 0103 十大推荐文章
	 * 
	 * param 会话ID sessionID return 版面ID boardID 组ID groupID 文章ID articleID 文章标题
	 * title 作者 author
	 */
	public static final String REQUEST_TYPE_INDEX_TOP = "0101";
	public static final String REQUEST_TYPE_INDEX_HOT = "0102";
	public static final String REQUEST_TYPE_INDEX_REC = "0103";
	public static final String REQUEST_TYPE_INDEX_BRD = "0104";

	/**
	 * 新闻: reqType: 0300 新闻大杂烩 - 获取精选出来的新闻 0301 军事 0302 海外 0303 体育 0304 娱乐 0305
	 * 科技 0306 财经 common ---0003 param 版面ID boardID 会话ID sessionID return
	 * 新闻信息（groupID articleID ArticleTitle ArticleDate）
	 * 
	 * common 0001 common 0002
	 */
	public static final String REQUEST_TYPE_NEWS_DZH = "0300";
	public static final String REQUEST_TYPE_NEWS_JS = "0301";
	public static final String REQUEST_TYPE_NEWS_HW = "0302";
	public static final String REQUEST_TYPE_NEWS_TY = "0303";
	public static final String REQUEST_TYPE_NEWS_YL = "0304";
	public static final String REQUEST_TYPE_NEWS_KJ = "0305";
	public static final String REQUEST_TYPE_NEWS_CJ = "0306";
	public static final String REQUEST_TYPE_NEWS_COM = "0003";

	/**
	 * 获取分组列表
	 */
	public static final String REQUEST_TYPE_NEWS_GROUP = "0310";

	/**
	 * 移民 reqType: 0201 律师专栏 - 获取律师列表 param 会话ID sessionID return 律师头像 headPic
	 * 姓名 LawyerName 简介 Introduction
	 * 
	 * 
	 * reqType 0202 律师专栏 - 获取每位律师发布的最新两篇文章 param 会话ID sessionID return 版面ID
	 * boardID 组ID groupID 文章ID articleID 文章标题 ArticleTitle 发布时间 ArticleDate
	 * 
	 * 律师专栏 - 获取律师发布的文章详细信息 ----- common 0003
	 * 
	 * common 0001 common 0002
	 */
	public static final String REQUEST_TYPE_LAWER_LIST = "0201";
	public static final String REQUEST_TYPE_LAWER_SUBJECT = "0202";
	public static final String REQUEST_TYPE_LAWER_ARTICLE = "0203";
	public static final String REQUEST_TYPE_YIMIN_GROUP = "0204";

	public static final String LOGIN_RESULT_OK = "1";

	public static final String USER_PROCESS_DATA = "user_process_data";
	public static final String USER_REFRESH_DATA = "user_refresh_data";
	public static final String USER_PROCESS_DISP_ARTICLE = "user_process_display_article";

	/**
	 * 论坛集粹: boardsType 1 新闻中心 2 海外生活 3 华人世界 4 健身体育 5 娱乐休闲 6 情感杂想 7 文学艺术 8校友联谊 9
	 * 乡里乡情 10 电脑网络 11学术学科 12 本站系统
	 * 
	 * reqType 0401 获取某一热门版面列表 param 会话ID sessionID return 版面ID boardID 板块名称
	 * BoardsName 板块英文名称 BoardsEngName
	 */
	public static final String REQUEST_TYPE_BOARD_SECTION = "0400";
	public static final String REQUEST_TYPE_SECTION_HOT_ARTICLES = "0402";

	/**
	 * // *reqType 0001 通过文章ID获取文章详细信息
	 * 
	 * 
	 * // *param 版面ID boardID 文章ID articleID 会话ID sessionID // *return 内容
	 * content 标题 title 作者 author 时间 date /* reqType 
	 * 0002 通过组ID获取文章详细信息 param
	 * 版面ID boardID 组ID groupID 会话ID sessionID return 内容 content 标题 title 作者
	 * author 时间 date
	 * 
	 * reqType 0003 通过版面获取文章列表 param 版面ID boardID 起始位置start_pos 每页显示数目 showNum
	 * 排序类型 sortType(0 加M时间倒序 1 最晚时间回复倒序) 会话ID sessionID return 组ID groupID 文章ID
	 * articleID 文章标题 title 作者 author
	 * 
	 * reqType 0004 申请会话ID param return code(sessionID: 申请成功并返回会话ID FAIL:
	 * 申请失败并返回NULL)
	 */

	/**
	 * 分类讨论 boardsType 1 新闻中心 2 海外生活 3 华人世界 4 健身体育 5 娱乐休闲 6 情感杂想 7 文学艺术 8校友联谊 9
	 * 乡里乡情 10 电脑网络 11学术学科 12 本站系统
	 * 
	 * 
	 * reqType 0501 获取所有版面列表 param 会话ID sessionID return 版面ID boardID 板块名称
	 * BoardsName 板块英文名称 BoardsEngName
	 * 
	 * 获取某一版面文章列表 --- common 0003
	 * 
	 * 通过文章ID获取内容 -----common 0001 通过组ID获取内容 -----common 0002
	 */
	public static final String REQUEST_TYPE_BOARD = "0501";

	/**
	 * reqType 0801我的文章 param 用户ID userID 会话ID sessionID return OK：组ID groupID
	 * 文章ID articleID 文章标题 title 版面 boardName 日期 date ERROR: NULL
	 * 
	 * reqType 0802 讨论区 param 用户ID userID 会话ID sessionID return OK: 讨论区名称
	 * forumName 主题数 TitleNum ERROR: NULL
	 * 
	 * reqType 0803 好友 param 用户ID userID 会话ID sessionID return OK:好友名称
	 * friendName 状态 state ERROR: NULL
	 * 
	 * reqType 0804 黑名单 param 用户ID UserID 会话ID sessionID return OK:帐号 account
	 * ERROR: NULL
	 */
	public static final String REQUEST_TYPE_SEND_EMAIL_TO_FRIENDS = "0908";
	public static final String REQUEST_TYPE_DELETE_EMAIL = "0914";
	public static final String REQUEST_TYPE_DELETE_EMAIL_BOX = "0905";

	public static final String REQUEST_TYPE_SEND_BLACK = "08041";
	public static final String REQUEST_TYPE_DELETE_BLACK = "08042";
	public static final String REQUEST_TYPE_MY_ARTICLE = "0801";
	public static final String REQUEST_TYPE_MY_BOARD = "0802";
	public static final String REQUEST_TYPE_MY_FRIENDS = "0803";
	public static final String REQUEST_TYPE_ADD_MY_FRIEND = "08031";
	public static final String REQUEST_TYPE_DELETE_MY_FRIEND = "08032";
	public static final String REQUEST_TYPE_MY_BLACKNAME = "0804";
	public static final String REQUEST_TYPE_USERINFO = "0604";
	
	public static final String REQUEST_TYPE_MY_ARTICLE_CLUB = "100013";
	
	public static final String REQUEST_TYPE_MY_COLLECTION_NEWS = "1003";
	public static final String REQUEST_TYPE_MY_COLLECTION_TAOLUNQU = "1002";
	
	public static final String REQUEST_TYPE_YIMIN_YIMIN = "0212";
	public static final String REQUEST_TYPE_YIMIN_VISA = "0213";
	public static final String REQUEST_TYPE_YIMIN_ABOARD = "0214";
	public static final String REQUEST_TYPE_YIMIN_GO_HOME = "0215";
	public static final String REQUEST_TYPE_YIMIN_RELATIVES = "0216";
	public static final String REQUEST_TYPE_YIMIN_OVERSEASLIFE = "0217";
	
	
	/**
	 * reqType 1101 获取俱乐部  param 俱乐部类型  clubtype 
	 * 
	 * reqType 1106 获取俱乐部文章
	 * */
	public static final String REQUEST_TYPE_MITCLUB_GROUP_LIST = "1101";
	public static final String REQUEST_TYPE_MITCLUB_TYPE = "1102";
	public static final String REQUEST_TYPE_CLUB_DETAIL = "1103";
	public static final String REQUEST_TYPE_JOIN_CLUB = "1104";
	public static final String REQUEST_TYPE_EXIT_CLUB = "1105";
	public static final String REQUEST_TYPE_ARTICLELIST_BY_CLUB = "1106";
	public static final String REQUEST_GET_ARTICLE_REPLY_BY_CLUB = "1107";
	public static final String REQUEST_TYPE_POST_ARTICLELIST_IN_CLUB = "1110";
	public static final String REQUEST_TYPE_CLUB_MEMBER_LIST = "1112";
	public static final String REQUEST_TYPE_MY_CLUB_LIST1 = "100004";
	public static final String REQUEST_TYPE_MY_CLUB_LIST = "1113";
	public static final String REQUEST_MODIFY_CLUB_ARTICLE = "1115";
	public static final String REQUEST_DELETE_CLUB_ARTICLE = "1116";
	public static final String REQUEST_DELETE_SEARCH_CLUB = "1118";
}
