package com.github.ralgond.fje;

import com.github.ralgond.fje.UserService;
import com.twitter.util.Future;

public class UserServiceImpl implements UserService.ServiceIface {

	@Override
	public Future<User> createUser(String name) {
		return Future.value(new User(1, name+"[processed12]"));
	}

}
