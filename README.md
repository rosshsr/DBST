# DBST
This is the code for a Distributed Binary Search Tree that uses Akka to distribute the data storage among different actors for quicker lookup.

For me, this is an exercise is becoming more comfortable with this tech

This is the first upload.

#How To Use:

Methods:

insert(elem: Int)

remove(elem: Int)

search(elem: Int) : Boolean

close() (This shuts down the actor system in DBST)

You can just use DBST like any other object. (val dbst = new DBST())

There is a sample of me using it in the Test class.

#Next Steps:

- Improve the implementation of the BST itself.
- Make it a typeclass so it can support more than just Int as the data element type.
- Fully seperate the distributed piece so that you can implement any data structure that supports Insert, Search, Remove as Distributed
