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

    public int getSize() {
        return data.size();
    }

    public T getValue(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        }
        return null; // or throw an exception if an invalid index is accessed
    }
}
