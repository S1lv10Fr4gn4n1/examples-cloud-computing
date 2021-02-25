import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class LeaderElection implements Watcher {

    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    private static final int ZOOKEEPER_TIMEOUT = 3000;
    private static final String ELECTION_NAMESPACE = "/election";

    private ZooKeeper zooKeeper;
    private String currentZnodeName;

    public static void main(String[] args) {
        try {
            LeaderElection leaderElection = new LeaderElection();
            leaderElection.connectToZookeeper();
            leaderElection.createElectionNode();
            leaderElection.volunteerForLeadership();
            leaderElection.reelectLeader();
            leaderElection.run();
            leaderElection.close();
            System.out.println("Disconnected from Zookeeper, exiting application");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, ZOOKEEPER_TIMEOUT, this);
    }

    private void createElectionNode() throws KeeperException, InterruptedException {
        try {
            final String rootNodePath = zooKeeper.create(ELECTION_NAMESPACE, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            if (rootNodePath == null) {
                throw new IllegalStateException("Unable to create/access leader election root node with path: " + rootNodePath);
            }
        } catch (KeeperException.NodeExistsException e) {
            System.out.println(ELECTION_NAMESPACE + " namespace already exists");
        }
    }

    private void volunteerForLeadership() throws KeeperException, InterruptedException {
        String znodePrefix = ELECTION_NAMESPACE + "/c_";
        String znodeNameFullPath = zooKeeper.create(znodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("znode name " + znodeNameFullPath);

        this.currentZnodeName = znodeNameFullPath.replace(ELECTION_NAMESPACE + "/", "");
    }

    private void reelectLeader() {
        try {
            Stat predecessorStat = null;
            String predecessorZnodeName = "";
            while (predecessorStat == null) {
                List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
                Collections.sort(children);

                String smallestChild = children.stream().findAny().orElse("");

                if (smallestChild.equals(currentZnodeName)) {
                    System.out.println("I'm the leader");
                    return;
                } else {
                    System.out.println("I'm NOT the leader, " + smallestChild + " is the leader");
                    int predecessorIndex = Collections.binarySearch(children, currentZnodeName) - 1;
                    predecessorZnodeName = children.get(predecessorIndex);
                    predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorZnodeName, this);
                }
            }
            System.out.println("Watching znode " + predecessorZnodeName);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    private void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to Zookeeper");
                } else {
                    synchronized (zooKeeper) {
                        System.out.println("Disconnected from Zookeeper event");
                        zooKeeper.notifyAll();
                    }
                }
            case NodeDeleted:
                reelectLeader();
        }
    }
}
