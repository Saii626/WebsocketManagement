package WaspberrySocketManagement.WaspberryMessageHandler;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WaspberryMessageHandler {

    private Logger logger = LoggerFactory.getLogger(WaspberryMessageHandler.class.getSimpleName());
    private Gson gson;
    private List<MessageHandler> messageHandlers;


    WaspberryMessageHandler(Gson gson, List<MessageHandler> handlers) {
        this.gson = gson;
        this.messageHandlers = handlers;
    }

    public void handleMessage(String message) {

        WaspberryMessage waspberryMessage = gson.fromJson(message, WaspberryMessage.class);

        if (waspberryMessage != null) {
            Class payloadClass = waspberryMessage.getType();

            MessageModel messageModel = (MessageModel) gson.fromJson(waspberryMessage.getPayload(), payloadClass);

            for (MessageHandler handler : messageHandlers) {
                if (handler.handlesMessageOfType().equals(payloadClass)) {
                    logger.debug("Found {} for handling message", handler.getClass().getSimpleName());
                    handler.onMessage(messageModel);
                    return;
                }
            }

            logger.error("No handler for for handling {}", payloadClass.getSimpleName());
        }
    }
}
