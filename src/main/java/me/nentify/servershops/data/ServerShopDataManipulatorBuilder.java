package me.nentify.servershops.data;

import me.nentify.servershops.ShopType;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class ServerShopDataManipulatorBuilder extends AbstractDataBuilder<ServerShopData> implements DataManipulatorBuilder<ServerShopData, ImmutableServerShopData> {

    public ServerShopDataManipulatorBuilder() {
        super(ServerShopData.class, 2);
    }

    @Override
    public ServerShopData create() {
        return new ServerShopData();
    }

    @Override
    public Optional<ServerShopData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(ServerShopData.class).orElse(new ServerShopData()));
    }

    @Override
    protected Optional<ServerShopData> buildContent(DataView container) throws InvalidDataException {
        if (container.contains(ServerShopKeys.SHOP_TYPE, ServerShopKeys.ITEM, ServerShopKeys.PRICE, ServerShopKeys.QUANTITY)) {
            ShopType shopType = ShopType.valueOf(container.getString(ServerShopKeys.SHOP_TYPE.getQuery()).get());
            ItemStack item = container.getSerializable(ServerShopKeys.ITEM.getQuery(), ItemStack.class).get();
            double price = container.getDouble(ServerShopKeys.PRICE.getQuery()).get();
            int quantity = container.getInt(ServerShopKeys.QUANTITY.getQuery()).get();

            return Optional.of(new ServerShopData(shopType, item, price, quantity));
        }

        return Optional.empty();
    }
}
