# luxsoft-scala

Sensor Statistics Project Using Vanilla Scala

# Sensor Statistics Task

Create a command line program that calculates statistics from humidity sensor data.

### Background story

The sensors are in a network, and they are divided into groups. Each sensor submits its data to its group leader.
Each leader produces a daily report file for a group. The network periodically re-balances itself, so the sensors could
change the group assignment over time, and their measurements can be reported by different leaders. The program should
help spot sensors with highest average humidity.

## Input

- Program takes one argument: a path to directory
- Directory contains many CSV files (*.csv), each with a daily report from one group leader
- Format of the file: 1 header line + many lines with measurements
- Measurement line has sensor id and the humidity value
- Humidity value is integer in range `[0, 100]` or `NaN` (failed measurement)
- The measurements for the same sensor id can be in the different files

### Example

leader-1.csv
```
sensor-id,humidity
s1,10
s2,88
s1,NaN
```

leader-2.csv
```
sensor-id,humidity
s2,80
s3,NaN
s2,78
s1,98
```

## Expected Output

- Program prints statistics to StdOut
- It reports how many files it processed
- It reports how many measurements it processed
- It reports how many measurements failed
- For each sensor it calculates min/avg/max humidity
- `NaN` values are ignored from min/avg/max
- Sensors with only `NaN` measurements have min/avg/max as `NaN/NaN/NaN`
- Program sorts sensors by highest avg humidity (`NaN` values go last)


### Example

```
Num of processed files: 2
Num of processed measurements: 7
Num of failed measurements: 2

Sensors with highest avg humidity:

sensor-id,min,avg,max
s2,78,82,88
s1,10,54,98
s3,NaN,NaN,NaN
```

## Notes

- Single daily report file can be very large, and can exceed program memory
- Program should only use memory for its internal state (no disk, no database)
- Any open source library can be used (besides Spark) 
- Please use vanilla scala, akka-stream, monix or similar technology. 
- You're more than welcome to implement a purely functional solution using cats-effect, fs2 and/or ZIO to impress, 
  but this is not a mandatory requirement. 
- Sensible tests are welcome

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





