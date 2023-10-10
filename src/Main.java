import java.util.Random;

public class Main {
    public static void main(String[] args) {
        while (!isGameOver()) {
            playRound();
        }
    }

    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {250, 260, 270, 500, 200, 250, 200, 150};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 0, 5, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Healer", "Golem", "Lucky", "Witcher", "Thor"}; // Добавлены новые типы персонажей
    public static int roundNumber = 0;

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        healHeroes();
        applyGolemAbility();
        applyLuckyAbility();
        applyWitcherAbility();
        applyThorAbility();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                int damage = bossDamage;
                if (heroesAttackType[i].equals("Golem")) {
                    damage = bossDamage / 5;
                }
                if (heroesAttackType[i].equals("Lucky")) {
                    Random random = new Random();
                    if (random.nextInt(2) == 0) {
                        damage = 0;
                    }
                }
                if (heroesAttackType[i].equals("Witcher")) {
                    continue;
                }
                if (heroesAttackType[i].equals("Thor")) {
                    Random random = new Random();
                    if (random.nextInt(10) == 0) {
                        bossDefence = "Stunned";
                        System.out.println("Thor stunned the boss!");
                    }
                }
                if (heroesHealth[i] - damage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= damage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void healHeroes() {
        for (int i = 0; i < heroesHealth.length - 4; i++) {
            if (heroesAttackType[i].equals("Healer") && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                heroesHealth[i] += 70;
                System.out.println("Healer healed " + heroesAttackType[i] + " for 70 HP.");
            }
        }
    }

    public static void applyGolemAbility() {
        if (heroesAttackType[4].equals("Golem") && heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length - 4; i++) {
                if (i != 4 && heroesHealth[i] > 0) {
                    int damage = bossDamage / 5;
                    if (heroesHealth[i] - damage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] -= damage;
                    }
                }
            }
        }
    }

    public static void applyLuckyAbility() {
        if (heroesAttackType[5].equals("Lucky") && heroesHealth[5] > 0) {
            Random random = new Random();
            if (random.nextInt(2) == 0) {
                bossDefence = "Lucky";
                System.out.println("Lucky evaded the boss is attack!");
            }
        }
    }

    public static void applyWitcherAbility() {
        if (heroesAttackType[6].equals("Witcher") && heroesHealth[6] > 0) {
            Random random = new Random();
            if (random.nextInt(5) == 0) {
                for (int i = 0; i < heroesHealth.length - 4; i++) {
                    if (i != 6 && heroesHealth[i] == 0) {
                        heroesHealth[i] = 30;
                        heroesHealth[6] = 0;
                        System.out.println("Witcher revived " + heroesAttackType[i] + " using his own life.");
                    }
                }
            }
        }
    }

    public static void applyThorAbility() {
        if (heroesAttackType[7].equals("Thor") && heroesHealth[7] > 0) {
            Random random = new Random();
            if (random.nextInt(10) == 0) {
                bossDefence = "Stunned";
                System.out.println("Thor stunned the boss!");
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));

        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length - 4; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}
// надеюсь правильно, с доп заданием вообще запуталась 0_0