## STAG assignment
### Task 1: Introduction


This workbook introduces the final assignment that is worth the remaining 60% of the assessment for this unit. The focus of this assignment is the construction of a general-purpose socket-server game-engine for text adventure games. 

A typical game of this genre is illustrated in the screenshot shown below. The following sections of this workbook explain the construction of this Simple Text Adventure Game (STAG) in more detail and provide a breakdown of the features you are required to implement.

<a href="http://www.web-adventures.org/cgi-bin/webfrotz?s=ZorkDungeon&n=4185" target="_blank">
<img src="resources/zork.jpg">
</a>



# 
### Task 2: Game Engine
 <a href='02%20Game%20Engine/video/adventure.mp4' target='_blank'> ![](../../resources/icons/video.png) </a>

Your aim is to build a game engine server that communicates with one or more game clients.
Your server will listen for incoming connections from clients (in a similar way to the DB exercise).
When a connection has been made, your server will receive an incoming command, process the actions
that have been requested, make changes to any game state that is required and then finally send back a
suitable response to the client. View the video linked to above for a demonstration of a typical example
of communication from the perspective of the client (note that there is no audio track in this video).

The basic networking operation is provided for you in the 
<a href="resources/cw-stag/" target="_blank">maven template</a> for this project.
As with the DB exercise, you do not need to write the client since this is provided for you.
It is again essential that you do not implement any required features in the client.
The interactive client will be replaced by an automated test script during the marking process.

To execute the server from the command line, type `mvnw exec:java@server`.
To execute the client that will connect to the server, type `mvnw exec:java@client -Dexec.args="simon"`
note that the `-Dexec.args` flag allows us to pass an argument into the client
(in this case the username of the current player).
This username is then passed by the client to the server at the start of each command
(so the server knows which player the command came from).
The client is provided for manual exploration - most of the time you will run your code
using a JUnit test script using `mvnw test`.

The aim of this assignment is to build a versatile game engine that it is able to play _any_ text adventure game
(providing that it conforms to certain rules). To support this versatility, two configuration files:
**entities** and **actions** are used to describe the various "things" that are present in the game,
their structural layout and dynamic behaviours.
These two configuration files are passed into the game server when it is instantiated like so:

``` java
public GameServer(File entitiesFile, File actionsFile)
```

