package WaspberrySocketManagement.WaspberryMessageHandler;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public class WaspberryMessageDeserializer implements JsonDeserializer<WaspberryMessage> {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private List<MessageModel> messageModels;

    public WaspberryMessageDeserializer(List<MessageModel> messageModels) {
        this.messageModels = messageModels;
    }

    @Override
    public WaspberryMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = json.getAsJsonObject().get("type").getAsString();

        logger.debug("found {} payload", type);

        for(MessageModel model : messageModels) {
            if (model.modelName().equals((type))) {
                WaspberryMessage message = new WaspberryMessage();
                message.setType(model.getClass());
                message.setPayload(json.getAsJsonObject().get("payload"));

                return message;
            }
        }

        logger.error("No message model found of {}", type);

        return null;
    }
}
