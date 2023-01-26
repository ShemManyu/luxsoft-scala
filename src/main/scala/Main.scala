import scala.io.Source
import java.io.File
import scala.collection.mutable.HashMap
import scala.math._

object Main {
  def main(args: Array[String]): Unit = {
    val directory = args(0).trim()
    val fileList = Utils.getListofFile(directory)
    var totalMeasurements = 0
    val totalFiles = fileList.length
    var totalFailedMeasurements = 0
    var sensors = Set[String]()
    var statistics = new HashMap[String, HashMap[String, Int]]()

    //sensor min, average, max, total, count
    for (filename <- fileList) {
      val bufferedSourceFile = Source.fromFile(filename)
      //drop moves iterator index by 1 to skip first line
      for (line <- bufferedSourceFile.getLines.drop(1)) {
        val Array(sensor, measurement) = line.trim().split(",")
        totalMeasurements += 1
        if(!sensors.contains(sensor)){
          sensors += sensor
        }

        if (measurement != "NaN") {
          //capture stats
          if (statistics.contains(sensor)) {
            statistics(sensor)("count") += 1
            statistics(sensor)("total") += measurement.toInt
            statistics(sensor)("min") = min(statistics(sensor)("min"), measurement.toInt)
            statistics(sensor)("max") = max(statistics(sensor)("max"), measurement.toInt)
          } else {
            statistics(sensor) = HashMap(
              "total" -> measurement.toInt,
              "count" -> 1,
              "min" -> measurement.toInt,
              "max" -> measurement.toInt
            )
          }
        } else {
          totalFailedMeasurements += 1
        }
      }
      bufferedSourceFile.close()
    }

    println("Num of processed files: " + totalFiles)
    println("Num of processed measurements: " + totalMeasurements)
    println("Num of failed measurements: " + totalFailedMeasurements + "\n")

    println("Sensors with highest avg humidity:\n")
    for ((sensor, stats) <- statistics) {
      statistics(sensor) += ("avg" ->  stats("total")/stats("count"))
    }

    //sort by avg
    val sortedStats = statistics.toList.sortWith(_._2("avg") > _._2("avg"))
    var processedsensors = Set[String]()

    println("sensor-id,min,avg,max")
    for(stat <- sortedStats) {
      val sensor = stat._1
      val stats = stat._2
      processedsensors += sensor
      println(sensor + "," + stats("min") + "," + stats("avg") + "," + stats("max"))
    }
    for (sensor <- sensors) {
      if(!processedsensors.contains(sensor)){
        println(sensor + ",NaN,NaN,NaN")
      }
    }

  }
}

object Utils {
  def getListofFile(directory: String): List[File] = {
    val d = new File(directory)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
}
