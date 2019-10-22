package com.onlol.fetcher.api.riotModel;

public class SampleSummonerSpell {
    private String id;
    private String name;
    private String description;
    private String tooltip;
    private Short maxRank;
    private Integer[] cooldown;
    private Float cooldownBurn;
    private Integer[] cost;
    private Float costBurn;
    private Object datavalues; // always empty :?
    private Integer[][] effect;
    private Float[] effectBurn;
    private SampleSummonerSpellVariable[] vars;
    private Integer key;
    private Short summonerLevel;
    private String[] modes;
    private String costType;
    private Integer maxammo;
    private Integer[] range;
    private Float rangeBurn;
    private SampleImage image;
    private String resource;

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

    public SampleSummonerSpellVariable[] getVars() {
        return vars;
    }

    public void setVars(SampleSummonerSpellVariable[] vars) {
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

    public SampleImage getImage() {
        return image;
    }

    public void setImage(SampleImage image) {
        this.image = image;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
