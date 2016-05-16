package com.nju.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.nju.authorization.Authorization;
import com.nju.dao.impl.BaseDaoImpl;
import com.nju.dao.impl.UserInfoDaoImpl;
import com.nju.model.UserInfo;
import com.nju.util.Constant;
import com.nju.util.SchoolFriendGson;

public class XueXinService {

	private static final Logger log = Logger.getLogger(XueXinService.class.getSimpleName());
	private Authorization authorization;
	public XueXinService(Authorization mauthoriztion) {
		this.authorization = mauthoriztion;
	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @param label_id
	 * @return Constant.HTTP_ERROR
	 *         Constant.HTTP_URL_ERROR
	 *         
	 * @throws IOException
	 */
	
	public String login(String username,String password,String authorId) throws IOException{
		String homeHtml = authorization.getHomeHtml(Constant.XUE_XIN_LOGIN_URL);
		if(homeHtml.equals(Constant.HTTP_ERROR) || homeHtml.equals(Constant.HTTP_URL_ERROR)) {
			return homeHtml;
		} else{
			 Document doc = authorization.praseHtml(homeHtml);
			 String it = authorization.getIt(doc);
			 return exeLogin(it,username,password,authorId);
		}
		
	}
	
	public String loginWithCaptcha(String it,String username,String password,String captcha,String authorId) throws IOException {
		String html = authorization.postFormWithCaptcha(it, username, password,captcha);
		if(html.equals(Constant.HTTP_ERROR) || html.equals(Constant.HTTP_URL_ERROR)) {
			return html;
		} else {	
			return saveUserInfo(html,authorId);
		}
	}
	
	private String exeLogin(String it,String username,String password,String authorId) throws IOException {
		
		String html = authorization.postForm(it, username, password);
		if(html.equals(Constant.HTTP_ERROR) || html.equals(Constant.HTTP_URL_ERROR)) {
			return html;
		} else{
			return saveUserInfo(html,authorId);
		}
		
	}
	
	private String saveUserInfo(String html,String authorId) throws IOException {
		Document doc = authorization.praseHtml(html);
		SchoolFriendGson gson = SchoolFriendGson.newInstance();
		if(authorization.isErrorUsernameOrPassword(doc)) {
			Map<String,String> infoMap = new HashMap<String,String>();
			infoMap.put(Constant.XUE_XIN_USERNAME_OR_PASS_ERROR,Constant.USERNAME_OR_PASS_ERROR);
			return gson.toJson(infoMap);
		} else if(authorization.isNeedCaptcha(doc)){
			Map<String,String> info = new HashMap<String,String>();
			info.put(Constant.XUE_XIN_CAPTCHA,html);
			return gson.toJson(info);
		} else{
				BaseDaoImpl<UserInfo> userInfo = new UserInfoDaoImpl();
				ArrayList<UserInfo> lists = authorization.getUserInfo(doc);
				if(lists==null) {
					Map<String,String> infoMap = new HashMap<String,String>();
					infoMap.put(Constant.XUE_XIN_USERNAME_OR_PASS_ERROR,Constant.USERNAME_OR_PASS_ERROR);
					return gson.toJson(infoMap);
				} else{
					for (UserInfo info:lists) {
						info.setAuthorId(Integer.valueOf(authorId));
						log.info(info.getSubSchoolName()+info.getSchoolNameName());
						log.info(gson.toJson(info));
						userInfo.save(info);
					}
					Map<String,List<UserInfo>> infoMap = new HashMap<String,List<UserInfo>>();
					infoMap.put(Constant.XUE_XIN_INFO, lists);
					return gson.toJson(infoMap);
				}
		   }
	 }
}
