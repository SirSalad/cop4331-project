package edu.ucf.cop4331project.common.storage;

public class User {

    private final String username;
    private final String password;
    private int coins;
    private boolean premium;

    public User(String username, String password, int coins, boolean premium) {
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.premium = premium;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
