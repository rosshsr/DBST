package dbst

import akka.actor.{ActorSystem, ActorRef, Props}
import akka.actor.PoisonPill
import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}
import scala.concurrent.Await

import bst.BST

import scala.concurrent.{Future, ExecutionContext}

class DBST {
  
  import DBSTMaster._
  
  val system = ActorSystem("DBST")
  import system.dispatcher
  implicit val timeout = Timeout(5 seconds)
  val dbstMaster = system.actorOf(Props(classOf[DBSTMaster],3), "DBSTMaster")
  
  dbstMaster ! Initialize(BST())
  
  def insert(e: Int) = dbstMaster ! Insert(e)
  
  def remove(e: Int) = dbstMaster ! Remove(e)
  
  def search(e: Int) : Boolean = {

    val fut = dbstMaster ? Search(e)
    var res : Boolean = false

    res = Await.result(fut, timeout.duration).asInstanceOf[Boolean]
    
    res
  
  }
  
  def close = system.terminate
  
}