## Introduction and Orientation
### Task 1: Introduction
 <a href='01%20Introduction/slides/segment-1.pdf' target='_blank'> ![](../../resources/icons/slides.png) </a> <a href='01%20Introduction/video/segment-1.mp4' target='_blank'> ![](../../resources/icons/video.png) </a>

Welcome to this first practical workbook for the Object Oriented Programming with Java unit.
The aim of these workbooks is to present key theoretical concepts that underlie the unit and 
give you the opportunity to apply these in practical exercises.

Where you see the blue "Slides" and "Video" buttons at the top of a section, 
you should view these _before_ attempting the practical activities described in that section. 
You should do this now for this current task - this will present an introduction to
Java and provide you with a high-level understanding of the nature of Object Orientation.
Note that it is easier to "clone" these workbooks and view them locally on your own computer
(rather than trying to browse them online via the GitHub website).

One of the reasons we focus on Java in this unit is that it is a very popular and marketable
programming language. Before moving on to the main tasks in this workbook, you might like to watch 
<a href="https://www.youtube.com/watch?v=Og847HVwRSI" target="_blank">this animation</a>
to see how Java's popularity has changed over the years.
  


# 
### Task 2: Object Orientation
 <a href='02%20Object%20Orientation/slides/segment-1.pdf' target='_blank'> ![](../../resources/icons/slides.png) </a> <a href='02%20Object%20Orientation/video/segment-1.mp4' target='_blank'> ![](../../resources/icons/video.png) </a>

In the previous teaching block, you learnt to program in C (to varying degrees of success ;o). In this unit however, you will be using **Java** and as such the _organisation_ of your code _should_ be very different. There are a number of key concepts that are fundamental to **Object Oriented** code. Before we progress to any hands-on practical exercises, it is important that you have an appreciation of these concepts. The first session of this unit introduced a lot of these concepts - if you missed this session (or feel you need to recap on the topics covered), view the slides and video above. At this stage, we have only introduced the concepts at a very high level - we will revisit them again in later workbooks.  


# 
### Task 3: Hello World
 <a href='03%20Hello%20World/slides/segment-1.pdf' target='_blank'> ![](../../resources/icons/slides.png) </a> <a href='03%20Hello%20World/video/segment-1.mp4' target='_blank'> ![](../../resources/icons/video.png) </a>

Traditionally, the first program that you write in _most_ languages is "Hello World".
Take a look at the slides and video above that describe "Hello World" in Java.
Now work through the following step in order to create and run the "Hello World" program using the IntelliJ Integrated Development Environment (IDE). If you already have IntelliJ installed on your laptop, you might like to follow these steps using your own computer. If not, don't worry - you can work through this activity on the lab machines.

1. Create a new project in IntelliJ (see screenshot below for suggested project settings).
2. Find the `java` folder in the project view panel (it should be inside the `src/main` folder).
3. Right click on the `java` folder (`control` click on a Mac).
4. Create a new Java Class called `HelloWorld` (by selecting `New > Java Class`).
5. Add code to your class by copy-and-pasting the code from the above slide
5. Make sure you save your class file !
6. Click on the green `Run` button on the top-bar menu (or to the left of the `main` method)

There will be a short delay as IntelliJ compiles and runs your code.
If everything is successful you should see the following output in the console panel:

```
Hello World!

Process finished with exit code 0
```  


![](03%20Hello%20World/images/new-project.jpg)

**Hints & Tips:**  
Make sure your code compiles and runs correctly before moving on to the next task in this workbook.  


# 
### Task 4: Template Project


Rather than you having to create a new project each time we start a workbook or assignment,
we will often provide you with an existing template project (in order to help you get started).
These templates will contain all of the rules and dependencies required to build a project. One of the main benefits is that these projects can be imported directly into Intellij (or any other IDE for that matter). This will save us a lot of time installing required libraries and setting up the build environment.

For the next couple of workbooks, we will be using the <a href="IntelliJ Template/" target="_blank">shapes</a> template project.
You should copy the entire `cw-shapes` directory into your work folder (wherever you want to keep your code for this unit). In order to open an existing project in IntelliJ, simply click on the `Open` button on the IntelliJ startup screen (shown below) and pick the `cw-shapes` folder using the file dialog. 

