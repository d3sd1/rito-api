package com.onlol.fetcher.api.riotModel;

public class SampleItemGold {
    private Integer base;
    private boolean purchasable;
    private Integer total;
    private Integer sell;

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public void setPurchasable(boolean purchasable) {
        this.purchasable = purchasable;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    @Override
    public String toString() {
        return "SampleItemGold{" +
                "base=" + base +
                ", purchasable=" + purchasable +
                ", total=" + total +
                ", sell=" + sell +
                '}';
    }
}
