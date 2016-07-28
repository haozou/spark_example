package com.alpine.scalapractice

/**
  * Created by Hao on 7/14/16.
  */
object SyncExample {
  def main(args: Array[String]) : Unit = {
    val g = new ThreadGroup("WorkerBees")
    val t1 = new Thread(g, new Worker())
    val t2 = new Thread(g, new Worker())
    t1.start
    t2.start
    t1.join
    t2.join
  }
}

object Lock {

}

object Print {
  def print(id: Long) : Unit = {
    Lock.synchronized {
      println(s"nima $id")
      println(s"woma $id")
    }
  }
}


class Worker extends Runnable {
  override def run(): Unit = {
    val threadId = Thread.currentThread().getId()
    Print.print(threadId)
  }
}
