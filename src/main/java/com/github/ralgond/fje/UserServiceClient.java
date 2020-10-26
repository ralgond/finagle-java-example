package com.github.ralgond.fje;

import org.apache.thrift.protocol.TBinaryProtocol;

import com.github.ralgond.fje.User;
import com.github.ralgond.fje.UserService;
import com.twitter.finagle.ServiceFactory;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.thrift.RichClientParam;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import com.twitter.util.Future;
import com.twitter.util.TimeoutException;

public class UserServiceClient {

	public static void client1() {
		UserService.ServiceIface client = Thrift.client().build("localhost:5555", UserService.ServiceIface.class);

		System.out.println(Thread.currentThread());
		Future<User> resp = client.createUser("ht3");
		resp.onSuccess(v -> {
			System.out.println(v);
			System.out.println(Thread.currentThread());
			return null;
		});

		try {
			Await.ready(resp);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void client2() {
		ServiceFactory<ThriftClientRequest, byte[]> serviceFactory = Thrift.client().newClient("localhost:5555");
		UserService.ServiceToClient client = new UserService.ServiceToClient(serviceFactory.toService(),
				RichClientParam.apply(new TBinaryProtocol.Factory()));

		Future<User> resp = client.createUser("ht5");
		resp.onSuccess(v -> {
			System.out.println(v);
			return null;
		});

		try {
			Await.ready(resp);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		serviceFactory.close();
	}

	public static void main(String args[]) {
		client2();
	}
}
