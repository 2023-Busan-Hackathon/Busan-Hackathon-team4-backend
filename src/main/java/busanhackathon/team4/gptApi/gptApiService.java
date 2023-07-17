package busanhackathon.team4.gptApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class gptApiService {

    public String callApi(String query) {
        try {
            log.info("query: " + query);
            // 파이썬 실행 명령과 파일 경로
            String command = "python";
            String filePath = "src/main/java/busanhackathon/team4/gptApi/gptApi.py";


            ProcessBuilder processBuilder = new ProcessBuilder(command, filePath, query);
            Process process = processBuilder.start();

            // 실행 결과 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));

            // 오류 읽기
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));


            String result = "";
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // 실행 결과 출력
                result += line + "\n";
            }
            while ((line = errorReader.readLine()) != null) {
                log.info("예외터짐");
                System.out.println(line);
                result += line + "\n";
            }


            // Extracting the recommended food name
            String food = "";
            String[] lines = result.split("\n");
            for (String l : lines) {
                if (l.contains("추천")) {
                    food = l;
                    break;
                }
            }

            // Extracting the 재료
            StringBuilder ingredientBuilder = new StringBuilder();
            boolean startCollectingRecipe1 = false;
            for (String l : lines) {
                if (startCollectingRecipe1) {
                    if (l.trim().isEmpty()) {
                        break;
                    }
                    ingredientBuilder.append(l).append("\n");
                }
                if (l.startsWith("재료:")) {
                    startCollectingRecipe1 = true;
                }
            }
            String ingredient = ingredientBuilder.toString().trim();

            // Extracting the recipe
            StringBuilder recipeBuilder = new StringBuilder();
            boolean startCollectingRecipe2 = false;
            for (String l : lines) {
                if (startCollectingRecipe2) {
                    if (startCollectingRecipe1) {
                        if (l.trim().isEmpty()) {
                            break;
                        }
                        recipeBuilder.append(l).append("\n");
                    }
                    if (l.startsWith("레시피:")) {
                        startCollectingRecipe2 = true;
                    }
                }
            }
            String recipe = recipeBuilder.toString().trim();

            // Printing the extracted information
            System.out.println("요리할 음식: " + food);
            System.out.println("필요한 재료: " + ingredient);
            System.out.println("레시피: " + recipe);

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}