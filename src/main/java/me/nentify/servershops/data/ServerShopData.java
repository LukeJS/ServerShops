package me.nentify.servershops.data;

import com.google.common.base.Objects;
import me.nentify.servershops.ShopType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class ServerShopData extends AbstractData<ServerShopData, ImmutableServerShopData> {

    private ShopType shopType;
    private String itemId;
    private int itemMeta;
    private int price;
    private int quantity;

    public ServerShopData() {
        this(null, "", 0, 0, 0);
    }

    public ServerShopData(ShopType shopType, String itemId, int itemMeta, int price, int quantity) {
        this.shopType = shopType;
        this.itemId = itemId;
        this.itemMeta = itemMeta;
        this.price = price;
        this.quantity = quantity;
    }

    public Value<String> shopType() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.SHOP_TYPE, shopType.toString());
    }

    public Value<String> itemId() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.ITEM_ID, itemId);
    }

    public Value<Integer> itemMeta() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.ITEM_META, itemMeta);
    }

    public Value<Integer> price() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.PRICE, price);
    }

    public Value<Integer> quantity() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.QUANTITY, quantity);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(ServerShopKeys.SHOP_TYPE, () -> shopType);
        registerFieldSetter(ServerShopKeys.SHOP_TYPE, value -> shopType = ShopType.valueOf(value));
        registerKeyValue(ServerShopKeys.SHOP_TYPE, this::shopType);

        registerFieldGetter(ServerShopKeys.ITEM_ID, () -> itemId);
        registerFieldSetter(ServerShopKeys.ITEM_ID, value -> itemId = value);
        registerKeyValue(ServerShopKeys.ITEM_ID, this::itemId);

        registerFieldGetter(ServerShopKeys.ITEM_META, () -> itemMeta);
        registerFieldSetter(ServerShopKeys.ITEM_META, value -> itemMeta = value);
        registerKeyValue(ServerShopKeys.ITEM_META, this::itemMeta);

        registerFieldGetter(ServerShopKeys.PRICE, () -> price);
        registerFieldSetter(ServerShopKeys.PRICE, value -> price = value);
        registerKeyValue(ServerShopKeys.PRICE, this::price);

        registerFieldGetter(ServerShopKeys.QUANTITY, () -> quantity);
        registerFieldSetter(ServerShopKeys.QUANTITY, value -> quantity = value);
        registerKeyValue(ServerShopKeys.QUANTITY, this::quantity);
    }

    @Override
    public Optional<ServerShopData> fill(DataHolder dataHolder, MergeFunction overlap) {
        return Optional.empty();
    }

    @Override
    public Optional<ServerShopData> from(DataContainer container) {
        if (!container.contains(
                ServerShopKeys.ITEM_ID.getQuery(),
                ServerShopKeys.ITEM_META.getQuery(),
                ServerShopKeys.PRICE.getQuery(),
                ServerShopKeys.QUANTITY.getQuery(),
                ServerShopKeys.SHOP_TYPE.getQuery()
        ))
            return Optional.empty();

        this.shopType = ShopType.valueOf(container.getString(ServerShopKeys.SHOP_TYPE.getQuery()).get());
        this.itemId = container.getString(ServerShopKeys.ITEM_ID.getQuery()).get();
        this.itemMeta = container.getInt(ServerShopKeys.ITEM_META.getQuery()).get();
        this.price = container.getInt(ServerShopKeys.PRICE.getQuery()).get();
        this.quantity = container.getInt(ServerShopKeys.QUANTITY.getQuery()).get();

        return Optional.of(this);
    }

    @Override
    public boolean supports(BaseValue<?> baseValue) {
        // i want to support signs
        return super.supports(baseValue);
    }

    @Override
    public ServerShopData copy() {
        return new ServerShopData(shopType, itemId, itemMeta, price, quantity);
    }

    @Override
    public ImmutableServerShopData asImmutable() {
        return new ImmutableServerShopData(shopType, itemId, itemMeta, price, quantity);
    }

    @Override
    public int compareTo(ServerShopData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(ServerShopKeys.SHOP_TYPE, shopType.toString())
                .set(ServerShopKeys.ITEM_ID, itemId)
                .set(ServerShopKeys.ITEM_META, itemMeta)
                .set(ServerShopKeys.PRICE, price)
                .set(ServerShopKeys.QUANTITY, quantity);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("shopType", shopType)
                .add("itemId", itemId)
                .add("itemMeta", itemMeta)
                .add("price", price)
                .add("quantity", quantity)
                .toString();
    }

    public ShopType getShopType() {
        return shopType;
    }
}
