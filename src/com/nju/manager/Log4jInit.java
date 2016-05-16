package com.nju.manager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {
	private static final String LOG_FILE_NAME = "log4j-init-file";
	private static final Logger log = Logger.getLogger(Log4jInit.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(){
		String prefix =  getServletContext().getRealPath("/");
	    String file = getInitParameter(LOG_FILE_NAME );
	    // if the log4j-init-file is not set, then no point in trying
	    if(file != null) {
	      PropertyConfigurator.configure(prefix+file);
	      log.info("log4j success");
	    }
	}
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		
	}

}
