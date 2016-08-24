package me.nentify.servershops.commands;

import me.nentify.servershops.ServerShops;
import me.nentify.servershops.ShopType;
import me.nentify.servershops.data.ServerShopData;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.math.BigDecimal;
import java.util.Optional;

public class ShopCommand implements CommandExecutor {

    private ShopType shopType;

    public ShopCommand(ShopType shopType) {
        this.shopType = shopType;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        double price = args.<Double>getOne("price").get();
        Optional<Integer> quantityOptional = args.getOne("quantity");

        if (src instanceof Player) {
            Player player = (Player) src;

            BlockRay<World> blockRay = BlockRay.from(player)
                    .blockLimit(100)
                    .filter(BlockRay.continueAfterFilter(BlockRay.onlyAirFilter(), 1))
                    .build();

            Optional<BlockRayHit<World>> hitOptional = blockRay.end();

            if (hitOptional.isPresent()) {
                BlockRayHit<World> hit = hitOptional.get();
                Location<World> blockLoc = hit.getLocation();
                Optional<TileEntity> tileEntity = blockLoc.getTileEntity();

                if (tileEntity.isPresent() && tileEntity.get() instanceof Sign) {
                    Sign sign = (Sign) tileEntity.get();

                    Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);

                    if (itemStackOptional.isPresent()) {
                        ItemStack itemStack = itemStackOptional.get();

                        int quantity;

                        if (quantityOptional.isPresent())
                            quantity = quantityOptional.get();
                        else
                            quantity = itemStack.getQuantity();

                        ServerShopData serverShopData = new ServerShopData(shopType, itemStack, price, quantity);
                        sign.offer(serverShopData);

                        Currency defaultCurrency = ServerShops.instance.economyService.getDefaultCurrency();
                        player.sendMessage(Text.of(TextColors.GREEN, "Successfully created ", shopType.toString().toLowerCase(), " shop for ", quantity, " ", itemStack, " for ", defaultCurrency.format(BigDecimal.valueOf(price))));
                    }
                } else {
                    player.sendMessage(Text.of(TextColors.RED, "You are not looking at a sign"));
                }
            } else {
                player.sendMessage(Text.of(TextColors.RED, "You are not looking at a sign"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be a player to run this command"));
        }

        return CommandResult.success();
    }
}
