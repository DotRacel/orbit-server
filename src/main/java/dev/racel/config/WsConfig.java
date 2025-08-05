package dev.racel.config;

import com.corundumstudio.socketio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.racel.listener.WsConnectListener;
import dev.racel.listener.WsDisconnectListener;
import dev.racel.listener.WsEventListener;
import dev.racel.listener.handler.OrbitEventHandlerRegistry;
import dev.racel.listener.handler.impl.*;
import dev.racel.listener.handler.impl.cosmetics.GetAllCosmeticsEventHandler;
import dev.racel.listener.handler.impl.cosmetics.GetPlayerCosmeticsEventHandler;
import dev.racel.listener.handler.impl.group.*;
import dev.racel.listener.handler.impl.profile.SaveProfileEventHandler;
import dev.racel.listener.handler.impl.profile.UseProfileEventHandler;
import dev.racel.listener.handler.impl.verify.ClientInfoEventHandler;
import dev.racel.listener.handler.impl.verify.IsVerifiedEventHandler;
import dev.racel.listener.handler.impl.verify.VerifyEventHandler;
import dev.racel.session.SessionManager;
import lombok.Getter;
import org.tinylog.Logger;

public class WsConfig {
    private static WsConfig INSTANCE;
    public static ObjectMapper mapper = new ObjectMapper();
    @Getter
    private SocketIOServer server;

    private WsConfig() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8080);
        config.setTransports(Transport.WEBSOCKET);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);

        OrbitEventHandlerRegistry registry = new OrbitEventHandlerRegistry();
        registry.register(new ClientInfoEventHandler());
        registry.register(new IsVerifiedEventHandler());
        registry.register(new VerifyEventHandler());

        registry.register(new SaveProfileEventHandler());
        registry.register(new UseProfileEventHandler());

        registry.register(new GroupCreateEventHandler());
        registry.register(new GroupJoinEventHandler());
        registry.register(new GetSelectedGroupEventHandler());
        registry.register(new SetSelectedGroupEventHandler());
        registry.register(new GroupGetAllEventHandler());
        registry.register(new GetSelectedGroupMembersEventHandler());
        registry.register(new UploadSchemShareEventHandler());
        registry.register(new DeleteGroupSchematicEventHandler());

        registry.register(new GetFeaturedServersEventHandler());
        registry.register(new GetPlayerCosmeticsEventHandler());
        registry.register(new GetAllCosmeticsEventHandler());
        registry.register(new GetSelectedTagsEventHandler());

        server = new SocketIOServer(config);
        SessionManager sessionManager = new SessionManager();

        server.addConnectListener(new WsConnectListener(sessionManager));
        server.addEventListener("event",
                String.class,
                new WsEventListener(registry, mapper, sessionManager));
        server.addDisconnectListener(new WsDisconnectListener(sessionManager));
        server.start();

        Logger.info("Websocket server initialized");
    }

    public static WsConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WsConfig();
        }
        return INSTANCE;
    }
}
