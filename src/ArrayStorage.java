
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    private int count = 0;

    void clear() {
        Arrays.fill(storage, 0, count - 1, null);
        count = 0;
    }

    void save(final Resume r) {
        storage[count++] = r;
    }

    Resume get(final String uuid) {

        for (int i = 0; i < count - 1; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            } else {
                break;
            }
        }
        return null;
    }

    void delete(final String uuid) {

        int i;
        for (i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) break;
        }
        System.arraycopy(storage, i + 1, storage, i, count - 1);
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

