package me.nentify.servershops.data;

import me.nentify.servershops.ShopType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableServerShopData extends AbstractImmutableData<ImmutableServerShopData, ServerShopData> {

    private ShopType shopType;
    private String itemId;
    private int itemMeta;
    private int price;
    private int quantity;

    public ImmutableServerShopData() {
        this(null, "", 0, 0, 0);
    }

    public ImmutableServerShopData(ShopType shopType, String itemId, int itemMeta, int price, int quantity) {
        this.shopType = shopType;
        this.itemId = itemId;
        this.itemMeta = itemMeta;
        this.price = price;
        this.quantity = quantity;
    }

    public ImmutableValue<String> shopType() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.SHOP_TYPE, shopType.toString()).asImmutable();
    }

    public ImmutableValue<String> itemId() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.ITEM_ID, itemId).asImmutable();
    }

    public ImmutableValue<Integer> itemMeta() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.ITEM_META, itemMeta).asImmutable();
    }

    public ImmutableValue<Integer> price() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.PRICE, price).asImmutable();
    }

    public ImmutableValue<Integer> quantity() {
        return Sponge.getRegistry().getValueFactory().createValue(ServerShopKeys.QUANTITY, quantity).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(ServerShopKeys.SHOP_TYPE, () -> shopType);
        registerKeyValue(ServerShopKeys.SHOP_TYPE, this::shopType);

        registerFieldGetter(ServerShopKeys.ITEM_ID, () -> itemId);
        registerKeyValue(ServerShopKeys.ITEM_ID, this::itemId);

        registerFieldGetter(ServerShopKeys.ITEM_META, () -> itemMeta);
        registerKeyValue(ServerShopKeys.ITEM_META, this::itemMeta);

        registerFieldGetter(ServerShopKeys.PRICE, () -> price);
        registerKeyValue(ServerShopKeys.PRICE, this::price);

        registerFieldGetter(ServerShopKeys.QUANTITY, () -> quantity);
        registerKeyValue(ServerShopKeys.QUANTITY, this::quantity);
    }

    @Override
    public ServerShopData asMutable() {
        return new ServerShopData(shopType, itemId, itemMeta, price, quantity);
    }

    @Override
    public int compareTo(ImmutableServerShopData o) {
        return 0;
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
    public int getContentVersion() {
        return 1;
    }

    @Override
    public boolean supports(BaseValue<?> baseValue) {
        return super.supports(baseValue);
    }
}
