import java.util.*;

class Item {
    int value;
    int weight;

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

class KnapsackNode {
    int level;
    int value;
    int weight;
    double bound;

    public KnapsackNode(int level, int value, int weight, double bound) {
        this.level = level;
        this.value = value;
        this.weight = weight;
        this.bound = bound;
    }
}

public class Ass4 {
    public static int solve(int maxWeight, Item[] items) {
        Arrays.sort(items, (a, b) -> Double.compare(b.value * 1.0 / b.weight,
                a.value * 1.0 / a.weight));
        Queue<KnapsackNode> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> -node.bound));
        int n = items.length;
        int bestValue = 0;

        KnapsackNode root = new KnapsackNode(-1, 0, 0, 0);
        queue.offer(root);
        while (!queue.isEmpty()) {
            KnapsackNode node = queue.poll();
            int level = node.level + 1;
            int value = node.value;
            int weight = node.weight;
            if (level < n) {
                Item item = items[level];
                // Include the item
                if (weight + item.weight <= maxWeight) {
                    int newValue = value + item.value;
                    int newWeight = weight + item.weight;
                    double bound = getBound(newValue, newWeight, level, items,

                            maxWeight);

                    if (bound > bestValue) {
                        bestValue = newValue;
                    }
                    queue.offer(new KnapsackNode(level, newValue, newWeight,

                            bound));

                }
                // Exclude the item
                double bound = getBound(value, weight, level, items,

                        maxWeight);

                if (bound > bestValue) {
                    queue.offer(new KnapsackNode(level, value, weight,

                            bound));

                }
            }
        }
        return bestValue;
    }

    private static double getBound(int value, int weight, int level, Item[] items, int maxWeight) {
        double bound = value;
        int remainingWeight = maxWeight - weight;
        while (level < items.length && remainingWeight >= items[level].weight) {

            bound += items[level].value;
            remainingWeight -= items[level].weight;

            level++;
        }
        if (level < items.length) {
            bound += items[level].value * (remainingWeight * 1.0 /

                    items[level].weight);
        }
        return bound;
    }

    public static void main(String[] args) {
        Item[] items = {
                new Item(60, 10),
                new Item(100, 20),
                new Item(120, 30)
        };
        int maxWeight = 50;
        int bestValue = solve(maxWeight, items);
        System.out.println("Best value: " + bestValue);
    }
}