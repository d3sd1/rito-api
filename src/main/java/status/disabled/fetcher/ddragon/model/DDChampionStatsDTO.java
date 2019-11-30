package status.disabled.fetcher.ddragon.model;

public class DDChampionStatsDTO {
    private float hp = 0F;
    private float hpperlevel = 0F;
    private float mp = 0F;
    private float mpperlevel = 0F;
    private float movespeed = 0F;
    private float armor = 0F;
    private float armorperlevel = 0F;
    private float spellblock = 0F;
    private float spellblockperlevel = 0F;
    private float attackrange = 0F;
    private float hpregen = 0F;
    private float hpregenperlevel = 0F;
    private float mpregen = 0F;
    private float mpregenperlevel = 0F;
    private float crit = 0F;
    private float critperlevel = 0F;
    private float attackdamage = 0F;
    private float attackdamageperlevel = 0F;
    private float attackspeedoffset = 0F;
    private float attackspeedperlevel = 0F;

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getHpperlevel() {
        return hpperlevel;
    }

    public void setHpperlevel(float hpperlevel) {
        this.hpperlevel = hpperlevel;
    }

    public float getMp() {
        return mp;
    }

    public void setMp(float mp) {
        this.mp = mp;
    }

    public float getMpperlevel() {
        return mpperlevel;
    }

    public void setMpperlevel(float mpperlevel) {
        this.mpperlevel = mpperlevel;
    }

    public float getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(float movespeed) {
        this.movespeed = movespeed;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getArmorperlevel() {
        return armorperlevel;
    }

    public void setArmorperlevel(float armorperlevel) {
        this.armorperlevel = armorperlevel;
    }

    public float getSpellblock() {
        return spellblock;
    }

    public void setSpellblock(float spellblock) {
        this.spellblock = spellblock;
    }

    public float getSpellblockperlevel() {
        return spellblockperlevel;
    }

    public void setSpellblockperlevel(float spellblockperlevel) {
        this.spellblockperlevel = spellblockperlevel;
    }

    public float getAttackrange() {
        return attackrange;
    }

    public void setAttackrange(float attackrange) {
        this.attackrange = attackrange;
    }

    public float getHpregen() {
        return hpregen;
    }

    public void setHpregen(float hpregen) {
        this.hpregen = hpregen;
    }

    public float getHpregenperlevel() {
        return hpregenperlevel;
    }

    public void setHpregenperlevel(float hpregenperlevel) {
        this.hpregenperlevel = hpregenperlevel;
    }

    public float getMpregen() {
        return mpregen;
    }

    public void setMpregen(float mpregen) {
        this.mpregen = mpregen;
    }

    public float getMpregenperlevel() {
        return mpregenperlevel;
    }

    public void setMpregenperlevel(float mpregenperlevel) {
        this.mpregenperlevel = mpregenperlevel;
    }

    public float getCrit() {
        return crit;
    }

    public void setCrit(float crit) {
        this.crit = crit;
    }

    public float getCritperlevel() {
        return critperlevel;
    }

    public void setCritperlevel(float critperlevel) {
        this.critperlevel = critperlevel;
    }

    public float getAttackdamage() {
        return attackdamage;
    }

    public void setAttackdamage(float attackdamage) {
        this.attackdamage = attackdamage;
    }

    public float getAttackdamageperlevel() {
        return attackdamageperlevel;
    }

    public void setAttackdamageperlevel(float attackdamageperlevel) {
        this.attackdamageperlevel = attackdamageperlevel;
    }

    public float getAttackspeedoffset() {
        return attackspeedoffset;
    }

    public void setAttackspeedoffset(float attackspeedoffset) {
        this.attackspeedoffset = attackspeedoffset;
    }

    public float getAttackspeedperlevel() {
        return attackspeedperlevel;
    }

    public void setAttackspeedperlevel(float attackspeedperlevel) {
        this.attackspeedperlevel = attackspeedperlevel;
    }

    @Override
    public String toString() {
        return "SampleChampionStats{" +
                "hp=" + hp +
                ", hpperlevel=" + hpperlevel +
                ", mp=" + mp +
                ", mpperlevel=" + mpperlevel +
                ", movespeed=" + movespeed +
                ", armor=" + armor +
                ", armorperlevel=" + armorperlevel +
                ", spellblock=" + spellblock +
                ", spellblockperlevel=" + spellblockperlevel +
                ", attackrange=" + attackrange +
                ", hpregen=" + hpregen +
                ", hpregenperlevel=" + hpregenperlevel +
                ", mpregen=" + mpregen +
                ", mpregenperlevel=" + mpregenperlevel +
                ", crit=" + crit +
                ", critperlevel=" + critperlevel +
                ", attackdamage=" + attackdamage +
                ", attackdamageperlevel=" + attackdamageperlevel +
                ", attackspeedoffset=" + attackspeedoffset +
                ", attackspeedperlevel=" + attackspeedperlevel +
                '}';
    }
}
