package com.github.ralgond.fje;

import java.net.InetSocketAddress;

import com.twitter.finagle.Announcement;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Thrift;
import com.twitter.util.Await;
import com.twitter.util.Future;
import com.twitter.util.TimeoutException;

public class ZkUserServiceServer {
	public static void main(String args[]) throws TimeoutException, InterruptedException {
		ListeningServer server = Thrift.server().serveIface("127.0.0.1:5555", new UserServiceImpl());
		
		String zkFullPath = String.format("zk!%s!%s!0","127.0.0.1:2181","/ht/service/thrift/user");
		
		Future<Announcement> anno = server.announce(zkFullPath);
		anno.onSuccess(v->{System.out.println(v); return null;})
			.onFailure(e->{e.printStackTrace();return null;});
		Await.ready(anno);
		Await.ready(server);
	}
}
