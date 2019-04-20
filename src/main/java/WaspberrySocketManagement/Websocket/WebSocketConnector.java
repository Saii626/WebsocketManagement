package WaspberrySocketManagement.Websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Optional;

@ClientEndpoint
public class WebSocketConnector {

    private Session session;
    private Optional<OnWebsocketMessageListener> websocketMessageListener = Optional.empty();
    private Optional<OnWebsocketOpenListener> websocketOpenListener = Optional.empty();
    private Optional<OnWebsocketCloseListener> websocketCloseListener = Optional.empty();
    private URI uri;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private Duration reconnectTimeout = Duration.ZERO;

    @Inject
    public WebSocketConnector(URI endpointURI) {
        this.uri = endpointURI;
    }

    public void setWebsocketOpenListener(OnWebsocketOpenListener websocketOpenListener) {
        this.websocketOpenListener = Optional.ofNullable(websocketOpenListener);
    }

    public void setWebsocketMessageListener(OnWebsocketMessageListener websocketMessageListener) {
        this.websocketMessageListener = Optional.ofNullable(websocketMessageListener);
    }

    public void setWebsocketCloseListener(OnWebsocketCloseListener websocketCloseListener) {
        this.websocketCloseListener = Optional.ofNullable(websocketCloseListener);
    }

    public void setReconnectTimeout(Duration reconnectTimeout) {
        this.reconnectTimeout = reconnectTimeout;
    }

    public void connect() throws IOException {
        logger.debug("Connecting to websocket");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, this.uri);
        } catch (DeploymentException e) {
//            e.printStackTrace();

            logger.warn("Unable to connect to server");
            if (!reconnectTimeout.isZero()) {
                logger.debug("Retrying...");
                try {
                    Thread.sleep(reconnectTimeout.toMillis());
                    connect();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        logger.debug("Opening websocket");
        this.session = userSession;
        websocketOpenListener.ifPresent(listener -> listener.onWebsocketOpen(this));
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        logger.debug("Closing websocket due to {}", reason.getReasonPhrase());
        this.session = null;
        websocketCloseListener.ifPresent(listener -> listener.onWebsocketClose(this));
    }

    @OnMessage
    public void onMessage(String message) {
        logger.debug("message received from websocket");
        logger.debug("message: {}", message);
        websocketMessageListener.ifPresent(listener -> listener.onWebsocketMessage(this, message));
    }

    @OnError
    public void onError(Throwable err) throws Throwable {
        logger.error("Error occurred in websocket connection");
        throw err;
    }

    public void sendMessage(String message) {
        logger.debug("sending message to websocket");
        this.session.getAsyncRemote().sendText(message);
    }
}
