package edu.uob;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.*;

public final class GameServer {
    private static HashMap<String, Location> locHashMap;
    private static HashSet<String> entityNames;
    private static HashMap<String, HashSet<GameAction>> actHashMap;
    private static HashMap<String, Player> playerHashMap;

    private static final char END_OF_TRANSMISSION = 4;
    private String firstLocation = null;

    public static void main(String[] args) throws IOException {
        File entitiesFile = Paths.get("config" + File.separator + "extended-entities.dot").toAbsolutePath().toFile();
        File actionsFile = Paths.get("config" + File.separator + "extended-actions.xml").toAbsolutePath().toFile();
        GameServer server = new GameServer(entitiesFile, actionsFile);
        server.blockingListenOn(8888);
    }

    /**
    * Do not change the following method signature, or we won't be able to mark your submission
    * Instanciates a new server instance, specifying a game with some configuration files
    *
    * @param entitiesFile The game configuration file containing all game entities to use in your game
    * @param actionsFile The game configuration file containing all game actions to use in your game
    */
    public GameServer(File entitiesFile, File actionsFile) {
        // player hashmap
        playerHashMap = new HashMap<>();
        entityNames = new HashSet<>();
        // Load entities and actions from files
        loadEntities(entitiesFile);
        loadActions(actionsFile);
    }

    // load entities from file
    private void loadEntities(File entitiesFile) {
        HashMap<String, Location> locationHashMap = new HashMap<>();
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entitiesFile);
            parser.parse(reader);
            Graph wholeDocument = parser.getGraphs().get(0);
            ArrayList<Graph> sections = wholeDocument.getSubgraphs();
            ArrayList<Graph> locations = sections.get(0).getSubgraphs();
            for (Graph cluster : locations) {
                Node clusterNode = cluster.getNodes(true).get(0);
                String locationName = clusterNode.getId().getId();
                if(firstLocation == null){
                    firstLocation = locationName;
                }
                Location location = new Location(locationName, clusterNode.getAttribute("description"));
                ArrayList<Graph> entityList = cluster.getSubgraphs();
                for (Graph entity : entityList) {
                    String entityName = entity.getId().getId();
                    EntitySet entitySet = EntitySet.getByEntityName(entityName);

                    if (entitySet == EntitySet.CHARACTER) {
                        HashMap<String, Character> characterHashMap = new HashMap<>();
                        for (Node node : entity.getNodes(true)) {
                            String name = node.getId().getId();
                            String des = node.getAttribute("description");
                            entityNames.add(name);
                            characterHashMap.put(name, new Character(name, des));
                        }
                        location.setCharacterHashMap(characterHashMap);
                    } else if (entitySet == EntitySet.ARTEFACT) {
                        HashMap<String, Artefact> artefactHashMap = new HashMap<>();
                        for (Node node : entity.getNodes(true)) {
                            String name = node.getId().getId();
                            String des = node.getAttribute("description");
                            entityNames.add(name);
                            artefactHashMap.put(name, new Artefact(name, des));
                        }
                        location.setArtefactHashMap(artefactHashMap);
                    } else if (entitySet == EntitySet.FURNITURE) {
                        HashMap<String, Furniture> furnitureHashMap = new HashMap<>();
                        for (Node node : entity.getNodes(true)) {
                            String name = node.getId().getId();
                            String des = node.getAttribute("description");
                            entityNames.add(name);
                            furnitureHashMap.put(name, new Furniture(name, des));
                        }
                        location.setFurnitureHashMap(furnitureHashMap);
                    }
                }
                locationHashMap.put(locationName, location);
            }

            // add path
            ArrayList<Edge> paths = sections.get(1).getEdges();
            for(Edge path: paths){
                Node fromLocation = path.getSource().getNode();
                String fromName = fromLocation.getId().getId();
                Node toLocation = path.getTarget().getNode();
                String toName = toLocation.getId().getId();
                locationHashMap.get(fromName).addToNameHashMap(toName, locationHashMap.get(toName));
            }

