package DZ_7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class EncryptedClassLoader extends ClassLoader {
    /**
     * Ключ шифрования, используемый для расшифровки файлов классов.
     */
    private final String key;

    /**
     * Корневая директория, где находятся зашифрованные файлы классов.
     */
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    /**
     * Находит и загружает класс по его имени. Этот метод расшифровывает файл `.class`,
     * определяет класс и возвращает объект {@link Class}.
     *
     * @param name полное имя требуемого класса
     * @return объект {@link Class}, представляющий загруженный класс
     * @throws ClassNotFoundException если класс не может быть найден или загружен
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = name.replace('.', File.separatorChar) + ".class";
        File classFile = new File(dir, fileName);

        if (!classFile.exists()) {
            throw new ClassNotFoundException("Class file not found: " + classFile.getAbsolutePath());
        }

        try {
            byte[] encryptedBytes = Files.readAllBytes(classFile.toPath());

            byte[] decryptedBytes = decrypt(encryptedBytes);

            return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }

    /**
     * Расшифровывает массив байт с использованием переданного ключа шифрования.
     *
     * <p>Логика расшифровки сдвигает каждый байт на значение, вычисленное из хэша ключа.</p>
     *
     * @param encryptedBytes зашифрованный массив байт
     * @return расшифрованный массив байт
     */
    private byte[] decrypt(byte[] encryptedBytes) {
        byte[] decryptedBytes = new byte[encryptedBytes.length];
        byte keyByte = (byte) key.hashCode();

        for (int i = 0; i < encryptedBytes.length; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] - keyByte);
        }

        return decryptedBytes;
    }

    public static void main(String[] args) throws Exception {
        // Пример использования
        File rootDir = new File("path/to/classes");
        String encryptionKey = "mySecretKey";

        EncryptedClassLoader loader = new EncryptedClassLoader(encryptionKey, rootDir, ClassLoader.getSystemClassLoader());

        // Загрузка класса
        Class<?> loadedClass = loader.loadClass("com.example.MyClass");

        // Проверка работы
        System.out.println("Class loaded: " + loadedClass.getName());
    }
}

