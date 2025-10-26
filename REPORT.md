# Analytical Report: Optimization of a City Transportation Network (Assignment 3)

**Author:** Serik ALikhan
**Date:** October 26, 2025

## 1. Summary of Input Data and Algorithm Results

### 1.1. Input Data

For the analysis and testing of Prim's and Kruskal's algorithms, the dataset `all_graphs.json` was used, containing 28 graphs of varying sizes and densities. The graphs model a city's transportation network where:

* **Vertices (`nodes`)**: Represent city districts (denoted by strings, e.g., "1", "2", ...).
* **Edges (`edges`)**: Represent potential roads between districts. Each edge has `from`, `to`, and `weight` attributes.
* **Edge Weight (`weight`)**: Represents the construction cost of the road.

The graphs varied in size:

* **Small**: E.g., `graph_id: 1` (V=5, E=10).
* **Medium**: E.g., `graph_id: 10` (V=55, E=110).
* **Large**: E.g., `graph_id: 19` (V=838, E=1005).
* **Very Large**: E.g., `graph_id: 28` (V=1922, E=1921).

Most of the provided graphs are **sparse** (E ≈ V or E slightly larger than V, like E=2V or E=1.2V in many cases), which is common in real-world networks. Graph `graph_id: 2` (V=10, E=25, E=2.5V) is an example of a denser graph within the dataset.

### 1.2. Algorithm Results

Both Prim's and Kruskal's algorithms were implemented in Java using an object-oriented approach (see Bonus Section) and tested on all graphs from `all_graphs.json`. The execution results, including the Minimum Spanning Tree (MST) structure, total cost, operation count, and execution time, were recorded in `output.json`. A summary of the results can be found in the `results.csv` file.

**Key Observations:**

* **Correctness:** For every graph (`graph_id`), both algorithms yielded the **same** total cost (`total_cost`), confirming their correctness in finding the *minimum* spanning tree. The structure of the tree itself (the list of edges) might differ, but the minimum cost is guaranteed.
* **Performance:** Execution time (`execution_time_ms`) and operation count (`operations_count`) vary between the algorithms and depending on graph characteristics. A detailed analysis is provided in the next section.

---

## 2. Comparison between Prim’s and Kruskal’s Algorithms

### 2.1. Theoretical Analysis (Theory)

This section reviews the asymptotic complexity and theoretical foundations of the **optimized** implementations of both algorithms. Notation: **V** = number of vertices, **E** = number of edges.

#### 2.1.1. Kruskal's Algorithm

**Strategy:** A greedy approach based on edges. Sorts all edges by weight and adds an edge to the MST if it doesn't form a cycle.

**Data Structures and Complexity:**

1.  **Edge Sorting:** Using an efficient algorithm (like Timsort in `Collections.sort`), the complexity is **O(E log E)**.
2.  **Cycle Check (DSU):** The Disjoint Set Union data structure with *path compression* and *union by rank/size* performs E `find` operations and V-1 `union` operations in **O(E α(V))** amortized time, where α(V) is the inverse Ackermann function (practically a constant). (Source: *Cormen et al., "Introduction to Algorithms"*)

**Overall Kruskal Complexity:** **O(E log E)** (dominated by sorting).

#### 2.1.2. Prim's Algorithm

**Strategy:** A greedy approach based on vertices. "Grows" the MST from a starting vertex, adding the minimum-weight edge connecting a vertex in the MST to one outside it.

**Data Structures and Complexity:**
An optimized Prim's algorithm uses a **Priority Queue (Min-Heap)** to store edges crossing the cut between the MST and the remaining vertices.

1.  **Priority Queue (e.g., `PriorityQueue` in Java - binary heap):**
    * V `extract-min` operations: **O(V log V)**.
    * Up to E `add` / `decrease-key` operations: **O(E log V)**.

**Overall Prim Complexity:** **O(E log V)** (since E ≥ V-1 for a connected graph). (Source: *Sedgewick, Wayne, "Algorithms, 4th Edition"*)

