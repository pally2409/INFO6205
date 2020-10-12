/*
 * Copyright (c) 2017. Phasmid Software
 */
package edu.neu.coe.info6205.union_find;

/**
 * Weighted Quick Union with Path Compression
 */
public class WeightedQuickUnion {
    private final int[] parent;   // parent[i] = parent of i
    private final int[] size;   // size[i] = size of subtree rooted at i
    private int count;  // number of components
    private boolean pathCompression;
    private int typePathCompression;
    

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WeightedQuickUnion(int n, boolean pathCompression) {
        count = n;
        parent = new int[n];
        size = new int[n];
        this.pathCompression = pathCompression;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }
    
    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WeightedQuickUnion(int n, boolean pathCompression, int typePathCompression) {
        count = n;
        parent = new int[n];
        size = new int[n];
        this.pathCompression = pathCompression;
        this.typePathCompression = typePathCompression;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], size[i]);
        }
    }
    
    public int getMaximumDepth() {
        if(this.count() > 1){
            return -1;
        }
        
        int maxDepth = Integer.MIN_VALUE;
        int currentDepth = 0;
        
        for(int i = 0; i < parent.length; i++) {
        	currentDepth = 0;
        	int j = i;
            while (j!= parent[j]) {
                j = parent[j];
                currentDepth++;
            }
            maxDepth = Math.max(maxDepth, currentDepth);
        }
        return maxDepth;
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        if(pathCompression) {
        	if(typePathCompression == 1) {
        		while (root != parent[root]) {
                    root = parent[root];
                }
        		while (p != root) {
                    int newp = parent[p];
                    parent[p] = root;
                    p = newp;
                }
        	}
        	
        	else {
        		while (root != parent[root]) {
        			parent[root] = parent[parent[root]];
                    root = parent[root];
                } 	
            }
        }
        
        else {	
        	while (root != parent[root]) {
                root = parent[root];
            } 	
        	
        }
        return root;
    }
  
    
    

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

}
