package me.nentify.servershops.data;

import me.nentify.servershops.ShopType;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class ServerShopDataManipulatorBuilder implements DataManipulatorBuilder<ServerShopData, ImmutableServerShopData> {

    @Override
    public ServerShopData create() {
        return new ServerShopData();
    }

    @Override
    public Optional<ServerShopData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(ServerShopData.class).orElse(new ServerShopData()));
    }

    @Override
    public Optional<ServerShopData> build(DataView container) throws InvalidDataException {
        if (container.contains(ServerShopKeys.SHOP_TYPE, ServerShopKeys.ITEM_ID, ServerShopKeys.ITEM_META, ServerShopKeys.PRICE, ServerShopKeys.QUANTITY)) {
            ShopType shopType = ShopType.valueOf(container.getString(ServerShopKeys.SHOP_TYPE.getQuery()).get());
            String itemId = container.getString(ServerShopKeys.ITEM_ID.getQuery()).get();
            int itemMeta = container.getInt(ServerShopKeys.ITEM_META.getQuery()).get();
            int price = container.getInt(ServerShopKeys.PRICE.getQuery()).get();
            int quantity = container.getInt(ServerShopKeys.QUANTITY.getQuery()).get();

            return Optional.of(new ServerShopData(shopType, itemId, itemMeta, price, quantity));
        }

        return Optional.empty();
    }
}
