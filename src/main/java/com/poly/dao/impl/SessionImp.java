package com.poly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.poly.dao.SessionDAO;
import com.poly.entity.Session;
import org.springframework.stereotype.Service;



@Service
public class SessionImp implements SessionDAO {
	public static List<Session> listSession=new ArrayList<>();
	@Override
	public Object get(String key) {
		Object value = null;
		if(key.isEmpty()||key=="") {
			return null;
		}
		for(Session item:listSession) {
			if(key.equalsIgnoreCase(item.getKey())) {
				value=item.getValue();
			}
		}
		return value;
	}

	@Override
	public void set(String key, Object value) {
		for(int i=0;i<listSession.size();i++) {
			Session item=listSession.get(i);
			if(key.equalsIgnoreCase(item.getKey())) {
				listSession.remove(i);
			}
		}
		Session session=new Session();
		session.setKey(key);
		session.setValue(value);
		listSession.add(session);
	}	
	@Override
	public void clear() {
//		for(int i=0;i<listSession.size();i++) {
//			if(key.equals(listSession.get(i).getKey())) {
//				listSession.remove(i);
//				return;
//			}
//		}
		listSession.removeAll(listSession);
		
	}
}
