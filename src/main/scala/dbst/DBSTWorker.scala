package dbst

import akka.actor._
import akka.pattern.pipe

import bst.BST

class DBSTWorker extends Actor {
  
  import DBSTMaster._
  
  var bst : BST = null
  
  def receive = init
  
  def init : Receive = {
    case InitializeWorker(bst : BST) => this.bst = bst; context.become(active)
    case _ => println("WTF MSG IS THIS SHIT DAWG?") //throw error of some kind 
  }
  
  def active : Receive = {
    case Insert(e) => println("inserting"); bst = bst.insert(e);
    case Remove(e) => println("removing"); bst = bst.remove(e)
    case Search(e) => println("searching"); sender ! SearchResponse(bst.search(e)) 
  }
  
}