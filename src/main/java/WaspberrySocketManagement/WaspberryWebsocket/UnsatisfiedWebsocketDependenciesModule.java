package WaspberrySocketManagement.WaspberryWebsocket;

import WaspberrySocketManagement.WaspberryMessageHandler.MessageHandler;
import WaspberrySocketManagement.WaspberryMessageHandler.MessageModel;
import dagger.Module;
import dagger.Provides;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Module
public class UnsatisfiedWebsocketDependenciesModule {

    private URI websocketURI;
    private Duration retryTimeout;
    private List<MessageModel> messageModels;
    private List<MessageHandler> messageHandlers;

    public UnsatisfiedWebsocketDependenciesModule(URI websocketURI, Duration retryTimeout, List<MessageModel> messageModels, List<MessageHandler> messageHandlers) {
        this.websocketURI = websocketURI;
        this.retryTimeout = retryTimeout;
        this.messageModels = messageModels;
        this.messageHandlers = messageHandlers;
    }

    @Provides
    @WaspberryWebsocketScope
    URI getUri() {
        return websocketURI;
    }

    @Provides
    @WaspberryWebsocketScope
    Duration getRetryTimeout() {
        return retryTimeout;
    }

    @Provides
    @WaspberryWebsocketScope
    List<MessageModel> getMessageModels() {
        return messageModels;
    }

    @Provides
    @WaspberryWebsocketScope
    List<MessageHandler> getHandlers() {
        return messageHandlers;
    }
}