            locHashMap = locationHashMap;

        } catch (FileNotFoundException | ParseException fnfe) {
            System.out.println("Exception");
        }

    }

    // load actions from file
    private void loadActions(File actionsFile) {
        HashMap<String, HashSet<GameAction>> actionHashMap = new HashMap<>();
        // logic to read and parse actions from the file
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(actionsFile);
            Element root = document.getDocumentElement();
            NodeList actions = root.getChildNodes();
            // Get the first action (only the odd items are actually actions - 1, 3, 5 etc.)
            for (int i = 1; i < actions.getLength(); ) {
                Element action = (Element) actions.item(i);

                NodeList triggerList = action.getElementsByTagName("triggers");
                String[] trigger = triggerList.item(0).getTextContent().replaceAll(" ", "").trim().split("\n");
                GameAction gameAction = getGameAction(trigger, action);
                for (String s: trigger) {
                    if(actionHashMap.get(s) == null){
                        HashSet<GameAction> gameActions = new HashSet<>();
                        gameActions.add(gameAction);
                        actionHashMap.put(s, gameActions);
                    }else{
                        HashSet<GameAction> gameActions = actionHashMap.get(s);
                        gameActions.add(gameAction);
                    }

                }
                i += 2;

            }


        } catch(ParserConfigurationException | SAXException | IOException pce) {
            System.out.println("exception");
        }

        actHashMap = actionHashMap;
    }

    private static GameAction getGameAction(String[] trigger, Element action) {
        HashSet<String> triggerSet = new HashSet<>(Arrays.asList(trigger));

        NodeList subjectsList = action.getElementsByTagName("subjects");
        String[] subjects = subjectsList.item(0).getTextContent().replaceAll(" ", "").trim().split("\n");
        HashSet<String> subjectsSet = new HashSet<>(Arrays.asList(subjects));


        NodeList consumedList = action.getElementsByTagName("consumed");
        String[] consumed = consumedList.item(0).getTextContent().replaceAll(" ", "").trim().split("\n");
        HashSet<String> consumedSet = new HashSet<>(Arrays.asList(consumed));

        NodeList producedList = action.getElementsByTagName("produced");
        String[] produced = producedList.item(0).getTextContent().replaceAll(" ", "").trim().split("\n");
        HashSet<String> producedSet = new HashSet<>(Arrays.asList(produced));

        NodeList narrationList = action.getElementsByTagName("narration");
        String narration = narrationList.item(0).getTextContent();

        return new GameAction(triggerSet, subjectsSet, consumedSet, producedSet, narration);
    }


    /**
    * Do not change the following method signature, or we won't be able to mark your submission
    * This method handles all incoming game commands and carries out the corresponding actions.</p>
    *
    * @param command The incoming command to be processed
    */
    public String handleCommand(String command) {
        // Split the command into its components
        String[] parts = command.trim().split(":");

        String userName = parts[0].toLowerCase();

        if(!playerHashMap.containsKey(userName)){
            Player player = new Player(userName, "player", firstLocation);
            playerHashMap.put(userName, player);
        }

        // Extract the action and target
        String[] at = new String[2];

        String su = getActionAndTarget(parts[1].trim(), at);

        if(su != null){
            return su;
        }

        String action = at[0];
        String target = at[1];
        // Perform the action based on the command
        Player player = playerHashMap.get(userName);
        return switch (action) {
            case "inventory", "inv" -> listInventory(player);
            case "data" -> showHealth(player);
            case "get", "pick up" -> pickupArtifact(player, target);
            case "drop" -> dropArtifact(player, target);
            case "goto", "move to" -> movePlayer(player, target);
            case "look" -> lookAround(player);
            case "help" -> help();
            default -> otherAction(player, action, target);
        };
    }

    private String getActionAndTarget(String p, String [] at) {
        p = p.toLowerCase();
        HashSet<String> action = new HashSet<>();
        HashSet<String> target = new HashSet<>();
        for (String ac: actHashMap.keySet()) {
            if(p.contains(ac)) {
                action.add(ac);
            }
        }
        String[] ks = {"inv", "inventory", "data", "get", "pick up", "drop", "goto", "move to", "look", "help"};
        for(String k: ks){
            if(p.contains(k)) {
                action.add(k);
            }
        }
        for (String ta: entityNames) {
            if (p.contains(ta)) {
                target.add(ta);
            }
        }
        for (String ta: locHashMap.keySet()) {
            if (p.contains(ta)) {
                target.add(ta);
            }
        }
        removeContainedStrings(action);
        if(action.size() != 1){
            return "Wrong, you should input one action.";
        }else{
            at[0] = action.toString().replace("[", "").replace("]", "");
        }
        if(target.isEmpty()){
            at[1] = "";
        }else{
            at[1] = (String) target.toArray()[0];
            at[1] = at[1].replace("[", "").replace("]", "");
            removeContainedStrings(target);
            if(target.size() > 1){
                if(actHashMap.containsKey(at[0])){
                    for(GameAction ga: actHashMap.get(at[0])){
                        if(ga.getSubjectsList().containsAll(target)){
                            return null;
                        }
                    }
                }
                return "Wrong, input things can not action.";
            }
        }

        return null;
    }

    public static void removeContainedStrings(HashSet<String> set) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String current = iterator.next();
            for (String str : set) {
                if (!str.equals(current) && str.contains(current)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    private String showHealth(Player player) {
        return player.getHealthStr();
    }

    private String help() {
        return """
                Input Action or Action + target
                Action: inv, data, get, drop, goto, look
                """ + actHashMap.keySet();
    }

    private String otherAction(Player player, String action, String target) {
        String adds = "";
        StringBuilder con = new StringBuilder(player.getName() + " " + action + " " + target + " \n");
        Location location = locHashMap.get(player.getLocation());
        if(!player.containsInv(target) && !location.getArtefactHashMap().containsKey(target) && !location.getFurnitureHashMap().containsKey(target) && !location.getCharacterHashMap().containsKey(target)) {
            return con + "Failed! " + target + " is not within this scene.";
        }
        HashSet<GameAction>choicedHashSet;
        if(actHashMap.containsKey(action)) {
            choicedHashSet = actHashMap.get(action);
        }else{
            return con + "Failed! " + action + " is not right.\n" + help();
        }

        GameAction gameAction = null;
        for (GameAction ga: choicedHashSet) {
            HashSet<String> subjectsList = ga.getSubjectsList();
            if(subjectsList.contains(target)) {
                gameAction = ga;
                break;
            }
        }

        if(gameAction == null){
            return con + "Failed! " + action + " and " + target + " don't match.\n";
        }

        HashSet<String> subjectList = gameAction.getSubjectsList();
        for(String sub: subjectList) {
            if(!player.containsInv(sub) && !location.getCharacterHashMap().containsKey(sub) && !location.getFurnitureHashMap().containsKey(sub) && !location.getArtefactHashMap().containsKey(sub)) {
                return con + "Failed! Missing object, " + target + " can not " + action + ".\n";
            }
        }

        HashSet<String> consumedList = gameAction.getConsumedList();

            for (String consume : consumedList) {
                location.getCharacterHashMap().remove(consume);
                player.consumedInv(consume);
                if(player.getHealth() <= 0){
                    adds = " (you died and lost all of your items, you must return to the start of the game).\n";
                    for(Artefact artefact: player.getInv().values()){
                        location.getArtefactHashMap().put(artefact.getName(), artefact);
                    }
                    player.setInv(new HashMap<>());
                    player.setHealth(3);
                    player.setLocation(firstLocation);
                }

                location.getFurnitureHashMap().remove(consume);
            }

            HashSet<String> producedList = gameAction.getProducedList();
            Location storeroom = locHashMap.get("storeroom");
            for (String product : producedList) {
                if(Objects.equals(product, "health")){
                    player.setHealth(player.getHealth() + 1);
                } else if(locHashMap.containsKey(product)){
                    location.addToNameHashMap(product, locHashMap.get(product));
                } else if(storeroom.getFurnitureHashMap().containsKey(product)){
                    location.getFurnitureHashMap().put(product, storeroom.getFurnitureHashMap().get(product));
                } else if(storeroom.getCharacterHashMap().containsKey(product)){
                    location.getCharacterHashMap().put(product, storeroom.getCharacterHashMap().get(product));
                } else if(storeroom.getArtefactHashMap().containsKey(product)){
                    location.getArtefactHashMap().put(product, storeroom.getArtefactHashMap().get(product));
                }
            }
        return player.getName() + ": " + gameAction.getNarration() + adds;
    }

    // list inventory
    private String listInventory(Player player) {
        return player.getInvStr();
    }

    // pick up an artifact
    private String pickupArtifact(Player player, String artifact) {
        Location nowLoc = locHashMap.get(player.getLocation());
        if (nowLoc.getArtefactHashMap().containsKey(artifact)) {
            player.getInv().put(artifact, nowLoc.getArtefactHashMap().get(artifact));
            nowLoc.getArtefactHashMap().remove(artifact);
            return player.getName() + ": pick up " + artifact;
        } else {
            return "Wrong, this place exist not " + artifact + ".";
        }

    }

    // drop an artifact
    private String dropArtifact(Player player, String artifact) {
        Artefact ar = player.getInv().remove(artifact);
        if (ar != null) {
            Location location = locHashMap.get(player.getLocation());
            location.getArtefactHashMap().put(artifact, ar);
            return player.getName() + ": drop " + artifact;
        } else {
            return "Wrong, player has not " + artifact + ".";
        }
    }

    // move the player to a new location
    private String movePlayer(Player player, String location) {
        Location nowLoc = locHashMap.get(player.getLocation());
        if (nowLoc.getToNameHashMap().containsKey(location)) {
            player.setLocation(location);
            return player.getName() + ": move to " + location;
        } else {
            return location + " can not be reached.";
        }

    }

    // look around the current location
    private String lookAround(Player player) {
        StringBuilder others = new StringBuilder();
        for (Player p: playerHashMap.values()) {
             if(!Objects.equals(p.getName(), player.getName())){
                 others.append("Player: ").append(p.getName()).append(" Location: ").append(p.getLocation()).append("\n");
             }
        }
        Location nowLoc = locHashMap.get(player.getLocation());
        return others + nowLoc.toString();
    }

    /**
    * Do not change the following method signature, or we won't be able to mark your submission
    * Starts a *blocking* socket server listening for new connections.
    *
    * @param portNumber The port to listen on.
    * @throws IOException If any IO related operation fails.
    */
    public void blockingListenOn(int portNumber) throws IOException {
        try (ServerSocket s = new ServerSocket(portNumber)) {
            System.out.println("Server listening on port " + portNumber);
            while (!Thread.interrupted()) {
                try {
                    blockingHandleConnection(s);
                } catch (IOException e) {
                    System.out.println("Connection closed");
                }
            }
        }
    }

    /**
    * Do not change the following method signature, or we won't be able to mark your submission
    * Handles an incoming connection from the socket server.
    *
    * @param serverSocket The client socket to read/write from.
    * @throws IOException If any IO related operation fails.
    */
    private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
        try (Socket s = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
            System.out.println("Connection established");
            String incomingCommand = reader.readLine();
            if(incomingCommand != null) {
                System.out.println("Received message from " + incomingCommand);
                String result = handleCommand(incomingCommand);
                writer.write(result);
                writer.write("\n" + END_OF_TRANSMISSION + "\n");
                writer.flush();
            }
        }
    }
}