---

### 2.2. Practical Analysis (In Practice)

This section compares the theoretical expectations with the actual performance metrics observed in `results.csv`.

#### 2.2.1. Theoretical Expectations vs. Observed Performance

* **Theory - Sparse Graphs (E ≈ V):** Asymptotically, both Kruskal (O(V log V)) and Prim (O(V log V)) are similar. However, Kruskal is often considered faster in practice due to the high efficiency of sorting and DSU operations compared to the overhead of priority queue operations.
    * **Hypothesis 1:** Kruskal should outperform Prim on sparse graphs.
* **Theory - Dense Graphs (E ≈ V²):** Asymptotically, both are O(V² log V). Prim has a slight theoretical edge because O(E log V) becomes O(V² log V), while Kruskal's O(E log E) becomes O(V² log(V²)) = O(V² * 2 log V).
    * **Hypothesis 2:** Prim might outperform Kruskal on dense graphs.

#### 2.2.2. Analysis of `results.csv` Data




* **Confirming Hypothesis 1 (Sparse Graphs):**
    * Many graphs in the dataset are sparse (e.g., ID 6-9, E=2V; ID 26-28, E ≈ V).
    * On these larger sparse graphs, **Kruskal consistently shows better execution time**. For instance:
        * `graph_id: 6` (V=161, E=322): Kruskal 0.63ms vs Prim 1.49ms.
        * `graph_id: 8` (V=182, E=364): Kruskal 0.57ms vs Prim 1.03ms.
        * `graph_id: 27` (V=1601, E=1600): Kruskal 1.57ms vs Prim 1.94ms.
    * This **supports the hypothesis** that Kruskal is generally faster for sparse graphs in practice.

* **Analyzing Hypothesis 2 (Dense Graphs):**
    * The dataset contains few dense graphs. `graph_id: 2` (V=10, E=25) is relatively dense (E=2.5V). `graph_id: 1` (V=5, E=10) is also dense (E=2V).
    * On `graph_id: 2`, Kruskal was **significantly faster** (0.05ms vs 0.12ms).
    * On `graph_id: 1`, Kruskal was also slightly faster (3.81ms vs 4.55ms).
    * These results **do not support Hypothesis 2** for these small graph sizes. The overhead and constant factors likely outweigh the asymptotic difference. Kruskal's optimized sorting and DSU appear highly effective even here.

* **Investigating Anomalies (Prim Faster on Sparse):**
    * Interestingly, for a range of sparse graphs (roughly IDs 10 through 25, where E = 1.2V to 2V), Prim often recorded a *faster* execution time than Kruskal. Examples:
        * `graph_id: 15` (V=82, E=164): Prim 0.20ms vs Kruskal 0.39ms.
        * `graph_id: 19` (V=838, E=1005): Prim 1.09ms vs Kruskal 1.27ms.
        * `graph_id: 25` (V=592, E=710): Prim 0.92ms vs Kruskal 0.72ms. (Correction: Kruskal faster here).
        * `graph_id: 24` (V=390, E=468): Prim 0.63ms vs Kruskal 0.48ms. (Correction: Kruskal faster here).
        * *Revisiting results carefully*: Checking IDs 10-15 again shows Kruskal being slower. ID 16-25 show mixed results, with Kruskal sometimes faster (21, 24, 25) and sometimes slower or very close (16, 17, 18, 19, 20, 22, 23).
    * **Conclusion on Anomalies:** While Kruskal generally excels on large sparse graphs, Prim can be faster on some medium-to-large sparse graphs. This might be due to:
        * **Graph Structure:** Specific edge weight distributions might favor the priority queue updates in Prim.
        * **Implementation Details:** Constant factors in the Java `PriorityQueue` vs `Collections.sort` + `DisjointSetUnion`.
        * **Measurement Variability:** Micro-benchmarking execution time can be sensitive.

#### 2.2.3. Operation Count Analysis (`operations_count`)



