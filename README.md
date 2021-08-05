# car-assignment-wcg

Martin Hawes-Lambourne `hawesmartin@googlemail.com`

### To build and run:

Prerequisite: to build and run you need to have java JDK 1.8 or later. 

1. Clone this repository on your machine using git: `git clone https://github.com/mhawes/car-assignment-wcg.git`
2. Open a terminal and change directory to the root of the project (the same one as this README)
3. Run the following command to build and execute the tests: `gradlew build test`
4. Run the following command to start the server: `gradlew bootrun`

### Usage:

In order to calculate the new position of a car an API is provided. 
There are two endpoints provided which you can post requests to using your favourite HTTP client (I tested with postman)

- Make a POST request to the URI http://localhost:8080/car/new/position/string?newPositionString=6,5:FLFRF and the response will look something like `8,4`

- Make a POST request to the URI http://localhost:8080/car/new/position with a body in json format like `{"initialX": 6, "initialY": 5, "movementCommands": ["F","L","F","R","F"]}` and the response will come back in json format as `{"x": 8, "y": 4 }`

Thanks for your consideration!
