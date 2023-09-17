import java.util.ArrayList;
import java.util.Scanner;
import java.security.SecureRandom;

public class Main {
    static boolean player_turn = true;
    static boolean poisoned = false;

    static boolean enemyPoisoned = false;

    static int p_dmg = 1;
    static int poison_time = 3;
    static int enemy_poison_time = 3; // setting the stage for the whole game

    public static void main(String[] args) {
        int enemy_damage = 0;

        ArrayList<String> playerOptions = new ArrayList<>(); // giving the player options to do whatever they want
        playerOptions.add("slay");
        playerOptions.add("slam");
        playerOptions.add("potion");
        playerOptions.add("defense");

        ArrayList<String> enemyOptions = new ArrayList<>(); // giving the enemy option to do whatever it wants
        enemyOptions.add("Scratch with sharp nails");
        enemyOptions.add("Bite gently");
        enemyOptions.add("Belch all over target");
        enemyOptions.add("Defense");

        Character character = new Character("Player", 30); // creating a player object with a couple params
        System.out.println("Welcome to my game, " + character.name + "");
        System.out.println("You have " + character.HP + " HP\n");

        Character enemy = new Character("Enemy", 10); // creating the enemy object
        System.out.println("Your first enemy is " + enemy.name + "");
        System.out.println(enemy.name + " has " + enemy.HP + " HP");

        while (enemy.HP > 0 && character.HP > 0) { // the main game loop
            if (player_turn) {

                if (playerOptions.isEmpty()) { // checking whether the player ran out of options or not
                    playerOptions.add("slay");
                    playerOptions.add("slam");
                    playerOptions.add("potion");
                    playerOptions.add("defense");
                }

                System.out.println("\nYour options are " + playerOptions); // displaying player options

                Scanner scanner = new Scanner(System.in);
                System.out.println();
                System.out.println("Make your decision");
                String choice = scanner.nextLine(); // asking the player for input and storing it in a variable

                if (poisoned) { // checking if the player is poisoned and calculating when the poison wears off
                    System.out.println("You're poisoned");
                    character.HP -= p_dmg;
                    poison_time--;
                } if (poison_time == 0) {
                    System.out.println("\nYour poison wore off");
                    poisoned = false;
                }

                if (enemyPoisoned) { // checking the same process above but for the enemy
                    enemy.HP -= p_dmg;
                    enemy_poison_time--;
                    System.out.println("Poison lasts for " + enemy_poison_time + " turns");
                } if (enemy_poison_time == 0) {
                    enemyPoisoned = false;
                }

                if (choice.equalsIgnoreCase("slay") && playerOptions.contains(choice)) {
                    if (enemy.hasShield) {
                        character.damage = enemy_damage; // making the character damage 0 if the enemy has a shield
                    } else {
                        character.damage = 5; // if enemy doesn't have a shield the damage stays the same
                    }

                    if (enemyPoisoned) {
                        character.Attack(enemy.name, character.damage + p_dmg); // same thing as above
                    } else {
                        character.Attack(enemy.name, character.damage); // but for enemy being poisoned so I can
                    } // display damage correctly

                    enemy.HP -= character.damage;
                    System.out.println(enemy.name + " HP is " + enemy.HP);
                    enemy.hasShield = false;
                } else {
                    while (!playerOptions.contains(choice)) { // checking for player entering the same and/or
                        System.out.println("Make your decision"); // unavailable options
                        choice = scanner.nextLine();
                    }
                }

                /*
                Readability
                */

                if (choice.equalsIgnoreCase("slam") && playerOptions.contains(choice)) {

                    if (enemy.hasShield) {
                        character.damage = enemy_damage; // pretty much all the code in the if statements for choice
                    } else { // is identical and doesn't need extra comments but definitely needs refactoring
                        character.damage = 2;
                    }

                    if (enemyPoisoned) {
                        character.Attack(enemy.name, character.damage + p_dmg);
                    } else {
                        character.Attack(enemy.name, character.damage);
                    }

                    enemy.HP -= character.damage;
                    System.out.println(enemy.name + " HP is " + enemy.HP);
                    enemy.hasShield = false;
                } else {
                    while (!playerOptions.contains(choice)) {
                        System.out.println("Make your decision");
                        choice = scanner.nextLine();
                    }
                }

                /*
                Readability
                */

                if (choice.equalsIgnoreCase("potion") && playerOptions.contains(choice)) {

                    if (enemy.hasShield) {
                        character.damage = enemy_damage;
                    } else {
                        character.damage = 1;
                    }

                    character.Attack(enemy.name, character.damage);
                    enemy.HP -= character.damage;
                    System.out.println(enemy.name + " HP is " + enemy.HP);
                    enemy.hasShield = false;
                    enemyPoisoned = true;
                } else {
                    while (!playerOptions.contains(choice)) {
                        System.out.println("Make your decision");
                        choice = scanner.nextLine();
                    }
                }

                /*
                Readability
                */

                if (choice.equalsIgnoreCase("defense") && playerOptions.contains(choice)) {
                    character.Defense();
                    enemy.hasShield = false;
                }

                playerOptions.remove(choice); // this removes the choice after it's made to spice up the gameplay

                endTurn(); // ends the turn so the enemy can have a go at this game

            } else {

                if (enemyOptions.isEmpty()) { // same code as with playerOptions but it's enemyOptions
                    enemyOptions.add("Scratch with sharp nails");
                    enemyOptions.add("Bite gently");
                    enemyOptions.add("Belch all over target");
                    enemyOptions.add("Defense");
                }

                System.out.println();
                System.out.println("Enemy options are " + enemyOptions + "\n");

                SecureRandom rand = new SecureRandom();
                int bot_choice = rand.nextInt(enemyOptions.toArray().length); // creating a variable with a random value

                System.out.println(enemyOptions.get(bot_choice)); // to simulate an AI playing against you

                switch(enemyOptions.get(bot_choice).toLowerCase()) { // AI options

                    case "scratch with sharp nails":

                        if (enemyOptions.contains(enemyOptions.get(bot_choice))) {
                            if (character.hasShield) {
                                enemy.damage = enemy_damage;
                            } else {
                                enemy.damage = 5;
                            }

                            if (poisoned) {
                                enemy.Attack(character.name,  enemy.damage + p_dmg);
                            } else {
                                enemy.Attack(character.name,  enemy.damage);
                            }

                            character.HP -=  enemy.damage;
                            System.out.println(character.name + " HP is " + character.HP);
                            character.hasShield = false;
                        }
                        break;

                    case "bite gently":

                        if (enemyOptions.contains(enemyOptions.get(bot_choice))) {
                            if (character.hasShield) {
                                enemy.damage = enemy_damage;
                            } else {
                                enemy.damage = 2;
                            }

                            if (poisoned) {
                                enemy.Attack(character.name,  enemy.damage + p_dmg);
                            } else {
                                enemy.Attack(character.name,  enemy.damage);
                            }

                            character.HP -= enemy.damage;
                            System.out.println(character.name + " HP is " + character.HP);
                            character.hasShield = false;
                        }
                        break;

                    case "belch all over target":

                        if (enemyOptions.contains(enemyOptions.get(bot_choice))) {
                            if (character.hasShield) {
                                enemy.damage = enemy_damage;
                            } else {
                                enemy.damage = 1;
                            }

                            System.out.println(enemy.name + " has poisoned you");
                            enemy.Attack(character.name, enemy.damage);
                            character.HP -= enemy.damage;
                            System.out.println(character.name + " HP is " + character.HP);
                            character.hasShield = false;
                            poisoned = true;
                        }
                        break;

                    case "defense":
                        enemy.Defense();
                        character.hasShield = false;
                        break;

                    default:
                        System.out.println("Unknown command");
                    }

                enemyOptions.remove(enemyOptions.get(bot_choice)); // remove options after the AI makes a decision

                player_turn = true; // giving the player the ability to have a go again
            }
        }

        if (character.HP <= 0) { // when the game ends the compiler has to determine who won
            System.out.println("\nGame over " + character.name);
        } else {
            System.out.println("\nYou have defeated " + enemy.name);
        }

    }
    public static void endTurn() { // a simple command with a self-explanatory name
        System.out.println("You've finished your turn");
        player_turn = false;
    }
}