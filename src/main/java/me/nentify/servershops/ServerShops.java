package me.nentify.servershops;

import com.google.inject.Inject;
import me.nentify.servershops.commands.ShopCommand;
import me.nentify.servershops.data.ImmutableServerShopData;
import me.nentify.servershops.data.ServerShopData;
import me.nentify.servershops.data.ServerShopDataManipulatorBuilder;
import me.nentify.servershops.events.BlockEventHandler;
import org.slf4j.Logger;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

@Plugin(id = ServerShops.PLUGIN_ID, name = ServerShops.PLUGIN_NAME, version = ServerShops.PLUGIN_VERSION)
public class ServerShops {

    public static final String PLUGIN_ID = "servershops";
    public static final String PLUGIN_NAME = "Server Shops";
    public static final String PLUGIN_VERSION = "1.0.0";

    public static ServerShops instance;

    @Inject
    public Logger logger;

    @Inject
    public GameRegistry gameRegistry;

    public EconomyService economyService;

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        logger.info("Starting " + PLUGIN_NAME + " v" + PLUGIN_VERSION);

        instance = this;

        Sponge.getDataManager().register(ServerShopData.class, ImmutableServerShopData.class, new ServerShopDataManipulatorBuilder());

        Sponge.getGame().getEventManager().registerListeners(this, new BlockEventHandler());

        CommandSpec buyShopCommand = CommandSpec.builder()
                .description(Text.of("Create a buy shop"))
                .permission("servershops.create.buy")
                .arguments(
                        GenericArguments.integer(Text.of("price")),
                        GenericArguments.optional(GenericArguments.integer(Text.of("quantity")))
                )
                .executor(new ShopCommand(ShopType.BUY))
                .build();

        CommandSpec sellShopCommand = CommandSpec.builder()
                .description(Text.of("Create a sell shop"))
                .permission("servershops.create.sell")
                .arguments(
                        GenericArguments.integer(Text.of("price")),
                        GenericArguments.optional(GenericArguments.integer(Text.of("quantity")))
                )
                .executor(new ShopCommand(ShopType.SELL))
                .build();

        Sponge.getCommandManager().register(this, sellShopCommand, "sellshop");
        Sponge.getCommandManager().register(this, buyShopCommand, "buyshop");
    }

    @Listener
    public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
        if (event.getService().equals(EconomyService.class)) {
            economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
        }
    }
}