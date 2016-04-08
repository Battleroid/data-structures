# 22

* average case analysis: an average-case analysis attempts to determine the average amount of time among all the possible input of the same size
* backtracking approach: backtracking approach searches for a candidate solution incrementally, abandoning the option as soon as it determines that the candidate is not a valid solution, then looks for a new candidate
* best-case input: input that results in the shortest execution time is called the best-case input
* big O notation: comparing algos by examining their growth rates. This notation allows you to ignore constants and smaller terms while focusing on the dominating terms
* brute force: refers to algo approach where you try to solve the problem in simplest way or trying all possible combinations
* constant time: big O notation estimates the execution time of an algorithm in relation to the input size. If the time is not related to the input size, the algo is said to take constant time with the notation O(1)
* convex hull: given a set of points, a convex hull is the smallest convex polygon that encompasses all points
* divide & conquer approach: divides problem into subproblems, solve the subproblems, then combines the solutions of the subproblems to obtain the solution for the entire problem
* dynamic programming approach: process of solving subproblems, then combining the solutions of the subproblems to obtain an overall solution. Naturally leads to recursive solution, however it's inefficient to use recursion. Key idea is to solve each subproblem only once and store the results for later use to avoid redundant operations

# 23

* Heaps are a useful data structure for designing efficient algos such as sorting
* Bucket and Radix sorts are specialized for integer keys, they sort keys using buckets not comparing the keys directly, this is more efficient than general sorting algos

# 24

* You can implement a priority queue using a heap

# 25

* binary search tree: BST with no duplicate elements has the property that for every node in the tree, the value of any node in its left subtree is less than the value of the node and the value of any node in its right subtree is greater than the value of the node (let <, right >)
* greedy algo: often used in solving optimization problems. The algo makes the choice that is optimal locally in the hope that this choice will lead to a globally optimal solution
* Huffman encoding: compresses data by using fewer bits to encode characters that occur more frequently. The codes for the characters are constructed based on the occurrence of the characters in the text using a binary tree, called the Huffman coding tree
* inorder traversal: left subtree of node is visited, then the current node, and finally the right node. This shows the nodes in an increasing order
* postorder traversal: left subtree of current node is visited, then the right subtree, and finally the current node
* preorder traversal: current node is visited first, then left subtree of the current node, and finally the right subtree, this is literally DFS

# 26

* AVL tree: well balanced binary tree. Difference between the heights of two subtrees for every node is 0 or 1
* balance factor: of a node is the height of its right subtree minus the height of its left subtree
* left heavy: node is considered left heavy if the balance factor is -1 or less
* LL rotation: LL imbalance occurs at a node _a_, such that _a_ has a balance factor of -2 and a left child _b_ with a balance factor of -1 or 0. This type of imbalance can be fixed by performing a single right rotation at _a_.
* LR rotation: LR imbalance occurs at a node _a_, such that _a_ has a balance factor of -2 and a left child _b_ with a balance factor of +1. Assume _b's_ right child is c. This type of imbalance can be fixed by performing a double rotation (first a single left rotation at _b_ and then a single right rotation at _a_)
* right heavy: node is considered right heavy if the balance factor is +1 or greater
* RL rotation: an RL imbalance occurs at a node _a_, such that _a_ has a balance factor of +2 and a right child _b_ with a balance factor of -1. Assume _b's_ left child is _c_. This type of imbalance can be fixed by performing a double rotation (first a single right rotation at _b_ and then a single left rotation at _a_)
* RR rotation: an RR imbalance occurs at a node _a_, such that _a_ has a balance factor of +2 and a right child _b_ with a balance factor of 0 to +1. This type of imbalance can be fixed by performing a single left rotation at _a_.
* well balanced tree: tree said to be well balanced if the height of the subtrees is about the same

# 27

* associative array: an alternative term for map
* cluster: linear probing tends to cause groups of consecutive cells in the hash table to be occupied. Each group is called a _cluster_
* dictionary: same thing as map
* double hashing: uses a secondary hash function _h'(key)_ on the keys to determine the increments to avoid the clustering problem
* hash code: hash code is an integer value derived from the object
* hash function: function that maps a key to an index in the hash table
* hash map: map that is implemented using a hash table
* hash set: set that is implemented using a hash table
* hash table: essentially an array that stores the elements whose keys are mapped to integers as the indexes of the array
* linear probing: technique for handling collision. When a collision occurs during the insertion of an entry to a hash table, linear probing finds the next available location sequentially
* load factor: measures how full the hash table is. It is the ratio of the number of elements to the size of the hash table. That is λ = n/N, _n_ being number of elements and _N_ the number of locations available (size of array)
* open addressing: process of finding an open location in the hash table in the event of a collision
* perfect hash function: function that maps each search key to a different index in the hash table
* polynomial hash code: polynomial function used for obtaining a hash code
* quadratic probing: can avoid the clustering problem that can occur in linear probing. Linear probing looks at the consecutive cells beginning at index _k_. Quadratic probing one the other hand looks at the cells at indicies _k_ + a quadratic number
* rehashing: load factor measures how full a hash table is. If the load factor is exceeded the hash table size is increased and the entries are reloaded into a larger hash table
* secondary clustering: one of the failure modes of open addressing based hash tables. It occurs after a hash collision of two records to the same position and causes one of the records to be moved to the next location in its probing sequence. Causes searches for keys within the cluster to be longer
* separate chaining: hashing scheme that places all entries with the same hash index into the same location, rather than finding new locations. Each location in the separate chaining scheme is called a bucket. Bucket is a container that holds multiple entries

# misc

* Big O Notation: http://bigocheatsheet.com/
* Java Data Structures notations:
  * https://simplenotions.wordpress.com/2009/05/13/java-standard-data-structures-big-o-notation/
  * http://algs4.cs.princeton.edu/cheatsheet/
* Algorithms in general: https://xlinux.nist.gov/dads/
* CS in general: https://gist.github.com/TSiege/cbb0507082bb18ff7e4b
* AVL Tree
  * https://en.wikipedia.org/wiki/AVL_tree
  * https://www.cs.usfca.edu/~galles/visualization/AVLtree.html
