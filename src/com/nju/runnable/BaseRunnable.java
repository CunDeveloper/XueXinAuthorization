package com.nju.runnable;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.nju.util.SchoolFriendGson;

public abstract class BaseRunnable  implements Runnable{
	protected Logger logger;
	protected SchoolFriendGson gson;
	protected abstract void exeRequest() throws IOException;
	
	public BaseRunnable() {
		super();
		logger = Logger.getLogger(this.getClass());
		gson = SchoolFriendGson.newInstance();
	}

	@Override
	public void run() {
		 
		 try {
			exeRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
