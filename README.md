# luxsoft-scala

Sensor Statistics Project Using Vanilla Scala

# Solution

Project uses Vanilla scala.
Buffered Source for reading the files to ensure it is read line by line and entire file is not loaded into memory at once.
HashMap for keeping track of the sensor statisctics which is in the shape of HashMap[String, HashMap[String, Int]]. This would like:

```
"sensor_1" -> {
    "min" -> 10,
    "max" -> 10,
    "count" -> 1,
    "avg", 10
},
"sensor_2" -> {
    "min" -> 20,
    "max" -> 10,
    "count" -> 2,
    "avg", 15
}
```

# Executing the project

Project can be run by providing the input directory as Program arguement for the Main object or by simply reassiging the directory value directly with the value
val directory = "C:\\Users\\USER\\Desktop\\Test Files"





