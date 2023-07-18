package busanhackathon.team4.gptApi;

import busanhackathon.team4.food.dto.FoodDto;
import busanhackathon.team4.food.service.FoodService;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GptApiService {

    private final FoodService foodService;
    public GptApiDto callApi(String username, GptApiFormDto gptApiFormDto) {
        try {
            List<FoodDto> foodDtoList = foodService.findAllFoodByMember(username);
            String exceptFood = foodDtoList.stream()
                    .map(FoodDto::getFoodName)
                    .collect(Collectors.joining(", "));

            String query = gptApiFormDto.getIngredient() + "\n" + exceptFood + "\n" + gptApiFormDto.getDifficulty();

            System.out.println("=====쿼리=====");
            System.out.println(query);
            System.out.println("=====쿼리=====");

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


            boolean startCollectingRecipe0 = false;
            for (String l : lines) {
                if (startCollectingRecipe0) {
                    food = l;
                    break;
                }
                if (l.contains("### 음식")) {
                    log.info("음식 추출");
                    startCollectingRecipe0 = true;
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
                if (l.contains("### 재료")) {
                    log.info("재료 추출");
                    startCollectingRecipe1 = true;
                }
            }
            String ingredient = ingredientBuilder.toString().trim();

            // Extracting the recipe
            StringBuilder recipeBuilder = new StringBuilder();
            boolean startCollectingRecipe2 = false;
            for (String l : lines) {
                if (startCollectingRecipe2) {
                    if (l.trim().isEmpty()) {
                        break;
                    }
                    recipeBuilder.append(l).append("\n");
                }
                if (l.contains("### 레시피")) {
                    log.info("레시피 추출");
                    startCollectingRecipe2 = true;
                }
            }
            String recipe = recipeBuilder.toString().trim();

            // Printing the extracted information
            System.out.println("요리할 음식: " + food);
            System.out.println("필요한 재료: " + ingredient);
            System.out.println("레시피: " + recipe);

            GptApiDto responseDto = GptApiDto.builder()
                    .gptResponse(result)
                    .food(food)
                    .ingredient(ingredient)
                    .recipe(recipe)
                    .build();

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
            return responseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}