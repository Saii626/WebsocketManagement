package WaspberrySocketManagement.WaspberryWebsocket.impl;

import WaspberrySocketManagement.WaspberryMessageHandler.WaspberryMessageHandler;
import WaspberrySocketManagement.WaspberryWebsocket.WaspberrySocketManager;
import WaspberrySocketManagement.Websocket.WebSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

public class WaspberrySocketManagerImpl implements WaspberrySocketManager {

    private Logger logger = LoggerFactory.getLogger(WaspberrySocketManagerImpl.class.getSimpleName());

    private WebSocketConnector webSocketConnector;
    private WaspberryMessageHandler messageHandler;
    private Duration retryTimeout;

    public WaspberrySocketManagerImpl(WebSocketConnector connector, WaspberryMessageHandler messageHandler, Duration retryTimeout) {
        this.webSocketConnector = connector;
        this.messageHandler = messageHandler;
        this.retryTimeout = retryTimeout;
    }

    @Override
    public void connect() {
        webSocketConnector.setReconnectTimeout(retryTimeout);
        webSocketConnector.setWebsocketMessageListener((conn, msg) -> messageHandler.handleMessage(msg));
        webSocketConnector.setWebsocketCloseListener(conn -> {
            try {
                Thread.sleep(retryTimeout.toMillis());
                conn.connect();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        Thread websocketThread = new Thread(() -> {
            logger.debug("Starting new websocket Thread");
            try {
                webSocketConnector.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        websocketThread.setName("waspberrySocket");
        websocketThread.start();
    }

    @Override
    public void send(String msg) {
        webSocketConnector.sendMessage(msg);
    }

}
