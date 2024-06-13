package game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameResultManager {
    //gameresult object
    //serialize to json somehow, read when we want
    //have to order them

    //WORKS ALL DONE, REFACTOR, TIDY UP

    //!!!!!!!!!!!!
    //DO we lose results on restart? //do we not read results on startup?
    //!!!!!!!!!!!!

    private static final Gson gson = new Gson();

    public static void resetResults(){
        bestResults = new ArrayList<>();
        //dont work
    }

    //might need to kiszervezni ordering and order after reading also
    private static List<GameResult> bestResults;
    private static boolean created = false;

    public static List<GameResult> getBestResults(){
        if (!created){
            return new ArrayList<>();
        }
        readJson();
        return bestResults;
    };


    public static void addResult(GameResult result){
        if (!created){
            bestResults = new ArrayList<>();
            created = true;
            if (!Files.exists(Path.of("highscores.json"))){
                System.out.println("file not exist");
                writeJson(); //create the file on the very first startup so we dont try to read a non-existing one
            }
            readJson(); // we need to read results on startup bc we would overwrite them
        }
        bestResults.add(result); //itt best null
        bestResults.sort(Comparator.comparingInt(GameResult::getSteps));
        //refactor?
        List<GameResult> topFew = new ArrayList<>();

        int i=0;
        while (i<10 && i <bestResults.size()){
            topFew.add(bestResults.get(i));
            i+=1;
        }

        bestResults = topFew;
        writeJson();
    };

    //write
    private static void writeJson(){
        String s = gson.toJson(bestResults);
        try (FileWriter fileWriter = new FileWriter("highscores.json", false)) {
            fileWriter.write(s);
            fileWriter.flush();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    };

    //read
    private static void readJson(){
        //read file, deserialize, set bestRes
        //IDK ABOUT THIS
        List<GameResult> stuff;
        try {
            String s = Files.readString(Path.of("highscores.json"));
            Type listType = new TypeToken<ArrayList<GameResult>>(){}.getType();
            stuff = gson.fromJson(s, listType);
            bestResults = stuff;
        } catch (IOException e) {
            //gets called before creating the file on first run, dont throw just smth
            bestResults = new ArrayList<>();
        }
    };


}
