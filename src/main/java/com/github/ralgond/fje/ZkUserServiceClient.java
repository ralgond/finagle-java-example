package com.github.ralgond.fje;

import org.apache.thrift.protocol.TBinaryProtocol;

import com.twitter.finagle.ServiceFactory;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.thrift.RichClientParam;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;
import com.twitter.util.Future;
import com.twitter.util.TimeoutException;

public class ZkUserServiceClient {
	public static void client2() {
		String zkFullPath = String.format("zk!%s!%s","127.0.0.1:2181","/ht/service/thrift/user");
		
		ServiceFactory<ThriftClientRequest, byte[]> serviceFactory = Thrift.client().newClient(zkFullPath);
		UserService.ServiceToClient client = new UserService.ServiceToClient(serviceFactory.toService(),
				RichClientParam.apply(new TBinaryProtocol.Factory()));

		Future<User> resp = client.createUser("ht2");
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
