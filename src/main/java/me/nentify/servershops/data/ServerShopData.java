package me.nentify.servershops.data;

import com.google.common.base.Objects;
import me.nentify.servershops.ShopType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class ServerShopData extends AbstractData<ServerShopData, ImmutableServerShopData> {

    private ShopType shopType;
    private ItemStack item;
    private double price;
    private int quantity;

    public ServerShopData() {
        this(null, null, 0.0, 0);
    }

    public ServerShopData(ShopType shopType, ItemStack item, double price, int quantity) {
        this.shopType = shopType;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public Value<String> shopType() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.SHOP_TYPE, shopType.toString());
    }

    public Value<ItemStack> item() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.ITEM, item);
    }

    public Value<Double> price() {
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

        registerFieldGetter(ServerShopKeys.ITEM, () -> item);
        registerFieldSetter(ServerShopKeys.ITEM, value -> item = value);
        registerKeyValue(ServerShopKeys.ITEM, this::item);

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
                ServerShopKeys.ITEM.getQuery(),
                ServerShopKeys.PRICE.getQuery(),
                ServerShopKeys.QUANTITY.getQuery(),
                ServerShopKeys.SHOP_TYPE.getQuery()
        ))
            return Optional.empty();

        this.shopType = ShopType.valueOf(container.getString(ServerShopKeys.SHOP_TYPE.getQuery()).get());
        this.item = container.getSerializable(ServerShopKeys.ITEM.getQuery(), ItemStack.class).get();
        this.price = container.getDouble(ServerShopKeys.PRICE.getQuery()).get();
        this.quantity = container.getInt(ServerShopKeys.QUANTITY.getQuery()).get();

        return Optional.of(this);
    }

    @Override
    public ServerShopData copy() {
        return new ServerShopData(shopType, item, price, quantity);
    }

    @Override
    public ImmutableServerShopData asImmutable() {
        return new ImmutableServerShopData(shopType, item, price, quantity);
    }

    public int compareTo(ServerShopData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 2;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(ServerShopKeys.SHOP_TYPE, shopType.toString())
                .set(ServerShopKeys.ITEM, item)
                .set(ServerShopKeys.PRICE, price)
                .set(ServerShopKeys.QUANTITY, quantity);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("shopType", shopType)
                .add("item", item)
                .add("price", price)
                .add("quantity", quantity)
                .toString();
    }

    public ShopType getShopType() {
        return shopType;
    }
}
