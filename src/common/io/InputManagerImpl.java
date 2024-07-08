package common.io;

import common.connection.CommandMsg;
import common.data.Coordinates;
import common.data.Difficulty;
import common.data.Discipline;
import common.data.LabWork;
import common.exceptions.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * basic implementation of InputManager
 */
public abstract class InputManagerImpl implements InputManager{
    private static final int TWICE = 2;
    private static final int THE_FIRST_PART = 0;
    private static final int THE_SECOND_PART = 1;
    private Scanner scanner;

    public InputManagerImpl(Scanner sc){
        scanner = sc;
    }

    public Scanner getScanner(){
        return scanner;
    }

    public void setScanner(Scanner sc){
        scanner = sc;
    }
    public String readName() throws EmptyStringException, InvalidInputCharacterException {
        String s = scannerNextLine();
        if (s.equals("")){
            throw new EmptyStringException();
        }
        return s;
    }

    public double readXCoord() throws InvalidNumberException, InvalidInputCharacterException {
        double x;
        try{
            x = Double.parseDouble(scannerNextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return x;
    }

    public Integer readYCoord() throws InvalidNumberException, InvalidInputCharacterException {
        Integer y;
        try{
            y = Integer.parseInt(scannerNextLine());
            if (y <= -545) throw new InvalidNumberException();
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return y;
    }
    public Coordinates readCoords() throws InvalidNumberException, InvalidInputCharacterException {
        double x = readXCoord();
        Integer y = readYCoord();
        Coordinates coord = new Coordinates(x,y);
        return coord;
    }

    public Integer readMinimalPoint() throws InvalidNumberException, InvalidInputCharacterException {
        Integer minimalPoint;
        try{
            minimalPoint = Integer.parseInt(scannerNextLine());
            if (minimalPoint <= 0) throw new InvalidNumberException();
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return minimalPoint;
    }

    /**
     * reads personal qualities minimum from input
     * @return
     * @throws InvalidNumberException
     */
    public int readPersonalQualitiesMinimum() throws InvalidNumberException, InvalidInputCharacterException {
        int personalQualitiesMinimum;
        try{
            personalQualitiesMinimum = Integer.parseInt(scannerNextLine());
            if (personalQualitiesMinimum <= 0) throw new InvalidNumberException();
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return personalQualitiesMinimum;
    }

    /**
     * reads average point from input
     * @return
     * @throws InvalidNumberException
     */
    public Double readAveragePoint() throws InvalidNumberException, InvalidInputCharacterException {
        Double averagePoint;
        try{
            averagePoint = Double.parseDouble(scannerNextLine());
            if (averagePoint != null && (averagePoint <= 0)) throw new InvalidNumberException();
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return averagePoint;
    }

    public Difficulty readDifficulty() throws InvalidEnumException, InvalidInputCharacterException {
        String s;
        try{
            s = scannerNextLine();
            switch (s) {
                case "1":
                    return Difficulty.values()[0];
                case "2":
                    return Difficulty.values()[1];
                case "3":
                    return Difficulty.values()[2];
                case "4":
                    return Difficulty.values()[3];
                default:
                    return Difficulty.valueOf(s);
            }
        } catch(IllegalArgumentException e){
            throw new InvalidEnumException();
        }
    }

    public Integer readLectureHours() throws InvalidNumberException, InvalidInputCharacterException {
        Integer lectureHours;
        try{
            lectureHours = Integer.parseInt(scannerNextLine());
        }
        catch(NumberFormatException e){
            throw new InvalidNumberException();
        }
        return lectureHours;
    }

    public Discipline readDiscipline() throws InvalidDataException{
        String name = readName();
        Integer lectureHours = readLectureHours();
        return new Discipline(name, lectureHours);
    }

    public LabWork readLabWork() throws InvalidDataException{
        LabWork labWork = null;

        String name = readName();
        Coordinates coords = readCoords();
        Integer minimalPoint = readMinimalPoint();
        int personalQualitiesMinimum = readPersonalQualitiesMinimum();
        Double averagePoint = readAveragePoint();
        Difficulty difficulty = readDifficulty();
        Discipline discipline = readDiscipline();
        labWork = new LabWork(name, coords, minimalPoint, personalQualitiesMinimum, averagePoint, difficulty, discipline);

        return labWork;
    }

    public CommandMsg readCommand() throws InvalidInputCharacterException {
        String cmd = scannerNextLine();
        String arg = null;
        LabWork labWork = null;
        if (cmd.contains(" ")){ //if command has argument
            String commandLine [] = cmd.split(" ",TWICE);
            cmd = commandLine[THE_FIRST_PART];
            arg = commandLine[THE_SECOND_PART];
        }
        if(cmd.equals("add") || cmd.equals("add_if_max")){
            try{
                labWork = readLabWork();
            } catch(InvalidDataException e){
            }
        }
        return new CommandMsg(cmd, arg, labWork);
    }

    protected String scannerNextLine() throws InvalidInputCharacterException {
        String value = "";
        try {
            value = scanner.nextLine().replace(",", ".").trim();
        }
        catch (NoSuchElementException e){
            throw new InvalidInputCharacterException();
        }
        return value;
    }
}