**Important Note:** As defined in my implementation:
* **Prim:** Counts main loop iterations, `PriorityQueue` operations (`add`, `poll`), and adjacency list iterations.
* **Kruskal:** Counts sorting comparisons (`compareTo`), `find()` calls, and `union()` calls.

**Analysis:**

* **Observation:** `results.csv` consistently shows **Kruskal having a significantly higher `operations_count`** than Prim, especially for larger graphs. (e.g., `graph_id: 28`: Prim 5763 vs Kruskal 26239).
* **Discrepancy:** This contrasts sharply with Kruskal often being faster in execution time.
* **Explanation:**
    * The high count for Kruskal likely stems from including all O(E log E) sorting comparisons.
    * The *cost per operation* differs significantly. DSU operations are near O(1) amortized, while PriorityQueue operations are O(log V).
    * Therefore, Kruskal performs *many fast* operations, while Prim performs *fewer, slightly slower* operations.
* **Conclusion on Operations:** The raw `operations_count`, as measured here, is **not a reliable predictor** of relative wall-clock performance between these two algorithms due to the differing nature and cost of the counted operations. Execution time remains the better practical metric.

---

## 3. Conclusions

1.  **Correctness:** Both algorithms correctly compute the MST with identical minimum costs across all datasets.
2.  **Performance (Theory vs. Practice):**
    * **Kruskal's Algorithm** aligns with the expectation of being **highly efficient for sparse graphs**, demonstrating superior execution time on the largest sparse datasets provided. Its practical performance seems driven by efficient sorting and DSU implementations.
    * **Prim's Algorithm**, while theoretically potentially better for dense graphs, did not show this advantage on the small dense graphs tested. However, it proved **competitive and sometimes faster than Kruskal on several medium-to-large sparse graphs**, indicating its practical viability is strong, possibly influenced by graph structure or implementation specifics.
    * The `operations_count` metric, as implemented, was misleading for performance comparison due to the different nature and cost of counted operations.
3.  **Recommendations:**
    * For **general use, especially on potentially large sparse graphs**, **Kruskal's Algorithm** is recommended due to its strong and consistent practical performance observed in tests.
    * **Prim's Algorithm** is a solid choice, particularly if the graph is given via adjacency lists or requires incremental building. It can be faster in certain scenarios, even on sparse graphs.

---

## 4. Bonus Section: Graph Design in Java

As part of the bonus task, `Graph.java` and `Edge.java` classes were implemented using object-oriented principles.

* **`Edge.java`**: Encapsulates an edge (`from`, `to`, `weight`). Implements `Comparable<Edge>` for sorting by weight, crucial for Kruskal's.



* **`Graph.java`**:
    * Stores the list of all edges (`allEdges`), vertex count (`V`), and edge count (`E`).
    * Uses a `Map<String, Integer> nodeToIndex` for **efficiently** mapping string node names to integer indices required by DSU and `PriorityQueue`.
    * Implements an **adjacency list** (`Map<Integer, List<Edge>> adj` and `List<List<Edge>> adjacencyList`) for **fast** access to a vertex's neighbors, important for Prim's.
    * The constructor takes node and edge lists (from JSON) and builds these internal structures.



**Advantages of the OOP Approach:**

1.  **Encapsulation:** The graph representation logic is hidden within the `Graph` class. Algorithms interact with a clean interface (`getV()`, `getAllEdges()`, `getAdj()`).
2.  **Readability:** Algorithm code is cleaner as it operates on `Graph` and `Edge` objects rather than raw matrices or index lists.
3.  **Extensibility:** Easy to add new methods to `Graph` or `Edge` (e.g., for other graph algorithms) without affecting the core MST code.
4.  **Efficiency:** Using a `Map` for `nodeToIndex` and adjacency lists ensures good performance for the operations needed by MST algorithms.

This OOP design was successfully used as input for the Prim and Kruskal implementations, contributing to more structured and maintainable code.