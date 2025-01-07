package edu.uob;

import java.util.HashMap;
import java.util.Objects;

public class Player extends GameEntity{
    private String location;
    private HashMap<String, Artefact> inv;
    private int health;
    public Player(String name, String description, String location) {
        super(name, description);
        this.location = location;
        this.inv = new HashMap<>();
        this.health = 3;
    }

    public String getLocation() {
        return location;
    }

    public HashMap<String, Artefact> getInv() {
        return inv;
    }

    public boolean containsInv(String object){
        if(Objects.equals(object, "health")){
            return true;
        }else{
            return inv.containsKey(object);
        }
    }

    public void consumedInv(String consume){
        if(Objects.equals(consume, "health")){
            health -= 1;
        }
        else{
            getInv().remove(consume);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInvStr(){
        StringBuilder invs = new StringBuilder(getName() + "'s inventory: \n");
        for (String s: getInv().keySet()) {
            invs.append(s).append(" ").append("(").append(inv.get(s).getDescription()).append(")\n");
        }
        return invs.toString();
    }

    public String getHealthStr(){
        return getName() + "'s health: \n" + health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setInv(HashMap<String, Artefact> inv) {
        this.inv = inv;
    }
}