Open the `cw-shapes` project and make sure you can run the code before moving on to the next task. The first time you open a project, you may find that you have to wait some time before you can run it - this is due to the need for IntelliJ generate various project setting files (see the progress bar at the bottom of the window for details). Note that you might need to select a JDK from the list of available development kits (if you get the "Project JDK is not defined - Setup JDK" message when you open the project.

Good news: when you have opened the project once, IntelliJ will remember it and show it in the list of recent projects.  


![](04%20Template%20Project/images/open.jpg)

# 
### Task 5: Command Line
 <a href='05%20Command%20Line/slides/maven.pdf' target='_blank'> ![](../../resources/icons/slides.png) </a>

In this workbook, we have spent the majority of the time exploring the use of IntelliJ.
At this stage however, it is worth spending a little bit of time taking a look at how we can build and run
Java projects from the command line. This is important because the assessed exercises later on in the
unit will be marked on the command line and you need to be able to make sure that your code will actually build and run.

You may have used *GNU Make* previously - this software saves you from having
to manually type out all the compile and link commands every time you want to build your software.
`make` takes care of running all the required build commands (as specified in the `Makefile`)
and intelligently works out what needs to be done.
`make` however isn't commonly used for Java development since building Java projects
usually involves much more complicated activities than just running a set of commands.
This is where the **Maven** build system comes in - it provides various tools for building and running complex Java projects.

On your command line, change into the project root folder (the directory where the `pom.xml` file resides) and type one of the following commands (depending on your platform):

    Linux/OSX:   ./mvnw clean compile
    Windows:     mvnw clean compile

**Note**: On some unix systems, you _might_ need to make `mvnw` executable first
(i.e. by running `chmod +x mvnw`)

The command is doing 2 separate things in sequential order:

1. `clean` ensures that everything will be freshly built by cleaning out (deleting) previously generated files
2. `compile` compiles and builds all files and resources (stopping if any build errors occur)

Note that you can learn more about these and other build tasks in the <a href="https://maven.apache.org/guides/getting-started/index.html">Maven documentation</a>.

The first time you run this command, it might take a while to complete so be patient. This is because Maven may need to download project dependencies.
If successful, the output should looks something like this:

    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 42 s
    [INFO] Finished at: 2022-01-01T00:00:00Z
    [INFO] ------------------------------------------------------------------------

If you get a `BUILD FAILURE`, it means the project failed to complete some of the specified tasks.
If this is the case, it might make sense to go back into IntelliJ and make sure no code issues are detected
(i.e. no code is highlighted in red and the project can still be built and run).
Fix any problems in IntelliJ and then return to the command line.

Once you have successfully built your code on the command line, you can run your program using:

    Linux/OSX:   ./mvnw exec:java
    Windows:     mvnw exec:java

For a given project, there may be many different things that can be executed - the above command runs the `main` method of the Java part of the project (currently the only thing we have written).

If you are interested in finding out more about Maven, take a look at the slides linked to at the top of this document.  


# 
### Task 6: Final Thoughts


This has been a relatively short and straight-forward workbook.
The main was to get you settled in before we start digger deeper into Java and OOP.
Make sure you finish all the practical activities in this workbook - you are going to
need to be able to use all of the tools and techniques in order to actually build software in Java.
If you plan to use your own computer for writing code, now is a good time to get IntelliJ installed.
Some of the installation steps are platform-specific, so we have provided separate videos for getting started with IntelliJ on
<a href="https://web.microsoftstream.com/video/608b2c4c-1834-4429-9c86-bf19530c7f3a" target="_blank">Ubuntu</a>, 
<a href="https://web.microsoftstream.com/video/382a7600-3940-4415-a680-002de6960b99" target="_blank">Windows</a> and
<a href="https://mediasite.bris.ac.uk/Mediasite/Play/e879f20d3cb44e989202926a64f5be481d" target="_blank">Mac OSX</a>.
  


# 
