import java.util.Scanner;

public class Ass3 {
    static class Item {
        int value;
        int weight;
        float density;

        public Item(int value, int weight, float density) {
            this.value = value;
            this.weight = weight;
            this.density = density;
        }
    }

    static class Knapsack {
        Item[] items;
        int W;

        public Knapsack(Item[] items, int W) {
            this.items = items;
            this.W = W;
        }

        public float solve() {
            int i, j, pos;
            Item mx, temp;
            float totalValue = 0, totalWeight = 0;
            // calculating density of each item
            for (i = 0; i < items.length; i++) {
                items[i].density = (float) items[i].value / items[i].weight;
            }
            // sorting w.r.t to density by using compare function
            for (i = 0; i < items.length; i++) {
                mx = items[i];
                pos = i;
                for (j = i; j < items.length; j++) {
                    if (items[j].density > mx.density) {
                        mx = items[j];
                        pos = j;
                    }
                }
                temp = items[i];
                items[i] = mx;
                items[pos] = temp;
            }
            for (i = 0; i < items.length; i++) {
                if (totalWeight + items[i].weight <= W) {
                    totalValue += items[i].value;
                    totalWeight += items[i].weight;
                } else {
                    float remainingWeight = W - totalWeight;
                    totalValue += remainingWeight * items[i].density;
                    totalWeight += remainingWeight;
                    break;
                }
            }
            System.out.println("Total weight in bag: " + totalWeight);
            return totalValue;
        }
    }

    static Scanner in = new Scanner(System.in);

    public static void main(String args[]) {
        int W;
        System.out.print("Enter total no of items: ");
        int n = in.nextInt();
        Item[] items = new Item[n]; // Fix the size here
        System.out.println("Enter data");
        System.out.println("Enter total " + n + " item's values and weight");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter " + (i + 1) + " Value: ");
            int value = in.nextInt();
            System.out.print("Enter " + (i + 1) + " Weight: ");
            int weight = in.nextInt();
            items[i] = new Item(value, weight, 0);
        }
        System.out.println("Entered data");
        display(items);
        System.out.print("Enter Knapsack Weight: ");
        W = in.nextInt();
        Knapsack ks = new Knapsack(items, W);
        float mxVal = ks.solve();
        System.out.println("---Max value for " + W + " weight is " + mxVal);
    }

    static void display(Item items[]) {
        System.out.print("Values: ");
        for (int i = 0; i < items.length; i++) {
            System.out.print(items[i].value + "\t");
        }
        System.out.println();
        System.out.print("Weights: ");
        for (int i = 0; i < items.length; i++) {
            System.out.print(items[i].weight + "\t");
        }
        System.out.println();
    }
}
