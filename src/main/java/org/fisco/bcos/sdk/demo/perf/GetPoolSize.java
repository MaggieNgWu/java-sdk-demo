package org.fisco.bcos.sdk.demo.perf;

import java.math.BigInteger;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.PendingTxSize;
import org.fisco.bcos.sdk.demo.perf.model.DagUserInfo;
import org.fisco.bcos.sdk.model.ConstantConfig;

public class GetPoolSize {

    private static Client client;
    private static DagUserInfo dagUserInfo = new DagUserInfo();

    public static void main(String[] args) {
        String configFileName = ConstantConfig.CONFIG_FILE_NAME;
        URL configUrl = ParallelOkPerf.class.getClassLoader().getResource(configFileName);
        if (configUrl == null) {
            System.out.println("The configFile " + configFileName + " doesn't exist!");
            return;
        }
        String configFile = configUrl.getPath();
        BcosSDK sdk = BcosSDK.build(configFile);
        client = sdk.getClient(1);
        Timer timer = new Timer();
        timer.schedule(new MyTask(client), 0, 1000);
    }
}

class MyTask extends TimerTask {
    private static Client client;

    MyTask(Client c) {
        client = c;
    }

    @Override
    public void run() {
        PendingTxSize pt = client.getPendingTxSize();
        BigInteger size = pt.getPendingTxSize();
        System.out.println("size," + size.toString());
    }
}
