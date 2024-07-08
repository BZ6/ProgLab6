package common.file;

import static common.io.OutputManager.*;
import common.exceptions.*;

import java.io.*;

import java.util.Scanner;

/**
 * Operates the main collection file for saving/loading.
 */
public class FileManager implements ReaderWriter{
    private static final int START_OF_BUFFER = 0;
    private String path;
    /**
     * Constructor, just save variable for all class.
     * @param pth Path to collection file.
     */
    public FileManager(String pth){
        path = pth;
    }
    public void setPath(String pth){
        if (!pth.equals("default"))
            path = pth;
    }

    public FileManager(){
        path = null;
    }
    public String read() throws FileException {
        String text = "";
        try {
            if (path == null) throw new EmptyPathException();
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            if (!file.exists()) throw new FileNotExistsException();
            if (!file.canRead()) throw new FileWrongPermissionsException("cannot read file");
            while (scanner.hasNextLine())
                text += scanner.nextLine() + "\n";
            scanner.close();
        } catch (IOException e) {
            throw  new FileException("cannot read file");
        }
        return text;
    }

    private void create(File file) throws CannotCreateFileException{
        try{
            file.createNewFile();
        } catch(IOException e){
            throw new CannotCreateFileException();
        }
    }
    public boolean write(String text){
        boolean res = true;
        try{
            if (path == null || path.equals("")) throw new EmptyPathException();
            File file = new File(path);
            if(!file.exists()) {
                print("src/file " + path +" does not exist, trying to create new file");
                create(file);
            };
            if(!file.canWrite()) throw new FileWrongPermissionsException("cannot write file");

            FileOutputStream out = new FileOutputStream(path);
            BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
            byte[] buffer = text.getBytes();
            bufferedOut.write(buffer, START_OF_BUFFER, buffer.length);
            bufferedOut.close();
            out.close();
        } catch(FileException e){
            printErr(e.getMessage());
            res = false;
        } catch (IOException e) {
            res = false;
            printErr("cannot access file");
        }
        return res;
    }
}
