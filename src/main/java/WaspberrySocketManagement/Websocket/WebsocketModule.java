package WaspberrySocketManagement.Websocket;

import dagger.Module;
import dagger.Provides;

import java.net.URI;

@Module
public class WebsocketModule {

    @Provides
    WebSocketConnector connector(URI uri) {
        return new WebSocketConnector(uri);
    }
}
