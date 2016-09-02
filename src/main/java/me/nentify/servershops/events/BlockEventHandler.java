package me.nentify.servershops.events;

import me.nentify.servershops.ServerShops;
import me.nentify.servershops.ShopType;
import me.nentify.servershops.data.ServerShopData;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.math.BigDecimal;
import java.util.Optional;

public class BlockEventHandler {

    @Listener
    public void onBlockInteract(InteractBlockEvent.Secondary.MainHand event, @First Player player) {
        Optional<Location<World>> blockLoc = event.getTargetBlock().getLocation();

        if (blockLoc.isPresent()) {
            Optional<TileEntity> tileEntity = blockLoc.get().getTileEntity();

            if (tileEntity.isPresent() && tileEntity.get() instanceof Sign) {
                Sign sign = (Sign) tileEntity.get();

                Optional<ServerShopData> serverShopDataOptional = sign.get(ServerShopData.class);

                if (serverShopDataOptional.isPresent()) {
                    if (player.get(Keys.IS_SNEAKING).orElse(false)) {
                        player.sendMessage(Text.of(TextColors.RED, "You can not use this sign while sneaking"));
                        return;
                    }

                    ServerShopData serverShopData = serverShopDataOptional.get();

                    ShopType shopType = serverShopData.getShopType();
                    ItemStack item = serverShopData.item().get();
                    BigDecimal price = BigDecimal.valueOf(serverShopData.price().get());
                    int quantity = serverShopData.quantity().get();

                    item.setQuantity(quantity);

                    Optional<UniqueAccount> account = ServerShops.instance.economyService.getOrCreateAccount(player.getUniqueId());
                    Currency defaultCurrency = ServerShops.instance.economyService.getDefaultCurrency();

                    if (account.isPresent()) {
                        if (shopType == ShopType.BUY) {
                            if (player.getInventory().size() < player.getInventory().capacity()) {
                                TransactionResult result = account.get().withdraw(
                                        defaultCurrency,
                                        price,
                                        Cause.source(ServerShops.instance).build()
                                );

                                if (result.getResult() == ResultType.SUCCESS) {
                                    player.getInventory().offer(item);
                                    player.sendMessage(Text.of(TextColors.GREEN, "Bought ", quantity, " ", item, " for ", defaultCurrency.format(price)));
                                } else if (result.getResult() == ResultType.ACCOUNT_NO_FUNDS) {
                                    player.sendMessage(Text.of(TextColors.RED, "You cannot afford this!"));
                                } else {
                                    player.sendMessage(Text.of(TextColors.RED, "Error during transaction"));
                                }
                            } else {
                                player.sendMessage(Text.of(TextColors.RED, "You do not have enough space to buy this!"));
                            }
                        } else {
                            Inventory inventoryStacks = player.getInventory().query(item);
                            Optional<ItemStack> peek = inventoryStacks.peek(quantity);

                            if (peek.isPresent() && peek.get().getQuantity() >= quantity) {
                                TransactionResult result = account.get().deposit(
                                        defaultCurrency,
                                        price,
                                        Cause.source(ServerShops.instance).build()
                                );

                                if (result.getResult() == ResultType.SUCCESS) {
                                    inventoryStacks.poll(quantity);
                                    player.sendMessage(Text.of(TextColors.GREEN, "Sold ", quantity, " ", item, " for ", defaultCurrency.format(price)));
                                } else if (result.getResult() == ResultType.ACCOUNT_NO_SPACE) {
                                    player.sendMessage(Text.of(TextColors.RED, "You cannot sell this item as your account is full!"));
                                } else {
                                    player.sendMessage(Text.of(TextColors.RED, "Error during transaction"));
                                }
                            } else {
                                player.sendMessage(Text.of(TextColors.RED, "You do not have ", quantity,  " ", item, " to sell"));
                            }
                        }
                    }
                }
            }
        }
    }
}