package dev.racel.config;

import com.corundumstudio.socketio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.racel.handler.event.impl.group.manage.*;
import dev.racel.handler.event.impl.group.share.*;
import dev.racel.listener.WsConnectListener;
import dev.racel.listener.WsDisconnectListener;
import dev.racel.listener.WsEventListener;
import dev.racel.handler.event.OrbitEventHandlerRegistry;
import dev.racel.handler.event.impl.*;
import dev.racel.handler.event.impl.cosmetics.GetAllCosmeticsEventHandler;
import dev.racel.handler.event.impl.cosmetics.GetPlayerCosmeticsEventHandler;
import dev.racel.handler.event.impl.group.*;
import dev.racel.handler.event.impl.group.schematic.DeleteGroupSchematicEventHandler;
import dev.racel.handler.event.impl.group.schematic.UploadSchemShareEventHandler;
import dev.racel.handler.event.impl.profile.SaveProfileEventHandler;
import dev.racel.handler.event.impl.profile.UseProfileEventHandler;
import dev.racel.handler.event.impl.verify.ClientInfoEventHandler;
import dev.racel.handler.event.impl.verify.IsVerifiedEventHandler;
import dev.racel.handler.event.impl.verify.VerifyEventHandler;
import dev.racel.session.SessionManager;
import lombok.Getter;
import org.tinylog.Logger;

import java.util.ResourceBundle;

public class WsConfig {
    private static WsConfig INSTANCE;
    public static ObjectMapper mapper = new ObjectMapper();
    @Getter
    private SocketIOServer server;

    private WsConfig(int port) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(port);
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
        registry.register(new KickMemberEventHandler());
        registry.register(new PromoteMemberEventHandler());
        registry.register(new DemoteMemberEventHandler());
        registry.register(new GroupLeaveEventHandler());
        registry.register(new GroupDisbandEventHandler());
        registry.register(new SendPingLocationEventHandler());
        registry.register(new SendPingAdjustEventHandler());
        registry.register(new SendPingBlockEventHandler());
        registry.register(new SendPingChunkEventHandler());
        registry.register(new SendSharePatchCrumbEventHandler());

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
            INSTANCE = new WsConfig(Integer.parseInt(ResourceBundle.getBundle("config").getString("websocket.port")));
        }
        return INSTANCE;
    }
}
