package WaspberrySocketManagement.Websocket;

public interface OnWebsocketMessageListener {

    void onWebsocketMessage(WebSocketConnector connector, String message);
}
