package WaspberrySocketManagement.WaspberryWebsocket;

import WaspberrySocketManagement.WaspberryMessageHandler.WaspberryMessageHandler;
import dagger.Component;

@WaspberryWebsocketScope
@Component(modules = WaspberryWebsocketModule.class)
public interface WaspberryWebsocketComponent {

    WaspberrySocketManager getWaspberrySocketManager();

    WaspberryMessageHandler getWaspberryMessageHandler();
}
