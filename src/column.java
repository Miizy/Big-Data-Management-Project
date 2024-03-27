import java.util.ArrayList;
import java.util.Arrays;

class column<T> {
    private ArrayList<T> data;

    public column() {
        this.data = new ArrayList<>();
    }

    public void addValue(T value) {
        data.add(value);
    }

    public ArrayList getValues(ArrayList<Integer> indexes) {
        ArrayList ans = new ArrayList();
        for (int i=0; i<indexes.size(); i++) {
            ans.add(data.get(indexes.get(i)));

        }
        return ans;
    }

    public int[] getIndexesSorted(T value) {
        int[] ans = new int[2];
        ans[0] = (data.indexOf(value));
        ans[1] = (data.lastIndexOf(value));
        return ans;
    }

    public ArrayList getIndexesUnsorted(T value) {
        ArrayList ans = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == value) {
                ans.add(i);
            }
        }
        return ans;
    }
    public ArrayList getMonthsIndexes(String[] months, int[] indexes) {
        ArrayList ans = new ArrayList();
        for (int i = indexes[0]; i <= indexes[1]; i++) {
            if (Arrays.asList(months).contains(data.get(i))) {
                ans.add(i);
            }
        }
        return ans;
    }

    public int getSize() {
        return data.size();
    }

    public T getValue(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        }
        return null; // or throw an exception if an invalid index is accessed
    }
//    public ArrayList getIndexesSorted(T value) {
//        ArrayList ans = new ArrayList();
//        ans.add(data.indexOf(value));
//        ans.add(data.lastIndexOf(value));
//        return ans;
//    }
//    public ArrayList getIndexesUnsorted(T value, int[] indexes) {
//        ArrayList ans = new ArrayList();
//        for (int i = indexes[0]; i < indexes[1]; i++) {
//            if (data.get(i) == value) {
//                ans.add(i);
//            }
//        }
//        return ans;
//    }
//    public ArrayList getIndexesUnsorted(T value, ArrayList<Integer> indexes) {
//        ArrayList ans = new ArrayList();
//        for (int i = 0; i < indexes.size(); i++) {
//            if (value == data.get(indexes.get(i))) {
//                ans.add(indexes.get(i));
//            }
//        }
//        return ans;
//    }

}
