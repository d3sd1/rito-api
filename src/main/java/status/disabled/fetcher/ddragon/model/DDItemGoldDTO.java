package status.disabled.fetcher.ddragon.model;

public class DDItemGoldDTO {
    private Integer base = 0;
    private boolean purchasable = false;
    private Integer total = 0;
    private Integer sell = 0;

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
