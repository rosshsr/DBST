package dbst

import akka.actor._
import bst.BST

import akka.actor.Actor
import akka.routing.Broadcast
import akka.routing.ActorRefRoutee
import akka.actor.Props
import akka.event.Logging
import akka.routing.RoundRobinRoutingLogic
import akka.routing.Router

import scala.util.Success
import akka.pattern
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

import scala.concurrent.Future
object DBSTMaster {

  case class Initialize(bst: BST)
  case class Insert(e: Int)
  case class Remove(e: Int)
  case class Search(e: Int)

  case class InitializeWorker(bst: BST)
  case class SearchResponse(b: Boolean)
}

class DBSTMaster extends Actor {

  import context.dispatcher

  var askTarget: ActorRef = null
  val actorCount = 1
  var responseCount = 0

  var router = {
    val routees = Vector.fill(actorCount) {
      val r = context.actorOf(Props[DBSTWorker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  import DBSTMaster._

  def receive = init

  def init: Receive = {
    case Initialize(bst) => context.become(ready); router.route(Broadcast(InitializeWorker(bst)), context.self);
  }

  def ready: Receive = {
    case Insert(e) => router.route(Insert(e), sender)
    case Remove(e) => router.route(Broadcast(Remove(e)), sender)
    case Search(e) => askTarget = sender; context.become(searching); router.route(Broadcast(Search(e)), context.self);
  }

  def searching: Receive = {
    case SearchResponse(true) =>
      context.become(ready)
      askTarget ! true
      responseCount = 0

    case SearchResponse(false) =>
      responseCount += 1
      if (responseCount == actorCount) {
        context.become(ready)
        askTarget ! false
        responseCount = 0
      }
  }

}