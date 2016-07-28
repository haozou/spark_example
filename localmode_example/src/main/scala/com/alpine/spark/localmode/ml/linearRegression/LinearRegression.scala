package com.alpine.spark.localmode.ml.linearRegression

import org.apache.spark.mllib.evaluation.{RegressionMetrics, BinaryClassificationMetrics}
import org.apache.spark.mllib.regression.{LinearRegressionWithSGD, LabeledPoint}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.{Vectors, Vector}
/**
 * Created by Hao on 12/21/15.
 */

case class Person(rating: String, income: Double, age: Int)

object LinearRegression {
  def apply(sc: SparkContext) = {
    val conf = sc.getConf
    val people = sc.textFile(conf.get("inputFile"), 2).map(line => {
      val info = line.stripLineEnd.split(" ")
      val person = new Person(info{0}, info{1}.toDouble, info{2}.toInt)
      person
    })
    val data = prepareFeaturesWithlabels(prepareFeatures(people))
    val splits = data randomSplit Array(0.8, 0.2)
    val training = splits(0) cache
    val test = splits(1) cache

    val model = LinearRegressionWithSGD.train(training, 40)
    println(model.weights)
    val prediction = model predict(test map(_ features))
    val predictionAndLabel = prediction zip(test map(_ label))
    val regMetrics = new RegressionMetrics(predictionAndLabel)
    println(regMetrics.explainedVariance)

    predictionAndLabel.foreach {
      case (pred, actual) => println(s"predicted label: ${pred}, actual label: ${actual}")
    }


    val metrics = new BinaryClassificationMetrics(predictionAndLabel)
    val auROC = metrics.areaUnderROC()
    println("Area under ROC = " + auROC)
    val loss = predictionAndLabel.map { case (p, l) =>
      val err = p - l
      err * err
    }.reduce(_ + _)

    val rmse = math.sqrt(loss / test.count())

    val list = Seq(("one", "i"), ("two", "2"), ("two", "ii"), ("one", "1"), ("four", "iv"), ("two", "2"), ("two", "2"), ("two", "2"), ("two", "2"), ("two", "2"), ("two", "2"), ("two", "2"))

    println(list)

    val str: Map[String, Seq[String]] = list.aggregate(Map[String, Seq[String]]()) (
        seqop = (result, element) =>  {
          element match {
            case (v1, v2) => {
              result + (element._1 -> (result.getOrElse(element._1, Seq[String]()) ++ Seq(element._2)))
            }
          }
          result
        },
        combop = (num1, num2) => {
          num1 ++ num2
        }
      )
    println(str)
//    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
//    val df = sqlContext.read.load("./src/main/resources/golfnew.parquet")
//    df.registerTempTable("input")
//    val selected = sqlContext.sql("SELECT * FROM input WHERE `outlook` == 'overcast'")
//    selected.columns.foreach(str => println(str))
  }

  def prepareFeatures(people: RDD[Person]): RDD[(Double, Vector)] = {
    val maxIncome = people map(p => p.income) max
    val maxAge = people map(_ age) max

    people.map(p => {
      (if (p.rating == "A") 0.7 else if (p.rating == "B") 0.5 else 0.3,
      Vectors.dense(
        p.income / maxIncome,
        p.age.toDouble / maxAge
      ))
    })
  }

  def prepareFeaturesWithlabels(features: RDD[(Double, Vector)]): RDD[LabeledPoint] = {
    features map(l => LabeledPoint(l._1, l._2))
  }
}
