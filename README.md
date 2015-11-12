# DBST
This is the code for a Distributed Binary Search Tree that uses Akka to distribute the data storage among different actors for quicker lookup.

This is the first upload.

#Next Steps:

- Improve the implementation of the BST itself.
- Make it a typeclass so it can support more than just Int as the data element type.
- Fully seperate the distributed piece so that you can implement any data structure that supports Insert, Search, Remove as Distributed
