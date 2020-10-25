package com.github.ralgond.fje;

import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Thrift;
import com.twitter.util.Await;
import com.twitter.util.TimeoutException;

public class UserServiceServer {
	public static void main(String args[]) throws TimeoutException, InterruptedException {
		ListeningServer server = Thrift.server().serveIface("localhost:5555", new UserServiceImpl());
		Await.ready(server);
	}
}
