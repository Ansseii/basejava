
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    private int count = 0;

    void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    void save(final Resume r) {
        storage[count++] = r;
    }

    Resume get(final String uuid) {

        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(final String uuid) {

        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) {
                System.arraycopy(storage, i + 1, storage, i, count - i - 1);
                count--;
                System.out.println(count);
            }
        }

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

