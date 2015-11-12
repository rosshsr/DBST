import dbst.DBST
import bst.BST

object Test extends App {
  
  var dbst = new DBST()
  
  for ( i <- (1 to 100)) {
    dbst.insert(i)
  }

  
  dbst.insert(2)
  dbst.insert(3)
  dbst.remove(9)
  
  
  
  println(dbst.search(5))
  println(dbst.search(9))
  println(dbst.search(101))
  println(dbst.search(3423))
  
  dbst.close
}