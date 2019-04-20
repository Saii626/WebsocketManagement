package WaspberrySocketManagement.WaspberryMessageHandler;

import WaspberrySocketManagement.WaspberryWebsocket.WaspberryWebsocketScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

import java.util.List;

@Module
public class WaspberryMessageHandlerModule {

    @Provides
    @WaspberryWebsocketScope
    static WaspberryMessageHandler getHandler(Gson gson, List<MessageHandler> messageHandlers) {
        return new WaspberryMessageHandler(gson, messageHandlers);
    }

    @Provides
    @WaspberryWebsocketScope
    static Gson getGson(List<MessageModel> models) {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .registerTypeAdapter(WaspberryMessage.class, new WaspberryMessageDeserializer(models))
                .create();
    }
}
