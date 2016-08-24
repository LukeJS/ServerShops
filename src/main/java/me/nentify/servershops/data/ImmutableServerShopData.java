package me.nentify.servershops.data;

import me.nentify.servershops.ShopType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.item.inventory.ItemStack;

public class ImmutableServerShopData extends AbstractImmutableData<ImmutableServerShopData, ServerShopData> {

    private ShopType shopType;
    private ItemStack item;
    private double price;
    private int quantity;

    public ImmutableServerShopData() {
        this(null, null, 0.0, 0);
    }

    public ImmutableServerShopData(ShopType shopType, ItemStack item, double price, int quantity) {
        this.shopType = shopType;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public ImmutableValue<String> shopType() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.SHOP_TYPE, shopType.toString()).asImmutable();
    }

    public ImmutableValue<ItemStack> item() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.ITEM, item).asImmutable();
    }

    public ImmutableValue<Double> price() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.PRICE, price).asImmutable();
    }

    public ImmutableValue<Integer> quantity() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.QUANTITY, quantity).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(ServerShopKeys.SHOP_TYPE, () -> shopType);
        registerKeyValue(ServerShopKeys.SHOP_TYPE, this::shopType);

        registerFieldGetter(ServerShopKeys.ITEM, () -> item);
        registerKeyValue(ServerShopKeys.ITEM, this::item);

        registerFieldGetter(ServerShopKeys.PRICE, () -> price);
        registerKeyValue(ServerShopKeys.PRICE, this::price);

        registerFieldGetter(ServerShopKeys.QUANTITY, () -> quantity);
        registerKeyValue(ServerShopKeys.QUANTITY, this::quantity);
    }

    @Override
    public ServerShopData asMutable() {
        return new ServerShopData(shopType, item, price, quantity);
    }

    @Override
    public int compareTo(ImmutableServerShopData o) {
        return 0;
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
    public int getContentVersion() {
        return 2;
    }
}
