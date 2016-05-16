package com.nju.runnable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nju.authorization.Authorization;
import com.nju.service.XueXinService;
import com.nju.util.Constant;

public class XueXinAuthRunnable extends BaseRunnable{
	private static final Logger log = Logger.getLogger(XueXinAuthRunnable.class);
	private static final String UTF_8 = "utf-8";
	private AsyncContext asyncContext;
	public XueXinAuthRunnable(AsyncContext context) {
		this.asyncContext = context;
	}
	
	@Override
	protected void exeRequest() throws IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request =(HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
		Authorization authorization = null;
		 
		OutputStream out = response.getOutputStream();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String captcha = request.getParameter("captcha");
		String authorId = request.getParameter("authorId");
		logger.info("authorId=" +authorId);
		
		if((authorization=(Authorization) request.getSession().getAttribute(Constant.AUTH_OBJECT)) == null) {
			 authorization = new Authorization();
			 request.getSession().setAttribute(Constant.AUTH_OBJECT,authorization);
		}
		
		XueXinService service = new XueXinService(authorization);
		if(captcha ==null || captcha.equals("")){ 
			String result = service.login(username, password,authorId);
			if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
				out.write(result.getBytes(UTF_8));
			} else{
				loginExe(authorization,result,request,response,out);
			}
			
		}
		else{
 			String result = service.loginWithCaptcha(request.getSession().getAttribute(Constant.XUE_XIN_IT).toString(), username, password, captcha, authorId);
			if(result.equals(Constant.HTTP_ERROR) || result.equals(Constant.HTTP_URL_ERROR)) {
				//out.print(result);
				out.write(result.getBytes(UTF_8));
			} else{
				loginExe(authorization,result,request,response,out);
			}
		}
		out.close();
	}

	
	
	private void loginExe(Authorization authorization,String result,HttpServletRequest request,HttpServletResponse response, OutputStream out) throws IOException {
		Map<Object, Object> resultMap =gson.fromJsonToMap(result);
	 
		if(resultMap.containsKey(Constant.XUE_XIN_CAPTCHA)) {
			String IT = authorization.getItT(resultMap.get(Constant.XUE_XIN_CAPTCHA).toString());
			log.info("it=="+IT);
			request.getSession().setAttribute(Constant.XUE_XIN_IT,IT);
			 
			byte[] buffer =authorization.getCaptcha();
			String fileName = request.getServletContext().getRealPath("/")+"captcha.jpg";
			FileOutputStream fileOut = new FileOutputStream(new File(fileName));
			log.info(request.getContextPath());
			log.info(fileName);
			fileOut.write(buffer);
			fileOut.close();
			int length = buffer.length;
			byte[] finBuffer = new byte[length+1];
			System.arraycopy(buffer, 0, finBuffer, 0, length);
			finBuffer[length]='#';
			 
			out.write(finBuffer);
		} else {
			out.write(result.getBytes(UTF_8));
		}
	}
	

}
