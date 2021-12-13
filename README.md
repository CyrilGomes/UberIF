# UberIF

## How to launch the application:

### Using a JavaFX JRE / SDK
This project uses some functions from the JavaFX framework. To launch the 
application, please install a compatible JRE / SDK. Examples below:
- https://gluonhq.com/products/javafx/
- https://www.oracle.com/java/technologies/install-javafx-sdk.html

This project is running on Java version 8.

### Running the application
The application can be started through any IDE supporting Maven. It is 100% 
working with IntelliJ IDEA and NetBeans.

On IntelliJ IDEA, use the following method:
- Open the project. The IDE might generate some files.
- Go to `Run --> Edit Configurations...`, then add an `Application` 
  configuration with `View.MainWindow` as the main class.
- Download the Maven dependencies through the Maven tab (on the right-hand
  side of the IDE) and by clicking on `Reload all Maven projects`.

You can also build the application through the supported IDEs and run the 
generated JAR from a command line terminal.

### Testing
The unit tests are located in `src/test`.

To run the tests, any tool supporting JUnit 3 and 4 along with Maven can be 
used, although IntelliJ IDEA and NetBeans are highly recommended.

## Error handling cases when importing a file:

Quoted message is the message shown to the user.

### When importing a map:

- When we select a file that is not xml: "Please only select .xml files".
- When there are no <intersection\> in the file: "No intersections found in file".
- If an <intersection\> doesn't have id or latitude or longitude attribute we consider that it is missing important data, and we ignore that intersection.
- If all <intersection\> in the file lack important data: "All intersections in the file lack important data".
- When there are no <segment\> in the file: "No segments found in file".
- If a <segment\> doesn't have origin or destination or length attribute we consider that it is missing important data, and we ignore that segment.
- If all <segment\> in the file lack important data: "All segments in the file lack important data".

### When importing a planning request (tour):

- When we select a file that is not xml: "Please only select .xml files".
- If the file doesn't have <depot\>: "Depot not found".
- If <depot\> doesn't have address attribute: "Depot address not specified".
- If <depot\> doesn't have departureTime attribute: "Departure time not specified".
- When there are no <request\> in the file: "No requests found in file".
- If a <request\> doesn't have pickupId or deliveryId attribute we consider that it is missing important data, and we ignore that request.
- If all <request\> in the file lack important data: "All requests in file lack important data".
- If the user imports a small map like smallMap.xml then imports a tour that has requests with intersections not figuring on the map like requestsLarge7.xml :"The map is too small for the requests imported, please use a bigger map." 
