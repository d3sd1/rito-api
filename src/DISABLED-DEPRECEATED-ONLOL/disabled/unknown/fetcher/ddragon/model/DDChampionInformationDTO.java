package status.disabled.unknown.fetcher.ddragon.model;

public class DDChampionInformationDTO {
    private Integer attack = 0;
    private Integer defense = 0;
    private Integer magic = 0;
    private Integer difficulty = 0;

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "SampleChampionInformation{" +
                "attack=" + attack +
                ", defense=" + defense +
                ", magic=" + magic +
                ", difficulty=" + difficulty +
                '}';
    }
}
