public class Character {
    int HP;
    String name;
    int damage;

    boolean hasShield;

    Character() {}
    Character(String name, int HP) {
        this.name = name;
        this.HP = HP;
    }
    public void Attack(String enemy_name, int dmg) {
        System.out.println(this.name + " has attacked " + enemy_name);
        System.out.println(enemy_name + " took " + dmg + " damage");
    }
    public void Defense() {
        System.out.println(this.name + " has used a shield!");
        hasShield = true;
    }
}
