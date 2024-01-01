package cn.zurish.snow.core.spi;

/**
 * 2023/12/31 22:00
 */
import java.io.*;

public class MyDefindClassLoader extends ClassLoader {

    private final ClassLoader parent=this.getClass().getClassLoader();

    private String classPath;


    public MyDefindClassLoader(String path){
        this.classPath=path;

    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);


                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            System.out.println("mydefineClassLoader loading class " + name);
            String realpath = this.classPath + "/" + name.replace(".", "/") + ".class";
            File f = new File(realpath);
            FileInputStream inputStream = new FileInputStream(f);
            byte[] b = new byte[(int) f.length()];
            inputStream.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }

    }


}