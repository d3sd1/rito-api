package status.disabled.unknown.fetcher.ddragon.model;

import java.util.Arrays;

public class DDSummonerSpellDTO {
    private String id = "";
    private String name = "";
    private String description = "";
    private String tooltip = "";
    private Short maxRank = 0;
    private Integer[] cooldown = new Integer[0];
    private Float cooldownBurn = 0F;
    private Integer[] cost = new Integer[0];
    private Float costBurn = 0F;
    private Object datavalues = new Object(); // always empty :?
    private Integer[][] effect = new Integer[0][0];
    private Float[] effectBurn = new Float[0];
    private DDummonerSpellVariableDTO[] vars = new DDummonerSpellVariableDTO[0]; // DEPRECEATED
    private Integer key = 0;
    private Short summonerLevel = 0;
    private String[] modes = new String[0];
    private String costType = "";
    private Integer maxammo = 0;
    private Integer[] range = new Integer[0];
    private Float rangeBurn = 0F;
    private DDImageDTO image = new DDImageDTO();
    private String resource = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Short getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(Short maxRank) {
        this.maxRank = maxRank;
    }

    public Integer[] getCooldown() {
        return cooldown;
    }

    public void setCooldown(Integer[] cooldown) {
        this.cooldown = cooldown;
    }

    public Float getCooldownBurn() {
        return cooldownBurn;
    }

    public void setCooldownBurn(Float cooldownBurn) {
        this.cooldownBurn = cooldownBurn;
    }

    public Integer[] getCost() {
        return cost;
    }

    public void setCost(Integer[] cost) {
        this.cost = cost;
    }

    public Float getCostBurn() {
        return costBurn;
    }

    public void setCostBurn(Float costBurn) {
        this.costBurn = costBurn;
    }

    public Object getDatavalues() {
        return datavalues;
    }

    public void setDatavalues(Object datavalues) {
        this.datavalues = datavalues;
    }

    public Integer[][] getEffect() {
        return effect;
    }

    public void setEffect(Integer[][] effect) {
        this.effect = effect;
    }

    public Float[] getEffectBurn() {
        return effectBurn;
    }

    public void setEffectBurn(Float[] effectBurn) {
        this.effectBurn = effectBurn;
    }

    public DDummonerSpellVariableDTO[] getVars() {
        return vars;
    }

    public void setVars(DDummonerSpellVariableDTO[] vars) {
        this.vars = vars;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Short getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(Short summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public String[] getModes() {
        return modes;
    }

    public void setModes(String[] modes) {
        this.modes = modes;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public Integer getMaxammo() {
        return maxammo;
    }

    public void setMaxammo(Integer maxammo) {
        this.maxammo = maxammo;
    }

    public Integer[] getRange() {
        return range;
    }

    public void setRange(Integer[] range) {
        this.range = range;
    }

    public Float getRangeBurn() {
        return rangeBurn;
    }

    public void setRangeBurn(Float rangeBurn) {
        this.rangeBurn = rangeBurn;
    }

    public DDImageDTO getImage() {
        return image;
    }

    public void setImage(DDImageDTO image) {
        this.image = image;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "SampleSummonerSpell{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tooltip='" + tooltip + '\'' +
                ", maxRank=" + maxRank +
                ", cooldown=" + Arrays.toString(cooldown) +
                ", cooldownBurn=" + cooldownBurn +
                ", cost=" + Arrays.toString(cost) +
                ", costBurn=" + costBurn +
                ", datavalues=" + datavalues +
                ", effect=" + Arrays.toString(effect) +
                ", effectBurn=" + Arrays.toString(effectBurn) +
                ", vars=" + Arrays.toString(vars) +
                ", key=" + key +
                ", summonerLevel=" + summonerLevel +
                ", modes=" + Arrays.toString(modes) +
                ", costType='" + costType + '\'' +
                ", maxammo=" + maxammo +
                ", range=" + Arrays.toString(range) +
                ", rangeBurn=" + rangeBurn +
                ", image=" + image +
                ", resource='" + resource + '\'' +
                '}';
    }
}
