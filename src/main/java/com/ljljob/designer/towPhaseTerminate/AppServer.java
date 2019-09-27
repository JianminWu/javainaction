package com.ljljob.designer.towPhaseTerminate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 16:38
 * @Function:
 * @Version 1.0
 */
public class AppServer extends Thread {

    private ServerSocket serverSocket;

    private final static int DEFAULT_PORT = 9999;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    private boolean start;

    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.start = true;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (start) {
                Socket socket = serverSocket.accept();
                if (Objects.isNull(socket)) {
                    continue;
                }
                executor.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // towPhaseTerminated
            doDestroy();
        }
    }

    private void doDestroy() {
        try {
            serverSocket.close();
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.start = false;
    }
}
