package com.wxn.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TestAPI {

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		final CountDownLatch cdl = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("192.168.176.129:2181,192.168.176.130:2181,192.168.176.131:2181", 4000,
				new Watcher() {
					@Override
					public void process(WatchedEvent watchedEvent) {
						System.out.println("watcher in");
						if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
							System.out.println("连接成功");
							cdl.countDown();
						}
					}
				});
		cdl.await();



		zk.create("/zk-test", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		Thread.sleep(1000);
		Stat s = new Stat();
		byte[] zkTest = zk.getData("/zk-test", null, s);
		System.out.println(new String(zkTest));
		System.out.println(s.getVersion());
		zk.setData("/zk-test", "1".getBytes(), s.getVersion());
		System.out.println(s.getVersion());
		zk.getData("/zk-test", null, s);
		System.out.println(s.getVersion());

//		zk.delete("/zk-test", s.getVersion());
		zk.close();
	}
}
