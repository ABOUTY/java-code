package edu.uob;

import java.util.HashMap;
import java.util.Map;

public class Location extends GameEntity{
    private HashMap<String, Artefact> artefactHashMap;
    private HashMap<String, Furniture> furnitureHashMap;
    private HashMap<String, Character> characterHashMap;
    private HashMap<String, Location> toNameHashMap;

    public Location(String name, String description) {
        super(name, description);
        artefactHashMap = new HashMap<>();
        furnitureHashMap = new HashMap<>();
        characterHashMap = new HashMap<>();
        toNameHashMap = new HashMap<>();
    }

    public HashMap<String, Artefact> getArtefactHashMap() {
        return artefactHashMap;
    }

    public void setArtefactHashMap(HashMap<String, Artefact> artefactHashMap) {
        this.artefactHashMap = artefactHashMap;
    }

    public HashMap<String, Furniture> getFurnitureHashMap() {
        return furnitureHashMap;
    }

    public void setFurnitureHashMap(HashMap<String, Furniture> furnitureHashMap) {
        this.furnitureHashMap = furnitureHashMap;
    }

    public HashMap<String, Character> getCharacterHashMap() {
        return characterHashMap;
    }

    public void setCharacterHashMap(HashMap<String, Character> characterHashMap) {
        this.characterHashMap = characterHashMap;
    }

    public HashMap<String, Location> getToNameHashMap() {
        return toNameHashMap;
    }

    public void setToNameHashMap(HashMap<String, Location> toNameHashMap) {
        this.toNameHashMap = toNameHashMap;
    }

    public void addToNameHashMap(String name, Location location) {
        this.toNameHashMap.put(name, location);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Location name: ").append(getName()).append("\n");
        s.append("Location description: ").append(getDescription()).append("\n");
        if(!artefactHashMap.isEmpty()){
            s.append("Artefact: \n");
            for (Map.Entry<String, Artefact> entry : artefactHashMap.entrySet()) {
                String key = entry.getKey();
                Artefact value = entry.getValue();
                s.append(key).append(" (").append(value.getDescription()).append(")\n");
            }
        }
        if(!furnitureHashMap.isEmpty()){
            s.append("Furniture: \n");
            for (Map.Entry<String, Furniture> entry : furnitureHashMap.entrySet()) {
                String key = entry.getKey();
                Furniture value = entry.getValue();
                s.append(key).append(" (").append(value.getDescription()).append(")\n");
            }
        }
        if(!characterHashMap.isEmpty()){
            s.append("Character: ").append("\n");
            for (Map.Entry<String, Character> entry : characterHashMap.entrySet()) {
                String key = entry.getKey();
                Character value = entry.getValue();
                s.append(key).append(" (").append(value.getDescription()).append(")\n");
            }
        }
        if(!toNameHashMap.isEmpty()){
            s.append("ToName: ").append(toNameHashMap.keySet()).append("\n");
        }
        return s.toString();
    }
}
