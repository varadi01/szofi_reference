package game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.tinylog.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameResultManager {

    private static final Gson gson = new Gson();

    private static List<GameResult> bestResults;
    private static boolean created = false; //as far as I'm concerned there isn't a way to initialize bestResults in this case, so we need this bool

    public static List<GameResult> getBestResults(){
        if (!created){
            Logger.info("bestResults was not initialized yet");
        }
        readJson();
        return bestResults;
    }

    public static void resetResults(){
        bestResults = new ArrayList<>();
    }

    public static void addResult(GameResult result){
        if (!created){
            bestResults = new ArrayList<>();
            created = true;
            if (!Files.exists(Path.of("highscores.json"))){
                writeJson(); //create the file on the very first startup so we dont try to read a non-existing one
            }
            readJson();
        }
        bestResults.add(result);

        Logger.info("added new result: {}", result);

        sortBestResults();

        List<GameResult> topFew = new ArrayList<>();
        int i=0;
        while (i<10 && i <bestResults.size()){
            topFew.add(bestResults.get(i));
            i+=1;
        }

        bestResults = topFew;
        writeJson();
    }

    private static void sortBestResults() {
        if (bestResults != null){
            bestResults.sort(Comparator.comparingInt(GameResult::getSteps));
        }
    }

    private static void writeJson(){
        String s = gson.toJson(bestResults);
        try (FileWriter fileWriter = new FileWriter("highscores.json", false)) {
            fileWriter.write(s);
            fileWriter.flush();
        } catch (IOException e) {
            Logger.error("An error occured when attempting to write to highscores.json");
            throw new RuntimeException(e);
        }
    }

    private static void readJson(){
        List<GameResult> results;
        try {
            String s = Files.readString(Path.of("highscores.json"));
            if (s.isEmpty()){
                bestResults = new ArrayList<>();
                return;
            }

            Type listType = new TypeToken<ArrayList<GameResult>>(){}.getType();
            results = gson.fromJson(s, listType);
            bestResults = results;
            sortBestResults();
        } catch (IOException e) {
            //gets called before creating the file on first run
            Logger.info("highscores.json does not exist, bestResults initialized empty");
            bestResults = new ArrayList<>();
        }
    }


}
