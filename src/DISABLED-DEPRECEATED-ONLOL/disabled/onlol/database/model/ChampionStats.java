package status.disabled.onlol.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @OneToOne
    private Champion champion;

    @OneToOne
    private GameVersion gameVersion;

    @Column(nullable = false, unique = false)
    private float hp;

    @Column(nullable = false, unique = false)
    private float hpPerLevel;

    @Column(nullable = false, unique = false)
    private float mp;

    @Column(nullable = false, unique = false)
    private float mpPerLevel;

    @Column(nullable = false, unique = false)
    private float movSpeed;

    @Column(nullable = false, unique = false)
    private float armor;

    @Column(nullable = false, unique = false)
    private float armorPerLevel;

    @Column(nullable = false, unique = false)
    private float spellBlock;

    @Column(nullable = false, unique = false)
    private float spellBlockPerLevel;

    @Column(nullable = false, unique = false)
    private float attackRange;

    @Column(nullable = false, unique = false)
    private float hpRegen;

    @Column(nullable = false, unique = false)
    private float hpRegenPerLevel;

    @Column(nullable = false, unique = false)
    private float mpRegen;

    @Column(nullable = false, unique = false)
    private float mpRegenPerLevel;

    @Column(nullable = false, unique = false)
    private float crit;

    @Column(nullable = false, unique = false)
    private float critPerLevel;

    @Column(nullable = false, unique = false)
    private float attackDamage;

    @Column(nullable = false, unique = false)
    private float attackDamagePerLevel;

    @Column(nullable = false, unique = false)
    private float attackSpeedOffset;

    @Column(nullable = false, unique = false)
    private float attackSpeedPerLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(GameVersion gameVersion) {
        this.gameVersion = gameVersion;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getHpPerLevel() {
        return hpPerLevel;
    }

    public void setHpPerLevel(float hpPerLevel) {
        this.hpPerLevel = hpPerLevel;
    }

    public float getMp() {
        return mp;
    }

    public void setMp(float mp) {
        this.mp = mp;
    }

    public float getMpPerLevel() {
        return mpPerLevel;
    }

    public void setMpPerLevel(float mpPerLevel) {
        this.mpPerLevel = mpPerLevel;
    }

    public float getMovSpeed() {
        return movSpeed;
    }

    public void setMovSpeed(float movSpeed) {
        this.movSpeed = movSpeed;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getArmorPerLevel() {
        return armorPerLevel;
    }

    public void setArmorPerLevel(float armorPerLevel) {
        this.armorPerLevel = armorPerLevel;
    }

    public float getSpellBlock() {
        return spellBlock;
    }

    public void setSpellBlock(float spellBlock) {
        this.spellBlock = spellBlock;
    }

    public float getSpellBlockPerLevel() {
        return spellBlockPerLevel;
    }

    public void setSpellBlockPerLevel(float spellBlockPerLevel) {
        this.spellBlockPerLevel = spellBlockPerLevel;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public float getHpRegen() {
        return hpRegen;
    }

    public void setHpRegen(float hpRegen) {
        this.hpRegen = hpRegen;
    }

    public float getHpRegenPerLevel() {
        return hpRegenPerLevel;
    }

    public void setHpRegenPerLevel(float hpRegenPerLevel) {
        this.hpRegenPerLevel = hpRegenPerLevel;
    }

    public float getMpRegen() {
        return mpRegen;
    }

    public void setMpRegen(float mpRegen) {
        this.mpRegen = mpRegen;
    }

    public float getMpRegenPerLevel() {
        return mpRegenPerLevel;
    }

    public void setMpRegenPerLevel(float mpRegenPerLevel) {
        this.mpRegenPerLevel = mpRegenPerLevel;
    }

    public float getCrit() {
        return crit;
    }

    public void setCrit(float crit) {
        this.crit = crit;
    }

    public float getCritPerLevel() {
        return critPerLevel;
    }

    public void setCritPerLevel(float critPerLevel) {
        this.critPerLevel = critPerLevel;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getAttackDamagePerLevel() {
        return attackDamagePerLevel;
    }

    public void setAttackDamagePerLevel(float attackDamagePerLevel) {
        this.attackDamagePerLevel = attackDamagePerLevel;
    }

    public float getAttackSpeedOffset() {
        return attackSpeedOffset;
    }

    public void setAttackSpeedOffset(float attackSpeedOffset) {
        this.attackSpeedOffset = attackSpeedOffset;
    }

    public float getAttackSpeedPerLevel() {
        return attackSpeedPerLevel;
    }

    public void setAttackSpeedPerLevel(float attackSpeedPerLevel) {
        this.attackSpeedPerLevel = attackSpeedPerLevel;
    }

    @Override
    public String toString() {
        return "ChampionStats{" +
                "id=" + id +
                ", champion=" + champion +
                ", version=" + gameVersion +
                ", hp=" + hp +
                ", hpPerLevel=" + hpPerLevel +
                ", mp=" + mp +
                ", mpPerLevel=" + mpPerLevel +
                ", movSpeed=" + movSpeed +
                ", armor=" + armor +
                ", armorPerLevel=" + armorPerLevel +
                ", spellBlock=" + spellBlock +
                ", spellBlockPerLevel=" + spellBlockPerLevel +
                ", attackRange=" + attackRange +
                ", hpRegen=" + hpRegen +
                ", hpRegenPerLevel=" + hpRegenPerLevel +
                ", mpRegen=" + mpRegen +
                ", mpRegenPerLevel=" + mpRegenPerLevel +
                ", crit=" + crit +
                ", critPerLevel=" + critPerLevel +
                ", attackDamage=" + attackDamage +
                ", attackDamagePerLevel=" + attackDamagePerLevel +
                ", attackSpeedOffset=" + attackSpeedOffset +
                ", attackSpeedPerLevel=" + attackSpeedPerLevel +
                '}';
    }
}
