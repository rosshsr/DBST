package bst

object BST {
  def apply() : BST = new EmptyBST()
}

abstract sealed class BST {
  def insert(e: Int) : BST
  def remove(e: Int) : BST
  def search(e: Int) : Boolean
  def min : Int
}

case class EmptyBST() extends BST {
  def insert(e: Int) : BST = NonEmptyBST(EmptyBST(), EmptyBST(), e)
  def remove(e: Int) : BST = this
  def search(e: Int) : Boolean = false
  def min : Nothing = throw new Exception()
}

case class NonEmptyBST(left: BST, right: BST, value: Int) extends BST {

  def min: Int = this.left match {
    case EmptyBST()           => this.value
    case NonEmptyBST(l, _, _) => l.min
  }
  
  def insert(e: Int) : BST = e match {
    case this.value => this 
    case `e` if e > value => NonEmptyBST(left, right.insert(e), value) 
    case _ => NonEmptyBST(left.insert(e), right, value)
  }
  
  def remove(target: Int) : BST = target match {
    case this.value => this match {
      case NonEmptyBST(EmptyBST(), EmptyBST(), _) => EmptyBST()
      case NonEmptyBST(EmptyBST(), r, _) => r
      case NonEmptyBST(l, EmptyBST(), _) => l
      case NonEmptyBST(l, r, _) => val min = r.min
                NonEmptyBST(l, r.remove(min), min)
    }
    case _ if target > this.value => NonEmptyBST(left, right.remove(target), value)
    case _ => NonEmptyBST(left.remove(target), right, value)
  }
  
  def search(target: Int) : Boolean = target match {
    case this.value => true
    case _ if this.value > target => left.search(target) 
    case _ => right.search(target)
  }
}