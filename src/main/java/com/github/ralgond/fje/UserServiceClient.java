package com.github.ralgond.fje;

import com.github.ralgond.fje.User;
import com.github.ralgond.fje.UserService;
import com.twitter.finagle.Thrift;
import com.twitter.util.Await;
import com.twitter.util.Function;
import com.twitter.util.Future;
import com.twitter.util.TimeoutException;

import scala.runtime.BoxedUnit;

public class UserServiceClient {

	public static void main(String args[]) throws TimeoutException, InterruptedException {
		UserService.ServiceIface client = Thrift.client().build("localhost:5555", UserService.ServiceIface.class);
		Future<User> resp = client.createUser("ht3");
		resp.onSuccess(new Function<User, BoxedUnit>() {
			@Override
			public BoxedUnit apply(User v1) {
				System.out.println(v1);
				return null;
			}
		});
		
		Await.ready(resp);
	}
}
