package WaspberrySocketManagement;

import WaspberrySocketManagement.WaspberryWebsocket.DaggerWaspberryWebsocketComponent;
import WaspberrySocketManagement.WaspberryWebsocket.UnsatisfiedWebsocketDependenciesModule;
import WaspberrySocketManagement.WaspberryWebsocket.WaspberryWebsocketComponent;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Test {

    public static void main(String[] args) throws URISyntaxException {
        WaspberryWebsocketComponent component = DaggerWaspberryWebsocketComponent.builder().
                unsatisfiedWebsocketDependenciesModule(new UnsatisfiedWebsocketDependenciesModule(
                        new URI(""),
                        Duration.of(5, SECONDS),
                        new ArrayList<>(),
                        new ArrayList<>()
                )).build();
    }
}
