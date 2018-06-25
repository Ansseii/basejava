
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    private int count = 0;

    void clear() {
        Arrays.fill(storage, null);
        count = 0;
    }

    void save(final Resume r) {
        storage[count++] = r;
    }

    Resume get(final String uuid) {

        for (Resume i : storage) {
            if (uuid.equals(i.uuid)) return i;
            else break;
        }
        return null;
    }

    void delete(final String uuid) {

        int i;
        for (i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) break;
        }
        for (int j = i; j < count - 1; j++) {
            storage[j] = storage[j + 1];
        }
        count--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    int size() {
        return count;
    }
}

