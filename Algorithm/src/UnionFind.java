
public class UnionFind {
	
	private int count; // tree count
	private int[] id;	// parent
	private int[] size;	// tree size

	public UnionFind(int N) {
		count = N;
		id = new int[count];
		size = new int[count];
		for (int i = 0; i < count; i ++) {
			id[i] = i;
			size[i] = 1;
		}
	}
	
	public void union(int p, int q) {
		//System.out.println("union: " + p + "," + q);
		int pid = find(p);
		int qid = find(q);
		if (pid != qid) {
			id[pid] = qid;
			count --;
			size[qid] += size[pid];
		}
	}
	
	public int find(int p) {
		while (p != id[p]) {
			if (id[p] != id[id[p]]) {
				size[id[p]] -= size[p];
			}
			id[p] = id[id[p]];
			p = id[p];
		}
		return p;
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public int count() {
		return count;
	}
	
	public int connectedCount(int p) {
		return size[find(p)];
	}
	
	public static void main(String[] args) {
		UnionFind uf = new UnionFind(10);
		uf.union(0, 1);
		uf.union(2, 3);
		uf.union(2, 8);
		uf.union(4, 7);
		uf.union(2, 7);
		/*for (int i = 0; i < 10; i ++) {
			System.out.println(uf.id[i]);
		}*/
		for (int i = 0; i < 10; i ++) {
			System.out.println(uf.size[i]);
		}
	}
	
}
