package WaspberrySocketManagement.WaspberryWebsocket;

import WaspberrySocketManagement.WaspberryMessageHandler.WaspberryMessageHandler;
import WaspberrySocketManagement.WaspberryMessageHandler.WaspberryMessageHandlerModule;
import WaspberrySocketManagement.WaspberryWebsocket.impl.WaspberrySocketManagerImpl;
import WaspberrySocketManagement.Websocket.WebSocketConnector;
import dagger.Module;
import dagger.Provides;

import java.time.Duration;

@Module(includes = {WaspberryMessageHandlerModule.class, UnsatisfiedWebsocketDependenciesModule.class})
public class WaspberryWebsocketModule {

    @Provides
    @WaspberryWebsocketScope
    static WaspberrySocketManager getSocketManager(WebSocketConnector connector, WaspberryMessageHandler messageHandler, Duration retryTimeout) {
        return new WaspberrySocketManagerImpl(connector, messageHandler, retryTimeout);
    }

}