The server will load the game scenario in from the two configuration files, thus allowing a range of different games to be played.
During the marking process, we will use some custom game files in order to explore the full range of functionality in your code.
It is therefore essential that your game engine allows these files to be passed in and then reads and interprets their content
(otherwise we won't be able to test your code).

As with the Database assignment, you should build a robust and resilient server which will be able to keep running,
no matter what commands the user throws at it. Note that game state must NOT be made persistent between server invocations.
When the server starts up, the game state should be loaded from the _original_ config files
(do NOT update these with changes as the game progresses). The server should NOT remember the state of any previous games.

  


# 
### Task 3: Basic Commands


In order to communicate with the server, we need an agreed language
(otherwise the user won't know what to type to interact with the game !).
There are a number of standard "built-in" gameplay commands that your game engine should respond to:

- `inventory` (or `inv` for short) lists all of the artefacts currently being carried by the player
- `get` picks up a specified artefact from the current location and adds it into player's inventory
- `drop` puts down an artefact from player's inventory and places it into the current location
- `goto` moves the player from the current location to the specified location (if there is a path to that location)
- `look` prints names and descriptions of entities in the current location and lists paths to other locations

It is essential that you conform to this standard set of commands, otherwise it won't be possible to play your game
(and your game engine will fail some of the marking tests !). To illustrate these commands in use (and to demonstrate
the expected responses) some examples are included in a test script found in the maven project's test folder.

In addition to these standard "built-in" commands, it is possible to customise a game with a number of additional **actions**.
These will be introduced in more detail in a later section of this workbook.

The skeleton 
<a href="resources/cw-stag/src/main/java/edu/uob/GameServer.java" target="_blank">GameServer</a> class
(provided as part of the project template) includes all the code required to deal with network communication.
All you will need to do is to complete the command handler that interprets the incoming command string,
makes changes to the game state and then return a suitable text response to send back to the client.





**Hints & Tips:**  
Note that built-in commands are reserved words and therefore cannot be used as names for any other elements of the command language.  





为了与服务器通信，我们需要一种商定的语言
（否则用户将不知道该键入什么来与游戏进行交互！）。
游戏引擎应该响应许多标准的“内置”游戏命令：
-“inventory”（简称“inv”）列出了玩家目前携带的所有文物
-“get”从当前位置拾取指定的文物并将其添加到玩家的库存中
-“drop”从玩家的库存中放下一件文物，并将其放置在当前位置
-“转到”将玩家从当前位置移动到指定位置（如果有指向该位置的路径）
-“look”打印当前位置中实体的名称和描述，并列出到其他位置的路径
你必须遵守这套标准的命令，否则你的游戏就无法进行
（您的游戏引擎将无法通过某些标记测试！）。说明这些使用中的命令（并演示
预期的响应）一些示例包含在maven项目的测试文件夹中的测试脚本中。
除了这些标准的“内置”命令之外，还可以通过一些额外的**动作**来定制游戏。
这些将在本工作簿的后面部分进行更详细的介绍。
骨架
<a href=“resources/cw stag/src/main/java/edu/uob/GameServer.java”target=“_blank”>GameServer</a>类
（作为项目模板的一部分提供）包括处理网络通信所需的所有代码。
您所需要做的就是完成解释传入命令字符串的命令处理程序，
对游戏状态进行更改，然后返回合适的文本响应以发送回客户端。请注意，内置命令是保留字，因此不能用作命令语言的任何其他元素的名称。


# 
### Task 4: Game Entities


Game **entities** are a fundamental building block of any text adventure game.
Entities represent a range of different "things" that exist within a game.
The different types of entity represented in the game are as follows:

- Locations: Rooms, environments or places that exist within the game
- Artefacts: Physical things within the game that can be collected by the player
- Furniture: Physical things that are an integral part of a location
(these can NOT be collected by the player)
- Characters: The various creatures or people involved in game
- Players: A special kind of character that represents the user in the game

It is worth noting that **locations** are complex constructs in their own right and
have various different attributes including:
- Paths to other locations (note: it is possible for paths to be one-way !)
- Characters that are currently at a location
- Artefacts that are currently present in a location
- Furniture that belongs within a location

Entities are defined in one of the game configuration files using a language called "DOT".
This language can be used to express the structure of a graph (which is basically what a text adventure game fundamentally is !)

The big benefit of using DOT files to store game entities is that we can render them graphically
using visualisation tools such as <a href="https://dreampuf.github.io/GraphvizOnline/" target="_blank">this</a>.
These tools allow us to actually SEE the structure of the game configuration
(for example see the graphical representation of an entity file shown below).
As you can see, each location is represented by a rectangular box containing a number of different entities
(each type of entity being represented by a different shape). The paths between locations are
presented in the form of directed arrows.  


![](04%20Game%20Entities/images/basic-entities.png)

# 

游戏**实体**是任何文本冒险游戏的基本构建块。
实体表示游戏中存在的一系列不同的“事物”。
游戏中表示的不同类型的实体如下：
-位置：游戏中存在的房间、环境或场所
-艺术品：玩家可以收集的游戏中的实物
-家具：作为一个地点不可分割的一部分的实物
（玩家无法收集）
-角色：参与游戏的各种生物或人
-玩家：一种特殊的角色，代表游戏中的用户
值得注意的是，**位置**本身就是复杂的结构，并且
具有各种不同的属性，包括：
-到其他位置的路径（注意：路径可能是单向的！）
-当前位于某个位置的字符
-当前存在于某个位置的艺术品
-属于某个位置的家具
实体是使用一种名为“DOT”的语言在其中一个游戏配置文件中定义的。
这种语言可以用来表达图形的结构（这基本上就是文本冒险游戏的本质！）
使用DOT文件存储游戏实体的最大好处是，我们可以图形化地渲染它们
使用可视化工具，如<a href=“https://dreampuf.github.io/GraphvizOnline/“target=”_blank“>这</a>。
这些工具使我们能够实际看到游戏配置的结构
（例如，请参见下面显示的实体文件的图形表示）。
如您所见，每个位置都由一个矩形框表示，其中包含许多不同的实体
（每种类型的实体由不同的形状表示）。位置之间的路径为
以定向箭头的形式呈现。



### Task 5: Loading Entities


We have provided some example entity files for you to use in your project.
Firstly there is a <a href="resources/cw-stag/config/basic-entities.dot" target="_blank">basic entities file</a>
to help get you started in constructing your game engine.
We have also provided an <a href="resources/cw-stag/config/extended-entities.dot" target="_blank">extended entities file</a>
that can be used for more extensive testing during the later stages of your work.

You already have much experience of writing parsers from previous exercises (both on this unit and others).
We don't really want to cover old ground, so for this assignment you will NOT be required to build your own parser.
Instead, you are able to use existing parsing libraries for reading in the configuration files.
There is considerable educational value in learning to use existing libraries and frameworks in this way.

With this in mind, you should use the
<a href="http://www.alexander-merz.com/graphviz/doc/com/alexmerz/graphviz/Parser.html" target="_blank">JPGD parser</a>
in order to parse the entity DOT files. Use this library to extract a set of 
<a href="http://www.alexander-merz.com/graphviz/doc/com/alexmerz/graphviz/objects/package-summary.html" target="_blank">GraphViz Objects</a>
and then store them in a suitable data structure so that your server can access them.
We have provided you with an abstract `GameEntity` class in the maven project that you should use to represent entities within your game.
You should write a number of concrete subclasses that inherit from this abstract class to represent specific types of entity.

All entities will need at least a name and a description, some may need additional attributes as well.
To make things easier, entity names cannot contain spaces (the DOT parser doesn't like it if they do !).
It is also worth mentioning that entity names defined in the configuration files will be unique.
Additionally, there should only be a single instance of each entity within the game.
You won't have to deal with two things called `door` (although your might see a `trapdoor` and a `frontdoor`).
As such, you can safely use entity names as unique identifiers.

Every game has a "special" location that is the starting point for an adventure.
The start location can be called anything we like, however it will _always_ be the **first** location
that appears in the "entities" file.

There is another special location called `storeroom` that can be found in the entities file.
This location does not appear in the game world (there will be no paths to/from it).
The `storeroom` is a container for all of the entities that have no initial location in the game.
Everything needs to exist somewhere in the game structure (so that they can be defined in the entities file).
These entities will not enter the game until an action places them into another location within the game.



**Hints & Tips:**  
To ensure that the basic entities file is in the correct location in the project folder and that it can be accessed by the DOT parser,
we have provided a <a href="resources/cw-stag/src/test/java/edu/uob/EntitiesFileTests.java" target="_blank">JUnit test class</a> for you to use.
Not only will these tests verify the content of the basic entities file, but they also provides a practical illustration of the use of the JPGD library for parsing DOT files.

A `.jar` file containing the JPGD library can be found in the `libs` folder of the maven template.
This _should_ already be part of the project dependencies, but it is useful to know where the library resides
(in case you have add it manually to your IntelliJ project).

Note that the `locations` subgraph will _always_ be **first** in the entities file and the `paths` subgraph will _always_
appear **after** the locations (the DOT parser doesn't like it if we switch this ordering !)

You may assume that all entity files used during marking are in a valid format (our aim isn't to test the robustness of the parsing libraries).



我们提供了一些示例实体文件供您在项目中使用。
首先是一个<a href=“resources/cw stag/config/basic entities.dot”target=“_blank”>基本实体文件</a>
帮助您开始构建游戏引擎。
我们还提供了<a href=“resources/cw stag/config/extended entities.dot”target=“_blank”>扩展实体文件</a>
可以在工作的后期阶段用于更广泛的测试。
您已经从以前的练习（包括本单元和其他单元）中获得了编写解析器的丰富经验。
我们真的不想覆盖旧的领域，所以对于这项任务，您将不需要构建自己的解析器。
相反，您可以使用现有的解析库来读取配置文件。
以这种方式学习使用现有的图书馆和框架具有相当大的教育价值。
考虑到这一点，您应该使用
<a href=“http://www.alexander-merz.com/graphviz/doc/com/alexmerz/graphviz/Parser.html“target=”_blank“>JPGD解析器</a>
以便解析实体DOT文件。使用此库提取一组
<a href=“http://www.alexander-merz.com/graphviz/doc/com/alexmerz/graphviz/objects/package-summary.html“target=”_blank“>GraphViz对象</a>
然后将它们存储在合适的数据结构中，以便您的服务器可以访问它们。
我们在maven项目中为您提供了一个抽象的“GameEntity”类，您应该使用它来表示游戏中的实体。
您应该编写许多具体的子类，这些子类继承自这个抽象类，以表示特定类型的实体。
所有实体都至少需要一个名称和描述，有些实体可能还需要额外的属性。
为了简化操作，实体名称不能包含空格（DOT解析器不喜欢这样做！）。
还值得一提的是，配置文件中定义的实体名称将是唯一的。
此外，游戏中每个实体应该只有一个实例。
你不必处理两件叫做“门”的事情（尽管你可能会看到“活板门”和“前门”）。
因此，您可以安全地使用实体名称作为唯一标识符。
每个游戏都有一个“特殊”的地点，这是冒险的起点。
起始位置可以称为我们喜欢的任何位置，但它始终是**第一个**位置
显示在“实体”文件中。
在实体文件中可以找到另一个名为“储藏室”的特殊位置。
此位置不会出现在游戏世界中（不会有通往/离开它的路径）。
“储藏室”是所有在游戏中没有初始位置的实体的容器。
所有东西都需要存在于游戏结构中的某个地方（这样它们就可以在实体文件中定义）。
这些实体将不会进入游戏，直到一个动作将它们放置到游戏中的另一个位置。
**提示和提示：**
为了确保基本实体文件在项目文件夹中的正确位置，并且DOT解析器可以访问它，
我们提供了<a href=“resources/cw stag/src/test/java/edu/uob/EntitiesFileTests.java”target=“_blank”>JUnit测试类</a>供您使用。
这些测试不仅将验证基本实体文件的内容，而且还提供了使用JPGD库解析DOT文件的实用说明。
在maven模板的“libs”文件夹中可以找到包含JPGD库的“.jar”文件。
这应该已经是项目依赖项的一部分，但了解库所在的位置是有用的
（以防您必须手动将其添加到IntelliJ项目中）。
请注意，“locations”子图将_always_是实体文件中的第一个**，“paths”子图将_alway_
出现在**位置之后**（如果我们切换此顺序，DOT解析器不喜欢它！）
您可以假设标记过程中使用的所有实体文件都是有效的格式（我们的目的不是测试解析库的稳健性）。



# 
### Task 6: Game Actions


In addition to the standard "built-in" commands (e.g. `get`, `goto`, `look` etc.), your game engine should also
respond to any of a number of game-specific commands (as specified in the "actions" file).
Each of these **actions** will have the following elements:

- One or more possible **trigger** phrases (ANY of which can be used to initiate the action)
- One or more **subject** entities that are acted upon (ALL of which need to be available to perform the action)
- An optional set of **consumed** entities that are all removed ("eaten up") by the action
- An optional set of **produced** entities that are all created ("generated") by the action
- A **narration** that provides a human-readable explanation of what happened when the action is performed

Note that being "available" requires the entity to _either_ be in the inventory of the player invoking the action
_or_ for that entity to be in the room/location where the action is being performed. This feature is
intended to be a shortcut so that a player can use an entity in their location without having to explicitly pick it up first.
Additionally, subjects of an action can be locations, characters or furniture (which can't be picked up).

It is worth noting that action trigger keyphrases are NOT unique - for example there may be multiple "open" actions that
act on different entities. Note that trigger phrases cannot (and will not) contain the names of entities,
since this would make incoming commands far too difficult to interpret. Just consider the challenge of trying to
interpret the command: `lock lock with key`.

Upon receiving an action command, your server should attempt to find an appropriate matching action.
Note that the action is only valid if ALL **subject** entities (as specified in the actions file) are available to the player.
If a valid action is found, your server must undertake the relevant additions/removals (production/consumption).

When an entity is _produced_, it should be moved from its current location in the game (which might be in the `storeroom`)
to the location in which the action was trigged. The entity should NOT automatically appear in a players inventory -
it might be furniture (which the player can't carry) or it might be an artefact they don't actually want to pick up !

When an entity is _consumed_ it should be removed from its current location (which could be any location within the game)
and moved into the `storeroom` location. If the game writer wants to enforce co-location (i.e. the consumed entity must be
in the same location as the player) then they must include that entity as a subject of the action.

Note that it is NOT possible to perform an action where a subject, or a consumed or produced entity is currently in
_another_ player's inventory. You should consider these entities "out of bounds" and not available to a player who does not
currently hold that artefact. You can't unlock a door if another player has the key !

Locations can be used as subjects, consumed and produced entities of an action (just like other entities).
Consumed locations however are not moved to the storeroom - instead, the path between the current location and consumed location is removed
(there may still be other paths to that location in other game locations). For produced locations, a new (one-way) path is added from the
current location to the "produced" location.

除了标准的“内置”命令（例如“get”、“goto”、“look”等），您的游戏引擎还应该
响应许多特定于游戏的命令中的任何一个（如“操作”文件中所指定的）。
这些**行动**中的每一项都将具有以下要素：
-一个或多个可能的**触发**短语（其中任何一个都可用于启动动作）
-被采取行动的一个或多个**主体**实体（所有这些实体都需要可用于执行行动）
-一组可选的**消耗的**实体，这些实体都被操作移除（“吃掉”）
-由操作创建（“生成”）的**生成**实体的可选集合
-一种**叙述**，它为执行动作时发生的事情提供了人类可读的解释
请注意，“可用”要求实体_either_位于调用动作的玩家的库存中
_或者_使该实体位于执行操作的房间/位置。此功能是
旨在成为一种快捷方式，这样玩家就可以在自己的位置使用实体，而不必先明确地拿起它。
此外，动作的主题可以是位置、角色或家具（这些都是无法拾取的）。
值得注意的是，动作触发的关键短语并不是唯一的——例如，可能有多个“开放”动作
对不同的实体采取行动。注意，触发短语不能（也不会）包含实体的名称，
因为这将使传入的命令太难解释。只要考虑一下尝试的挑战
解释命令：“用钥匙锁住锁”。
收到操作命令后，服务器应尝试查找适当的匹配操作。
请注意，只有当玩家可以使用所有**主题**实体（如操作文件中所指定）时，该操作才有效。
如果找到有效的操作，则服务器必须进行相关的添加/删除（生产/消耗）。
当一个实体被制作时，它应该从游戏中的当前位置（可能在“储藏室”中）移动
到触发动作的位置。实体不应自动出现在玩家库存中-
它可能是家具（玩家不能携带），也可能是他们实际上不想捡的艺术品！
当实体被占用时，它应该从其当前位置（可以是游戏中的任何位置）移除
并搬进了“储藏室”的位置。如果游戏作者想强制执行同一地点（即，被消费的实体必须
在与玩家相同的位置），则他们必须包括该实体作为动作的主体。
请注意，不可能在主体、消费或生产实体当前所在的位置执行动作
_另一个玩家的库存。你应该认为这些实体是“越界”的，不可供没有的玩家使用
目前持有该文物。如果其他玩家有钥匙，你就无法解锁门！
位置可以用作动作的主体、消费和产生的实体（就像其他实体一样）。
但是，消耗的位置不会移动到储藏室，而是删除当前位置和消耗位置之间的路径
（在其他游戏位置中可能仍然存在到该位置的其他路径）。对于生产的位置，将从添加一个新的（单向）路径
当前位置到“生产”位置。

# 
### Task 7: Loading Actions


We have provided some example action files for you to use in your project.
Firstly there is a <a href="resources/cw-stag/config/basic-actions.xml" target="_blank">basic actions file</a>
to help get you started in constructing your game engine.
We have also provided an <a href="resources/cw-stag/config/extended-actions.xml" target="_blank">extended actions file</a>
that can be used for more extensive testing during the later stages of your work.

Both of these documents are written in eXtensible Markup Language (XML).
In order to successfully parse these XML files, you should use the Java API for XML Processing (JAXP).
In particular, you should make use of the
<a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.xml/javax/xml/parsers/DocumentBuilder.html" target="_blank">DocumentBuilder</a>
class as well as various classes from the 
<a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.xml/org/w3c/dom/package-summary.html" target="_blank">org.w3c.dom</a> package.
See the "Hints and Tips" section of this task for an example of how to use these classes.

Once loaded in from the XML file, you should store the actions in a suitable data structure so that your server can access them quickly and easily.
Your first thought might be to use an `ArrayList` for this purpose.
This approach would however be slow and inefficient for some operations.
In the interests of efficiency (and to provide you with broader experience of using the classes in the Java `Collections` package)
you should use the following data structure to store all of your actions:

```java
HashMap<String,HashSet<GameAction>> actions = new HashMap<String, HashSet<GameAction>>();
```

The data structure described in the above code is illustrated in the diagram shown below. The
<a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html" target="_blank">HashMap</a>
provides a fast and efficient lookup mechanism - using a `String` key (the trigger keyphrase) to map to a 
<a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashSet.html" target="_blank">HashSet</a>
of matching actions. Remember that a particular trigger keyphrase may be used
in more than one action, so we need to map to a _composite_ data structure (rather than just a single `GameAction` element).
It is useful for us to make use a _set_ (rather than, for example, an `ArrayList`)
since this allows us to ensure that there are no duplicate actions being stored in the data structure (all elements of a set will be unique).



我们提供了一些示例操作文件供您在项目中使用。
首先是<a href=“resources/cw stag/config/basic-actions.xml”target=“_blank”>基本操作文件</a>
帮助您开始构建游戏引擎。
我们还提供了<a href=“resources/cw stag/config/extended actions.xml”target=“_blank”>扩展操作文件</a>
可以在工作的后期阶段用于更广泛的测试。
这两个文档都是用可扩展标记语言（XML）编写的。
为了成功解析这些XML文件，您应该使用Java API for XML Processing（JAXP）。
特别是，您应该利用
<a href=“https://docs.oracle.com/en/java/javase/17/docs/api/java.xml/javax/xml/parsers/DocumentBuilder.html“target=”_blank“>DocumentBuilder</a>
类以及来自的各种类
<a href=“https://docs.oracle.com/en/java/javase/17/docs/api/java.xml/org/w3c/dom/package-summary.html“target=”_blank“>org.w3c.dom</a>软件包。
有关如何使用这些类的示例，请参阅此任务的“提示和技巧”部分。
从XML文件加载后，应该将操作存储在适当的数据结构中，以便服务器能够快速方便地访问它们。
您的第一个想法可能是为此使用“ArrayList”。
然而，这种方法对于某些业务来说是缓慢和低效的。
为了提高效率（并为您提供更广泛的使用Java“Collections”包中类的经验）
您应该使用以下数据结构来存储所有操作：
Java语言
HashMap＜String，HashSet＜GameAction＞＞actions＝new HashMap＜String，HashSet＞GameAction＞（）；

```
上面代码中描述的数据结构如下图所示。这个
<a href=“https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashMap.html“target=”_blank“>HashMap</a>
提供了一种快速高效的查找机制-使用“String”键（触发器关键字）映射到
<a href=“https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/HashSet.html“target=”_blank“>哈希集</a>
匹配动作的数量。请记住，可能会使用特定的触发键措辞
在多个动作中，因此我们需要映射到_composite_数据结构（而不仅仅是单个“GameAction”元素）。
使用_set_（而不是“ArrayList”）对我们来说很有用
因为这允许我们确保没有重复的操作存储在数据结构中（集合的所有元素都是唯一的）。
```



![](07%20Loading%20Actions/images/HashMap-HashSet.jpg)

**Hints & Tips:**  
To ensure that the basic actions file is in the correct location in the project folder and that it can be accessed by the XML parser,
we have provided a <a href="resources/cw-stag/src/test/java/edu/uob/ActionsFileTests.java" target="_blank">JUnit test class</a> for you to use.
Not only will these tests verify the basic actions file, but they also provides a practical illustration of the use of the JAXP library
for parsing XML files.

You may assume that all action files used during marking are in a valid format (our aim isn't to test the robustness of the parsing libraries).



# 
### Task 8: Command Flexibility

Interpreting the input from users in this assignment is very different from dealing with queries in the Database exercise.
The simplified SQL that we used for the last assignment was limited, rigid and constrained.
As such, it was possible to write a reasonably concise BNF grammar to define the entire language.
The same is not true for natural language, which is very complex, permits much variation and is used diversely (depending on the speaker/writer).
The best we can hope do is to try to make our command interpreter as flexible and versatile as possible - in order to cope with most eventualities.
Below are various features your should implement in your interpreter:

**Case Insensitivity**  
All commands (including entity names, locations, built in commands and action triggers) should be treated as case insensitive.
This ensure that, no matter what capitalisation a player chooses to use in their commands, the server will be able in interpret their intensions.
For this reason, it is not possible for the configuration files to contain two different things with the same name, but different capitalisation
(e.g. there cannot be a `door` and a `DOOR` in the same game)

**Decorated Commands**  
In order to support command variability, your interpreter should be able to cope with _additional_ "decorative" words being inserted into a command.
For example, the basic command `chop tree with axe` might well be entered by the user as `please chop the tree using the axe`.
Both versions are equivalent and should both be accepted by your command interpreter.

**Word Ordering**  
The ordering of the words in a command should not effect the server's ability to find appropriate matching actions.
For example `chop tree with axe` and `use axe to chop tree` are equivalent and should both be accepted by your command interpreter.

**Partial Commands**  
To further support flexible natural language communication, your server should be able to operate with shortened, "partial" commands.
It is convenient for the user to be able to omit _some_ of the subjects from a command, whilst still providing enough information for
the correct action to be identified.
For example, the command `unlock trapdoor with key` could alternatively be entered as _either_ `unlock trapdoor` _or_ `unlock with key` -
both of which provide enough detail for an action match to be attempted.
In order to stand a chance of matching a command to an action, each incoming command MUST contain a trigger phrase and _at least_ one subject.
Anything less than this and the intended action will probably be too vague to identify.

**Extraneous Entities**  
When searching for an action, you must match ALL of the subjects that are specified in the incoming command (e.g. `repair door with hammer and nails`).
Extraneous entities included within an incoming command (i.e. entities that are in the incoming command, but not specified in the action file)
should prevent a match from being made. This is to prevent the user attempting to perform actions with inappropriate entities
(e.g. `open potion with hammer` should not succeed). Similarly, specifying extraneous entities for built-in command (e.g. `get key from forest`)
should not be permitted. The rational for this is that it could be problematic for your command interpreter to determine which entity to perform
the built-in command upon if there is more than one in the command.

**Ambiguous Commands**  
Much of the above "fuzzy" matching of actions is risky - there may be situations where _more than one_ action matches a particular command.
If a particular command is ambiguous (i.e. there is _more than one_ **valid** and **performable** action possible - given the current state of the game)
then NO action should be performed and a suitable warning message sent back to the user
(e.g. `there is more than one 'open' action possible - which one do you want to perform ?`)

**Composite Commands**  
Composite commands (commands involving more than one activity) should NOT be supported.
Users are unable to use commands such as `get axe and coin`, `get key and open door` or `open door and potion`.
A single command can only be used to perform a single built-in command or single game action.

**Error messages**  
Note that due to the range of possible response messages it is possible to return to the user, we will not test for specific error/anomaly messages.
Rather, we will check the game state using the standard command set (`look`, `inv` etc) in order to check that inappropriate actions have not
been performed by your server. That said, we still encourage you to return suitable error messages to the user for the sake of playability of your game.

Although there is much flexibility and variability in the command language, the test cases we will use during the marking process
will focus upon testing user input that is "fair and reasonable".
We are interested in assessing the ability of the command interpreter to detect valid, sensible and likely inputs from the user.
This requires the user to provide enough information to allow the interpreter to uniquely identify an action,
whilst at the same time giving them some flexibility about how they go about expressing the command.
We aren't looking for the ability of the interpreter to deal with illogical or silly commands.
We just want to avoid the situation where the user thinks: "That was a fair command, why won't it understand me".
If the user types something weird and strange they shouldn't be surprised if something weird and strange happens.

  

在这个作业中解释用户的输入与处理数据库练习中的查询非常不同。
我们在上一次分配中使用的简化SQL是有限的、严格的和受约束的。
因此，可以编写一个相当简洁的BNF语法来定义整个语言。
自然语言也是如此，它非常复杂，允许很多变化，并且使用方式多种多样（取决于说话者/作者）。
我们所能做的最好的事情就是尽可能地使我们的命令解释器灵活多样，以应对大多数可能发生的情况。
以下是您应该在解释器中实现的各种功能：
**大小写不敏感**
所有命令（包括实体名称、位置、内置命令和操作触发器）都应视为不区分大小写。
这确保了，无论玩家选择在命令中使用何种资本，服务器都能够解释其意图。
由于这个原因，配置文件不可能包含两个不同的东西，它们具有相同的名称，但大写不同
（例如，在同一个游戏中不能有“门”和“门”）
**装饰命令**
为了支持命令的可变性，解释器应该能够处理插入到命令中的_aditional_“装饰性”单词。
例如，基本命令“用斧头砍树”很可能由用户输入为“请用斧头砍下树”。
这两个版本是等效的，都应该被您的命令解释器接受。
**单词排序**
命令中单词的排序不应影响服务器查找适当匹配操作的能力。
例如，“用斧头砍树”和“用斧头砍树”是等效的，都应该被命令解释器接受。
**部分命令**
为了进一步支持灵活的自然语言通信，您的服务器应该能够使用缩短的“部分”命令进行操作。
用户可以方便地从命令中省略主题的_ some_，同时仍然为提供足够的信息
要识别的正确操作。
例如，命令“unlock trapdoor with key”也可以输入为_either_“unlock trapdoor”_or_“unlock with key”-
这两者都为要尝试的动作匹配提供了足够的细节。
为了有机会将命令与动作相匹配，每个传入的命令必须包含一个触发短语和至少一个主题。
任何低于这一点和预期行动都可能过于模糊，无法确定。
**外部实体**
搜索动作时，必须匹配传入命令中指定的所有主题（例如“用锤子和钉子修理门”）。
传入命令中包含的外部实体（即传入命令中但未在操作文件中指定的实体）
应该防止匹配。这是为了防止用户试图使用不适当的实体执行操作
（例如“用锤子打开药剂”不应成功）。类似地，为内置命令指定无关实体（例如“从林获取密钥”）
不应被允许。这样做的原因是，命令解释器在确定执行哪个实体时可能会出现问题
如果命令中有多个，则会调用内置命令。
**模棱两可的命令**
上面的许多“模糊”操作匹配都是有风险的——在某些情况下，可能会有多个_操作与特定命令匹配。
如果某个特定命令不明确（即在给定游戏当前状态的情况下，存在多个_**有效**和**可执行**操作）
则不应执行任何操作，并将适当的警告消息发送回用户
（例如，“可能有多个‘打开’操作——您想执行哪一个？”）
**复合命令**
不应支持复合命令（涉及多个活动的命令）。
用户无法使用诸如“获取斧头和硬币”、“获取钥匙并打开门”或“打开门和药剂”之类的命令。
单个命令只能用于执行单个内置命令或单个游戏动作。
**错误消息**
请注意，由于可能返回给用户的响应消息的范围，我们不会测试特定的错误/异常消息。
相反，我们将使用标准命令集（“look”、“inv”等）检查游戏状态，以检查是否有不适当的操作
由您的服务器执行。也就是说，为了游戏的可玩性，我们仍然鼓励您向用户返回适当的错误消息。
尽管命令语言有很大的灵活性和可变性，但我们将在标记过程中使用的测试用例
将重点测试“公平合理”的用户输入。
我们感兴趣的是评估命令解释器检测用户有效、合理和可能输入的能力。
这需要用户提供足够的信息以允许解释器唯一地识别动作，
同时在如何表达命令方面给他们一些灵活性。
我们不是在寻找口译员处理不合逻辑或愚蠢命令的能力。
我们只是想避免用户认为：“这是一个公平的命令，为什么它不理解我”的情况。
如果用户输入了一些奇怪的东西，如果发生了奇怪的事情，他们不应该感到惊讶。


# 
### Task 9: Multiple Players


If you are feeling ambitious, extend your game so that it is able to operate with more than just a single player.
In order to support this feature, incoming command messages begin with the username of the player who issued that command.
For example, a "full" incoming message might therefore take the form of:
```
simon: open door with key
```

Where everything _before_ the first `:` is the player's name and everything _after_ is the command itself.
Valid player names can consist of uppercase and lowercase letters, spaces, apostrophes and hyphens.
Note that there is no "formal" player registration process - when the server encounters a command from
a previously unseen user, a new player should be created and placed in the start location of the game.

Note that there is no need for your server to implement any form of authentication - 
you can assume that the client handles this responsibility.
Your server should simply trust any commands that purport to come from a particular user.
This may seem unsafe, however this is intended to limit the scope and complexity of this assignment.

It is essential that when an incoming message is received, the command is applied to the _correct_ player.
To achieve this, you will need to maintain _some_ elements of game state _separately_ for each individual player.
For example, each player may be in a different location in the game and will carry their own inventory of artefacts.

One final thing to remember is that you should include _other_ players in your description of a location
when a `look` command is issued by a user. There is no point having multiple players in the game if they can't
actually _see_ each other ! Note that even though they can see each other, human players cannot interact directly.
This is because, due to dynamic player naming, it is not possible to write action rules involving player names
(since they are not known in advance of the game being played).  

# 

如果你有野心，扩展你的游戏，使其能够与不止一个玩家一起操作。
为了支持此功能，传入的命令消息以发出该命令的玩家的用户名开始。
例如，“完整”传入消息可能因此采取以下形式：
```
西蒙：用钥匙开门
```
其中第一个“：”是玩家的名字，而“after_”是命令本身。
有效的玩家名称可以由大写和小写字母、空格、撇号和连字符组成。
请注意，当服务器遇到来自的命令时，没有“正式”的玩家注册过程
一个以前看不见的用户，应该创建一个新玩家并将其放置在游戏的开始位置。
请注意，您的服务器不需要实现任何形式的身份验证-
您可以假设客户端负责处理这一职责。
您的服务器应该简单地信任任何声称来自特定用户的命令。
这可能看起来不安全，但这是为了限制这项任务的范围和复杂性。
重要的是，当接收到传入消息时，该命令将应用于_correct_播放器。
为了实现这一点，您需要为每个玩家单独维护游戏状态的一些元素。
例如，每个玩家可能在游戏中的不同位置，并将携带自己的文物库存。
最后要记住的一件事是，你应该在对地点的描述中包括_其他_玩家
当用户发出“look”命令时。如果不能让多个玩家参与游戏，那就没有意义了
实际上是彼此_see_！请注意，尽管人类玩家可以看到彼此，但他们无法直接互动。
这是因为，由于动态玩家命名，无法编写涉及玩家名称的动作规则
（因为在玩游戏之前不知道它们）。

### Task 10: Player Health


As an extension to the basic game, you might like to add a "health level" feature.
Each player should start with the maximum health level of 3. Consumption of "Poisons & Potions" or interaction with beneficial
or dangerous characters will increase or decrease a player's health by one point. You will see in the 
<a href="resources/cw-stag/config/extended-actions.xml" target="_blank">extended actions file</a>
the use of the `health` keyword in the `produced` and `consumed` fields.
These indicate actions which increase and decrease your health by one unit.
Note that although a player's health can never increase above the maximum (i.e. 3)
actions producing health are still _performable_ but they will have no effect on the player's health level.

When a player's health runs out (i.e. when it becomes zero) they should lose all of the items in their inventory
(which are dropped in the location where they ran out of health).
The player should then be transported to the start location of the game and their health level restored to full (i.e. 3).
You might also like to send a suitable message to the user, for example:
```
you died and lost all of your items, you must return to the start of the game
```
It is important that you should not reset the whole game state when one player dies
(for example, previously opened location paths should still exist).
You should remember that there may be more than one player of the game - they shouldn't see their world change just
because another player has died.

In order to fully support these features in your game engine, you should implement a new `health` built-in command,
so that the player can keep track of their current health level. Upon receiving a `health` command from a user,
the server should report back the player's current health level (as a number).

  

作为基本游戏的扩展，您可能希望添加一个“健康等级”功能。
每个玩家的生命值上限应为3。服用“毒药和药剂”或与有益物质相互作用
或者危险人物会使玩家的生命值增加或减少一点。您将在中看到
<a href=“resources/cw stag/config/extended actions.xml”target=“_blank”>扩展操作文件</a>
在“生产”和“消费”字段中使用“健康”关键字。
这些指示将你的健康增加和减少一个单位的行动。
请注意，尽管玩家的生命值永远不会超过最大值（即3）
产生生命值的动作仍然可以执行，但对玩家的生命值没有影响。
当玩家的生命值耗尽时（即当它变为零时），他们应该失去库存中的所有物品
（它们被丢弃在它们失去健康的地方）。
然后，玩家应该被运送到游戏的开始位置，并且他们的健康水平恢复到完全（即3）。
您可能还想向用户发送一条合适的消息，例如：
```
你已经死亡并丢失了所有物品，你必须回到游戏开始
```
重要的是，当一名玩家死亡时，你不应该重置整个游戏状态
（例如，以前打开的位置路径应该仍然存在）。
你应该记住，游戏中可能有不止一个玩家——他们不应该看到自己的世界发生了变化
因为另一个玩家已经死亡。
为了在游戏引擎中完全支持这些功能，您应该实现一个新的“health”内置命令，
以便玩家能够跟踪他们当前的健康水平。在接收到来自用户的“健康”命令时，
服务器应该报告玩家的当前健康水平（作为一个数字）。


# 
### Task 11: Marking

For consistency and compatibly with all test cases, it is essential that you adhere to the gameplay commands detailed in this workbook.
You should also ensure that you do not change the name of your main class - it must be called `GameServer`.
If you change the name of the class (or the `handleCommand` method) the marking script will not be able to test your code !
It is **ESSENTIAL** that you check your code still passes the original test script provided as part of the maven project.
If your code does not pass these basic tests, then it is likely your code not not pass any of the marking test scripts.

Make sure your code does not contain anything specific to your computer (e.g. absolute file paths, operating system specific code etc).
Before submitting your code, we advise your to test your project on a computer _other than the one it was developed on_ (e.g. a lab machine).
Clear out all temporary files and then ensure the code compiles and runs correctly using Maven. We will apply a penalty mark if we cannot run your code
"out of the box" - we can't spend time fixing everyones projects before we mark them !

A key principle of Agile is early and regular delivery of value to the client, through the steady implementation and delivery of features. The emphasis is very much on "steady and sustainable development" - no "all-nighters", no "heroic effort". Doing most of the work in a few intensive coding sessions is just not the Agile way !
It is important that you have experience of working with an Agile ethos. For this reason, you will be assessed on the "Agileness" of your development process. Your aim is to achieve the steady build up of features over the entire duration of the assignment.

In order for us to gain insight into your development process, you should maintain your codebase in your existing GitHub repository.
Make sure all your project work is kept inside a `cw-stag` folder on GitHub, so that the codebase is distinct from your previous assignments).
You should commit and push to your repo on a regular basis - at the end of every coding session ("before you eat or sleep" is a good principle). You should practice continuous integration: always keep your master branch operational. It should be possible to clone the master branch of your repository and be able to compile and run the server via maven (without any kind of editing or copying of additional files) even if there are features which haven't yet been implemented !

In addition to functionality, robustness and flexibility of your implemented solution, you will also be assessed on the frequency and regularity of committing working features to your repository. We will be taking into consideration the "steadiness" of the accumulation of implemented features. Note that you should also be careful to adhere to the guidelines on _what_ to push to your repository - only appropriate content should appear on GitHub (no duplicates, no built resources, no stored data etc). You will need to employ a suitable `.gitignore` file to assist with this task.

Note that the "quality" of your code will be taken into account when assessing your work.
The code quality metrics outlined earlier in this unit will be used to derive your final mark.
It is important therefore that you adhere to the structure and style guidelines outlined in the "code quality" workbook.
It is also essential that you take heed of the quality feedback you have received for previous exercises,
since this will help you improve your work - not just for this exercise, but in the long term.  

# 

为了与所有测试用例保持一致性和兼容性，您必须遵守本工作簿中详细介绍的游戏命令。
您还应该确保不会更改主类的名称——它必须被称为“GameServer”。
如果更改类的名称（或“handleCommand”方法），标记脚本将无法测试代码！
检查您的代码是否仍然通过作为maven项目一部分提供的原始测试脚本是**ESSENTIAL**。
如果您的代码没有通过这些基本测试，那么您的代码很可能没有通过任何标记测试脚本。
确保您的代码不包含任何特定于您计算机的内容（例如绝对文件路径、操作系统特定代码等）。
在提交代码之前，我们建议您在计算机上测试您的项目，而不是在开发的计算机上（例如实验室机器）。
清除所有临时文件，然后确保代码使用Maven正确编译和运行。如果我们无法运行您的代码，我们将处以罚款
“开箱即用”-我们不能在标记之前花时间修复每个人的项目！
敏捷的一个关键原则是通过稳定的功能实现和交付，尽早、定期地向客户交付价值。重点是“稳定和可持续发展”——没有“通宵”，没有“英勇的努力”。在几个密集的编码会话中完成大部分工作不是敏捷的方式！
重要的是，你有与敏捷精神合作的经验。因此，将对您的开发过程的“敏捷性”进行评估。您的目标是在整个任务期间实现功能的稳定构建。
为了让我们深入了解您的开发过程，您应该在现有的GitHub存储库中维护您的代码库。
确保你所有的项目工作都保存在GitHub上的“cw-stag”文件夹中，这样代码库就与你以前的作业不同了）。
你应该定期承诺并推动你的回购——在每个编码会话结束时（“在你吃饭或睡觉之前”是一个好原则）。您应该练习持续集成：始终保持主分支机构的运行。应该可以克隆存储库的主分支，并能够通过maven编译和运行服务器（无需任何形式的编辑或复制其他文件），即使有尚未实现的功能！
除了实现的解决方案的功能性、稳健性和灵活性之外，还将评估将工作特性提交到存储库的频率和规律性。我们将考虑已实现功能积累的“稳定性”。请注意，您还应该小心遵守_what_上的指导原则来推送到您的存储库中——只有适当的内容应该出现在GitHub上（没有重复、没有构建的资源、没有存储的数据等）。您需要使用一个合适的“.gitignore”文件来协助完成此任务。
请注意，在评估您的工作时，将考虑代码的“质量”。
本单元前面概述的代码质量指标将用于获得您的最终分数。
因此，您必须遵守“代码质量”工作簿中列出的结构和样式准则。
同样重要的是，你要注意之前练习中收到的高质量反馈，
因为这将帮助你改进你的工作——不仅是为了这个练习，而且是从长远来看。



### Task 12: Plagiarism


You are encouraged to discuss assignments and possible solutions with other students.
HOWEVER it is essential that you only submit your own work.
This may feel like a grey area, however if you adhere to the following advice, you should be fine:

- Don't take segments of code from existing solutions (e.g. those found online)
- Don't paste segments of code generated by AI tools into your work
- Never exchange code with other students (via IM/email, GIT, forums, printouts, photos or any other means)
- You should avoid the use of pair programming on this unit - you must produce all your own work !
- It is OK to seek help from online sources (e.g. Stack Overflow) but don't just cut-and-paste chunks of code
- If you don't understand what a line of code actually does, you shouldn't be submitting it !
- Don't submit anything you couldn't re-implement under exam conditions (with a good textbook !)

Automated plagiarism detection tools will be used to flag any incidences of possible plagiarism.
If the markers feel that intentional plagiarism has actually taken place,
the incident will be reported to the school or faculty plagiarism panel.
This may result in a mark of zero for the assignment, or even the entire unit
(if it is a repeat offence).
Don't panic - if you stick to the above list of advice, you should remain safe !  


# 
