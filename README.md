# Real-time analysis and Viz with Storm-Camel-Highcharts
----------

#### You might also be interested in checking out extension of this repo for Twitter sentiment of states of US using D3.js Choropleth Map and Highcharts Columncharts on [StormTweetsSentimentD3Viz](https://github.com/P7h/StormTweetsSentimentD3Viz) and also a similar project for UK Twitter Sentiment on [StormTweetsSentimentD3UKViz](https://github.com/P7h/StormTweetsSentimentD3UKViz).

## Introduction
This repository contains an application for demonstrating `Apache Storm` distributed framework by counting the names of the companies fed randomly by the code [in the Spout] in real-time.<br>This project also visualizes the output in real-time using Queues, Websockets and Highcharts.<br>

[Apache Storm](http://storm.apache.org) is an open source distributed real-time computation system, developed at BackType by Nathan Marz and team. It has been open sourced by Twitter [post BackType acquisition] in August, 2011. And became a top level project in Apache on 29<sup>th</sup> September, 2014.<br>
This application has been developed and tested with Storm v0.8.2 on Windows 7 in local mode; and was eventually updated and tested with Storm v0.9.3 on 04<sup>th</sup> January, 2015. Application may or may not work with earlier or later versions than Storm v0.9.3.<br>

The initial version of this application was actually forked and later updated further from Robin van Breukelen's [storm-camel-example](https://github.com/robinvanb/storm-camel-example) project.

## Features
* Application receives a random company name from a Spout every 200 milliseconds.<br>
* Bolt counts frequency of the each company name passed on from the Spout.<br>
* This frequency is written to ActiveMQ using a JMSBolt.<br>
* Camel is used to read these tuples from ActiveMQ and also write to a WebSocket.<br>
* A pure HTML5 front-end reads from the above Websocket and visualizes the output in a Bar chart using Highcharts and updates the chart in real-time to reflect the latest count provided by the Bolt.<br>
* This project has also been made compatible with both Eclipse IDE and IntelliJ IDEA.
	* Import the project in your favorite IDE and you can quickly follow the code.
* As of today, this codebase has almost no or very less comments.<br>

## Demo
![GIF of visualization](Storm-Camel-Websockets__Demo.gif)

![Screenshot of visualization](Storm-Camel-Websockets__Demo.png)

## Dependencies
* Storm v0.9.3
* Camel v2.14.1
* ActiveMQ v5.10.0
* Spring v4.1.3.RELEASE
* xbean v4.1

Also, please check [`pom.xml`](pom.xml) for complete information on the various dependencies of the project.<br>

## Requirements
You need the following on your machine:

* Oracle JDK >= 1.8.x
* Apache Maven >= 3.2.3
* Clone this repo and import as an existing Maven project to either Eclipse IDE or IntelliJ IDEA.
	* Please enable / download Maven Plugin in the IDE.

## Usage
To build and run this topology, you must use Java 1.8.<br>

### Local Mode:
Local mode can also be run on Windows environment using either IntelliJ IDEA or Eclipse IDE.
<br>*Note*: Please be sure to clean your temp folder as it adds lot of temporary files in every run.

* Launch your favorite IDE and execute [`Runner.java`](runner/src/main/java/nl/java/runner/Runner.java).<br>
* All the required frameworks and libraries are downloaded by Maven as required in the above import process in the IDE.
	* If not, please execute `mvn clean compile` command once at the root of this repo.
* Launch a browser [preferably Google Chrome] and open [`index.html`](runner/src/main/resources/index.html) 
	* Click on "Start Viz" button to trigger the initialization.<br>
	* You can stop the visualization at any time by clicking on "Stop Viz" button.<br>
* This chart updates every second and displays real-time visualization of the words processed by the Bolts.<br>

### Remote Mode:
TBA.

## Problems
If you find any issues, please report them either raising an [issue](https://github.com/P7h/storm-camel-example/issues) here on Github or alert me on my Twitter handle [@P7h](http://twitter.com/P7h). Or even better, please send a [pull request](https://github.com/P7h/storm-camel-example/pulls).<br>
Appreciate your help. Thanks!

## License
Copyright &copy; 2013-2015 Robin van Breukelen and Prashanth Babu.<br>
Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).